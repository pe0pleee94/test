package id.co.maybank.digitallending.base.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatusCode;

public record Header(@JsonProperty("trxLog") String trxLog, @JsonProperty("processingTime") long processingTime,                     @JsonProperty("responseCode")
                     HttpStatusCode httpStatusCode, @JsonProperty("responseMessage") String responseMessage) {
    /**
     * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
     * @version 1.0
     * @since 1.0 (Created June. 22, 2023)
     */
}
