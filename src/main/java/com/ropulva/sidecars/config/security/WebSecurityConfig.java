package com.ropulva.sidecars.config.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Autowired
	private final JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationEntryPointJwt authenticationEntryPointJwt;
	@Autowired
	private AccessDeniedHandlerJwt accessDeniedHandlerJwt;

	@SuppressWarnings({ "removal" })
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(corsConfig -> {
			CorsConfiguration cors = new CorsConfiguration();
			cors.setAllowedOrigins(Collections.singletonList("*"));
			cors.setAllowedMethods(Collections.singletonList("*"));
			cors.setAllowedHeaders(Collections.singletonList("*"));
			cors.setExposedHeaders(Arrays.asList("Authorization"));
		});
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Entry points
		http.authorizeHttpRequests(requests -> requests.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**"),
				AntPathRequestMatcher.antMatcher("/auth/login"), AntPathRequestMatcher.antMatcher("/otp/**"),
				AntPathRequestMatcher.antMatcher("/notification/**"), AntPathRequestMatcher.antMatcher("/v2/api-docs"),
				AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
				AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
				AntPathRequestMatcher.antMatcher("/configuration/**"), AntPathRequestMatcher.antMatcher("/webjars/**"),
				AntPathRequestMatcher.antMatcher("/public")).permitAll().anyRequest().authenticated())
				.exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPointJwt))
				.exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandlerJwt));

		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/v2/api-docs/**"),
						AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
						AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
						AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
						AntPathRequestMatcher.antMatcher("/configuration/**"),
						AntPathRequestMatcher.antMatcher("/webjars/**"), AntPathRequestMatcher.antMatcher("/public"))
				.and().ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**/**"));
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
