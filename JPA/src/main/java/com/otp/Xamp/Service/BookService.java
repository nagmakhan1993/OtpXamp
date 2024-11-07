package com.otp.Xamp.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Entity.Book;
import com.otp.Xamp.Repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;

	int i = 1;
	private static final String TEMP_DIRECTORY = "C:/Users/Dell/Downloads/tempData";
	String baseDirectories = "C:/Users/Dell/Downloads/BookPdf";

	public void importBooksFromExcel(MultipartFile file) throws IOException {
		List<Book> books = new ArrayList<>();

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

				int check_header = 0;

				Sheet sheet = workbook.getSheetAt(i);

				for (Row row : sheet) {

					if (check_header == 0) {
						check_header++;
						continue;
					} else {
						System.out.println("check_header...." + check_header);
						List<Book> checkLink = bookRepo.findByHref(row.getCell(2).getStringCellValue().toString());

						if (checkLink.size() > 0) {
							continue;
						}
						Book book = new Book();
						book.setSubjectName(row.getCell(0).getStringCellValue()); // Assuming name is in the first
						book.setBookName(row.getCell(1).getStringCellValue()); // column
						book.setHref(row.getCell(2).getStringCellValue());
						int numOfChapter = (int) row.getCell(3).getNumericCellValue();
						book.setNumberOfChapter(String.valueOf(numOfChapter));
						book.setOriginLink(row.getCell(4).getStringCellValue());
						book.setClassName(sheet.getSheetName());
						books.add(book);
						bookRepo.save(book);
					}
					check_header++;
				}
			}
		}

	}

	public String bookDownload(String className, String subject, String bookName) throws IOException {
		System.out.println("For Class:   " + className + "  Subject:  " + subject + "  BookName:  " + bookName);
		List<Book> bookData = new ArrayList<>();
		bookData = bookRepo.findByClassNameSubjectAndBookName(className, subject, bookName);
		String bookUrl = "";
		if (bookData.size() > 0) {
			for (Book book : bookData) {
				bookUrl = book.getOriginLink();

				System.out.println(bookUrl);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<byte[]> response = restTemplate.exchange(URI.create(bookUrl), HttpMethod.GET,
							HttpEntity.EMPTY, byte[].class);

					if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {

						String customFileName = "C:/Users/Dell/Downloads/bookPdf/" + book.getClassName() + "/"
								+ book.getSubjectName() + "/" + book.getBookName();

						Path outputPath = Paths.get(customFileName);
						Files.createDirectories(outputPath);

						Path StoredPath = new File(outputPath + "/" + book.getBookName() + ".zip").toPath();

						Files.write(StoredPath, response.getBody());

						// Save the file to the local system
						return "File downloaded successfully: " + outputPath.toAbsolutePath();
					} else {
						return "Failed to download file. HTTP Status: " + response.getStatusCode();
					}
				} catch (IOException e) {
					return "Error saving the file: " + e.getMessage();
				} catch (Exception e) {
					return "Error occurred: " + e.getMessage();
				}
			}
		}
		return "method did not perfoRM";

	}

	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}

	public File downloadAndProcessZip(String zipUrl) throws IOException {
		// Create a temporary directory for processing
		Path tempDir = Paths.get(TEMP_DIRECTORY);
		Files.createDirectories(tempDir);
		// Step 1: Download the ZIP file
		Book book = bookRepo.findByOriginLink(zipUrl);
		String folderName = book.getClassName() + "_" + book.getSubjectName() + "_" + book.getBookName() + ".zip";
		File zipFile = new File(tempDir.toString(), "downloaded.zip");
		FileUtils.copyURLToFile(new URL(zipUrl), zipFile);

		File renamedZipFile = new File(tempDir.toString(), folderName);
		try (ZipArchiveInputStream zis = new ZipArchiveInputStream(new FileInputStream(zipFile));
				ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(renamedZipFile))) {

			ZipArchiveEntry entry;
			while ((entry = zis.getNextZipEntry()) != null) {
				// Create a new entry with a modified name
				String chapterNumber = entry.getName().substring(entry.getName().length() - 2);
				String newFileName = "Chapter_" + chapterNumber;
				ZipArchiveEntry newEntry = new ZipArchiveEntry(newFileName);

				zos.putArchiveEntry(newEntry);

				try (OutputStream output = zos) {
					zis.transferTo(output);
					zos.closeArchiveEntry();
				}

			}

		}

		// Step 3: Return the re-zipped file
		return renamedZipFile;
	}

	public void crateFolderAccordingBooks() {

		System.out.println("Number of Records in DB : " + bookRepo.count());

		List<String> listOfClasses = bookRepo.listOfClasses();
		listOfClasses.forEach(className -> {

			List<String> listOfSubjectClassWise = bookRepo.listOfSubjectsClassWise(className);

			listOfSubjectClassWise.forEach(subjectName -> {

				List<String[]> listOfBooksAndOriginLinkClassAndSubjectWise = new ArrayList<>();
				listOfBooksAndOriginLinkClassAndSubjectWise = bookRepo
						.listOfBooksAndOriginLinkClassAndSubjectWise(className, subjectName);
				listOfBooksAndOriginLinkClassAndSubjectWise.forEach(bookData -> {
					String bookName = bookData[0];
					String bookOriginLink = bookData[1];
					System.out.println(i + " ClassName: " + className + " SubjectName: " + subjectName + " BookName: "
							+ bookName + " BookLink: " + bookOriginLink);
					Path basePath = Paths.get(baseDirectories);

					Path classPath = basePath.resolve(className);

					Path subjectPath = classPath.resolve(subjectName);
					if (bookName.contains("Contemporary")) {
						System.out.println();
					}
					bookName = sanitizeFileName(bookName);
					Path bookPath = subjectPath.resolve(bookName.trim());
					try {
						Files.createDirectories(bookPath);
						System.out.println("Folder created successfully: " + bookPath);

						Path zipFilePath = bookPath.resolve(bookName + ".zip");

						downloadFile(bookOriginLink, bookPath.resolve(bookName + ".zip"));
						unzipFile(zipFilePath, bookPath);
						Files.delete(zipFilePath);
						System.out.println("File unzipped and original .zip file deleted.");
						renameFilesInFolder(bookPath);
					} catch (IOException e) {
						System.out.println("An error occurred while creating the folders.");
						e.printStackTrace();
					}
					i++;
				});

			});

		});

	}

	public static String sanitizeFileName(String fileName) {
		return fileName.replaceAll("[<>:\"/\\|?*]", "_");
	}

	public static void downloadFile(String fileURL, Path targetPath) {
		try (InputStream in = new URL(fileURL).openStream()) {
			Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING); // Download and save the file
			System.out.println("File downloaded successfully: " + targetPath);
		} catch (IOException e) {
			System.out.println("An error occurred while downloading the file.");
			e.printStackTrace();
		}
	}

	public static void unzipFile(Path zipFilePath, Path targetDir) {
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath.toFile()))) {
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				Path extractedFilePath = targetDir.resolve(entry.getName());

				// Create directories for the file if they do not exist
				if (entry.isDirectory()) {
					Files.createDirectories(extractedFilePath);
				} else {
					// Extract file content
					try (BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(extractedFilePath.toFile()))) {
						byte[] buffer = new byte[1024];
						int len;
						while ((len = zis.read(buffer)) > 0) {
							bos.write(buffer, 0, len);
						}
					}
				}
				zis.closeEntry();
			}
			System.out.println("File unzipped successfully to: " + targetDir);
		} catch (IOException e) {
			System.out.println("An error occurred while unzipping the file.");
			e.printStackTrace();
		}
	}

	public static void renameFilesInFolder(Path folderPath) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
			for (Path entry : stream) {
				if (!Files.isDirectory(entry)) {

					String newName = entry.getFileName().toString();

					String Check = newName.substring(newName.indexOf(".") - 2, newName.indexOf("."));
					if (Check.matches("[0-9]+")) {
						newName = "Chapter_" + newName.substring(newName.indexOf(".") - 2, newName.indexOf("."));
					} else if (Check.equals("ps")) {
						newName = "Prelims" + newName.substring(newName.indexOf(".") - 2, newName.indexOf("."));
					}

					Path renamedFilePath = folderPath.resolve(newName);

					Files.move(entry, renamedFilePath);
					System.out.println("Renamed file: " + entry.getFileName() + " to " + newName);
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred while renaming files.");
			e.printStackTrace();
		}
	}
}
