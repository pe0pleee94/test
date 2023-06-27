package id.co.maybank.digitallending.serviceimpl;

import com.google.gson.GsonBuilder;
import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.channelheader.ChannelHeader;
import id.co.maybank.esb.model.customerinformation.CustomerInformation;
import id.co.maybank.esb.model.customerinformationrequest.CustomerInformationRequest;
import id.co.maybank.esb.model.existingcustomerindividualinquiryresponse.ExistingCustomerIndividualInquiryResponse;
import id.co.maybank.esb.model.request.RequestExisCustIndInquiry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import id.co.maybank.esb.model.existingcustomerindividualinquiry.ExistingCustomerIndividualInquiry;
import id.co.maybank.esb.model.existingcustomerindividualrequest.ExistingCustomerIndividualRequest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 This is class for implement logic business from Entry Point (Individual or Corporate)
 * @since 1.0 (Created June. 22, 2023)
 */
@Service
@Slf4j
public class EntryPointServiceImpl implements EntryPointService {

	RestTemplate restTemplate;

	//ChannelID for SME Digital Lending
	private static final String CHANNEL_ID = "SME_DL";

	//ClientSupervisorID default 0000
	private static final String CLIENT_SUPERVISOR_ID = "0000";

	//ClientUserID default 0000
	private static final String CLIENT_USER_ID = "0000";

	//transactionDate format for ESB Request
	private static final String TRX_DATE_ESB_FORMAT = "dd-MM-yyyy";

	//transactionTime format for ESB Request
	private static final String TRX_TIME_ESB_FORMAT = "hh-mm-ss";

	//dateOfBirth format for ESB Request
	private static final String DATE_OF_BIRTH_ESB_FORMAT = "yyyy-MM-dd";

	private String esbUri;

	/**
	 * This method <p>checkExistingCustomer</p> will be checking customer is NTB, ETB or ETB lending to ESB Services
	 *
	 * @param {@link EntryPointRequestDTO} such (NIK, Name and DOB )
	 * @return @{@link Response} such (Company Name, Length Of Business, Business Category, Phone Number and Email)
	 */
	@Override
	public List<Response> checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO) {

		//construct channel header
		ChannelHeader channelHeader = getChannelHeader();

		//Set the header application/json for the json value
		var headers = new HttpHeaders();
		String isExistCustIndInquiry = requestExistCustIndInquiry(channelHeader, entryPointRequestDTO);

		//Setup HTTPEntity for the RestTemplate Object
		HttpEntity<String> httpEntity = new HttpEntity<>(isExistCustIndInquiry, Util.httpHeaders(headers));

		ExistingCustomerIndividualInquiryResponse existingCustomerIndividualInquiryResponse = restTemplate.postForObject(
				esbUri, httpEntity, ExistingCustomerIndividualInquiryResponse.class);

		// Check if customer existing (ETB / ETB Lending)s
		if (!existingCustomerIndividualInquiryResponse.getExistingCustomer().isEmpty()
				&& existingCustomerIndividualInquiryResponse.getExistingCustomer().equalsIgnoreCase("true")) {
			var gcifNo = existingCustomerIndividualInquiryResponse.getCustomerData().getGcifNo();

			customerInformation(channelHeader, gcifNo);

		}
		return null;
	}

	/*
	 * Construct Request for the ESB Service. It will be composite services.
	 * First we need hit to @ExistingCustomerIndividualInquiry and @ExistingCustomerIndividualRequest to get gcif if customer exist
	 */
	private String requestExistCustIndInquiry(ChannelHeader channelHeader, EntryPointRequestDTO entryPointRequestDTO) {

		/**
		 * First construct ChannelHeader class for element Request to ExistingCustomerIndividualInquiry
		 *
		 * branchCode and reference from ESB element will be filled empty String
		 */

		//Construct ExistingIndividualRequest for ESB Service
		var existingCustomerIndividualRequest = ExistingCustomerIndividualRequest.builder()
				.name(entryPointRequestDTO.name()).identityNo(entryPointRequestDTO.Nik())
				.dateOfBirth(Util.dateToString(entryPointRequestDTO.dob(), DATE_OF_BIRTH_ESB_FORMAT)).build();

		//Construct for hit to API ESB Services
		var existingIndividualInquiry = ExistingCustomerIndividualInquiry.builder().ChannelHeader(channelHeader)
				.ExistingCustomerIndividualRequest(existingCustomerIndividualRequest).build();

		//Final construct to setting to the request class for the sending to the ESB
		var requestExistingInd = RequestExisCustIndInquiry.builder()
				.ExistingCustomerIndividualInquiry(existingIndividualInquiry);

		//Convert to JSON
		var toGson = new GsonBuilder().create();
		return toGson.toJson(requestExistingInd);
	}

	private static ChannelHeader getChannelHeader() {
		//Construct Channel Header for ESB Service
		var channelHeader = ChannelHeader.builder().messageID(Util.uniqueId(CHANNEL_ID)).channelID(CHANNEL_ID)
				.clientSupervisorID(CLIENT_SUPERVISOR_ID).clientUserID(CLIENT_USER_ID)
				.reference("") //This empty string because ESB format
				.sequenceno("") //This empty string because ESB format
				.transactiondate(Util.dateToString(LocalDate.now(), TRX_DATE_ESB_FORMAT))
				.transactiontime(Util.dateTimeToString(LocalDateTime.now(), TRX_TIME_ESB_FORMAT)).build();
		return channelHeader;
	}

	private String customerInformation(ChannelHeader channelHeader, String gcifNo) {

		var customerInformationRequest = CustomerInformationRequest.builder().gcifNo(gcifNo).build();
		var customerInformation = CustomerInformation.builder().ChannelHeader(channelHeader)
				.CustomerInformationRequest(customerInformationRequest).build();

		//Convert to JSON
		var toGson = new GsonBuilder().create();
		return toGson.toJson(customerInformation);
	}

}
