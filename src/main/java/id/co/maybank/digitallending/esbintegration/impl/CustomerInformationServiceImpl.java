package id.co.maybank.digitallending.esbintegration.impl;

import id.co.maybank.digitallending.base.esb.header.ChannelHeaderEsb;
import id.co.maybank.digitallending.config.HttpConfig;
import id.co.maybank.digitallending.service.CustomerInformationService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.customerinformation.CustomerInformation;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequest;
import id.co.maybank.esb.model.customerinformation.CustomerInformationRequestWrapper;
import id.co.maybank.esb.model.customerinformation.CustomerInformationResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */

@Service
public class CustomerInformationServiceImpl implements CustomerInformationService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${esb.customer-information-endpoint.uri}")
	private String existingAccountServicesEsbUri;

	public CustomerInformationServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public CustomerInformationResponseWrapper customerInformationResponseWrapper(
			CustomerInformationRequest customerInformationRequest) {

		if (customerInformationRequest.getCIFNo() != null) {
			String cifNo = customerInformationRequest.getCIFNo();
		} else {
			String gCifNo = customerInformationRequest.getGCIFNo();
		}

		var constructCustomerInformation = CustomerInformation.builder()
				.ChannelHeader(ChannelHeaderEsb.getChannelHeader())
				.CustomerInformationRequest(customerInformationRequest).build();

		var constructCustomerInformationReq = CustomerInformationRequestWrapper.builder()
				.CustomerInformation(constructCustomerInformation).build();

		return restTemplate.postForObject(existingAccountServicesEsbUri,
				HttpConfig.httpEntity(Util.toStringGson(constructCustomerInformationReq)),
				CustomerInformationResponseWrapper.class);
	}
}
