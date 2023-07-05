package id.co.maybank.digitallending.base.esb.header;

import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.channelheader.ChannelHeader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelHeaderEsb {

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
}
