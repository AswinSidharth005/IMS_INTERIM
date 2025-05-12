package com.cts.demo.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.exception.PolicyNotFoundException;
import com.cts.demo.feignclient.AgentClient;
import com.cts.demo.feignclient.CustomerClient;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.project.Policy;
import com.cts.demo.repository.policyRepository;

@Service("policyService")
public class policyServiceImpl implements policyService {

	Logger logger = LoggerFactory.getLogger(policyServiceImpl.class);

	@Autowired
	policyRepository repository;

	@Autowired
	CustomerClient customerClient;

	@Autowired
	AgentClient agentClient;

	@Autowired
	NotificationClient notificationClient;

	/**
	 * Saves a new policy to the database and sends a notification.
	 * 
	 * @param policy The policy object to be saved.
	 * @return A message indicating the successful saving of the policy.
	 */
	@Override
	public String savePolicy(Policy policy) {
		logger.info("Saving policy: {}", policy);
		Policy savedPolicy = repository.save(policy);
		logger.info("Policy saved: {}", savedPolicy);
		// Notify about the new policy creation
		notificationClient.notify("A New Policy " + savedPolicy.getPolicyName() + " has been created",
				savedPolicy.getPolicyId(), savedPolicy.getPolicyId());
		logger.info("Notification sent for new policy creation: {}", savedPolicy.getPolicyName());
		return "Policy saved successfully";
	}

	/**
	 * Updates an existing policy in the database.
	 * 
	 * @param policy The policy object with updated information.
	 * @return The updated policy object.
	 * @throws PolicyNotFoundException if the policy with the given ID does not
	 *                                 exist.
	 */
	@Override
	public Policy updatePolicy(Policy policy) {
		logger.info("Updating policy: {}", policy);
		return repository.save(policy);
	}

	/**
	 * Retrieves a policy from the database based on its ID.
	 * 
	 * @param policyId The ID of the policy to retrieve.
	 * @return The policy object if found.
	 * @throws PolicyNotFoundException if the policy with the given ID is not found.
	 */
	@Override
	public Policy retrievePolicy(long policyId) throws PolicyNotFoundException {
		logger.info("Retrieving policy with ID: {}", policyId);
		Optional<Policy> optionalPolicy = repository.findById(policyId);
		if (optionalPolicy.isPresent()) {
			logger.info("Policy found: {}", optionalPolicy.get());
			return optionalPolicy.get();
		} else {
			logger.warn("Policy not found with ID: {}", policyId);
			return null;
		}
	}

	/**
	 * Deletes a policy from the database based on its ID.
	 * 
	 * @param policyId The ID of the policy to delete.
	 * @return A message indicating the successful deletion of the policy.
	 * @throws PolicyNotFoundException if the policy with the given ID does not
	 *                                 exist.
	 */
	@Override
	public String archivePolicy(long policyId) throws PolicyNotFoundException  {
	    logger.info("Archiving policy with ID: {}", policyId);
	    if (!repository.existsById(policyId)) {
	        logger.warn("Policy with ID {} not found.", policyId);
	        throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
	    }
	    repository.deleteById(policyId);
	    return "Policy deleted successfully";
	}

	/**
	 * Retrieves all policies from the database.
	 *
	 * @return A list of all policy objects.
	 * @throws PolicyNotFoundException 
	 */
	@Override
	public List<Policy> retrieveAll() throws PolicyNotFoundException {
	    logger.info("Retrieving all policies");
	    List<Policy> policies = repository.findAll();
	    if (policies.isEmpty()) {
	        logger.warn("No policies found in the database.");
	        throw new PolicyNotFoundException("No policies found in the database.");
	    }
	    return policies;
	}

	/**
	 * Assigns a policy tto a customer and sends a notification to the customer.
	 * This method also calls the CustomerClient to update the customer's policy
	 * list.
	 * 
	 * @param policyId   The ID of the policy to assign.
	 * @param customerId The ID of the customer to assign the policy to.
	 * @param policyType The type of the policy being assigned.
	 * @return The updated Customer object after assigning the policy.
	 * @throws PolicyNotFoundException if the policy with the given ID does not
	 *                                 exist.
	 */
	@Override
	public Customer assignPolicyToCustomer(long policyId, long customerId, String policyType)
			throws PolicyNotFoundException {
		logger.info("Assigning policy (ID: {}) of type '{}' to customer (ID: {})", policyId, policyType, customerId);
		if (!repository.existsById(policyId)) {
			logger.warn("Policy not found with ID: {}", policyId);
			throw new PolicyNotFoundException("Policy with ID " + policyId + " not found");
		}
		// Notify the customer about the policy assignment
		notificationClient.notify(policyType + " is assigned to you", customerId, policyId);
		// Delegate to the CustomerClient to update the customer's policies
		return customerClient.assignPoliciesToCustomer(policyId, customerId, policyType);
	}

