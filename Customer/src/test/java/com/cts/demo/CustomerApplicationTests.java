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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.demo.exception.CustomerNotFoundException;
import com.cts.demo.model.Customer;
import com.cts.demo.repository.CustomerRepository;
import com.cts.demo.service.CustomerServiceImpl;

public class CustomerApplicationTests extends CustomerServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CustomerApplicationTests.class);

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerApplicationTests customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        when(repository.save(customer)).thenReturn(customer);

        String result = customerService.saveCustomer(customer);
        assertEquals("Customer saved successfully", result);
        verify(repository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        when(repository.save(customer)).thenReturn(customer);

        Customer result = customerService.updateCustomer(customer);
        assertEquals(customer, result);
        verify(repository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() throws CustomerNotFoundException {
        long customerId = 1L;
        when(repository.existsById(customerId)).thenReturn(true);

        String result = customerService.deleteCustomer(customerId);
        assertEquals("Customer deleted successfully", result);
        verify(repository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomerNotFound() {
        long customerId = 1L;
        when(repository.existsById(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
        verify(repository, never()).deleteById(customerId);
    }

    @Test
    public void testSearchCustomerById() throws CustomerNotFoundException {
        long customerId = 1L;
        Customer customer = new Customer();
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.searchCustomerById(customerId);
        assertEquals(customer, result);
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    public void testSearchCustomerByIdNotFound() {
        long customerId = 1L;
        when(repository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.searchCustomerById(customerId);
        });
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    public void testSearchCustomerByName() {
        String customerName = "John Doe";
        Customer customer = new Customer();
        when(repository.findByCustomerName(customerName)).thenReturn(customer);

        Customer result = customerService.searchCustomerByName(customerName);
        assertEquals(customer, result);
        verify(repository, times(1)).findByCustomerName(customerName);
    }

    @Test
    public void testGetAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        when(repository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomer();
        assertEquals(customers, result);
        verify(repository, times(1)).findAll();
    }



    @Test
    public void testAssignPoliciesToCustomerNotFound() {
        long policyId = 1L;
        long customerId = 1L;
        String policyType = "Health";
        when(repository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.assignPoliciesToCustomer(policyId, customerId, policyType);
        });
        verify(repository, times(1)).findById(customerId);
        verify(repository, never()).save(any(Customer.class));
    }
}
