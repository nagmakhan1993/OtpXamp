package com.otp.Xamp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.User;

@Repository
public interface UserRepositry extends JpaRepository<User, Integer> {
	public Optional<User> findByUserName(String username);
}
