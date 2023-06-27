package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.esbintegration.EsbIntegration;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

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
			var gcifNo = responseExisCustIndInquiry.getExistingCustomerIndividualInquiryResponse().getCustomerData()
					.getGcifNo();

			String customerInformation = esbIntegration.customerInformation(gcifNo);

			HttpEntity<String> httpCustomerInformation = new HttpEntity<>(customerInformation,
					esbIntegration.headers());

			//Third call to Customer Information
			var responseCustomerInformationResponse = restTemplate.postForObject(existingAccountServicesEsbUri,
					httpCustomerInformation, CustomerInformationResponseWrapper.class);



		}
		return null;
	}
}
