package com.otp.Xamp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.otp.Xamp.Service.BookService;
import com.otp.Xamp.Service.schoolService;

@Controller
public class webPageController {

	@Autowired
	private schoolService schoolService;

	@Autowired
	private BookService bookService;

	@GetMapping("/home")
	public String index(Model module) {
		System.out.println("Welcome in Home page Now call login and registration api");
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

	@GetMapping("/schools")
	public String getSchoolList(Model model) {
		model.addAttribute("schools", schoolService.getAllSchools());
		return "schoolList";
	}

	@GetMapping("/books")
	public String getBooksList(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "bookList";
	}
}
