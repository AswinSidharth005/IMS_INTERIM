package com.cts.demo.service;

import java.util.List;

import com.cts.demo.exception.AgentNotFoundException;
import com.cts.demo.model.Agent;

/**
 * Interface for handling business logic related to Agent operations.
 */
public interface AgentService {

	/**
	 * New Agent is Saved
	 */
	String saveAgent(Agent agent) throws AgentNotFoundException;

	/**
	 * Updates an existing agent and returns the updated agent object.
	 * 
	 * @param agent The agent to be updated.
	 * @return The updated agent.
	 */
	Agent updateAgent(Agent agent) throws AgentNotFoundException;

	/**
	 * Deletes Agent with ID
	 * 
	 * @param agentId The ID of the agent to be deleted.
	 * @return Confirmation message after deletion.
	 */
	String deleteAgent(long agentId) throws AgentNotFoundException;

	/**
	 * Searches Agent By ID
	 * 
	 * @param agentId The ID of the agent to search for.
	 * @return The agent object.
	 * @throws AgentNotFoundException if the agent is not found.
	 */
	Agent searchAgentById(long agentId) throws AgentNotFoundException;

	/**
	 * Searches for an agent by their name and throws an exception if not found.
	 * 
	 * @param agentName The name of the agent to search for.
	 * @return The agent object.
	 * @throws AgentNotFoundException if the agent is not found.
	 */
	Agent searchAgentByName(String agentName) throws AgentNotFoundException;

	/**
	 * Retrieves a list of all agents.
	 * 
	 * @return List of all agents.
	 */
	List<Agent> getAllAgent() throws AgentNotFoundException;

	/**
	 * Assigns Policy and Return updated Object
	 */
	Agent assignPoliciesToAgent(long policyId, long agentId, String policyType) throws AgentNotFoundException;
}