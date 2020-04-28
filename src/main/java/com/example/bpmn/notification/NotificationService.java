package com.example.bpmn.notification;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    public void sendNotification() {
        System.out.println("Sending notification ...");
    }

}
