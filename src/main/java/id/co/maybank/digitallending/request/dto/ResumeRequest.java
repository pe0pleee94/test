package id.co.maybank.digitallending.request.dto;

import lombok.Getter;
import lombok.Setter;

public class ResumeRequest {
	
	@Getter @Setter
	private String businessType;
	
	@Getter @Setter
	private String idNumber;
	
	@Getter @Setter
	private String taxNumber;

}
