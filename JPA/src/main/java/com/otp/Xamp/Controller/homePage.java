package com.otp.Xamp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home") 	
public class homePage {

	@GetMapping("/")
	public String index (Model module) {
	    // ModelAndView modelAndView = new ModelAndView();
	    // modelAndView.setViewName("index");
	    // System.out.println("simple message");
		module.addAttribute("title", "Test");

	    return "index";
	}

}
