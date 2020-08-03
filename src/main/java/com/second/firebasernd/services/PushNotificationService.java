package com.second.firebasernd.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.second.firebasernd.config.FCMService;
import com.second.firebasernd.models.PushNotificationRequest;

@Service
public class PushNotificationService {
	
	@Value("#{${app.notifications.defaults}}")
	private Map<String, String> defaults;
	
	

	private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;
    
    public PushNotificationService(FCMService fcmService) {
    	this.fcmService = fcmService;
    }
    
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try {
            fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotification(PushNotificationRequest pushNotificationRequest) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), pushNotificationRequest);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest pushNotificationRequest) {
        try {
            fcmService.sendMessageWithoutData(pushNotificationRequest);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    public void sendPushNotificationToToken(PushNotificationRequest pushNotificationRequest) {
        try {
            fcmService.sendMessageToToken(pushNotificationRequest);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }


    private PushNotificationRequest getSamplePushNotificationRequest() {
    	PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(defaults.get("title"), defaults.get("message"), defaults.get("topic"));
        return pushNotificationRequest;
    }
}
	 