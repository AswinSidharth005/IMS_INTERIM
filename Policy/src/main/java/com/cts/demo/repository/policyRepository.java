package com.cts.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cts.demo.dto.Customer;
import com.cts.demo.project.Policy;

import jakarta.transaction.Transactional;

//Extends JPA Repo to access all Predefined CRUD Operations
public interface policyRepository extends JpaRepository<Policy, Long> {
	
}
