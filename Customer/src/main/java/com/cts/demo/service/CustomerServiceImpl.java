package com.cts.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;
import com.cts.demo.model.Policy1;
import com.cts.demo.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing customer-related operations.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	//Inject Customer Repository
	@Autowired
	private CustomerRepository repository;
	//saves new Customer
	@Override
	public String saveCustomer(Customer customer) {
		logger.info("Attempting to save customer: {}", customer);
		Customer saved = repository.save(customer);
		if (saved != null) {
			logger.info("Customer saved: {}", saved);
			return "Customer saved successfully";
		} else {
			logger.error("Customer saving failed for: {}", customer);
			return "Something went wrong";
		}
	}
	//Customer Values is Updated
	@Override
	public Customer updateCustomer(Customer customer) {
		logger.info("Updating existing customer: {}", customer);
		return repository.save(customer);
	}
	//Customer is Deleted
	@Override
	public String deleteCustomer(long customerId) {
		logger.info("Removing customer with ID: {}", customerId);
		repository.deleteById(customerId);
		return "Customer deleted successfully";
	}
	//Customer is Searched By ID
	@Override
	public Customer searchCustomerById(long customerId) throws CustomerNotFoundException {
		logger.info("Fetching customer with ID: {}", customerId);
		Optional<Customer> optional = repository.findById(customerId);
		if (optional.isPresent()) {
			logger.info("Customer retrieved: {}", optional.get());
			return optional.get();
		} else {
			logger.warn("No customer found with ID: {}", customerId);
			throw new CustomerNotFoundException("There is no customer in the given Id....");
		}
	}
	//Customer is Searched By Name
	@Override
	public Customer searchCustomerByName(String customerName) {
		logger.info("Looking up customer by name: {}", customerName);
		return repository.findByCustomerName(customerName);
	}
	//Retreive All Customer Details
	@Override
	public List<Customer> getAllCustomer() {
		logger.info("Retrieving complete customer list");
		return repository.findAll();
	}
	//Assign Policies to Customer
	@Override
	public Customer assignPoliciesToCustomer(long policyId, long customerId, String policyType)
			throws CustomerNotFoundException {
		logger.info("Initiating policy assignment: PolicyID={}, Type='{}', CustomerID={}", policyId, policyType,
				customerId);

		Optional<Customer> optional = repository.findById(customerId);
		if (!optional.isPresent()) {
			logger.warn("Assignment failed: Customer ID not found: {}", customerId);
			throw new CustomerNotFoundException("Try a valid customer Id...");
		}
		//Optional Class Helps to avoid or handle NULL values
		Customer customer = optional.get();

		Policy1 policy = new Policy1();
		policy.setPolicyId(policyId);
		policy.setPolicyType(policyType);

		List<Policy1> list = new ArrayList<>(customer.getPolicies());
		list.add(policy);
		customer.setPolicies(list);

		repository.save(customer);
		logger.info("Policy successfully linked to customer: {}", customer);
		return customer;
	}
}