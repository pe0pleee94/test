package id.co.maybank.digitallending.serviceimpl;

import id.co.maybank.digitallending.base.response.dto.Body;
import id.co.maybank.digitallending.base.response.dto.Data;
import id.co.maybank.digitallending.base.response.dto.Header;
import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.esbintegration.EsbIntegration;
import id.co.maybank.digitallending.esbintegration.service.ExistingCustomerIndividualInquiryService;
import id.co.maybank.digitallending.esbintegration.service.ExistingRsmeCustomerService;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

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

    Response response;


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
        var existCustIndInquiryReq = ExistingCustomerIndividualRequest.builder().name(entryPointRequestDTO.name())
                .identityNo(entryPointRequestDTO.Nik())
                .dateOfBirth(Util.dateToString(entryPointRequestDTO.dob(), DATE_OF_BIRTH_ESB_FORMAT)).build();

        //ExistingCustomerInquiryResponse
        var exisCustIndInquiryResponseWrapper = this.existingCustomerIndividualInquiryService.exisCustIndInquiryResponseWrapper(
                existCustIndInquiryReq);

        //Checking ETB / ETB Lending
        if (checkIfEtbOrEtbLending(exisCustIndInquiryResponseWrapper)) {

            //Customer RSME
            var customerRsme = MsgBodyRequest.builder()
                    .GCIF(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getCustomerData()
                            .getGcifNo()).build();

            MsgResponseWrapper rsmeMsgResponseWrapper = this.existingRsmeCustomerService.msgResponseWrapper(customerRsme);

            //Customer Information
            var customerInformation = CustomerInformationRequest.builder()
                    .GCIFNo(exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse()
                            .getCustomerData().getGcifNo()).build();


            CustomerInformationResponseWrapper customerInformationResponseWrapper = this.customerInformationService.customerInformationResponseWrapper(
                    customerInformation);

            if (checkIfRsmeExists(rsmeMsgResponseWrapper)) {


                var header = new Header("", 123, HttpStatusCode.valueOf(200), "ETB_LENDING");
                var data = Data.builder().object(rsmeMsgResponseWrapper.getMsg().getMsgBody()).build();
                var body = Body.builder().data(data).build();

                response = Response.builder().body(body).header(header).build();
            }
            var header = new Header("", 123, HttpStatusCode.valueOf(200), "ETB");
            var data = Data.builder().object("").build();
            var body = Body.builder().data(data).build();

            response = Response.builder().body(body).header(header).build();

        }
        var header = new Header("", 123, HttpStatusCode.valueOf(200), "NTB");
        var data = Data.builder().object("").build();
        var body = Body.builder().data(data).build();

        response = Response.builder().body(body).header(header).build();


        return response;
    }

    private static boolean checkIfEtbOrEtbLending(ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper) {
        return exisCustIndInquiryResponseWrapper != null
                && exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getExistingCustomer().equalsIgnoreCase("true")
                && exisCustIndInquiryResponseWrapper.getExistingCustomerIndividualInquiryResponse().getResponseCode().equals("00");
    }

    private static boolean checkIfRsmeExists(MsgResponseWrapper rsmeMsgResponseWrapper) {
        return rsmeMsgResponseWrapper != null
                && rsmeMsgResponseWrapper.getMsg() != null
                && rsmeMsgResponseWrapper.getMsg().getMsgHeader() != null
                && rsmeMsgResponseWrapper.getMsg().getMsgHeader().getStatusCode().equals("0");
    }
}

