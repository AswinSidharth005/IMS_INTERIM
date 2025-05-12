package com.cts.demo.dto;

import com.cts.demo.project.Policy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyCustResponseDto {
	private Customer customer;
	private Policy policy;
}
