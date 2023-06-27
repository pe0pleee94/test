package id.co.maybank.digitallending.model;

import id.co.maybank.digitallending.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * Let's create entity class for generate table to schema database with the name (table : partner_info) and we use snake
 * case for naming format
 */
@Data
@Entity
@Table(name = "partner_info")
public class PartnerInfo extends BaseModel implements Serializable {

	/**
	 * @author muhammadmufqi - Digital Non Retail Division - IT Digital Delivery & Operation, PT Bank Maybank Indonesia Tbk
	 * @version 1.0
	 * @since 1.0 (Created June. 13, 2023)
	 */

	/**
	 * Using @{@link Serializable} that object can save to memory
	 */
	@Serial
	private static final long serialVersionUID = 5394048067546633838L;

	/**
	 * The column name is not filled in (details the name of table), so that it is generated by JPA. This is because the
	 * naming column from ERD conforms to the Java naming standard. (Standard naming java and naming ERD (Documentation)
	 * are match) We use snake case for the naming to database
	 */

	@Id
	@Column(length = 15)
	private Long partnerNik;

	@NotNull
	@Column(length = 20)
	private String type;

	/**
	 * Two identifier (NIK or NPWP) from different table and mapped to the @{@link StepTracker} table. Identifier from
	 * each table either cannot be null
	 */
	@NotNull
	@Column(length = 16)
	private Long linkedId;

	@NotNull
	@Column(length = 20)
	private String relation;
}
