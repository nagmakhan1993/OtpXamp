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

	public Teacher addTeacher(Teacher teacher) {
		Teacher data = this.teacherrepo.save(teacher);
		return data;
	}

	public Optional<Teacher> getTeacherById(int tId) {
		Optional<Teacher> teacher = teacherrepo.findById(tId);
		return teacher;
	}

	public List<Teacher> getAllTeachers() {
		return teacherrepo.findAll();
	}

	public List<Teacher> findAllTeachersBySchoolID(String schoolID) {
		return teacherrepo.findBySchoolID(schoolID);
	}

	public Optional<Teacher> updateTeacherByID(Teacher teacherData) {
		Optional<Teacher> teacher = teacherrepo.findById(teacherData.getTId());

		if (teacher != null) {
			teacherrepo.updateTeacherDetailsById(teacherData.getTId(), teacherData.getTName(),
					teacherData.getSchoolID(), teacherData.getSchoolName(), teacherData.getAddress(),
					teacherData.getGender(), teacherData.getPhone(), teacherData.getSubjectList());
		}
		return teacher;
	}
}
