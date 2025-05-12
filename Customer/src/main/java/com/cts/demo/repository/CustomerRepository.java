package com.cts.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.demo.model.Customer;

//Extends JPA Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//Custom Query
	public abstract Customer findByCustomerName(String customerName);
}
