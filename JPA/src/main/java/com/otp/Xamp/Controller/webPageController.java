package com.otp.Xamp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class webPageController {

	@GetMapping("/")
	public String index(Model module) {
		System.out.println("Welcome in Home page Now call login api");
		module.addAttribute("title", "Test");

		return "index";
	}

	@GetMapping("/userHomePage")
	public String userHomePage(Model module) {
		System.out.println("Welcome in User Home page It shows User Name and Date and Time!!");
		module.addAttribute("title", "Test");

		return "homepage";
	}

	@GetMapping("/registerUser")
	public String registerUser(Model module) {
		System.out.println("Welcome in registration page !!");
		module.addAttribute("title", "Test");
		return "registration";
	}

	@GetMapping("/successRegisterUser")
	public String successRegisterUser(Model module) {
		System.out.println("data submitted successfully in registration page !!");
		module.addAttribute("title", "Test");
		return "registrationSuccess";
	}
}
