package id.co.maybank.digitallending.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 13, 2023)
 */
@Builder
public record EntryPointResponseDTO(@JsonProperty("nik") String nik, @JsonProperty("companyName") List<String> companyName,
		@JsonProperty("lengthOfBusiness") List<String> lengthOfBusiness,
		@JsonProperty("businessCategory") List<String> bussinessCategory, @JsonProperty("phoneNumber") String phoneNumber,
		@JsonProperty("email") String email, @JsonProperty("maritalStatus") String maritalStatus) {
}
