package com.otp.Xamp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
