package com.otp.Xamp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.User;
import com.otp.Xamp.Repository.UserRepositry;

@Service
public class UserService {

	@Autowired
	private UserRepositry repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User createUser(User user) {

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		User userData = new User(user.getUsername(), encodedPassword, user.getUserType());

		return this.repository.save(userData);

	}

	public Optional<User> findUserByUserName(String userName) {
		return repository.findByUserName(userName);
	}

	public List<User> getAllUser() {
		List<User> userList = this.repository.findAll();
		return userList;
	}

	public String updateUser(Integer id, String userName, String password) {
		String repsonse = "";
		User userById = this.repository.findById(id).orElse(null);
		if (userById != null) {
			if (userName != "" || !userName.isEmpty()) {
				userById.setUserName(userName);
				repsonse = repsonse + "User Name is update Successfully";
			}
			if (password != "" || !password.isEmpty()) {
				userById.setPassword(password);
				repsonse = repsonse + "password is update Successfully";
			}
		}
		this.repository.save(userById);
		return repsonse;
	}

	public User getUserById(Integer id) {
		User userById = this.repository.findById(id).orElse(null);
		return userById;
	}

	public String deleteUserById(Integer id) {
		String message = "";
		User userById = this.repository.findById(id).orElse(null);
		if (userById != null) {
			this.repository.delete(userById);
			message = "Delete Record Successfully!!";
			return message;
		} else {
			return message = "Record Not found";
		}

	}

}
