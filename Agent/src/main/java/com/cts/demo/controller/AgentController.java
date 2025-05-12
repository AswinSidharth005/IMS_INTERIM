package com.cts.demo.controller;

import java.util.List;

import com.cts.demo.exception.AgentNotFoundException;
import com.cts.demo.model.Agent;
import com.cts.demo.service.AgentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to manage Agent-related operations.
 */
@RestController
@RequestMapping("/agent")
public class AgentController {

	private static final Logger log = LoggerFactory.getLogger(AgentController.class);

	@Autowired
	private AgentService service;

	/**
	 * Adds a new agent to the system.
	 */
	@PostMapping("/save")
	public String saveAgent(@RequestBody @Validated Agent Agent) throws AgentNotFoundException {
		log.info("Agent Saved");
		return service.saveAgent(Agent);
	}

	/**
	 * Updates existing agent information.
	 */
	@PutMapping("/update")
	public Agent updateAgent(@RequestBody @Validated Agent Agent) throws AgentNotFoundException {
		log.info("Agent Updated");
		return service.updateAgent(Agent);
	}

	/**
	 * Deletes an agent based on the provided ID.
	 */
	@DeleteMapping("/delete/{aid}")
	public String deleteAgent(@PathVariable("aid") @Validated long AgentId) throws AgentNotFoundException {
		log.info("Agent Deleted");
		return service.deleteAgent(AgentId);
	}

	/**
	 * Retrieves an agent by their ID.
	 */
	@GetMapping("/searchById/{aid}")
	public Agent searchAgentById(@PathVariable("aid") @Validated long AgentId) throws AgentNotFoundException {
		log.info("Agent Searched by ID");
		return service.searchAgentById(AgentId);
	}

	/**
	 * Retrieves an agent by their name.
	 */
	@GetMapping("/searchByName/{aname}")
	public Agent searchAgentByName(@PathVariable("aname") @Validated String AgentName) throws AgentNotFoundException {
		log.info("Agent Searched By Name");
		return service.searchAgentByName(AgentName);
	}

	/**
	 * Fetches a list of all agents in the system.
	 */
	@GetMapping("/searchAll")
	public List<Agent> searchAll() throws AgentNotFoundException {
		log.info("Search All Agents ");
		return service.getAllAgent();
	}

	/**
	 * Assigns a policy to a specific agent.
	 */
	@PutMapping("/assignPoliciesToAgent/{pid}/{aid}/{ptype}")
	public Agent assignPoliciesToAgent(@PathVariable("pid") long policyId, @PathVariable("aid") long agentId,
			@PathVariable("ptype") String policyType) throws AgentNotFoundException {
		log.info("Assign Policy to Agent");
		return service.assignPoliciesToAgent(policyId, agentId, policyType);
	}
}