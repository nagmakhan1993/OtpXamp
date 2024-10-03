package com.otp.Xamp.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.Xamp.Model.JwtRequest;
import com.otp.Xamp.Model.JwtResponse;
import com.otp.Xamp.Model.UserModel;
import com.otp.Xamp.Security.JwtHelper;
import com.otp.Xamp.Service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserService userService;
	@Autowired
	private JwtHelper helper;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.helper.generateToken(userDetails);

		JwtResponse response = new JwtResponse(token, userDetails.getUsername());

//		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

		System.out.println();
		System.out.println("JwtToken: " + token);
		System.out.println("userName: " + userDetails.getUsername());
		ResponseEntity<JwtResponse> res = new ResponseEntity(response, HttpStatus.OK);

		return res;
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

	@PostMapping("/create-user")
	public UserModel createUser(@RequestBody UserModel userModel) {
		return this.userService.createUser(userModel);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}
}
