package com.cts.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.cts.demo.exception.AgentNotFoundException;
import com.cts.demo.model.Agent;
import com.cts.demo.model.Policy;
import com.cts.demo.repository.AgentRepository;
import com.cts.demo.service.AgentServiceImpl;
@ExtendWith(MockitoExtension.class)

class AgentApplicationTests {

	@Mock
	AgentRepository repository;

	@InjectMocks
	AgentServiceImpl service;

	@Test
	void saveTest() {

		Policy p1 = new Policy();
		p1.setAssignedPolicies("Health");
		p1.setPolicyId(201);
		List<Policy> policies = new ArrayList<>();
		policies.add(p1);
		Agent agent1 = new Agent(43, "aswin", "aswin@gmail.com", policies);
		Mockito.when(repository.save(agent1)).thenReturn(agent1);
		String response = service.saveAgent(agent1);
		assertEquals("Agent saved successfully", response);
	}
	

//	@Test
//	void updateTest() throws AgentNotFoundException {
//		Policy p1 = new Policy();
//		p1.setAssignedPolicies("Health");
//		p1.setPolicyId(201);
//		List<Policy> policies = new ArrayList<>();
//		policies.add(p1);
//		Agent agent = new Agent(43, "aswin", "aswin@gmail.com", policies);
//		Mockito.when(repository.save(agent)).thenReturn(agent);
//		Agent cust = service.updateAgent(agent);
//		assertEquals(agent, cust);
//	}

//	@Test
//	void deleteTest() throws AgentNotFoundException {
//		Policy p1 = new Policy();
//		p1.setAssignedPolicies("Health");
//		p1.setPolicyId(201);
//		List<Policy> policies = new ArrayList<>();
//		policies.add(p1);
//		Agent agent = new Agent(43, "aswin", "aswin@gmail.com", policies);
//		long userId = agent.getAgentId();
//		Mockito.doNothing().when(repository).deleteById(userId);
//		String response = service.deleteAgent(userId);
//		assertEquals("Agent deleted successfully", response);
//
//	}

	@Test
	void searchAgentByIdTest() throws AgentNotFoundException {
		Policy p1 = new Policy();
		p1.setAssignedPolicies("Health");
		p1.setPolicyId(201);
		List<Policy> policies = new ArrayList<>();
		policies.add(p1);
		Agent agent = new Agent(43, "aswin", "aswin@gmail.com", policies);
		long agentId = 21113016;
		agent.setAgentId(agentId);
		Mockito.when(repository.findById(agentId)).thenReturn(Optional.of(agent));
		Agent foundAgentById = service.searchAgentById(agentId);
		assertEquals(agent, foundAgentById);
	}

	@Test
	void searchAgentByNameTest() throws AgentNotFoundException {
		String agentName = "aswin";
		Policy p1 = new Policy();
		p1.setAssignedPolicies("Health");
		p1.setPolicyId(201);
		List<Policy> policies = new ArrayList<>();
		policies.add(p1);
		Agent agent = new Agent(43, "aswin", "aswin@gmail.com", policies);
		Mockito.when(repository.findByAgentName(agentName)).thenReturn(agent);
		Agent foundAgentByName = service.searchAgentByName(agentName);
		assertEquals(agent, foundAgentByName);
	}

	@Test
	void getAllAgentTest() throws AgentNotFoundException {
		Policy p1 = new Policy();
		p1.setAssignedPolicies("Health");
		p1.setPolicyId(201);
		List<Policy> policies = new ArrayList<>();
		policies.add(p1);

		Policy p2 = new Policy();
		p2.setAssignedPolicies("Gold");
		p2.setPolicyId(202);
		List<Policy> policies2 = new ArrayList<>();
		policies2.add(p2);
		List<Agent> agents = Arrays.asList(new Agent(43, "aswin", "aswin@gmail.com", policies),
				new Agent(44, "aswins", "aswin1@gmail.com", policies2));
		Mockito.when(repository.findAll()).thenReturn(agents);
		List<Agent> custs = service.getAllAgent();
		assertEquals(agents, custs);
	}

}
