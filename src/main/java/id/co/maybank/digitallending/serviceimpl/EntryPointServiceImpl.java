package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.Body;
import id.co.maybank.digitallending.base.response.dto.Data;
import id.co.maybank.digitallending.base.response.dto.Header;
import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.esbintegration.EsbIntegration;
import id.co.maybank.digitallending.esbintegration.service.ExistingCustomerIndividualInquiryService;
import id.co.maybank.digitallending.esbintegration.service.ExistingRsmeCustomerService;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.request.dto.EntryPointResponseDTO;
import id.co.maybank.digitallending.service.CustomerInformationService;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponse;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualInquiryResponse;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;
import id.co.maybank.esb.model.msg.MsgBodyRequest;
import id.co.maybank.esb.model.msg.MsgRequest;
import id.co.maybank.esb.model.msg.MsgResponseWrapper;
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

	@Autowired
	ExistingRsmeCustomerService existingRsmeCustomerService;

	@Autowired
	CustomerInformationService customerInformationService;

	@Autowired
	ExistingCustomerIndividualInquiryService existingCustomerIndividualInquiryService;
	//dateOfBirth format for ESB Request
	private static final String DATE_OF_BIRTH_ESB_FORMAT = "yyyy-MM-dd";

	public EntryPointServiceImpl(ExistingRsmeCustomerService existingRsmeCustomerService,
			CustomerInformationService customerInformationService,
			ExistingCustomerIndividualInquiryService existingCustomerIndividualInquiryService) {
		this.existingRsmeCustomerService = existingRsmeCustomerService;
		this.customerInformationService = customerInformationService;
		this.existingCustomerIndividualInquiryService = existingCustomerIndividualInquiryService;
	}

	/**
	 * This method <p>checkExistingCustomer</p> will be checking customer is NTB, ETB or ETB lending to ESB Services
	 *
	 * @param {@link EntryPointRequestDTO} such (NIK, Name and DOB )
	 * @return @{@link Response} such (Company Name, Length Of Business, Business Category, Phone Number and Email)
	 */
	@Override
	public Response checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO) {

		//ExistingCustomerInquiryRequest
		var exisCustIndInquiryReq = ExistingCustomerIndividualRequest.builder().name(entryPointRequestDTO.name())
				.identityNo(entryPointRequestDTO.Nik())
				.dateOfBirth(Util.dateToString(entryPointRequestDTO.dob(), DATE_OF_BIRTH_ESB_FORMAT)).build();

		ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper = existingCustomerIndividualInquiryService.exisCustIndInquiryResponseWrapper(
				exisCustIndInquiryReq);

		//Customer Information
		var customerInformation = CustomerInformationRequest.builder()
				.GCIFNo(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse()
						.getCustomerData().getGcifNo()).build();

		CustomerInformationResponseWrapper customerInformationResponseWrapper = customerInformationService.customerInformationResponseWrapper(
				customerInformation);

		//Customer RSME
		var customerRsme = MsgBodyRequest.builder()
				.GCIF(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getCustomerData()
						.getGcifNo()).build();
		MsgResponseWrapper msgResponseWrapper = existingRsmeCustomerService.msgResponseWrapper(customerRsme);

		return null;
	}
}

