package com.ropulva.sidecars.config.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ropulva.sidecars.service.impl.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PackageInterceptor implements HandlerInterceptor {

	@Autowired
	AdminService adminService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			String appId = request.getHeader("appId");
			adminService.findApp(appId);
			return true;

		} catch (Exception ex) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
			return false;
		}
	}
}
