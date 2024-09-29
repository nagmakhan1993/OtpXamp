package com.otp.Xamp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.Candidate;
import com.otp.Xamp.Repository.candidateRepo;

@Service
public class candidateService {

	@Autowired
	private candidateRepo candidateRepo;

	public Candidate addCandidate(Candidate candidate) {

		Candidate data = this.candidateRepo.save(candidate);
		return data;
	}

}
