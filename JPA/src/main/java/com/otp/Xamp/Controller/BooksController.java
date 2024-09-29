package com.otp.Xamp.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Entity.Book;
import com.otp.Xamp.Service.BookService;

@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookService bookservice;

	@PostMapping("/import")
	public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {
		try {
			bookservice.importBooksFromExcel(file);
			return ResponseEntity.ok("Users imported successfully!");
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Failed to import users: " + e.getMessage());
		}
	}

	@GetMapping("/searchBook")
	public ResponseEntity<String> searchUsers(@RequestParam("className") String className,
			@RequestParam("subjectName") String subject, @RequestParam("bookName") String bookName) throws IOException {
		List<Book> bookDownloadLink = new ArrayList<>();
		bookDownloadLink = bookservice.bookDownload(className, subject, bookName);
		return ResponseEntity.ok("Users imported successfully!");
	}
}
