package com.cts.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.exception.AgentNotFoundException;
import com.cts.demo.model.Agent;
import com.cts.demo.model.Policy;
import com.cts.demo.repository.AgentRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of AgentService, handling the business logic for Agent-related
 * operations.
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {

// Logger for tracking operations
	Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

// Injecting the Agent Repository
	@Autowired
	private AgentRepository repository;

	/**
	 * Saves a new agent 
	 */
	@Override
	public String saveAgent(Agent agent) {
		log.info("Inside service implementation of save agent");
		Agent savedAgent = repository.save(agent);
//ternary operator
		return (savedAgent != null) ? "Agent saved successfully" : "Problem while saving the agent";
	}

	/**
	 * Updates an existing agent.
	 */
	@Override
	public Agent updateAgent(Agent agent) {
		log.info("Inside service implementation of update agent");
		return repository.save(agent);
	}

	/**
	 * Deletes an agent.
	 */
	@Override
	public String deleteAgent(long agentId) {
		log.info("Inside service implementation of delete agent");
		repository.deleteById(agentId);
		log.info("Agent with ID {} was deleted successfully", agentId);
		return "Agent deleted successfully";
	}

	/**
	 * Searches for an agent by their ID
	 */
	@Override
	public Agent searchAgentById(long agentId) throws AgentNotFoundException {
		log.info("Inside service implementation of search agent by ID");
		Agent agent = repository.findById(agentId)
				.orElseThrow(() -> new AgentNotFoundException("Enter correct AgentId"));
		return agent;
	}

	/**
	 * Searches for an agent by their name.
	 * @throws AgentNotFoundException If the agent is not found.
	 */
	@Override
	public Agent searchAgentByName(String agentName) throws AgentNotFoundException {
		log.info("Inside service implementation of search agent by name");
		Agent agent = repository.findByAgentName(agentName);
		if (agent != null) {
			log.info("Found agent by name: {}", agentName);
			return agent;
		} else {
			throw new AgentNotFoundException("Enter a valid Agent Name");
		}
	}

	/**
	 * Retrieves all agents from the database.
	 * 
	 * @return List of all agents.
	 */
	@Override
	public List<Agent> getAllAgent() {
		log.info("Inside service implementation of search all agents");
		return repository.findAll();
	}

	/**
	 * Assigns a policy to an agent and returns the updated agent object.
	 * 
	 * @param policyId   The ID of the policy to be assigned.
	 * @param agentId    The ID of the agent to whom the policy will be assigned.
	 * @param policyType The type of the policy being assigned.
	 * @return The updated agent with the assigned policy.
	 * @throws AgentNotFoundException If the agent is not found.
	 */
	@Override
	public Agent assignPoliciesToAgent(long policyId, long agentId, String policyType) throws AgentNotFoundException {
		log.info("Inside service implementation of assign policies to agent");
		Agent agent = repository.findById(agentId)
				.orElseThrow(() -> new AgentNotFoundException("Try a valid agent ID"));

		Policy policy = new Policy();
		policy.setPolicyId(policyId);
		policy.setAssignedPolicies(policyType);

		List<Policy> policies = new ArrayList<>(agent.getPolicies());
		policies.add(policy);
		agent.setPolicies(policies);

		repository.save(agent);
		log.info("Policy successfully assigned to agent with ID {}", agentId);
		return agent;
	}
}