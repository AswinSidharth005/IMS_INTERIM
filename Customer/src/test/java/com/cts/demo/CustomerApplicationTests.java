package com.cts.demo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;
import com.cts.demo.model.Policy1;
import com.cts.demo.repository.CustomerRepository;
import com.cts.demo.service.CustomerServiceImpl;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class CustomerApplicationTests {

	@Mock
	CustomerRepository repository;

	@InjectMocks
	CustomerServiceImpl service;

	@Test
	void saveTest() {
		Policy1 p1 = new Policy1();
		p1.setPolicyType("Car");
		p1.setPolicyId(15);
		List<Policy1> policies = new ArrayList<>();
		policies.add(p1);
		Customer customer = new Customer(1, "Aswin", "Aswin@gmail.com", "9751222269", "Karur",
				policies);
		Mockito.when(repository.save(customer)).thenReturn(customer);
		String response = service.saveCustomer(customer);
		assertEquals("Customer saved successfully", response);
	}

	@Test
	void updateTest() {
		Policy1 p1 = new Policy1();
		p1.setPolicyType("Health");
		p1.setPolicyId(201);
		List<Policy1> policies = new ArrayList<>();
		policies.add(p1);
		Customer customer = new Customer(2113017, "Aswin", "aswin@gmail.com", "9751222269", "karur",
				policies);
		Mockito.when(repository.save(customer)).thenReturn(customer);
		Customer cust = service.updateCustomer(customer);
		assertEquals(customer, cust);
	}

//	@Test
//	void deleteTest() throws CustomerNotFoundException{
//		Policy1 p1 = new Policy1();
//		p1.setPolicyType("Health");
//		p1.setPolicyId(201);
//		List<Policy1> policies = new ArrayList<>();
//		policies.add(p1);
//		Customer customer = new Customer(2113017, "Aswin", "aswin@gmail.com", "9751222269", "karur",
//				policies);
//		long userId = customer.getCustomerId();
//		Mockito.doNothing().when(repository).deleteById(userId);
//		String response = service.deleteCustomer(userId);
//		assertEquals("Customer deleted successfully", response);
//
//	}

	@Test
	void searchCustomerByIdTest() throws CustomerNotFoundException {
		Policy1 p1 = new Policy1();
		p1.setPolicyType("Health");
		p1.setPolicyId(201);
		List<Policy1> policies = new ArrayList<>();
		policies.add(p1);
		Customer customer = new Customer("Aswin", "aswin@gmail.com", "9751222269", "karur",
				policies);
		long customerId = 21113016;
		customer.setCustomerId(customerId);
		Mockito.when(repository.findById(customerId)).thenReturn(Optional.of(customer));
		Customer foundCustomerById = service.searchCustomerById(customerId);
		assertEquals(customer, foundCustomerById);
	}

	@Test
	void searchCustomerByNameTest() {
		String customerName = "Aswin";
		Policy1 p1 = new Policy1();
		p1.setPolicyType("Health");
		p1.setPolicyId(201);
		List<Policy1> policies = new ArrayList<>();
		policies.add(p1);
		Customer customer = new Customer(2113017, "Aswin", "aswin@gmail.com", "9751222269", "karur",
				policies);
		Mockito.when(repository.findByCustomerName(customerName)).thenReturn(customer);
		Customer foundCustomerByName = service.searchCustomerByName(customerName);
		assertEquals(customer, foundCustomerByName);
	}

	@Test
	void getAllCustomerTest() {
		Policy1 p1 = new Policy1();
		p1.setPolicyType("Health");
		p1.setPolicyId(201);
		List<Policy1> policies = new ArrayList<>();
		policies.add(p1);

		Policy1 p2 = new Policy1();
		p2.setPolicyType("Gold");
		p2.setPolicyId(202);
		List<Policy1> policies2 = new ArrayList<>();
		policies2.add(p2);
		List<Customer> customers = Arrays.asList(
				new Customer(2113017, "Aswin", "aswin@gmail.com", "9751222269", "karur", policies),
				new Customer(2113016, "Aswinsidz", "aswin1@gmail.com", "9751222269", "karur", policies2));
		Mockito.when(repository.findAll()).thenReturn(customers);
		List<Customer> custs = service.getAllCustomer();
		assertEquals(customers, custs);
	}

}
