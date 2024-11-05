package com.otp.Xamp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.Teacher;

@Repository
public interface teacherRepo extends JpaRepository<Teacher, Integer> {

}
