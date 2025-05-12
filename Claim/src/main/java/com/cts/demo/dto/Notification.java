package com.cts.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@Data

//Lombok annotation to generate a constructor with all fields
@AllArgsConstructor

//Lombok annotation to generate a no-argument constructor
@NoArgsConstructor
public class Notification {

//Unique identifier for the notification
	private long notificationId;

//ID of the customer to whom the notification is related
	private long customerId;

//ID of the policy associated with the notification
	private long policyId;

//Message content of the notification
	private String message;
}
