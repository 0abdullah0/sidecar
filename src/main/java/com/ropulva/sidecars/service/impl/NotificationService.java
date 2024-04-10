package com.ropulva.sidecars.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.ropulva.sidecars.config.BeanFactoryRegisterer;
import com.ropulva.sidecars.dto.NotificationMessageDto;
import com.ropulva.sidecars.service.INotificationService;

@Service
public class NotificationService implements INotificationService {

	@Autowired
	BeanFactoryRegisterer beanFactoryRegisterer;

	@Override
	public List<String> sendNotificationToGroup(NotificationMessageDto message) throws FirebaseMessagingException {
		final FirebaseMessaging fcm = beanFactoryRegisterer.firebaseMessagingBean();
		Notification notification = new Notification(message.getTitle(), message.getBody());
		MulticastMessage msg = MulticastMessage.builder().addAllTokens(message.getRegistrationTokens())
				.setNotification(notification).putData("picture", message.getPicture())
				.putData("payload", message.getPayload()).build();

		BatchResponse response = fcm.sendMulticast(msg);

		List<String> ids = response.getResponses().stream().map(r -> r.getMessageId()).collect(Collectors.toList());
		return ids;
	}

	@Override
	public void sendNotificationToAll(NotificationMessageDto message) throws FirebaseMessagingException {
		final FirebaseMessaging fcm = beanFactoryRegisterer.firebaseMessagingBean();
		Notification notification = new Notification(message.getTitle(), message.getBody());

		Message msg = Message.builder().setNotification(notification).setTopic("public")
				.putData("picture", message.getPicture()).putData("payload", message.getPayload()).build();

		fcm.send(msg);
	}

}
