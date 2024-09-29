package com.otp.Xamp.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.Xamp.Entity.school;
import com.otp.Xamp.Repository.schoolRepo;

@Service
public class schoolService {

	@Autowired
	private schoolRepo repo;

	public school syncSchool() {

		String urlString = "https://www.edudel.nic.in/mis/eis/frmSchoolList.aspx?type=8v6AC39/z0ySjVIkvfDJzvxkdDvmSsz7pgALKMjL3UI=";
		try {
			// Fetch and parse the HTML document
			Document doc = Jsoup.connect(urlString).get();

			// Create a new Excel workbook and sheet
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Data");

			// Select the table (assuming there's one table in the HTML)
			Element table = doc.select("table").last();

			if (table != null) {
				// Loop through the rows of the table
				int rowIndex = 0;
				for (Element row : table.select("tr")) {
					Row excelRow = sheet.createRow(rowIndex++);
					Elements cols = row.select("th, td"); // Get both header and data cells

					int colIndex = 0;
					for (Element col : cols) {
						Cell cell = excelRow.createCell(colIndex++);
						cell.setCellValue(col.text()); // Set the cell value
					}
				}
			}

			// Write the output to a file
			try (FileOutputStream fileOut = new FileOutputStream("output.xlsx")) {
				workbook.write(fileOut);
			}
			Sheet new_sheet = workbook.getSheetAt(0);

			for (Row row : new_sheet) {
				// Assuming the first row is a header, skip it
				if (row.getRowNum() == 0) {
					continue;
				}

				school schoolObj = new school();

				schoolObj.setDistrict(row.getCell(1).getStringCellValue());
				schoolObj.setZone(row.getCell(2).getStringCellValue());
				schoolObj.setSchoolID(row.getCell(3).getStringCellValue());
				schoolObj.setuDISE_Code(row.getCell(4).getStringCellValue());
				schoolObj.setBuildingid(row.getCell(5).getStringCellValue());
				schoolObj.setSchoolName(row.getCell(6).getStringCellValue());
				schoolObj.setAddress(row.getCell(7).getStringCellValue());
				schoolObj.setShift(row.getCell(8).getStringCellValue());
				schoolObj.setSchoolLevel(row.getCell(9).getStringCellValue());
				schoolObj.setGender(row.getCell(10).getStringCellValue());
				schoolObj.setPhone(row.getCell(11).getStringCellValue());
				schoolObj.setHosName(row.getCell(12).getStringCellValue());
				schoolObj.setLatitude(row.getCell(13).getStringCellValue());
				schoolObj.setLongitude(row.getCell(14).getStringCellValue());

				List<school> schoolID = null;
				schoolID = repo.findBySchoolID(schoolObj.getSchoolID());

				if (schoolID.size() == 0) {
					this.repo.save(schoolObj);
					System.out.println("Record Saved Successfully....!!!");
				}
			}

			workbook.close();
			System.out.println("Excel file created successfully!");

		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<school> getschoolList() {
		// TODO Auto-generated method stub
		return this.repo.findAll();

	}

}
