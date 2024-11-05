package com.otp.Xamp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.otp.Xamp.Entity.Teacher;

@Repository
public interface teacherRepo extends JpaRepository<Teacher, Integer> {

	List<Teacher> findBySchoolID(String schoolID);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Teacher	t SET t.tName=:name, t.schoolID=:schoolID, t.schoolName=:schoolName, t.address=:address,"
			+ "t.gender=:gender, t.phone=:phone, t.subjectList=:subjectList WHERE t.id=:id", nativeQuery = true)

	int updateTeacherDetailsById(Integer id, String name, String schoolID, String schoolName, String address,
			String gender, String phone, List<String> subjectList);

}
