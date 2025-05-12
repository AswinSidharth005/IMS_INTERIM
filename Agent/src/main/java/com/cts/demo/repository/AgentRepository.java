package com.cts.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.demo.model.Agent;

//JPA Repository has CRUD Operations
public interface AgentRepository extends JpaRepository<Agent, Long> {

//Custom Query
	public abstract Agent findByAgentName(String agentName);
}
