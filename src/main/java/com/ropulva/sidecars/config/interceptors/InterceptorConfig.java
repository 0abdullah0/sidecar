package com.ropulva.sidecars.config.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	PackageInterceptor packageInterceptor;

	@Autowired
	FirebaseInterceptor firebaseInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(packageInterceptor).addPathPatterns("/otp/**", "/notification/**");
		interceptorRegistry.addInterceptor(firebaseInterceptor).addPathPatterns("/notification/**");
	}
}
