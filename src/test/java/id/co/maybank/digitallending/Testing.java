//package id.co.maybank.digitallending;
//
//
//import com.google.gson.GsonBuilder;
//import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
//import id.co.maybank.digitallending.util.Util;
//import id.co.maybank.esb.model.channelheader.ChannelHeader;
//import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualInquiry;
//import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;
//import id.co.maybank.esb.model.existingcustomerindividual.RequestExisCustIndInquiry;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class Testing {
//
////    public static void main (String [] args){
////        var testing = new Testing();
////        System.out.println(testing.check());
////
////    }
////
////    private String check () {
////
////        LocalDate dobRequest = LocalDate.of(1991,05, 06);
////
////        var entryPoint = new EntryPointRequestDTO("abc", "123324242", dobRequest);
////        //Construct Channel Header for ESB Service
////
////        String dateFormat = "dd-MM-yyyy";
////        String timeFormat = "hh:mm:ss";
////        String dobRequestFormat = "yyyy-MM-dd";
////
////        var transactionDateTime = LocalDateTime.now();
////        var dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
////        var timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
////        var dobFormatter = DateTimeFormatter.ofPattern(dobRequestFormat);
////
////        String dateToString = dateFormatter.format(transactionDateTime);
////        String timeToString = timeFormatter.format(transactionDateTime);
////        String dobToString = dobFormatter.format(dobRequest);
////
////        var channelHeader =
////                ChannelHeader.builder()
////                        .messageID(Util.uniqueId("a"))
////                        .channelID("b")
////                        .clientSupervisorID("c")
////                        .clientUserID("d")
////                        .reference("")
////                        .sequenceno("")
////                        .transactiondate(Util.dateTimeToString(transactionDateTime, dateFormat))
////                        .transactiontime(Util.dateTimeToString(transactionDateTime, timeFormat))
////                        .build();
////
////        //Construct ExistingIndividualRequest for ESB Service
////        var existingCustomerIndividualRequest =
////                ExistingCustomerIndividualRequest.builder()
////                        .name(entryPoint.name())
////                        .identityNo(entryPoint.Nik())
////                        .dateOfBirth(Util.dateToString(entryPoint.dob(), "yyyy-MM-dd"))
////                        .build();
////
////        //Final Construct for hit to API ESB Services
////        var existingIndividualInquiry =
////                ExistingCustomerIndividualInquiry.builder()
////                        .ChannelHeader(channelHeader)
////                        .ExistingCustomerIndividualRequest(existingCustomerIndividualRequest)
////                        .build();
////
////        //Setting to the request class for the sending to the ESB
////        var requestExisCustIndInquiry = RequestExisCustIndInquiry.builder()
////                .ExistingCustomerIndividualInquiry(existingIndividualInquiry);
////
////        //Convert to JSON
////        var toGson = new GsonBuilder().create();
////        return toGson.toJson(requestExisCustIndInquiry);
////    }
//}
