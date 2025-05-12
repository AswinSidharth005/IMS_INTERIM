package com.cts.demo.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cts.demo.dto.Policy;

@FeignClient(name = "POLICY", path = "/policy")
public interface PolicyClient {

    // Fetches a policy by its ID.
    @GetMapping("/retrieveById/{policyId}")
    Policy retrievePolicy(@PathVariable("policyId") long policyId);
}