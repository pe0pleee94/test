package id.co.maybank.digitallending.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ResumeResponse {
	
	@Getter @Setter
	private Map<String, String> message;
	
	@Getter @Setter
	private Integer stepComplete;
	
}
