package com.otp.Xamp.Service;

import java.util.List;
import java.util.Optional;

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

	public Optional<Candidate> getCandidateById(int cId) {
		Optional<Candidate> candidate = candidateRepo.findById(cId);
		return candidate;
	}

	public List<Candidate> getAllCandidates() {
		return candidateRepo.findAll();
	}
}
