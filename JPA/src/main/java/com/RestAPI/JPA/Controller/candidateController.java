package com.RestAPI.JPA.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestAPI.JPA.Entity.Candidate;
import com.RestAPI.JPA.Service.candidateService;

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
