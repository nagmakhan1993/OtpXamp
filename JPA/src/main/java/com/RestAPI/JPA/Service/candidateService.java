package com.RestAPI.JPA.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RestAPI.JPA.Entity.Candidate;
import com.RestAPI.JPA.Entity.User;
import com.RestAPI.JPA.Repository.candidateRepo;

@Service
public class candidateService {

	@Autowired
	private candidateRepo candidateRepo;

	public Candidate addCandidate(Candidate candidate) {

		Candidate data = this.candidateRepo.save(candidate);
		return data;
	}

}
