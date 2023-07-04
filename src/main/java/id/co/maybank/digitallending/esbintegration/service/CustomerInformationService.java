package id.co.maybank.digitallending.esbintegration.service;

import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
public interface CustomerInformationService {

	CustomerInformationResponseWrapper customerInformationResponseWrapper(
			CustomerInformationRequest customerInformationRequest);
}
