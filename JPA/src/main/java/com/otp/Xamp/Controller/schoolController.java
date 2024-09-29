package com.otp.Xamp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.Xamp.Entity.school;
import com.otp.Xamp.Service.schoolService;

@RestController
@RequestMapping("/school")
public class schoolController {

	@Autowired
	private schoolService service;

	@GetMapping("/syncList")
	public String syncSchoolList() {

		this.service.syncSchool();

		return "succesfuly synced";

	}

	@GetMapping("/list")
	public List<school> getList() {
		return this.service.getschoolList();

	}

}
