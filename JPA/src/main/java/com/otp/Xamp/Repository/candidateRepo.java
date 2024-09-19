package com.otp.Xamp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.Candidate;

@Repository
public interface candidateRepo extends JpaRepository<Candidate, Integer>{

}
