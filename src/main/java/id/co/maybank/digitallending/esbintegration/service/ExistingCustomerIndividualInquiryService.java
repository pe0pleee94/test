package id.co.maybank.digitallending.esbintegration.service;

import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
public interface ExistingCustomerIndividualInquiryService {

	ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper(
			ExistingCustomerIndividualRequest existingCustomerIndividualRequest);

}
