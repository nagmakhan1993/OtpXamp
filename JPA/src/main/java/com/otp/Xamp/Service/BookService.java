package com.otp.Xamp.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

						String customFileName = book.getBookName() + ".zip"; // You can modify this
						Path outputPath = new File("C:/Users/Dell/Downloads/" + customFileName).toPath();

						Files.write(outputPath, response.getBody());

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
}
