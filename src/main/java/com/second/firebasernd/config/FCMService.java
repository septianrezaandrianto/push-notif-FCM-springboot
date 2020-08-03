package com.second.firebasernd.config;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.second.firebasernd.models.PushNotificationRequest;

@Service
public class FCMService {

	private Logger logger = LoggerFactory.getLogger(FCMService.class);
	
	public void sendMessage(Map<String, String> data, PushNotificationRequest pushNotificationRequest)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data, pushNotificationRequest);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + pushNotificationRequest.getTopic() + ", " + response);
    }

    public void sendMessageWithoutData(PushNotificationRequest pushNotificationRequest)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithoutData(pushNotificationRequest);
        String response = sendAndGetResponse(message);
        logger.info("Sent message without data. Topic: " + pushNotificationRequest.getTopic() + ", " + response);
    }

    public void sendMessageToToken(PushNotificationRequest pushNotificationRequest)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(pushNotificationRequest);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + pushNotificationRequest.getToken() + ", " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest pushNotificationRequest) {
        return getPreconfiguredMessageBuilder(pushNotificationRequest).setToken(pushNotificationRequest.getToken())
                .build();
    }

    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest pushNotificationRequest) {
        return getPreconfiguredMessageBuilder(pushNotificationRequest).setTopic(pushNotificationRequest.getTopic())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest pushNotificationRequest) {
        return getPreconfiguredMessageBuilder(pushNotificationRequest).putAllData(data).setTopic(pushNotificationRequest.getTopic())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest pushNotificationRequest) {
        AndroidConfig androidConfig = getAndroidConfig(pushNotificationRequest.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(pushNotificationRequest.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(pushNotificationRequest.getTitle(), pushNotificationRequest.getMessage()));
    }

}
