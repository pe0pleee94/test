package id.co.maybank.digitallending;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.channelheader.ChannelHeader;
import id.co.maybank.esb.model.existingcustomerindividualinquiry.ExistingCustomerIndividualInquiry;
import id.co.maybank.esb.model.existingcustomerindividualrequest.ExistingCustomerIndividualRequest;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Testing {

    public static void main (String [] args){
        var testing = new Testing();
        testing.check();

    }

    private void check () {

        var entryPoint = new EntryPointRequestDTO("abc", "123324242", "2022-04-01");
        //Construct Channel Header for ESB Service

        String dateFormat = "dd-MM-yy";
        String timeFormat = "hh:mm:ss";
        String dobFormat = "yyyy-MM-dd";

        var transactionDateTime = LocalDateTime.now();
        var dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        var timeFormatter = DateTimeFormatter.ofPattern(timeFormat);

        String dateToString = dateFormatter.format(transactionDateTime);
        String timeToString = timeFormatter.format(transactionDateTime);
        var channelHeader =
                ChannelHeader.builder()
                        .messageId(Util.uniqueId("a"))
                        .channelID("b")
                        .clientSupervisorID("c")
                        .clientUserID("d")
                        .reference("")
                        .sequenceNo("")
                        .transactionDate(dateToString)
                        .transactionTime(timeToString)
                        .build();

        //Construct ExistingIndividualRequest for ESB Service
        var existingCustomerIndividualRequest =
                ExistingCustomerIndividualRequest.builder()
                        .name(entryPoint.name())
                        .identityNo(entryPoint.Nik())
                        .dateOfBirth(entryPoint.dob())
                        .build();

        //Final Construct for hit to API ESB Services
        var existingIndividualInquiry =
                ExistingCustomerIndividualInquiry.builder()
                        .channelHeader(channelHeader)
                        .existingCustomerIndividualRequest(existingCustomerIndividualRequest)
                        .build();

        var toGson = new GsonBuilder().create();
            String objectToJson = toGson.toJson(existingIndividualInquiry);


        System.out.println(objectToJson);
    }
}
