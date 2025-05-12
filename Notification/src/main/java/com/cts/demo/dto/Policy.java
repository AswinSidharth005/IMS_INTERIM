package com.cts.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor
public class Policy {

	private long policyId;

	private String policyName;

	private double premiumAmount;

	private String coverageDetails;

	private LocalDate validityPeriod;
}
