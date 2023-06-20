/*
 * Copyright (c) 2023 PT Bank Maybank Indonesia Tbk and/or its affiliates. All rights reserved
 *
 * IT Digital & Operation
 * Digital Non Retail
 *
 * This code is distributed in the hope that it will be useful
 *
 * This is class represents POJO class for request and response to ESB Services.
 * It is an ordinary Java object. POJOs are used for increasing the readability and re-usability of a program
 * Using POJOs can modify string type,because this class has characteristic mutable.
 * This is why using POJOs not Record class (JAVA 14)
 *
 */
package id.co.maybank.esb.model.existingcustomerindividualrequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0
 * @since 1.0 (Created June. 13, 2023)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({ "name", "identityNo", "dateOfBirth" })
public class ExistingCustomerIndividualRequest {

	@JsonProperty("name")
	private String name;

	@JsonProperty("identityNo")
	private String identityNo;

	@JsonProperty("dateOfBirth")
	private String dateOfBirth;
}
