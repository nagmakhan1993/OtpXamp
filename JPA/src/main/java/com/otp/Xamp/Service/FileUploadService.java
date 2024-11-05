package com.otp.Xamp.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private static final String UPLOAD_DIRECTORY = "C:/OtpXamp/uploaded_files/";

	public String uploadFile(MultipartFile file) throws IOException {

		if (!isSupportedFileType(file)) {
			throw new IOException("Unsupported file type. Please upload a PDF, JPG, or DOCX file.");
		}

		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));

		String fileName = file.getOriginalFilename();
		Path filePath = Paths.get(UPLOAD_DIRECTORY + fileName);

		Files.write(filePath, file.getBytes());

		return "File uploaded successfully: " + filePath.toString();
	}

	private boolean isSupportedFileType(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType != null && (contentType.equals("application/pdf") || contentType.equals("image/jpeg")
				|| contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
	}
}
