package com.cts.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Declares this interface as a Feign client for the "NOTIFICATION" service
//The base path for all endpoints is "/notify"
@FeignClient(name = "NOTIFICATION", path = "/notify")
public interface NotificationClient {

//Sends a notification message to a customer for a specific policy
//Maps to POST /notify/send/{message}/{cId}/{pId}
	@PostMapping("/send/{message}/{cId}/{pId}")
	public String sendNotification(@PathVariable("message") String message, @PathVariable("cId") long customerId,
			@PathVariable("pId") long policyId);

//Saves a notification message to the system for a customer and policy
//Maps to POST /notify/noti/{msg}/{cId}/{pId}
	@PostMapping("/noti/{msg}/{cId}/{pId}")
	public String notify(@PathVariable("msg") String message, @PathVariable("cId") long customerId,
			@PathVariable("pId") long policyId);
}
