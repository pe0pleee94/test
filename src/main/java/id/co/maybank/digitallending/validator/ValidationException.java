package id.co.maybank.digitallending.validator;

import java.util.Map;

import lombok.Getter;

public class ValidationException extends Exception {
	
	@Getter
	private Map<String, String> validationMessage;
	
	public ValidationException(Map<String, String> validationMessage) {
		
		this.validationMessage = validationMessage;
		
	}
	

}
