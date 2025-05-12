package com.cts.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an Agent in the system.
 */
@Entity
@Table(name="AGENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

	@Id
	@Min(value = 10, message = "The value should be greater than 10")
	private long agentId;

	@NotEmpty(message = "Agent Name should not be null or blank")
	private String agentName;

	@NotEmpty(message = "Agent contact_info Should not be null or blank")
	private String contactInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "agent_id", referencedColumnName = "agentId")
	private List<Policy> policies;

	public Agent(@NotEmpty(message = "Agent Id Should not be empty") long agentId,
			@NotEmpty(message = "Agent Name should not be null or blank") String agentName,
			@NotEmpty(message = "Agent contact_info Should not be null or blank") String contactInfo) {
		this.agentId = agentId;
		this.agentName = agentName;
		this.contactInfo = contactInfo;
	}
}