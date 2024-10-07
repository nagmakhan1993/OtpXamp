package com.otp.Xamp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.Xamp.Entity.Candidate;
import com.otp.Xamp.Entity.User;
import com.otp.Xamp.Service.UserService;
import com.otp.Xamp.Service.candidateService;

@RestController
@RequestMapping("/candidate")
public class candidateController {

	@Autowired
	private candidateService candidateService;

	@Autowired
	private UserService userService;

	@PostMapping("/addCandidate")
	public Candidate addCandidate(@RequestBody Candidate candidate) {
		System.out.println("add candidate api calling...!!!");

		System.out.println("User name" + candidate.getUserName() + " password:  " + candidate.getPassword());
		User usermodel = new User(candidate.getUserName(), candidate.getPassword(), "Student");
		if (userService.findUserByUserName(candidate.getUserName()) != null) {
			this.userService.createUser(usermodel);
		}

		this.candidateService.addCandidate(candidate);
		return candidate;
	}
}
