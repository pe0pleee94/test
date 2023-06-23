package id.co.maybank.digitallending.repository;

import id.co.maybank.digitallending.model.CorporateIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateIdentityRepository extends JpaRepository<CorporateIdentity, Long> {

	/**
	 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
	 * @version 1.0
	 * @since 1.0 (Created June. 20, 2023)
	 */

}
