package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.*;
import id.co.maybank.digitallending.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.dto.EntryPointResponseDTO;
import id.co.maybank.digitallending.esbintegration.service.ExistingCustomerIndividualInquiryService;
import id.co.maybank.digitallending.esbintegration.service.ExistingRsmeCustomerService;
import id.co.maybank.digitallending.model.IndividualIdentity;
import id.co.maybank.digitallending.repository.IndividualRepository;
import id.co.maybank.digitallending.service.CustomerInformationService;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;
import id.co.maybank.esb.model.msg.MsgBodyRequest;
import id.co.maybank.esb.model.msg.MsgResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 This is class for implement logic business from Entry Point (Individual or Corporate)
 * @since 1.0 (Created June. 22, 2023)
 */
@Service
@Slf4j
public class EntryPointServiceImpl implements EntryPointService {

	ExistingRsmeCustomerService existingRsmeCustomerService;

	CustomerInformationService customerInformationService;

	ExistingCustomerIndividualInquiryService existingCustomerIndividualInquiryService;

	IndividualRepository individualRepository;

	//dateOfBirth format for ESB Request
	private static final String DATE_OF_BIRTH_ESB_FORMAT = "yyyy-MM-dd";

	private static final String NTB_TYPE = "NTB";

	private static final String ETB_TYPE = "ETB";

	private static final String ETB_LENDING_TYPE = "ETL";

	Response response;

	//Dependency Inversion Principle
	public EntryPointServiceImpl(ExistingRsmeCustomerService existingRsmeCustomerService,
			CustomerInformationService customerInformationService,
			ExistingCustomerIndividualInquiryService existingCustomerIndividualInquiryService,
			IndividualRepository individualRepository) {
		this.existingRsmeCustomerService = existingRsmeCustomerService;
		this.customerInformationService = customerInformationService;
		this.existingCustomerIndividualInquiryService = existingCustomerIndividualInquiryService;
		this.individualRepository = individualRepository;
	}

