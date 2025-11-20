package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
	public String index() {
		return "Hello World from Spring Boot hahaha!";
	}
	
	@GetMapping("/user")
	public String userPage() {
		return "userPage!";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "adminPage";
	}

}
