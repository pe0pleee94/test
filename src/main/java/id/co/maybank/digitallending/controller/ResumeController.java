package id.co.maybank.digitallending.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.maybank.digitallending.request.dto.ResumeRequest;
import id.co.maybank.digitallending.request.dto.ResumeResponse;
import id.co.maybank.digitallending.service.ResumeEntryPointService;
import id.co.maybank.digitallending.validator.ValidationException;

@RestController
public class ResumeController {

	@Autowired
	private ResumeEntryPointService resEntPoServ;

	@PostMapping("/resume/entrypoint")
	public ResponseEntity<ResumeResponse> resumeEntryPoint(@RequestBody @Valid ResumeRequest request) {

		try {

			return ResponseEntity.ok(this.resEntPoServ.inquiry(request.getIdNumber(), request.getBusinessType(),
					request.getTaxNumber()));

		} catch (ValidationException e) {

			var res = new ResumeResponse();
			res.setMessage(e.getValidationMessage());
			return ResponseEntity.badRequest().body(res);
		}

	}

}
