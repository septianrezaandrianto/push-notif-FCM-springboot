package com.second.firebasernd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.second.firebasernd.config.FCMInitializer;
import com.second.firebasernd.models.PushNotificationRequest;
import com.second.firebasernd.models.PushNotificationResponse;
import com.second.firebasernd.services.PushNotificationService;

@RestController
@RequestMapping("/api")
public class PushNotificationController {

	private PushNotificationService pushNotificationService;
	
	public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

	@Autowired
	private FCMInitializer fcmInitializer;
	
    @PostMapping("/notification/topic")
    public ResponseEntity sendNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
    	pushNotificationService.sendPushNotificationWithoutData(pushNotificationRequest);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.sendPushNotification(pushNotificationRequest);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @GetMapping("/notification")
    public ResponseEntity sendSampleNotification() {
        pushNotificationService.sendSamplePushNotification();
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
}
