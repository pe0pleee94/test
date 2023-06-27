package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.Body;
import id.co.maybank.digitallending.base.response.dto.Data;
import id.co.maybank.digitallending.base.response.dto.Header;
import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.esbintegration.EsbIntegration;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.request.dto.EntryPointResponseDTO;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 This is class for implement logic business from Entry Point (Individual or Corporate)
 * @since 1.0 (Created June. 22, 2023)
 */
@Service
@Slf4j
public class EntryPointServiceImpl implements EntryPointService {

	@Autowired
	EsbIntegration esbIntegration;

	@Value("${esb.existing-customer-individual-endpoint.uri}")
	private String existingIndividualEsbUri;

	@Value("${esb.customer-information-endpoint.uri}")
	private String existingAccountServicesEsbUri;

	/**
	 * This method <p>checkExistingCustomer</p> will be checking customer is NTB, ETB or ETB lending to ESB Services
	 *
	 * @param {@link EntryPointRequestDTO} such (NIK, Name and DOB )
	 * @return @{@link Response} such (Company Name, Length Of Business, Business Category, Phone Number and Email)
	 */
	@Override
	public Response checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO) {

		var requestDateTime = LocalDateTime.now().getSecond();
		var restTemplate = new RestTemplate();

		String isExistCustIndInquiry = esbIntegration.requestExistCustIndInquiry(entryPointRequestDTO);

		//Setup HTTPEntity for the RestTemplate Object
		HttpEntity<String> httpExistingIndividual = new HttpEntity<>(isExistCustIndInquiry, esbIntegration.headers());

		//First Hit to ESB Service
		var responseExisCustIndInquiry = restTemplate.postForObject(existingIndividualEsbUri, httpExistingIndividual,
				ExisCustIndInquiryResponseWrapper.class);

		// Check if customer existing (ETB / ETB LENDING)
		if (responseExisCustIndInquiry != null
				&& responseExisCustIndInquiry.getExistingCustomerIndividualInquiryResponse().getExistingCustomer()
				!= null && responseExisCustIndInquiry.getExistingCustomerIndividualInquiryResponse()
				.getExistingCustomer().equalsIgnoreCase("true")) {

			//Get GCIFNo
			var gcifNo = responseExisCustIndInquiry.getExistingCustomerIndividualInquiryResponse().getCustomerData()
					.getGcifNo();
			//get CIFNo
			var cifNo = responseExisCustIndInquiry.getExistingCustomerIndividualInquiryResponse().getCustomerData()
					.getCifNo();

			//set to esb for gcifno
			var customerInformationGcif = esbIntegration.customerInformation(gcifNo, "gcifNo");

			HttpEntity<String> httpCustomerInformationGcif = new HttpEntity<>(customerInformationGcif,
					esbIntegration.headers());

			//Third call to Customer Information (Get phone Number & Email)
			var responseCustomerInformationResponseGcif = restTemplate.postForObject(existingAccountServicesEsbUri,
					httpCustomerInformationGcif, CustomerInformationResponseWrapper.class);

			//Checking response for gcif data
			if (responseCustomerInformationResponseGcif != null
					&& responseCustomerInformationResponseGcif.getCustomerInformationResponse() != null
					&& responseCustomerInformationResponseGcif.getCustomerInformationResponse().getResponseCode()
					.equals("00")) {

				//set to esb for cifno
				var customerInformationCif = esbIntegration.customerInformation(cifNo, "cifNo");

				HttpEntity<String> httpCustomerInformationCif = new HttpEntity<>(customerInformationCif,
						esbIntegration.headers());

				//Third call to Customer Information (Get marital status)
				var responseCustomerInformationResponseCif = restTemplate.postForObject(existingAccountServicesEsbUri,
						httpCustomerInformationCif, CustomerInformationResponseWrapper.class);

				//checking response for cif data
				if (responseCustomerInformationResponseCif != null
						&& responseCustomerInformationResponseCif.getCustomerInformationResponse() != null
						&& responseCustomerInformationResponseCif.getCustomerInformationResponse().getResponseCode()
						.equals("00")) {

					//Set phoneNumber, email and maritalStatus to the constructEntryPointGcif has already phoneNumber and Email
					var constructEntryPoint = EntryPointResponseDTO.builder().phoneNumber(
									responseCustomerInformationResponseGcif.getCustomerInformationResponse()
											.getCustomerInformationResponseData().getMobileNo())
							.email(responseCustomerInformationResponseGcif.getCustomerInformationResponse()
									.getCustomerInformationResponseData().getEmail()).maritalStatus(
									responseCustomerInformationResponseCif.getCustomerInformationResponse()
											.getCustomerInformationResponseData().getMarital_status()).build();

					//set trxResponse
					var responseDateTime = LocalDateTime.now().getSecond();

					//set trxProcessingTime
					var processingTime = (responseDateTime - requestDateTime) * 1000;

					//final construct response to client
					var data = Data.builder().object(constructEntryPoint).build();
					var body = Body.builder().data(data).build();
					var header = Header.builder().httpStatusCode(HttpStatusCode.valueOf(200))
							.responseMessage("Success Retrieve Data").trxLog(Util.uniqueId("SME_DL_ESB"))
							.processingTime(processingTime).build();
					var response = Response.builder().body(body).header(header).build();

					return response;

				}

			}

		}

		return null;
	}
}
