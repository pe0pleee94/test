package id.co.maybank.digitallending.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
@Configuration
public class RestConfig {

	@Value("${esb.connection-timeout}")
	private int connectionTimeOut;

	@Bean
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setConnectTimeout(connectionTimeOut);
		return new RestTemplate(simpleClientHttpRequestFactory);
	}
}
