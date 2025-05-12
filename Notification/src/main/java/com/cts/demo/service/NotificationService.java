package com.cts.demo.service;

import java.util.List;

import com.cts.demo.entity.Notification;

public interface NotificationService {

	public abstract String saveNotification(String message, long customerId, long policyId);

	public abstract String sendNotification(String message, long customerId, long policyId);
}
