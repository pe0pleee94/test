package id.co.maybank.digitallending.service;

import id.co.maybank.digitallending.base.response.dto.Response;
import id.co.maybank.digitallending.dto.EntryPointRequestDTO;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 This is class interface for create abstract method and put the logic to other class that implement this
 * interface
 * @since 1.0 (Created June. 22, 2023)
 */
public interface EntryPointService {
	Response checkExistingCustomer(EntryPointRequestDTO entryPointRequestDTO);

}
