package com.otp.Xamp.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Entity.MediaFile;
import com.otp.Xamp.Repository.MediaFileRepository;

@Service
public class MediaFileService {
	@Autowired
	private MediaFileRepository mediaFileRepository;

	private static final String UPLOAD_DIRECTORY = "C:/OtpXamp/Media/";

	public MediaFile uploadFile(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		String fileType = file.getContentType();

		if (!isSupportedFileType(fileType)) {
			throw new IllegalArgumentException("Unsupported file type. Only MP3 and MP4 are allowed.");
		}
		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));

		MediaFile mediaFile = new MediaFile(fileName, fileType, file.getBytes());

		Path filePath = Paths.get(UPLOAD_DIRECTORY + fileName);

		Files.write(filePath, file.getBytes());
		return mediaFileRepository.save(mediaFile);
	}

	private boolean isSupportedFileType(String fileType) {
		return fileType.equals("audio/mpeg") || fileType.equals("video/mp4");
	}
}
