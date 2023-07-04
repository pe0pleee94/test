package id.co.maybank.digitallending.validator;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TaxPayerValidator {

	public void validate(String taxPayerNumber) throws ValidationException {

		var msg = new LinkedHashMap<String, String>();
		if (StringUtils.isBlank(taxPayerNumber)) {
			msg.put("id", "Masukkan NPWP Perusahaan Anda");
			msg.put("en", "Please enter your Company's TaxPayer ID Number");
			throw new ValidationException(msg);

		}

		if (!taxPayerNumber.matches("\\d{15}")) {
			msg.put("id", "Nomor NPWP Perusahaan minimal 15 digit");
			msg.put("en", "Company's TaxPayer ID Number must be at least 15 digits long");
			throw new ValidationException(msg);

		}
	}

}
