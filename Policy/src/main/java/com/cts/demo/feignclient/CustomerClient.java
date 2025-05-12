package com.cts.demo.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.cts.demo.dto.Customer;
@FeignClient(name = "CUSTOMER", path = "/customer")
public interface CustomerClient {
   //Get Customer Data
   @GetMapping("/searchById/{cid}")
   public Customer getCustomer(@PathVariable("cid") long customerID);
   //Assign Policy to Customer
   @PutMapping("/assignPoliciesToCustomer/{pid}/{cid}/{pType}")
   public Customer assignPoliciesToCustomer(@PathVariable("pid") long policyId,
                                             @PathVariable("cid") long customerId,
                                             @PathVariable("pType") String policyType);
}