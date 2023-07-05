package id.co.maybank.digitallending.esbintegration.service;

import id.co.maybank.esb.model.msg.MsgBodyRequest;
import id.co.maybank.esb.model.msg.MsgResponseWrapper;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
public interface ExistingRsmeCustomerService {

    MsgResponseWrapper msgResponseWrapper(MsgBodyRequest msgBodyRequest);
}
