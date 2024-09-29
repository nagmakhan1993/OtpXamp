package com.otp.Xamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication /* (exclude = { DataSourceAutoConfiguration.class }) */

public class XampApplication {

	public static void main(String[] args) {
		SpringApplication.run(XampApplication.class, args);

	}

}
