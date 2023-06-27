package id.co.maybank.digitallending.esbintegration;

import com.google.gson.GsonBuilder;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.channelheader.ChannelHeader;
import id.co.maybank.esb.model.customerinformation.CustomerInformation;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequestWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryRequestWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualInquiry;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EsbIntegration {

	//ChannelID for SME Digital Lending
	private static final String CHANNEL_ID = "SME_DL";

	//ClientSupervisorID default 0000
	private static final String CLIENT_SUPERVISOR_ID = "0000";

	//ClientUserID default 0000
	private static final String CLIENT_USER_ID = "0000";

	//transactionDate format for ESB Request
	private static final String TRX_DATE_ESB_FORMAT = "dd-MM-yyyy";

	//transactionTime format for ESB Request
	private static final String TRX_TIME_ESB_FORMAT = "hh:mm:ss";

	//dateOfBirth format for ESB Request
	private static final String DATE_OF_BIRTH_ESB_FORMAT = "yyyy-MM-dd";

	/**
	 * Set the header channel for ESB Service All the header same for ESB Service
	 *
	 * @return @{@link ChannelHeader}
	 */
	public static ChannelHeader getChannelHeader() {
		//Construct Channel Header for ESB Service
		return ChannelHeader.builder().messageID(Util.uniqueId(CHANNEL_ID)).channelID(CHANNEL_ID)
				.clientSupervisorID(CLIENT_SUPERVISOR_ID).clientUserID(CLIENT_USER_ID)
				.reference("") //This empty string because ESB format
				.branchCode("") //This empty string because ESB format
				.sequenceno(Integer.toString(Util.generateRandom()))
				.transactiondate(Util.dateToString(LocalDate.now(), TRX_DATE_ESB_FORMAT))
				.transactiontime(Util.dateTimeToString(LocalDateTime.now(), TRX_TIME_ESB_FORMAT)).build();
	}

	/**
	 * Set the HttpHeaders for put value application/json
	 *
	 * @return HttpHeaders
	 */
	public HttpHeaders headers() {
		var httpHeaders = new HttpHeaders();
		return Util.httpHeaders(httpHeaders);
	}

	public String requestExistCustIndInquiry(EntryPointRequestDTO entryPointRequestDTO) {

		//Construct ExistingIndividualRequest for ESB Service
		var existingCustomerIndividualRequest = ExistingCustomerIndividualRequest.builder()
				.name(entryPointRequestDTO.name()).identityNo(entryPointRequestDTO.Nik())
				.dateOfBirth(Util.dateToString(entryPointRequestDTO.dob(), DATE_OF_BIRTH_ESB_FORMAT)).build();

		//Construct for hit to API ESB Services
		var existingIndividualInquiry = ExistingCustomerIndividualInquiry.builder().ChannelHeader(getChannelHeader())
				.ExistingCustomerIndividualRequest(existingCustomerIndividualRequest).build();

		//Final construct to setting to the request class for the sending to the ESB
		var requestExistingInd = ExisCustIndInquiryRequestWrapper.builder()
				.ExistingCustomerIndividualInquiry(existingIndividualInquiry);

		//Convert to JSON
		var toGson = new GsonBuilder().create();
		return toGson.toJson(requestExistingInd);
	}

	public String customerInformation(String no,String type) {

		CustomerInformationRequest customerInformationRequest;
		if(type.equalsIgnoreCase("gcifNo")) {
			customerInformationRequest = CustomerInformationRequest.builder().GCIFNo(no).build();
		}
		else {
			customerInformationRequest = CustomerInformationRequest.builder().CIFNo(no).build();

		}

		var customerInformation = CustomerInformation.builder().ChannelHeader(getChannelHeader())
				.CustomerInformationRequest(customerInformationRequest).build();

		var requestCustomerInformation = CustomerInformationRequestWrapper.builder()
				.CustomerInformation(customerInformation).build();

		//Convert to JSON
		var toGson = new GsonBuilder().create();
		return toGson.toJson(requestCustomerInformation);
	}
}
