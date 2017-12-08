package org.pdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

public class GenericFallbackProvider implements ZuulFallbackProvider {
	@Override
	public String getRoute() {
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.NOT_FOUND;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return 404;
			}

			@Override
			public String getStatusText() throws IOException {
				return "Service Unavailable";
			}

			@Override
			public void close() {

			}

			@Override
			public InputStream getBody() throws IOException {
				String responseBody = "{\"message\":\"Service Unavailable. Please try after sometime\"}";
				return new ByteArrayInputStream(responseBody.getBytes());
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
		};
	}

}
