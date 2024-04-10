package com.ropulva.sidecars.config;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

@Component
public class BeanFactoryRegisterer {

	DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

	public void registerFirebaseAppBean(FirebaseApp firebaseAppBean) {

		beanFactory.initializeBean(firebaseAppBean, "firebaseApp");
		beanFactory.autowireBeanProperties(firebaseAppBean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		beanFactory.registerSingleton("firebaseApp", firebaseAppBean);

	}

	public void registerFirebaseMessagingBean(FirebaseMessaging firebaseMessagingBean) {

		beanFactory.initializeBean(firebaseMessagingBean, "firebaseMessaging");
		beanFactory.autowireBeanProperties(firebaseMessagingBean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		beanFactory.registerSingleton("firebaseMessaging", firebaseMessagingBean);

	}

	public FirebaseMessaging firebaseMessagingBean() {

		return beanFactory.getBean(FirebaseMessaging.class);
	}

	public void unregisterFirebaseBeans() {
		beanFactory.destroySingleton("firebaseApp");
		beanFactory.destroySingleton("firebaseMessaging");
	}

}
