package com.otp.Xamp.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Entity.MediaFile;
import com.otp.Xamp.Entity.Teacher;
import com.otp.Xamp.Service.FileUploadService;
import com.otp.Xamp.Service.MediaFileService;
import com.otp.Xamp.Service.teacherService;

@RestController
@RequestMapping("/teacher")
public class teacherController {

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private teacherService teacherService;

	@Autowired
	private MediaFileService mediaFileService;

	@PostMapping("/addTeacher")
	public Teacher addTeacher(@RequestBody Teacher teacher) {
		teacherService.addTeacher(teacher);
		return teacher;

	}

	@GetMapping("/teacher/{id}")
	public Optional<Teacher> getTeacherById(int id) {
		Optional<Teacher> teacher = teacherService.getTeacherById(id);
		return teacher;
	}

	@GetMapping("/teacher/schoolId")
	public List<Teacher> findTeacherBySchool(@RequestBody String schoolID) {
		return teacherService.findAllTeachersBySchoolID(schoolID);
	}

	@GetMapping("/teacherList")
	public List<Teacher> getAllTeachers() {
		return teacherService.getAllTeachers();
	}

	@PutMapping("/updateTeacher")
	public String updateTeacherByID(@RequestBody Teacher teacherData) {
		Optional<Teacher> teacher = teacherService.updateTeacherByID(teacherData);
		if (teacher != null) {
			return "Teacher Data update successfully!!";
		} else {
			return "Teacher id is incorrect!! Please enter valid teacher ID";
		}
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}
		try {
			String message = fileUploadService.uploadFile(file);
			return ResponseEntity.ok(message);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("File upload failed: " + e.getMessage());
		}
	}

	@PostMapping("/upload/mp3_mp4")
	public ResponseEntity<String> uploadMp3_Mp4(@RequestParam("file") MultipartFile file) {
		try {
			MediaFile savedFile = mediaFileService.uploadFile(file);
			return ResponseEntity.ok("File uploaded successfully with ID: " + savedFile.getId());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("File upload failed: " + e.getMessage());
		}
	}
}
