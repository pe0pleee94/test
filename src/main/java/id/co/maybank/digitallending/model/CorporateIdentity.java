package id.co.maybank.digitallending.model;

import id.co.maybank.digitallending.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * Let's create entity class for generate table to schema database with the name (table : corporate_identity) and we
 * use snake case for naming format
 */

@Data
@Entity
@Table(name = "corporate_identity")
public class CorporateIdentity extends BaseModel implements Serializable {

	/**
	 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
	 * @version 1.0
	 * @since 1.0 (Created June. 13, 2023)
	 */

	/**
	 * Using @{@link Serializable} that object can save to memory
	 */
	@Serial
	private static final long serialVersionUID = -3688347163450987890L;

	/**
	 * The column name is not filled in (details the name of table), so that it is generated by JPA. This is because the
	 * naming column from ERD conforms to the Java naming standard. (Standard naming java and naming ERD (Documentation)
	 * are match) We use snake case for the naming to database
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 15)
	private Long npwp;

	@NotNull
	@Column(length = 13)
	private Long phoneNumber;

	/**
	 * This fetch type choose LAZY because not all record that will get from @{@link IndividualIdentity}
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pic_nik", nullable = false)
	private IndividualIdentity individualIdentity;

	@NotNull
	@Column(length = 3)
	private String category;
}
