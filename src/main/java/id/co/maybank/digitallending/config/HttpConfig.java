package id.co.maybank.digitallending.config;

import id.co.maybank.digitallending.util.Util;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpConfig {

	/**
	 * Set the HttpHeaders for put value application/json
	 *
	 * @return HttpHeaders
	 */
	public static HttpHeaders headers() {
		var httpHeaders = new HttpHeaders();
		return Util.httpHeaders(httpHeaders);
	}

	public static HttpEntity<String> httpEntity(String json) {
		return new HttpEntity<>(json, headers());
	}
}
