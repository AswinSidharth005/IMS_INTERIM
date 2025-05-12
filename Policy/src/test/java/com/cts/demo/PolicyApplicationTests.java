package com.cts.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.demo.dto.Agent;
import com.cts.demo.dto.Customer;
import com.cts.demo.dto.Policy1;
import com.cts.demo.dto.Policy2;
import com.cts.demo.feignclient.AgentClient;
import com.cts.demo.feignclient.CustomerClient;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.project.Policy;
import com.cts.demo.repository.policyRepository;
import com.cts.demo.service.policyServiceImpl;

@ExtendWith(MockitoExtension.class)

class PolicyApplicationTests {

	@Mock
	policyRepository repository;

	@Mock
	AgentClient agentClient;

	@Mock
	CustomerClient customerClient;
	
	@Mock
	NotificationClient notificationClient;

	@InjectMocks
	policyServiceImpl service;

	@Test
	void savePolicy() {
		Policy policy = new Policy(123, "Health Policy", 70000, "hospinal expences", LocalDate.of(2025, 06, 07));
		Mockito.when(repository.save(policy)).thenReturn(policy);
		String response = service.savePolicy(policy);
		assertEquals("Policy saved successfully", response);
	}

	@Test
	void updatePolicy() {
		Policy policy1 = new Policy(123, "Health Policy", 80000, "hospinal expences", LocalDate.of(2025, 12, 07));
		Mockito.when(repository.save(policy1)).thenReturn(policy1);
		Policy response = service.updatePolicy(policy1);
		assertEquals(policy1, response);
	}

	@Test
	void retrievePolicy() {
		Policy policy1 = new Policy(123, "Health Policy", 80000, "hospinal expences", LocalDate.of(2025, 12, 07));
		Mockito.when(repository.findById(123L)).thenReturn(Optional.of(policy1));
		Policy result = service.retrievePolicy(123L);
		assertEquals(policy1, result);

	}

	@Test
	void archivePolicy() {
		Policy policy1 = new Policy(123, "Health Policy", 80000, "hospinal expences", LocalDate.of(2025, 12, 07));
		Mockito.doNothing().when(repository).deleteById(policy1.getPolicyId());
		String response = service.archivePolicy(policy1.getPolicyId());
		assertEquals("Policy deleted successfully", response);

	}

	@Test
	void retrieveAll() {
		List<Policy> list = Arrays.asList(
				new Policy(123, "Health Policy", 80000, "hospinal expences", LocalDate.of(2025, 12, 07)),
				new Policy(124, "Life Policy", 90000, "hospinal expences", LocalDate.of(2025, 12, 07)));
		Mockito.when(repository.findAll()).thenReturn(list);
		List<Policy> resPolicy = service.retrieveAll();
		assertEquals(list, resPolicy);
	}


	@Test
	void assignPolicyToAgent() {
		Policy policy1 = new Policy(123, "Health Policy", 80000, "hospinal expences", LocalDate.of(2025, 12, 07));
		Policy2 p1 = new Policy2();
		p1.setAssignedPolicies(policy1.getPolicyName());
		p1.setPolicyId(policy1.getPolicyId());
		List<Policy2> policies = new ArrayList<>();
		policies.add(p1);
		Agent agent = new Agent(2113017, "Bharath", "6382669477", policies);

		Mockito.when(agentClient.assignPoliciesToAgent(policy1.getPolicyId(), 1, policy1.getPolicyName()))
				.thenReturn(agent);
		Agent response = service.assignPolicyToAgent(policy1.getPolicyId(), 1, policy1.getPolicyName());
		assertEquals(agent, response);
	}
}
