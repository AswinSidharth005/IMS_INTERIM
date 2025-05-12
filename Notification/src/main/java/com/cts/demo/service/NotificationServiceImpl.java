package com.cts.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.dto.Policy;
import com.cts.demo.entity.Notification;
import com.cts.demo.feignclient.PolicyClient;
import com.cts.demo.repository.NotificationRepository;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	NotificationRepository repository;

	// microservice
	@Autowired
	PolicyClient policyClient;

	@Override
	public String saveNotification(String message, long customerId, long policyId) {
		logger.info("Saving notification - Message: '{}', Customer ID: {}, Policy ID: {}", message, customerId,
				policyId);
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setCustomerId(customerId);
		notification.setPolicyId(policyId);
		repository.save(notification);
		logger.info("Notification saved successfully for Customer ID: {}", customerId);
		return "Notification saved Successfully";
	}

	@Override
	public String sendNotification(String message, long customerId, long policyId) {
		logger.info("Sending notification - Message: '{}', Customer ID: {}, Policy ID: {}", message, customerId,
				policyId);

		Policy policy = policyClient.retrievePolicy(policyId);
		LocalDate validityPeriod = policy.getValidityPeriod();
		LocalDate today = LocalDate.now();

		long daysBetween = ChronoUnit.DAYS.between(today, validityPeriod);
		logger.debug("Days until policy expiry: {}", daysBetween);

		if (daysBetween < 10) {
			logger.info("Policy is nearing expiry. Saving notification.");
			saveNotification(message, customerId, policyId);
			return "Message saved Successfully";
		} else {
			logger.info("Policy validity is sufficient. No notification saved.");
			return "The validity period is up to mark";
		}
	}
}
