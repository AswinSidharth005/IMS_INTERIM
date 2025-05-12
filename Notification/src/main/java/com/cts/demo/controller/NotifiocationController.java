package com.cts.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.demo.service.NotificationService;

@RestController

@RequestMapping("/notify")
public class NotifiocationController {

	private static final Logger logger = LoggerFactory.getLogger(NotifiocationController.class);

	@Autowired
	NotificationService service;

	@PostMapping("/noti/{msg}/{cId}/{pId}")
	public String notify(@PathVariable("msg") String message, @PathVariable("cId") long customerId,
			@PathVariable("pId") long policyId) {
		logger.info("Saving notification - Message: '{}', Customer ID: {}, Policy ID: {}", message, customerId,
				policyId);
		return service.saveNotification(message, customerId, policyId);
	}

	@PostMapping("/send/{message}/{cId}/{pId}")
	public String sendNotification(@PathVariable("message") String message, @PathVariable("cId") long customerId,
			@PathVariable("pId") long policyId) {
		logger.info("Sending notification - Message: '{}', Customer ID: {}, Policy ID: {}", message, customerId,
				policyId);
		return service.sendNotification(message, customerId, policyId);
	}
}
