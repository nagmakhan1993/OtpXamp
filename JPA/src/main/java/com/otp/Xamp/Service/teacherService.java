package com.otp.Xamp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.Teacher;
import com.otp.Xamp.Repository.teacherRepo;

@Service
public class teacherService {

	@Autowired
	private teacherRepo teacherrepo;

	public Teacher addCandidate(Teacher teacher) {
		Teacher data = this.teacherrepo.save(teacher);
		return data;
	}

	public Optional<Teacher> getCandidateById(int tId) {
		Optional<Teacher> teacher = teacherrepo.findById(tId);
		return teacher;
	}

	public List<Teacher> getAllCandidates() {
		return teacherrepo.findAll();
	}

}
