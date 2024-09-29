package com.otp.Xamp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Model.UserModel;
import com.otp.Xamp.Repository.UserModelRepositry;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserModelRepositry userModelRepositry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel user = this.userModelRepositry.findByUserName(username)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		return user;
	}

}
