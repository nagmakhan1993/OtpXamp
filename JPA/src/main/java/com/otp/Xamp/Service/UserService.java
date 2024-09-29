package com.otp.Xamp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.User;
import com.otp.Xamp.Model.UserModel;
import com.otp.Xamp.Repository.UserModelRepositry;
import com.otp.Xamp.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserModelRepositry userModelRepositry;

	public User addUser(User user) {
		User data = this.repository.save(user);
		return data;
	}

	public UserModel createUser(UserModel userModel) {

		UserModel model = new UserModel();
		model.setUserId(userModel.getUserId());
		model.setUserName(userModel.getUsername());
		model.setPassword(userModel.getPassword());
		// userModel.getUserId(), userModel.getUsername(), userModel.getPassword()
		UserModel save = this.userModelRepositry.save(userModel);
		return model;
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

	public List<UserModel> getUserModelList() {
		return this.userModelRepositry.findAll();
	}

//	public UserEntity addUser(UserEntity user) {
//		return this.userRepository.save(user);
//	}
//
//	public UserEntity getUserById(Integer id) {
//		return this.userRepository.findById(id).orElse(null);
//	}

//	public String updateUser(Integer id, String firstName, String lastName, String address, String pincode,
//			String c_number, String ref_number) {
//		UserEntity userEntity = this.userRepository.findById(id).orElse(null);
//		if (userEntity != null) {
//			if (firstName != "") {
//				userEntity.setFirstName(firstName);
//			}
//			if (lastName != "") {
//				userEntity.setLastName(lastName);
//			}
//			if (address != "") {
//				userEntity.setAddress(address);
//			}
//			if (pincode != "") {
//				userEntity.setPincode(pincode);
//			}
//			if (c_number != "") {
//				userEntity.setC_number(c_number);
//			}
//			if (ref_number != "") {
//				userEntity.setRef_number(ref_number);
//			}
//			this.userRepository.save(userEntity);
//			return "Record update Successfully!!!";
//		}
//		return "Record Not Found";
//	}

//	public String deleteUserById(Integer id) {
//		UserEntity userEntity = this.userRepository.findById(id).orElse(null);
//		if (userEntity != null) {
//			this.userRepository.findById(id);
//			return "Record Delete Successfully!!";
//		}
//		return "Record Not found!!! kindly check ID.";
//	}

}
