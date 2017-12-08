package org.pdb;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

	@Autowired
	PdbAuthServiceClient authSvcClient;

	@RequestMapping({ "/user" })
	public Map<String, Object> actuatorUser(Principal principal) {
		Map<String, Object> user = authSvcClient.user();
		OAuth2Authentication auth = (OAuth2Authentication) principal;

		if (principal != null) {
			user.put("details", auth.getDetails());
		}
		return user;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		return new ModelAndView("forward:/index.html");

	}

}
