package com.otp.Xamp.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
						List<Book> checkLink = bookRepo.findByBookLink(row.getCell(2).getStringCellValue().toString());

						if (checkLink.size() > 0) {
							continue;
						}
						Book book = new Book();
						book.setSubjectName(row.getCell(0).getStringCellValue()); // Assuming name is in the first
																					// column
						book.setBookName(row.getCell(1).getStringCellValue()); // Assuming email is in the second column
						book.setBookLink(row.getCell(2).getStringCellValue());
						book.setHref(row.getCell(3).getStringCellValue());
						book.setClassName(sheet.getSheetName());
						books.add(book);
						bookRepo.save(book);
					}
					check_header++;
				}
			}
		}

	}

	public List<Book> bookDownload(String className, String subject, String bookName) throws IOException {
		System.out.println("For Class:   " + className + "  Subject:  " + subject + "  BookName:  " + bookName);
		List<Book> bookData = new ArrayList<>();
		bookData = bookRepo.findByClassNameSubjectAndBookName(className, subject, bookName);
		String bookUrl = "";
		if (bookData.size() > 0) {
			for (Book book : bookData) {
				bookUrl = book.getHref();
				
				Document doc = Jsoup.connect(bookUrl).get();
				System.out.println(doc.data().toString());
			}
		}

		return bookData;
	}
}
