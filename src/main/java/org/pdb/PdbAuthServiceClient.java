package org.pdb;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pdb-auth")
public interface PdbAuthServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/me")
	public Map<String, Object> user();
}
