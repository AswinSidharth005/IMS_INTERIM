package com.cts.demo.controller;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;
import com.cts.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public String saveCustomer(@RequestBody Customer customer) {
		logger.info("Customer Created: {}", customer);
		return service.saveCustomer(customer);
	}
	//Update Customer Data
	@PutMapping("/update")
	public Customer updateCustomer(@RequestBody Customer customer) {
		logger.info("Customer Updated: {}", customer);
		return service.updateCustomer(customer);
	}
	//Delete Customer Data
	@DeleteMapping("/delete/{custId}")
	public String deleteCustomer(@PathVariable("custId") long custId) {
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
	public Customer searchCustomerByName(@PathVariable("custName") String custName) {
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
}
