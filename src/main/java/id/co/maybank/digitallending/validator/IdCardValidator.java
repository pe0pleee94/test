package id.co.maybank.digitallending.validator;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class IdCardValidator {

	public void validate(String idCardNumber) throws ValidationException {

		var msg = new LinkedHashMap<String, String>();
		if (StringUtils.isBlank(idCardNumber)) {

			msg.put("id", "Masukkan Nomor KTP Anda");
			msg.put("en", "Please enter your ID Card Number");
			throw new ValidationException(msg);

		}

		if (!idCardNumber.matches("\\d{16}")) {

			msg.put("id", "Nomor KTP harus berisi 16 digit");
			msg.put("en", "ID Card Number must be contain 16 digits long");
			throw new ValidationException(msg);

		}
	}

}
