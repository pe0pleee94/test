package id.co.maybank.digitallending.service;

import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;

public interface CustomerInformationService {

	CustomerInformationResponseWrapper customerInformationResponseWrapper(CustomerInformationRequest customerInformationRequest);
}
