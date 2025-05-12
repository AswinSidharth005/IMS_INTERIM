package com.cts.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data

@AllArgsConstructor

@NoArgsConstructor
public class Notification {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long notificationId;

	private long customerId;

	private long policyId;

	private String message;
}
