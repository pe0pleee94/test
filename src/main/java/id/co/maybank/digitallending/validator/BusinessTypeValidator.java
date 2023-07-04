package id.co.maybank.digitallending.validator;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import id.co.maybank.digitallending.common.BusinessTypeConst;

@Component
public class BusinessTypeValidator {

	public void validate(String businessType) throws ValidationException {

		var msg = new LinkedHashMap<String, String>();
		if (!StringUtils.equalsAny(businessType, BusinessTypeConst.CORPORATE, BusinessTypeConst.INDIVIDUAL)) {
			msg.put("id", "BUSINESS TYPE HARUS DIISI");
			msg.put("en", "BUSINESS TYPE HARUS DIISI");
			throw new ValidationException(msg);
		}
	}

}
