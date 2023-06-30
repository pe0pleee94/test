package id.co.maybank.digitallending.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.maybank.digitallending.common.BusinessTypeConst;
import id.co.maybank.digitallending.request.dto.ResumeRequest;
import id.co.maybank.digitallending.request.dto.ResumeResponse;

@RestController
public class ResumeController {
	
	@PostMapping("/resume/entrypoint")
	public ResponseEntity<ResumeResponse> resumeEntryPoint(@RequestBody final ResumeRequest request) {
		
		var res = new ResumeResponse();
		var businessType = StringUtils.stripToEmpty(request.getBusinessType());
		// jika business type required maka wajib diisi jangan kosong
		if (!StringUtils.equalsAny(businessType, BusinessTypeConst.CORPORATE, BusinessTypeConst.INDIVIDUAL)) {
			res.setMessageId("BUSINESS TYPE HARUS DIISI");
			res.setMessageEn("BUSINESS TYPE HARUS DIISI");
			return ResponseEntity.ok(res);
		}
		
		var idNumber = StringUtils.stripToEmpty(request.getIdNumber());
		// jika tidak ada validasi ktp kosong dikomen saja
		if (StringUtils.isBlank(idNumber)) {
			
			res.setMessageId("Masukkan Nomor KTP Anda");
			res.setMessageEn("Please enter your ID Card Number");
			return ResponseEntity.ok(res);
			
		}
		
		if (!idNumber.matches("\\d{16}")) {
			
			res.setMessageId("Nomor KTP harus berisi 16 digit");
			res.setMessageEn("ID Card Number must be contain 16 digits long");
			return ResponseEntity.ok(res);
			
		}
		
		var taxNumber = StringUtils.stripToEmpty(request.getTaxNumber());
		
		if (BusinessTypeConst.CORPORATE.equals(businessType)) {
			if (StringUtils.isBlank(taxNumber)) {
				
				res.setMessageId("Masukkan NPWP Perusahaan Anda");
				res.setMessageEn("Please enter your Company's TaxPayer ID Number");
				return ResponseEntity.ok(res);
				
			}
			
			if (!taxNumber.matches("\\d{15}")) {
				
				res.setMessageId("Nomor NPWP Perusahaan minimal 15 digit");
				res.setMessageEn("Company's TaxPayer ID Number must be at least 15 digits long");
				return ResponseEntity.ok(res);
				
			}
			
		}
		
		
		res.setMessageId("coba1");
		res.setMessageEn("test2");
		return ResponseEntity.ok(res);
	}

}
