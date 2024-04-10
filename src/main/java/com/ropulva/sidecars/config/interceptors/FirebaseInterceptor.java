package com.ropulva.sidecars.config.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ropulva.sidecars.config.BeanFactoryRegisterer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FirebaseInterceptor implements HandlerInterceptor {

	@Autowired
	BeanFactoryRegisterer beanFactoryRegisterer;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			String appId = request.getHeader("appId");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(appId + ".json").getInputStream()))
					.build();
			final FirebaseApp firebaseAppBean = FirebaseApp.initializeApp(options);

			beanFactoryRegisterer.registerFirebaseAppBean(firebaseAppBean);

			final FirebaseMessaging firebaseMessagingBean = FirebaseMessaging.getInstance(firebaseAppBean);

			beanFactoryRegisterer.registerFirebaseMessagingBean(firebaseMessagingBean);

			return true;

		} catch (Exception ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
			return false;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		System.out.println("unregister ");
		beanFactoryRegisterer.unregisterFirebaseBeans();

		removeFirebaseAppInstance();
	}

	private void removeFirebaseAppInstance() {

		FirebaseApp.getInstance().delete();

	}
}
