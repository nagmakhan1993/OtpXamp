package com.otp.Xamp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.school;

@Repository
public interface schoolRepo extends JpaRepository<school, Integer> {

	List<school> findBysId(Integer sId);

	List<school> findBySchoolID(String schoolID);
	
}
