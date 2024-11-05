package com.otp.Xamp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TextCoordinate {
	private String text;
	private float x;
	private float y;
	private float fontSize;

}
