package com.otp.Xamp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.Xamp.Service.BookService;

@RestController
@RequestMapping("/folder")
public class FileController {

	@Autowired
	private BookService bookService;

	@GetMapping("/create-folder")
	public String createFolderBookListAccording() {

		bookService.crateFolderAccordingBooks();
		return "successfully created!!";

	}
}
