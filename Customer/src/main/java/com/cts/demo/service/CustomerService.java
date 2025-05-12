package com.cts.demo.service;

import java.util.List;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;

/**
 * Service interface for handling operations related to Customer management.
 */
public interface CustomerService {

	/**
	 * Adds a new customer record.
	 */
	String saveCustomer(Customer customer);

	/**
	 * Updates the details of an existing customer.
	 */
	Customer updateCustomer(Customer customer);

	/**
	 * Removes a customer based on their ID.
	 */
	String deleteCustomer(long customerId);

	/**
	 * Fetches a customer by ID. Throws CustomerNotFoundException if not found.
	 */
	Customer searchCustomerById(long customerId) throws CustomerNotFoundException;

	/**
	 * Retrieves a customer based on their name.
	 */
	Customer searchCustomerByName(String customerName);

	/**
	 * Returns a list of all customers.
	 */
	List<Customer> getAllCustomer();

	/**
	 * Links a policy to a customer using policy ID, customer ID, and policy type.
	 * Throws CustomerNotFoundException if customer is not available.
	 */
	Customer assignPoliciesToCustomer(long policyId, long customerId, String policyType)
			throws CustomerNotFoundException;
}