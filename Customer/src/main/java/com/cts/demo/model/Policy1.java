package com.cts.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a Policy assigned to a customer.
 */
@Entity
@Table(name="CUSTOMERPOLICY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy1 {
	
	@Id
	private long policyId;

	@NotEmpty(message = "Make Sure the Policy Name is NOT EMPTY")
	private String policyType;
}
