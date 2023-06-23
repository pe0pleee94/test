package id.co.maybank.digitallending.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0
 * General Util Class
 * @since 1.0 (Created June. 22, 2023)
 */
public class Util {

    public static final String LOG_DATE_FORMAT = "yyyyMMddHHmmssSSS";

    public static final String HYVHEN_SEPARATOR = "-";

    /**
     * This method generate unique id log identifier
     *
     * @param String serviceName
     * @return String uniqueId
     */
    public static String uniqueRandom(String serviceName) {
        var dtf = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
        var uniqueId = new StringBuilder();
        var currentDateTime = LocalDateTime.now();

        String datePattern = dtf.format(currentDateTime);
        uniqueId.append(serviceName);
        uniqueId.append(HYVHEN_SEPARATOR);
        uniqueId.append(datePattern);

        return uniqueId.toString();
    }
}
