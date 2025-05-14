package com.cts.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.cts.demo.exception.AgentNotFoundException;
import com.cts.demo.model.Agent;
import com.cts.demo.repository.AgentRepository;
import com.cts.demo.service.AgentServiceImpl;

public class AgentApplicationTests {

    @Mock
    private AgentRepository repository;

    @InjectMocks
    private AgentServiceImpl agentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAgent() {
        Agent agent = new Agent();
        when(repository.save(agent)).thenReturn(agent);

        String result = agentService.saveAgent(agent);
        assertEquals("Agent saved successfully", result);
        verify(repository, times(1)).save(agent);
    }

    @Test
    public void testUpdateAgent() throws AgentNotFoundException {
        Agent agent = new Agent();
        agent.setAgentId(1L);
        when(repository.existsById(agent.getAgentId())).thenReturn(true);
        when(repository.save(agent)).thenReturn(agent);

        Agent result = agentService.updateAgent(agent);
        assertEquals(agent, result);
        verify(repository, times(1)).save(agent);
    }

    @Test
    public void testUpdateAgentNotFound() {
        Agent agent = new Agent();
        agent.setAgentId(1L);
        when(repository.existsById(agent.getAgentId())).thenReturn(false);

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.updateAgent(agent);
        });
        verify(repository, never()).save(agent);
    }

    @Test
    public void testDeleteAgent() throws AgentNotFoundException {
        long agentId = 1L;
        Agent agent = new Agent();
        when(repository.findById(agentId)).thenReturn(Optional.of(agent));

        String result = agentService.deleteAgent(agentId);
        assertEquals("Agent deleted successfully", result);
        verify(repository, times(1)).deleteById(agentId);
    }



    @Test
    public void testSearchAgentById() throws AgentNotFoundException {
        long agentId = 1L;
        Agent agent = new Agent();
        when(repository.findById(agentId)).thenReturn(Optional.of(agent));

        Agent result = agentService.searchAgentById(agentId);
        assertEquals(agent, result);
        verify(repository, times(1)).findById(agentId);
    }

    @Test
    public void testSearchAgentByIdNotFound() {
        long agentId = 1L;
        when(repository.findById(agentId)).thenReturn(Optional.empty());

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.searchAgentById(agentId);
        });
        verify(repository, times(1)).findById(agentId);
    }

    @Test
    public void testSearchAgentByName() throws AgentNotFoundException {
        String agentName = "John Doe";
        Agent agent = new Agent();
        when(repository.findByAgentName(agentName)).thenReturn(agent);

        Agent result = agentService.searchAgentByName(agentName);
        assertEquals(agent, result);
        verify(repository, times(1)).findByAgentName(agentName);
    }

    @Test
    public void testSearchAgentByNameNotFound() {
        String agentName = "John Doe";
        when(repository.findByAgentName(agentName)).thenReturn(null);

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.searchAgentByName(agentName);
        });
        verify(repository, times(1)).findByAgentName(agentName);
    }



    @Test
    public void testGetAllAgentNotFound() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.getAllAgent();
        });
        verify(repository, times(1)).findAll();
    }



    @Test
    public void testAssignPoliciesToAgentNotFound() {
        long policyId = 1L;
        long agentId = 1L;
        String policyType = "Health";
        when(repository.findById(agentId)).thenReturn(Optional.empty());

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.assignPoliciesToAgent(policyId, agentId, policyType);
        });
        verify(repository, times(1)).findById(agentId);
        verify(repository, never()).save(any(Agent.class));
    }
}
