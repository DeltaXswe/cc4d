package it.deltax.produlytics.uibackend.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {
	@GetMapping
	public void login(){
		System.out.println("login");
	}
}
