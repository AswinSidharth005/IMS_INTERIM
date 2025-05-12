package com.cts.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Policy assigned to an Agent.
 */
@Entity
@Table(name="AGENTPOLICY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

	@Id
	@Min(value = 1, message = "The Id should not be negative or zero...")
	private long policyId;

	@NotEmpty(message = "policies should not be empty...")
	private String assignedPolicies;
}
