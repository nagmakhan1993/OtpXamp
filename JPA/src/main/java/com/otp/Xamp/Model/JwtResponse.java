package com.otp.Xamp.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
	public JwtResponse(String token, String username) {

		this.jwtToken = token;
		this.username = username;
	}

	private String jwtToken;
	private String username;
}
