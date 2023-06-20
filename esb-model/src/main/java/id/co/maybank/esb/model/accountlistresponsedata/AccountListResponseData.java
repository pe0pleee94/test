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
 * Using POJOs can modify string type,because this class has characteristic mutable. This is why using POJOs not Record class (JAVA 14)
 *
 */
package id.co.maybank.esb.model.accountlistresponsedata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import id.co.maybank.esb.model.accountdata.AccountData;
import id.co.maybank.esb.model.creditcarddata.CreditCardData;
import lombok.*;

import java.util.List;

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
@JsonPropertyOrder({ "GCIFNo", "CIFType", "CustomerName", "BirthDate", "Salutation", "Gender", "RaceCode", "IdentityNo",
		"MobileNo", "Email", "Address1", "Address2", "Address3", "Zipcode", "ResidentPhoneNo", "AccountData",
		"CreditCardData" })
public class AccountListResponseData {

	@JsonProperty("GCIFNo")
	private String gcifNo;

	@JsonProperty("CIFType")
	private String cifType;

	@JsonProperty("CustomerName")
	private String customerName;

	@JsonProperty("BirthDate")
	private String birthDate;

	@JsonProperty("Salutation")
	private String salutation;

	@JsonProperty("Gender")
	private String gender;

	@JsonProperty("RaceCode")
	private String raceCode;

	@JsonProperty("IdentityNo")
	private String identityNo;

	@JsonProperty("MobileNo")
	private String mobilePhone;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("Address1")
	private String address1;

	@JsonProperty("Address2")
	private String address2;

	@JsonProperty("Address3")
	private String address3;

	@JsonProperty("ZipCode")
	private String zipCode;

	@JsonProperty("ResidentPhoneNo")
	private String residentPhoneNo;

	@JsonProperty("AccountData")
	private List<AccountData> accountData;

	@JsonProperty("CreditCardData")
	private List<CreditCardData> creditCardData;
}
