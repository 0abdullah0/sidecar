package com.ropulva.sidecars.service;

import java.util.List;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ropulva.sidecars.dto.NotificationMessageDto;

public interface INotificationService {

	public List<String> sendNotificationToGroup(NotificationMessageDto message) throws FirebaseMessagingException;

	public void sendNotificationToAll(NotificationMessageDto message) throws FirebaseMessagingException;

}
