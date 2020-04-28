package com.example.bpmn.notification;

import com.example.bpmn.controllers.InBoundController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification() {
        LOGGER.info("Process Completed");
        LOGGER.info("Sending notification ...");
    }

    public void sendNotification(String processName) {
        LOGGER.info(processName + " Process Completed");
        LOGGER.info("Sending notification ...");
    }

}
