package com.ropulva.sidecars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import com.ropulva.sidecars.constant.SystemConstants;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableScheduling
@EnableAsync
public class RopulvaSidecarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RopulvaSidecarsApplication.class, args);
	}

	@Bean
	WebClient smsApiClient() {
		return WebClient.create(SystemConstants.SMS_PROVIDER_URL);
	}

}