	/**
	 * Assigns a policy to an agent. This method calls the AgentClient to update the
	 * agent's policy list.
	 * 
	 * @param policyId   The ID of the policy to assign.
	 * @param agentId    The ID of the agent to assign the policy to.
	 * @param policyType The type of the policy being assigned.
	 * @return The updated Agent object after assigning the policy.
	 * @throws PolicyNotFoundException if the policy with the given ID does not
	 *                                 exist.
	 */
	@Override
	public Agent assignPolicyToAgent(long policyId, long agentId, String policyType) throws PolicyNotFoundException {
		logger.info("Assigning policy (ID: {}) of type '{}' to agent (ID: {})", policyId, policyType, agentId);
		if (!repository.existsById(policyId)) {
			logger.warn("Policy not found with ID: {}", policyId);
			throw new PolicyNotFoundException("Policy with ID " + policyId + " not found");
		}
		// Delegate to the AgentClient to update the agent's policies
		return agentClient.assignPoliciesToAgent(policyId, agentId, policyType);
	}
}
//package com.cts.demo.service;
//
//import java.util.List;
//import java.util.Optional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.cts.demo.dto.Agent;
//import com.cts.demo.dto.Customer;
//import com.cts.demo.feignclient.AgentClient;
//import com.cts.demo.feignclient.CustomerClient;
//import com.cts.demo.feignclient.NotificationClient;
//import com.cts.demo.project.Policy;
//import com.cts.demo.repository.policyRepository;
//
//@Service("policyService")
//public class policyServiceImpl implements policyService {
//
//    Logger logger = LoggerFactory.getLogger(policyServiceImpl.class);
//
//    @Autowired
//    policyRepository repository;
//
//    @Autowired
//    CustomerClient customerClient;
//
//    @Autowired
//    AgentClient agentClient;
//
//    @Autowired
//    NotificationClient notificationClient;
//
//    /**
//     * Saves a new policy to the database and sends a notification.
//     * @param policy The policy object to be saved.
//     * @return A message indicating the successful saving of the policy.
//     */
//    @Override
//    public String savePolicy(Policy policy) {
//        logger.info("Saving policy: {}", policy);
//        Policy savedPolicy = repository.save(policy);
//        logger.info("Policy saved: {}", savedPolicy);
//        // Notify about the new policy creation
//        notificationClient.notify("A New Policy " + savedPolicy.getPolicyName() + " has been created", savedPolicy.getPolicyId(),
//                savedPolicy.getPolicyId());
//        logger.info("Notification sent for new policy creation: {}", savedPolicy.getPolicyName());
//        return "Policy saved successfully";
//    }
//
//    /**
//     * Updates an existing policy in the database.
//     * @param policy The policy object with updated information.
//     * @return The updated policy object.
//     */
//    @Override
//    public Policy updatePolicy(Policy policy) {
//        logger.info("Updating policy: {}", policy);
//        return repository.save(policy);
//    }
//
//    /**
//     * Retrieves a policy from the database based on its ID.
//     * @param policyId The ID of the policy to retrieve.
//     * @return The policy object if found, otherwise null.
//     */
//    @Override
//    public Policy retrievePolicy(long policyId) {
//        logger.info("Retrieving policy with ID: {}", policyId);
//        Optional<Policy> optionalPolicy = repository.findById(policyId);
//        if (optionalPolicy.isPresent()) {
//            logger.info("Policy found: {}", optionalPolicy.get());
//            return optionalPolicy.get();
//        } else {
//            logger.warn("Policy not found with ID: {}", policyId);
//            return null;
//        }
//    }
//
//    /**
//     * Deletes a policy from the database based on its ID.
//     * @param policyId The ID of the policy to delete.
//     * @return A message indicating the successful deletion of the policy.
//     */
//    @Override
//    public String archivePolicy(long policyId) {
//        logger.info("Archiving policy with ID: {}", policyId);
//        repository.deleteById(policyId);
//        return "Policy deleted successfully";
//    }
//
//    /**
//     * Retrieves all policies from the database.
//     * @return A list of all policy objects.
//     */
//    @Override
//    public List<Policy> retrieveAll() {
//        logger.info("Retrieving all policies");
//        return repository.findAll();
//    }
//
//    /**
//     * Assigns a policy to a customer and sends a notification to the customer.
//     * This method also calls the CustomerClient to update the customer's policy list.
//     * @param policyId The ID of the policy to assign.
//     * @param customerId The ID of the customer to assign the policy to.
//     * @param policyType The type of the policy being assigned.
//     * @return The updated Customer object after assigning the policy.
//     */
//    @Override
//    public Customer assignPolicyToCustomer(long policyId, long customerId, String policyType) {
//        logger.info("Assigning policy (ID: {}) of type '{}' to customer (ID: {})", policyId, policyType, customerId);
//        // Notify the customer about the policy assignment
//        notificationClient.notify(policyType + " is assigned to you", customerId, policyId);
//        // Delegate to the CustomerClient to update the customer's policies
//        return customerClient.assignPoliciesToCustomer(policyId, customerId, policyType);
//    }
//
//    /**
//     * Assigns a policy to an agent.
//     * This method calls the AgentClient to update the agent's policy list.
//     * @param policyId The ID of the policy to assign.
//     * @param agentId The ID of the agent to assign the policy to.
//     * @param policyType The type of the policy being assigned.
//     * @return The updated Agent object after assigning the policy.
//     */
//    @Override
//    public Agent assignPolicyToAgent(long policyId, long agentId, String policyType) {
//        logger.info("Assigning policy (ID: {}) of type '{}' to agent (ID: {})", policyId, policyType, agentId);
//        // Delegate to the AgentClient to update the agent's policies
//        return agentClient.assignPoliciesToAgent(policyId, agentId, policyType);
//    }
//}