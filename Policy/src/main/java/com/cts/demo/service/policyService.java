package com.cts.demo.service;
import java.util.List;
import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.dto.PolicyAgentResponseDto;
import com.cts.demo.dto.PolicyCustResponseDto;
import com.cts.demo.exception.PolicyNotFoundException;
import com.cts.demo.project.Policy;
//Service interface for handling business logic related to Policy operations
public interface policyService {
   //Policy is Saved
   public abstract String savePolicy(Policy policy);
   //Policy is Updated
   public abstract Policy updatePolicy(Policy policy);
   //Retrieves Policy with help of ID
   public abstract Policy retrievePolicy(long policyId)throws PolicyNotFoundException;
   //Deletes Policy with help of ID
   public abstract String archivePolicy(long policyId)throws PolicyNotFoundException;
   //Retrieves All Policy
   public abstract List<Policy> retrieveAll()throws PolicyNotFoundException;
   //Assign Policy to Customer
   public abstract Customer assignPolicyToCustomer(long policyId, long customerId, String policyType)throws PolicyNotFoundException;
   //Assign Policy to Agent
   public abstract Agent assignPolicyToAgent(long policyId, long agentId, String policyType)throws PolicyNotFoundException;
}