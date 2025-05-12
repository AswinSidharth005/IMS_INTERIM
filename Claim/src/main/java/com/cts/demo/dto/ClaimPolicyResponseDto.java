package com.cts.demo.dto;

import com.cts.demo.model.Claim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimPolicyResponseDto {

	private Claim claim;
	private Policy policy;
}
// Represents the combined details of a claim and its associated policy.