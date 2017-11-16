package org.pdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableZuulProxy
public class PDBGateway {

	public static void main(String[] args) {
		SpringApplication.run(PDBGateway.class, args);

	}

	@Controller
	public class HomeController {
		@RequestMapping("/home")
		public String forward() {
			return "forward:/index.html";
		}
	}

}
