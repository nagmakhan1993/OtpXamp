package com.RestAPI.JPA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.RestAPI.JPA.Entity.Candidate;

@Repository
public interface candidateRepo extends JpaRepository<Candidate, Integer>{

}
