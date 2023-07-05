package id.co.maybank.digitallending.esbintegration.impl;

import id.co.maybank.digitallending.base.esb.header.ChannelHeaderEsb;
import id.co.maybank.digitallending.config.HttpConfig;
import id.co.maybank.digitallending.esbintegration.service.ExistingCustomerIndividualInquiryService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryRequestWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExisCustIndInquiryResponseWrapper;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualInquiry;
import id.co.maybank.esb.model.existingcustomerindividual.ExistingCustomerIndividualRequest;
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
public class ExistingCustomerIndividualInquiryServiceImpl implements ExistingCustomerIndividualInquiryService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${esb.existing-customer-individual-endpoint.uri}")
	private String existingIndividualEsbUri;

	@Override
	public ExisCustIndInquiryResponseWrapper exisCustIndInquiryResponseWrapper(
			ExistingCustomerIndividualRequest existingCustomerIndividualRequest) {

		var constructExistingCustomerIndividualRequest = ExistingCustomerIndividualRequest.builder()
				.name(existingCustomerIndividualRequest.getName())
				.identityNo(existingCustomerIndividualRequest.getIdentityNo())
				.dateOfBirth(existingCustomerIndividualRequest.getDateOfBirth()).build();

		var constructExistingIndividualInquiry = ExistingCustomerIndividualInquiry.builder()
				.ChannelHeader(ChannelHeaderEsb.getChannelHeader())
				.ExistingCustomerIndividualRequest(constructExistingCustomerIndividualRequest).build();

		var requestExistingInd = ExisCustIndInquiryRequestWrapper.builder()
				.ExistingCustomerIndividualInquiry(constructExistingIndividualInquiry);

		return restTemplate.postForObject(existingIndividualEsbUri,
				HttpConfig.httpEntity(Util.toStringGson(requestExistingInd)), ExisCustIndInquiryResponseWrapper.class);
	}
}
