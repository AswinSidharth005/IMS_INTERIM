package com.cts.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.exception.PolicyNotFoundException;
import com.cts.demo.feignclient.AgentClient;
import com.cts.demo.feignclient.CustomerClient;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.project.Policy;
import com.cts.demo.repository.policyRepository;
import com.cts.demo.service.policyServiceImpl;

public class PolicyApplicationTests {

    @Mock
    private policyRepository repository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private AgentClient agentClient;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private policyServiceImpl policyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePolicy() {
        Policy policy = new Policy();
        when(repository.save(policy)).thenReturn(policy);

        String result = policyService.savePolicy(policy);
        assertEquals("Policy saved successfully", result);
        verify(repository, times(1)).save(policy);
        verify(notificationClient, times(1)).notify(anyString(), anyLong(), anyLong());
    }

    @Test
    public void testUpdatePolicy() {
        Policy policy = new Policy();
        when(repository.save(policy)).thenReturn(policy);

        Policy result = policyService.updatePolicy(policy);
        assertEquals(policy, result);
        verify(repository, times(1)).save(policy);
    }

    @Test
    public void testRetrievePolicy() throws PolicyNotFoundException {
        long policyId = 1L;
        Policy policy = new Policy();
        when(repository.findById(policyId)).thenReturn(Optional.of(policy));

        Policy result = policyService.retrievePolicy(policyId);
        assertEquals(policy, result);
        verify(repository, times(1)).findById(policyId);
    }



    @Test
    public void testArchivePolicy() throws PolicyNotFoundException {
        long policyId = 1L;
        when(repository.existsById(policyId)).thenReturn(true);

        String result = policyService.archivePolicy(policyId);
        assertEquals("Policy deleted successfully", result);
        verify(repository, times(1)).deleteById(policyId);
    }

    @Test
    public void testArchivePolicyNotFound() {
        long policyId = 1L;
        when(repository.existsById(policyId)).thenReturn(false);

        assertThrows(PolicyNotFoundException.class, () -> {
            policyService.archivePolicy(policyId);
        });
        verify(repository, never()).deleteById(policyId);
    }


    @Test
    public void testRetrieveAllNotFound() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(PolicyNotFoundException.class, () -> {
            policyService.retrieveAll();
        });
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testAssignPolicyToCustomer() throws PolicyNotFoundException {
        long policyId = 1L;
        long customerId = 1L;
        String policyType = "Health";
        when(repository.existsById(policyId)).thenReturn(true);
        Customer customer = new Customer();
        when(customerClient.assignPoliciesToCustomer(policyId, customerId, policyType)).thenReturn(customer);

        Customer result = policyService.assignPolicyToCustomer(policyId, customerId, policyType);
        assertEquals(customer, result);
        verify(repository, times(1)).existsById(policyId);
        verify(notificationClient, times(1)).notify(anyString(), eq(customerId), eq(policyId));
        verify(customerClient, times(1)).assignPoliciesToCustomer(policyId, customerId, policyType);
    }

    @Test
    public void testAssignPolicyToCustomerNotFound() {
        long policyId = 1L;
        long customerId = 1L;
        String policyType = "Health";
        when(repository.existsById(policyId)).thenReturn(false);

        assertThrows(PolicyNotFoundException.class, () -> {
            policyService.assignPolicyToCustomer(policyId, customerId, policyType);
        });
        verify(repository, times(1)).existsById(policyId);
        verify(notificationClient, never()).notify(anyString(), eq(customerId), eq(policyId));
        verify(customerClient, never()).assignPoliciesToCustomer(policyId, customerId, policyType);
    }

    @Test
    public void testAssignPolicyToAgent() throws PolicyNotFoundException {
        long policyId = 1L;
        long agentId = 1L;
        String policyType = "Health";
        when(repository.existsById(policyId)).thenReturn(true);
        Agent agent = new Agent();
        when(agentClient.assignPoliciesToAgent(policyId, agentId, policyType)).thenReturn(agent);

        Agent result = policyService.assignPolicyToAgent(policyId, agentId, policyType);
        assertEquals(agent, result);
        verify(repository, times(1)).existsById(policyId);
        verify(agentClient, times(1)).assignPoliciesToAgent(policyId, agentId, policyType);
    }

    @Test
    public void testAssignPolicyToAgentNotFound() {
        long policyId = 1L;
        long agentId = 1L;
        String policyType = "Health";
        when(repository.existsById(policyId)).thenReturn(false);

        assertThrows(PolicyNotFoundException.class, () -> {
            policyService.assignPolicyToAgent(policyId, agentId, policyType);
        });
        verify(repository, times(1)).existsById(policyId);
        verify(agentClient, never()).assignPoliciesToAgent(policyId, agentId, policyType);
    }
}
