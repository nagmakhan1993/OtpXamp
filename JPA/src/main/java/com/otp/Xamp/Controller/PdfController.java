package com.otp.Xamp.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Model.TextCoordinate;
import com.otp.Xamp.Service.PdfBoxService;

@RestController
@RequestMapping("/uploadPDF")
public class PdfController {

	@Autowired
	private PdfBoxService pdfBoxService;

	private static final String OUTPUT_DIRECTORY = "C:/Users/Dell/Downloads/pdfFile/";

	@PostMapping("/extract")

	public ResponseEntity<List<TextCoordinate>> extractCoordinates(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
			return ResponseEntity.badRequest().body(null);
		}

		try {
			List<TextCoordinate> coordinates = pdfBoxService.extractTextWithCoordinates(file);

			return ResponseEntity.ok(coordinates);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/mergePdf")
	public ResponseEntity<String> mergePdfs(@RequestParam("files") List<MultipartFile> files) {
		if (files == null || files.size() < 2) {
			return ResponseEntity.badRequest().body("Please upload at least two PDF files.");
		}

		try {

			Files.createDirectories(Paths.get(OUTPUT_DIRECTORY));

			String outputFilePath = OUTPUT_DIRECTORY + "merged.pdf";

			pdfBoxService.mergePdfs(files, outputFilePath);

			return ResponseEntity.ok("PDFs merged and saved to: " + outputFilePath);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to merge PDFs: " + e.getMessage());
		}
	}
}
