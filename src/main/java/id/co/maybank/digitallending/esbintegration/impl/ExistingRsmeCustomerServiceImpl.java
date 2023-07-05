package id.co.maybank.digitallending.esbintegration.impl;

import id.co.maybank.digitallending.config.HttpConfig;
import id.co.maybank.digitallending.esbintegration.service.ExistingRsmeCustomerService;
import id.co.maybank.digitallending.util.Util;
import id.co.maybank.esb.model.msg.*;
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
public class ExistingRsmeCustomerServiceImpl implements ExistingRsmeCustomerService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${esb.existing-rsme-endpoint.uri}")
	private String existingRsmeEsbUri;

	//Msg ID for SME Digital Lending
	private static final String MSG_ID = "SME_DL";

	private static final String RSME_ENV = "S";

	private static final String RSME_VER = "01";

	private static final String SVC_ID = "IDCHKEXSTRSMECUST01";

	private static final String TXN_CODE = "TXN_SME_DL";

	@Override
	public MsgResponseWrapper msgResponseWrapper(MsgBodyRequest msgBodyRequest) {

		var msgHeaderRequest = MsgHeaderRequest.builder().MsgID(Util.uniqueId(MSG_ID)).Env(RSME_ENV).Ver(RSME_VER)
				.SvcID(SVC_ID).TxnCode(TXN_CODE).build();

		var msgBodyRequestReq = MsgBodyRequest.builder().GCIF(msgBodyRequest.getGCIF()).build();

		//set to msg
		var msg = MsgRequest.builder().MsgHeader(msgHeaderRequest).MsgBody(msgBodyRequestReq).build();

		var msgRequestWrapper = MsgRequestWrapper.builder().Msg(msg).build();

		return restTemplate.postForObject(existingRsmeEsbUri,
				HttpConfig.httpEntity(Util.toStringGson(msgRequestWrapper)), MsgResponseWrapper.class);
	}
}
