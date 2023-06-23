package id.co.maybank.digitallending.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EntryPointResponseDTO(@JsonProperty("companyName") String companyName,
                                    @JsonProperty("lengthOfBusiness") String lengthOfBusiness,
                                    @JsonProperty("businessCategory") String bussinessCategory) {
    /**
     * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
     * @version 1.0
     * @since 1.0 (Created June. 13, 2023)
     */
}
