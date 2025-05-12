package com.cts.demo.service;
import java.util.List;
import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.dto.PolicyAgentResponseDto;
import com.cts.demo.dto.PolicyCustResponseDto;
import com.cts.demo.project.Policy;
//Service interface for handling business logic related to Policy operations
public interface policyService {
   //Policy is Saved
   public abstract String savePolicy(Policy policy);
   //Policy is Updated
   public abstract Policy updatePolicy(Policy policy);
   //Retrieves Policy with help of ID
   public abstract Policy retrievePolicy(long policyId);
   //Deletes Policy with help of ID
   public abstract String archivePolicy(long policyId);
   //Retrieves All Policy
   public abstract List<Policy> retrieveAll();
   //Assign Policy to Customer
   public abstract Customer assignPolicyToCustomer(long policyId, long customerId, String policyType);
   //Assign Policy to Agent
   public abstract Agent assignPolicyToAgent(long policyId, long agentId, String policyType);
}