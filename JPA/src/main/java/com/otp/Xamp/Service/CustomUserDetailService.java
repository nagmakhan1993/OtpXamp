package com.otp.Xamp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.User;
import com.otp.Xamp.Repository.UserRepositry;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepositry userModelRepositry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userModelRepositry.findByUserName(username)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		return user;
	}

}
