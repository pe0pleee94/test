package id.co.maybank.digitallending.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record EntryPointRequestDTO(@JsonProperty("nik") String Nik, @JsonProperty("name") String name,
                                   @JsonProperty("dob")
                                   String dob) {

    /**
     * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
     * @version 1.0
     * @since 1.0 (Created June. 13, 2023)
     */
}
