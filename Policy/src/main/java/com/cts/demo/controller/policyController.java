package com.cts.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.project.Policy;
import com.cts.demo.service.policyService;
//Response Body + Controller
@RestController
// EndPoint URL
@RequestMapping("/policy")
public class policyController {
	// Log Info
	private static final Logger logger = LoggerFactory.getLogger(policyController.class);
	// Injects the policyService to handle business logic
	@Autowired
	private policyService service;

	// Save Policy(NEW)
	@PostMapping("/save")
	public String savePolicy(@RequestBody Policy policy) {
		logger.info("Saving policy: {}", policy);
		return service.savePolicy(policy);
	}

	//Update Policy
	@PutMapping("/update")
	public Policy updatePolicy(@RequestBody Policy policy) {
		logger.info("Updating policy: {}", policy);
		return service.updatePolicy(policy);
	}

	// Retreive Policy
	@GetMapping("/retrieveById/{pid}")
	public Policy retrievePolicy(@PathVariable("pid") long policyId) {
		logger.info("Retrieving policy by ID: {}", policyId);
		return service.retrievePolicy(policyId);
	}

	// Delete Policy
	@DeleteMapping("/deletePolicy/{pid}")
	public String archivePolicy(@PathVariable("pid") long policyId) {
		logger.info("Archiving policy with ID: {}", policyId);
		return service.archivePolicy(policyId);
	}

	// Retreive All Policy
	@GetMapping("/retrieveAll")
	public List<Policy> retrieveAll() {
		logger.info("Retrieving all policies");
		return service.retrieveAll();
	}

	// Assign Customer to Policy
	@PutMapping("/assignPoliciesToCustomer/{pid}/{cid}/{pType}")
	public Customer assignPoliciesToCustomer(@PathVariable("pid") long policyId, @PathVariable("cid") long customerId,
			@PathVariable("pType") String policyType) {
		logger.info("Assigning policy (ID: {}) of type '{}' to customer (ID: {})", policyId, policyType, customerId);
		return service.assignPolicyToCustomer(policyId, customerId, policyType);
	}

	// Assign Policy to Agent
	@PutMapping("/assignPoliciesToAgent/{pid}/{aid}/{pType}")
	public Agent assignPoliciesToAgent(@PathVariable("pid") long policyId, @PathVariable("aid") long agentId,
			@PathVariable("pType") String policyType) {
		logger.info("Assigning policy (ID: {}) of type '{}' to agent (ID: {})", policyId, policyType, agentId);
		return service.assignPolicyToAgent(policyId, agentId, policyType);
	}
}