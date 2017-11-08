package org.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableOAuth2Sso
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}

}
