package id.co.maybank.digitallending.controller;

import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.request.dto.EntryPointRequestDTO;
import id.co.maybank.digitallending.service.EntryPointService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
@RestController
@RequestMapping("/v1/entry-point")
public class EntryPointController {

	EntryPointService entryPointService;

	public EntryPointController(EntryPointService entryPointService) {
		this.entryPointService = entryPointService;
	}

	@PostMapping("/check")
	public ResponseEntity<Response> check(@RequestBody EntryPointRequestDTO entryPointRequestDTO) {
		Response response = entryPointService.checkExistingCustomer(entryPointRequestDTO);
		return new ResponseEntity<Response>(Response.builder().build(), HttpStatusCode.valueOf(200));
	}
}
