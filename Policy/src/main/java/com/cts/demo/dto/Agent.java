package com.cts.demo.dto;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Getter and Setter
@Data

//Constructor With All Feilds
@AllArgsConstructor

//No Args Constructor
@NoArgsConstructor
public class Agent {

	//Agent Id
	private long agentId;

	//Agent Name
	private String agentName;

	//Contact Info
	private String contactInfo;
	
	//Policy Will be Loaded Immediately
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "agent_id", referencedColumnName = "agentId")
	private List<Policy2> policies;
}
