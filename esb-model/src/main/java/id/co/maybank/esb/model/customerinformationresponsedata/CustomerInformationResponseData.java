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
package id.co.maybank.esb.model.customerinformationresponsedata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "encoding", "remoteprogram", "processcode", "userid", "poolname", "convid", "aid", "password",
		"datalength", "transid", "transcd", "cifno", "filler", "responsecode", "addrs_1", "addrs_2", "addrs_3", "city",
		"zip_code", "home_phone_area", "home_phone_no", "office_phone_area", "office_phone_no", "cif_name", "ss_tax",
		"cust_type", "ktp", "religion", "salutation", "title", "date_of_birth", "alt_name", "last_maint_date",
		"last_maint_time", "employer", "date_employed", "eod_code", "officer_1", "officer_2", "branch_code", "sex",
		"open_1st_acct", "educ_level", "marital_status", "inc_gross_amount", "inc_gross_scale", "own_rent",
		"businessreg", "taxid", "citizenship" })
public class CustomerInformationResponseData {

	@JsonProperty("encoding")
	private String encoding;

	@JsonProperty("remoteprogram")
	private String remoteProgram;

	@JsonProperty("processcode")
	private String processCode;

	@JsonProperty("userid")
	private String userId;

	@JsonProperty("poolname")
	private String poolName;

	@JsonProperty("convid")
	private String convid;

	@JsonProperty("aid")
	private String aid;

	@JsonProperty("password")
	private String password;

	@JsonProperty("datalength")
	private String dataLength;

	@JsonProperty("transid")
	private String transId;

	@JsonProperty("transcd")
	private String transCd;

	@JsonProperty("cifno")
	private String cifNo;

	@JsonProperty("filler")
	private String filler;

	@JsonProperty("responsecode")
	private String responseCode;

	@JsonProperty("addrs_1")
	private String addrs1;

	@JsonProperty("addrs_2")
	private String addrs2;

	@JsonProperty("addrs_3")
	private String addrs3;

	@JsonProperty("city")
	private String city;

	@JsonProperty("zip_code")
	private String zipCode;

	@JsonProperty("home_phone_area")
	private String homePhoneArea;

	@JsonProperty("home_phone_no")
	private String homePhoneNo;

	@JsonProperty("office_phone_area")
	private String officePhoneArea;

	@JsonProperty("office_phone_no")
	private String officePhoneNo;

	@JsonProperty("cif_name")
	private String cifName;

	@JsonProperty("ss_tax")
	private String ssTax;

	@JsonProperty("cust_type")
	private String custType;

	@JsonProperty("ktp")
	private String ktp;

	@JsonProperty("religion")
	private String religion;

	@JsonProperty("salutation")
	private String salutation;

	@JsonProperty("title")
	private String title;

	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	@JsonProperty("alt_name")
	private String altName;

	@JsonProperty("last_maint_date")
	private String lastMaintDate;

	@JsonProperty("last_maint_time")
	private String lastMaintTime;

	@JsonProperty("employer")
	private String employer;

	@JsonProperty("date_employed")
	private String dateEmployed;

	@JsonProperty("eod_code")
	private String eodCode;

	@JsonProperty("officer_1")
	private String officer1;

	@JsonProperty("officer_2")
	private String officer2;

	@JsonProperty("branch_code")
	private String branchCode;

	@JsonProperty("sex")
	private String sex;

	@JsonProperty("open_1st_acct")
	private String open1stAcct;

	@JsonProperty("educ_level")
	private String educLevel;

	@JsonProperty("marital_status")
	private String maritalStatus;

	@JsonProperty("inc_gross_amount")
	private String incGrossAmount;

	@JsonProperty("inc_gross_scale")
	private String incGrossScale;

	@JsonProperty("own_rent")
	private String ownRent;

	@JsonProperty("businessreg")
	private String businessReg;

	@JsonProperty("taxid")
	private String taxId;

	@JsonProperty("citizenship")
	private String citizenship;
}