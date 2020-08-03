package com.second.firebasernd.models;

import lombok.Data;

@Data
public class PushNotificationRequest {

	private String title;
	
	private String message;
	
	private String topic;
	
	private String token;
	
	
	public PushNotificationRequest() {
	
	}

    public PushNotificationRequest(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }
	
}
