package com.cts.demo.dto;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Generate getter and setter
@Data

//Constructor with all feilds
@AllArgsConstructor

//No arg Constructor
@NoArgsConstructor
public class Customer {

	// Customer Id
	private long customerId;

	// Customer Name
	private String customerName;

	// Customer Mail
	private String customerEmail;

	// Customer Mobile Number
	private String customerPhone;

	// Customer Address
	private String customerAddress;

	//Assigns all changes to Both entity
	//Loads At the time of Calling
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private List<Policy1> policies;
}