	/**
	 * This method <p>checkExistingCustomer</p> will be checking customer is NTB, ETB or ETB lending to ESB Services
	 *
	 * @param {@link EntryPointRequestDTO} such (NIK, Name and DOB )
	 * @return @{@link Response} such (Company Name, Length Of Business, Business Category, Phone Number and Email)
	 */
	@Override
	public Response checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO) {

		//Checking if user exist
		Optional<IndividualIdentity> existingUser = individualRepository.findById(
				Long.parseLong(entryPointRequestDTO.Nik()));

		if (existingUser.isPresent()) {
			return BaseResponse.response(HttpStatus.OK, "Account already registered", entryPointRequestDTO);
		}

		//If data not exist hit To ESB
		ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper = getExisCustIndInquiryResponseWrapper(
				entryPointRequestDTO);

		//If account exist then checking ETB / ETB Lending type
		if (checkIfEtbOrEtbLending(exisCustIndInquiryResponseWrapper)) {

			//Hit to ESB CheckExistingRSMECustomer
			MsgResponseWrapper rsmeMsgResponseWrapper = getRsmeMsgResponseWrapper(exisCustIndInquiryResponseWrapper);

			if (checkIfRsmeExists(rsmeMsgResponseWrapper)) {

				//Getting info from Systematic CIF (maritalStatus)
				var customerInformationResponseCif = getCustomerInformationCif(exisCustIndInquiryResponseWrapper);

				if (customerInformationResponseCif != null
						&& customerInformationResponseCif.getCustomerInformationResponse().getResponseCode()
						.equals("00")) {

					//Getting info from GCIF (phoneNumber,email)
					var customerInformationResponseGcif = getCustomerInformationGcif(exisCustIndInquiryResponseWrapper);

					if (customerInformationResponseGcif != null
							&& customerInformationResponseGcif.getCustomerInformationResponse().getResponseCode()
							.equals("00")) {

						saveToDb(entryPointRequestDTO, customerInformationResponseGcif, ETB_LENDING_TYPE);

						var entryPointResponse = getEntryPointResponseDTOEtbLending(rsmeMsgResponseWrapper,
								customerInformationResponseCif, customerInformationResponseGcif);

						return BaseResponse.response(HttpStatus.OK, "Success register for ETB Lending",
								entryPointResponse);
					}
				}
				return BaseResponse.response(HttpStatus.NOT_ACCEPTABLE, "Error", null);
			}

			//For ETB Type
			//Getting info from GCIF (phoneNumber,email)
			var customerInformationResponseGCif = getCustomerInformationGcif(exisCustIndInquiryResponseWrapper);

			if (customerInformationResponseGCif != null
					&& customerInformationResponseGCif.getCustomerInformationResponse().getResponseCode()
					.equals("00")) {

				//Getting info from Systematic CIF (maritalStatus)
				var customerInformationResponseCif = getCustomerInformationCif(exisCustIndInquiryResponseWrapper);

				if (customerInformationResponseCif != null
						&& customerInformationResponseCif.getCustomerInformationResponse().getResponseCode()
						.equals("00")) {

					//Save to DB for ETB
					saveToDb(entryPointRequestDTO, customerInformationResponseGCif, ETB_TYPE);

					return BaseResponse.response(HttpStatus.OK, "Register success for ETB type",
							EntryPointResponseDTO.builder()
									.email(customerInformationResponseGCif.getCustomerInformationResponse()
											.getCustomerInformationResponseData().getEmail()).phoneNumber(
											customerInformationResponseGCif.getCustomerInformationResponse()
													.getCustomerInformationResponseData().getMobileNo()).maritalStatus(
											customerInformationResponseCif.getCustomerInformationResponse()
													.getCustomerInformationResponseData().getMarital_status()).build());
				}
			}

		}

		//Save to DB for NTB
		var individualIdentity = new IndividualIdentity();
		individualIdentity.setNik(Long.parseLong(entryPointRequestDTO.Nik()));
		individualIdentity.setName(entryPointRequestDTO.name());
		individualIdentity.setDateOfBirth(entryPointRequestDTO.dob());
		individualIdentity.setCategory(NTB_TYPE);
		individualIdentity.setCreatedDate(LocalDateTime.now());
		individualIdentity.setUpdatedDate(LocalDateTime.now());

		individualRepository.save(individualIdentity);
		//Give the response for NTB
		return BaseResponse.response(HttpStatus.OK, "Register success for NTB type", entryPointRequestDTO);
	}

	private void saveToDb(EntryPointRequestDTO entryPointRequestDTO,
			CustomerInformationResponseWrapper customerInformationResponse, String type) {
		//Save to DB for ETB Lending
		var individualIdentity = new IndividualIdentity();
		individualIdentity.setNik(Long.parseLong(entryPointRequestDTO.Nik()));
		individualIdentity.setEmail(
				customerInformationResponse.getCustomerInformationResponse().getCustomerInformationResponseData()
						.getEmail());
		individualIdentity.setName(entryPointRequestDTO.name());
		individualIdentity.setDateOfBirth(entryPointRequestDTO.dob());
		individualIdentity.setCategory(type);
		individualIdentity.setCreatedDate(LocalDateTime.now());
		individualIdentity.setUpdatedDate(LocalDateTime.now());

		individualRepository.save(individualIdentity);
	}

	private static EntryPointResponseDTO getEntryPointResponseDTOEtbLending(MsgResponseWrapper rsmeMsgResponseWrapper,
			CustomerInformationResponseWrapper customerInformationResponseCif,
			CustomerInformationResponseWrapper customerInformationResponseGcif) {
		//construct response
		return EntryPointResponseDTO.builder().email(customerInformationResponseGcif.getCustomerInformationResponse()
				.getCustomerInformationResponseData().getEmail()).companyName(
				rsmeMsgResponseWrapper.getMsg().getMsgBody().getStatus().stream()
						.map(companyName -> companyName.getCompanyName()).toList()).bussinessCategory(
				rsmeMsgResponseWrapper.getMsg().getMsgBody().getStatus().stream()
						.map(businessCategory -> businessCategory.getBusinessCategory()).toList()).lengthOfBusiness(
				rsmeMsgResponseWrapper.getMsg().getMsgBody().getStatus().stream()
						.map(lengthOfBusiness -> lengthOfBusiness.getLengthOfBusiness()).toList()).phoneNumber(
				customerInformationResponseGcif.getCustomerInformationResponse().getCustomerInformationResponseData()
						.getMobileNo()).maritalStatus(
				customerInformationResponseCif.getCustomerInformationResponse().getCustomerInformationResponseData()
						.getMarital_status()).build();
	}

	// CustomerInformation (GCIF)
	private CustomerInformationResponseWrapper getCustomerInformationGcif(
			ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper) {
		//Customer Information
		var customerInformation = CustomerInformationRequest.builder()
				.GCIFNo(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse()
						.getCustomerData().getGcifNo()).build();

		return customerInformationService.customerInformationResponseWrapper(customerInformation);
	}

	//Systematic CustomerInformation(CIF)
	private CustomerInformationResponseWrapper getCustomerInformationCif(
			ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper) {
		//Customer Information
		var customerInformation = CustomerInformationRequest.builder()
				.CIFNo(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse()
						.getCustomerData().getCifNo()).build();

		return customerInformationService.customerInformationResponseWrapper(customerInformation);
	}

	private MsgResponseWrapper getRsmeMsgResponseWrapper(
			ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper) {
		//CustomerRsmeLending
		var customerRsme = MsgBodyRequest.builder()
				.GCIF(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getCustomerData()
						.getGcifNo()).build();

		return existingRsmeCustomerService.msgResponseWrapper(customerRsme);
	}

	private ExisCustIndInquiryResponseWrapper getExisCustIndInquiryResponseWrapper(
			EntryPointRequestDTO entryPointRequestDTO) {

		var existCustIndInquiryReq = ExistingCustomerIndividualRequest.builder().name(entryPointRequestDTO.name())
				.identityNo(entryPointRequestDTO.Nik())
				.dateOfBirth(Util.dateToString(entryPointRequestDTO.dob(), DATE_OF_BIRTH_ESB_FORMAT)).build();

		return existingCustomerIndividualInquiryService.exisCustIndInquiryResponseWrapper(existCustIndInquiryReq);
	}

	private static boolean checkIfEtbOrEtbLending(ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper) {
		return exisCustIndInquiryResponseWrapper != null
				&& exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse()
				.getExistingCustomer().equalsIgnoreCase("true")
				&& exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getResponseCode()
				.equals("00");
	}

	private static boolean checkIfRsmeExists(MsgResponseWrapper rsmeMsgResponseWrapper) {
		return rsmeMsgResponseWrapper != null && rsmeMsgResponseWrapper.getMsg() != null
				&& rsmeMsgResponseWrapper.getMsg().getMsgHeader() != null && rsmeMsgResponseWrapper.getMsg()
				.getMsgHeader().getStatusCode().equals("0") && rsmeMsgResponseWrapper.getMsg().getMsgHeader()
				.getStatusDesc().equals("Successfully");
	}
}

