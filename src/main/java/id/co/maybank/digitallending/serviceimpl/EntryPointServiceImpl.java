package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.request.dto.RequestDTO;
import id.co.maybank.digitallending.service.EntryPointService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.channelheader.ChannelHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import id.co.maybank.esb.model.existingcustomerindividualinquiry.ExistingCustomerIndividualInquiry;
import id.co.maybank.esb.model.existingcustomerindividualrequest.ExistingCustomerIndividualRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0
 * This is class for implement logic business from Entry Point (Individual or Corporate)
 * @since 1.0 (Created June. 22, 2023)
 */
@Service
@Slf4j
public class EntryPointServiceImpl implements EntryPointService {

    //ChannelID for SME Digital Lending
    private static final String CHANNEL_ID = "SME_DL";

    //ClientSupervisorID default 0000
    private static final String CLIENT_SUPERVISOR_ID = "0000";

    //ClientUserID default 0000
    private static final String CLIENT_USER_ID = "0000";


    /**
     * This method <p>checkExistingCustomer</p> will be checking customer is NTB, ETB or ETB lending to ESB Services
     *
     * @param {@link EntryPointRequestDTO} such (NIK, Name and DOB )
     * @return @{@link Response} such (Company Name, Length Of Business, Business Category, Phone Number and Email)
     */
    @Override
    public List<Response> checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO) {

        return null;
    }

    /*
     * Construct Request for the ESB Service. It will be composite services.
     * First we need hit to @ExistingCustomerIndividualInquiry and @ExistingCustomerIndividualRequest to get gcif if customer exist
     */
    private ExistingCustomerIndividualInquiry existingCustomerIndividualInquiry(EntryPointRequestDTO entryPointRequestDTO) {

        /**
         *First construct ChannelHeader class for element Request to ExistingCustomerIndividualInquiry
         *
         * branchCode and reference from ESB element will be filled empty String
         */

        //Construct Channel Header for ESB Service
        var channelHeader =
                ChannelHeader.builder()
                        .messageId(Util.uniqueId(CHANNEL_ID))
                        .channelID(CHANNEL_ID)
                        .clientSupervisorID(CLIENT_SUPERVISOR_ID)
                        .clientUserID(CLIENT_USER_ID)
                        .reference("")
                        .sequenceNo("")
                        .transactionDate("")
                        .transactionTime("")
                        .build();

        //Construct ExistingIndividualRequest for ESB Service
        var existingCustomerIndividualRequest =
                ExistingCustomerIndividualRequest.builder()
                        .name(entryPointRequestDTO.name())
                        .identityNo(entryPointRequestDTO.Nik())
                        .dateOfBirth(entryPointRequestDTO.dob())
                        .build();

        //Final Construct for hit to API ESB Services
        var existingIndividualInquiry =
                ExistingCustomerIndividualInquiry.builder()
                        .channelHeader(channelHeader)
                        .existingCustomerIndividualRequest(existingCustomerIndividualRequest)
                        .build();

        return null;
    }

}
