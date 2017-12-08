package org.pdb;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
@EnableCircuitBreaker
@EnableFeignClients
public class PDBGateway extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(PDBGateway.class, args);
	}

	// to mkae the sso eurka service ids work
	@Bean
	UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(SpringClientFactory springClientFactory) {
		return template -> {
			AccessTokenProviderChain accessTokenProviderChain = Stream
					.of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
							new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
					.peek(tp -> tp.setRequestFactory(new RibbonClientHttpRequestFactory(springClientFactory)))
					.collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
			template.setAccessTokenProvider(accessTokenProviderChain);
		};
	}

	@Bean
	@LoadBalanced
	public OAuth2RestOperations OAuth2RestOperations(OAuth2ProtectedResourceDetails resource,
			OAuth2ClientContext context) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, context);
		return restTemplate;
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext context,
			OAuth2ProtectedResourceDetails resource) {
		return new OAuth2FeignRequestInterceptor(context, resource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http.csrf().disable()
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/*.js", "/*.map", "/index.html","/oauth/**").permitAll()
			.anyRequest().authenticated();
		// @formatter:on
	}

	@Bean
	public ZuulFallbackProvider genericFallbackProvider() {
		return new GenericFallbackProvider();
	}
}
