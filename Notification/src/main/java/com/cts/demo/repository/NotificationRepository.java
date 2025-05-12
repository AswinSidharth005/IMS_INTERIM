package com.cts.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.demo.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


}
