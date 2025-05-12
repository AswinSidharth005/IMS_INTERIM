package com.cts.demo.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Customer entity mapped to the 'customer' table.
 */
@Entity
@Table(name = "CUSTOMER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@Positive(message = "The Value is Negative Kindly Provide Positive Value")
	private long customerId;
	
	@NotEmpty(message = "Kindly Ensure the Customer Name is Not NULL or BLANK")
	private String customerName;

	@Email(message = "Kindly Provide a Valid Mail ID")
	private String customerEmail;

	@Size(min = 10, max = 12, message = "Ensure Proper Mobile Format")
	private String customerPhone;

	@NotEmpty(message = "Make Sure the Address in Not NULL")
	private String customerAddress;

	//Connect CustomerID with Policies
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private List<Policy1> policies;

	/**
	 * Custom constructor without ID, typically used when ID is generated
	 * externally.
	 */
	public Customer(String customerName, String customerEmail, String customerPhone, String customerAddress,
			List<Policy1> policies) {
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.policies = policies;
	}
}