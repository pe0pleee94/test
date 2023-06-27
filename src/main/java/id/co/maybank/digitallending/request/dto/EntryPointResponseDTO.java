package id.co.maybank.digitallending.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EntryPointResponseDTO(@JsonProperty("phoneNumber") String phoneNumber,
		@JsonProperty("email") String email, @JsonProperty("maritalStatus") String maritalStatus) {

	/**
	 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
	 * @version 1.0
	 * @since 1.0 (Created June. 13, 2023)
	 */
}
