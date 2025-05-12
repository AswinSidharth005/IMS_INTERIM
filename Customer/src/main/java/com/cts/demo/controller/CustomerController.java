package com.cts.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;
import com.cts.demo.service.CustomerService;

/*
* Controller to handle all customer-related API requests.
*/
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	//inject customer service dependency
	@Autowired
	private CustomerService service;
//	Add Customer Data
	@PostMapping("/create")
	public String saveCustomer(@RequestBody Customer customer)throws CustomerNotFoundException  {
		logger.info("Customer Created: {}", customer);
		return service.saveCustomer(customer);
	}
	//Update Customer Data
	@PutMapping("/update")
	public Customer updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
		logger.info("Customer Updated: {}", customer);
		return service.updateCustomer(customer);
	}
	//Delete Customer Data
	@DeleteMapping("/delete/{custId}")
	public String deleteCustomer(@PathVariable("custId") long custId) throws CustomerNotFoundException {
		logger.info("Customer Deleted: {}", custId);
		return service.deleteCustomer(custId);
	}
	//Get Customer Data With Id
	@GetMapping("/searchById/{custId}")
	public Customer searchCustomerById(@PathVariable("custId") long custId) throws CustomerNotFoundException {
		logger.info("Customer Retreived with ID: {}", custId);
		return service.searchCustomerById(custId);
	}
	//Get Customer Data With Name
	@GetMapping("/searchByName/{custName}")
	public Customer searchCustomerByName(@PathVariable("custName") String custName)throws CustomerNotFoundException  {
		logger.info("Customer Retreived with Name: {}", custName);
		return service.searchCustomerByName(custName);
	}
	
	//Assign Policies to Customer
	@PutMapping("/assignPoliciesToCustomer/{pid}/{cid}/{pType}")
	public Customer assignPoliciesToCustomer(@PathVariable("pid") long pid, @PathVariable("cid") long cid,
			@PathVariable("pType") String pType) throws CustomerNotFoundException {
		logger.info("Policy Id assigned: {} with type '{}' to Customer ID: {}", pid, pType, cid);
		return service.assignPoliciesToCustomer(pid, cid, pType);
	}
	@GetMapping("/findAll")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		logger.info("Controller: Fetching all customers");
		List<Customer> customers = service.getAllCustomer();
		if (customers.isEmpty()) {
			logger.info("Controller: No customers found");
			return ResponseEntity.noContent().build(); // 204 No Content if the list is empty
		} else {
			logger.info("Controller: Returning {} customers", customers.size());
			return ResponseEntity.ok(customers); // 200 OK with the list of customers
		}
	}
	
}
