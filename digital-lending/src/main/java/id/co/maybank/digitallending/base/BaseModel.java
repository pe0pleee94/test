package id.co.maybank.digitallending.base;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * To retrieve the inherited field in the form of a base model (not business field) that will be used to produce to the
 * database (e.g. created_date,updated_date,etc)
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public class BaseModel implements Serializable {

	/**
	 * @author muhammadmufqi - Digital Non Retail Division
	 * @version 1.0
	 * @since 1.0 (Created June. 13, 2023)
	 */

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
	private LocalDate createdDate;

	@NotNull
	private LocalDate updatedDate;

}
