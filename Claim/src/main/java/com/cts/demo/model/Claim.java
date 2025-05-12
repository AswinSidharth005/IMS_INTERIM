package com.cts.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLAIM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long claimId; // Unique claim ID
	private long customerId; // ID of the customer
	private long policyId; // ID of the policy
	@Min(value = 20000, message = "The minimum amount should be 20000rs")
	@Max(value = 500000, message = "The maximum amount should be 500000rs")
	private double claimAmount; // Claimed amount
	@NotEmpty(message = "The status should not be empty : IN-REVIEW, ACCEPTED, REJECTED")
	private String claimStatus; // Current claim status

	public Claim(long customerId, long policyId, double claimAmount, String claimStatus) {
		this.customerId = customerId;
		this.policyId = policyId;
		this.claimAmount = claimAmount;
		this.claimStatus = claimStatus;
	}
}