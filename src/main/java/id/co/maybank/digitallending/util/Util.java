package id.co.maybank.digitallending.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 General Util Class
 * @since 1.0 (Created June. 22, 2023)
 */
public class Util {

	private static final String LOG_DATE_FORMAT = "yyyyMMddHHmmssSSS";

	private static final String HYVHEN_SEPARATOR = "-";

	private static final Integer RANDOM_NUMBER_BOUND = 999999;

	private Util() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * This method generate unique id log identifier
	 *
	 * @param String serviceName
	 * @return String uniqueId
	 */
	public static String uniqueId(String serviceName) {
		var dtf = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
		var uniqueId = new StringBuilder();
		var currentDateTime = LocalDateTime.now();

		String datePattern = dtf.format(currentDateTime);
		uniqueId.append(serviceName);
		uniqueId.append(HYVHEN_SEPARATOR);
		uniqueId.append(datePattern);

		return uniqueId.toString();
	}

	/**
	 * This util for using convert Date to pattern from the parameter
	 *
	 * @param localDate
	 * @param pattern
	 * @return String DateTimeResult
	 */
	public static String dateToString(LocalDate localDate, String pattern) {
		var dateFormat = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(dateFormat);
	}

	/**
	 * This util for using convert DateTime to pattern from the parameter
	 *
	 * @param localDateTime
	 * @param pattern
	 * @return String DateTimeResult
	 */
	public static String dateTimeToString(LocalDateTime localDateTime, String pattern) {
		var dateFormat = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(dateFormat);
	}

	public static int generateRandom(){
		var randomNumber = new Random(RANDOM_NUMBER_BOUND);
		return randomNumber.nextInt();

	}

	/**
	 * This method for use setup header request type application JSON
	 *
	 * @param httpHeaders
	 * @return Header : Accept : Application/JSON
	 */
	public static HttpHeaders httpHeaders(HttpHeaders httpHeaders) {
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return httpHeaders;
	}
}
