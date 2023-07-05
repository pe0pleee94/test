package id.co.maybank.digitallending.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * To retrieve the inherited field in the form of a base model (not business field) that will be used to produce to the
 * database (e.g. created_date,updated_date,etc)
 */

/**
 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
 * @version 1.0
 * @since 1.0 (Created June. 22, 2023)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public class BaseModel implements Serializable {

	/**
	 * Using @{@link Serializable} that object can save to memory
	 */
	@Serial
	private static final long serialVersionUID = -2549485189624264408L;

	/**
	 * Using @{@link LocalDate} on this POJOs, because @{@link java.util.Date} contains malicious vulnerability if
	 * implements to POJOs. Date has mutable characteristic
	 */

	@NotNull
	private LocalDateTime createdDate;

	@NotNull
	private LocalDateTime updatedDate;

}
