package com.otp.Xamp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.Xamp.Entity.Candidate;
import com.otp.Xamp.Service.candidateService;

@RestController
@RequestMapping("/candidate")
public class candidateController {

	@Autowired
	private candidateService candidateService;
	

	@PostMapping("/addCandidate")
	public Candidate addCandidate(@RequestBody Candidate candidate) {
		return this.candidateService.addCandidate(candidate);
	}
}
