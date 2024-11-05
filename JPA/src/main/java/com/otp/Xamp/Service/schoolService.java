package com.otp.Xamp.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
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
			try (FileOutputStream fileOut = new FileOutputStream("SchoolList.xlsx")) {
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
				schoolObj.setUDISE_Code(row.getCell(4).getStringCellValue());
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
		return this.repo.findAll();
	}

	public void exportSchoolListToExcel() throws IOException {

		List<school> schoolList = repo.findAll();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("SchoolList");

		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("S.NO");
		headerRow.createCell(1).setCellValue("District");
		headerRow.createCell(2).setCellValue("Zone");
		headerRow.createCell(3).setCellValue("SchoolID");
		headerRow.createCell(4).setCellValue("UDISE_Code");
		headerRow.createCell(5).setCellValue("Buildingid");
		headerRow.createCell(6).setCellValue("School_Name");
		headerRow.createCell(7).setCellValue("Address");
		headerRow.createCell(8).setCellValue("Shift");
		headerRow.createCell(9).setCellValue("SchoolLevel");
		headerRow.createCell(10).setCellValue("Gender");
		headerRow.createCell(11).setCellValue("Phone");
		headerRow.createCell(12).setCellValue("Hos_Name");
		headerRow.createCell(13).setCellValue("Latitude");
		headerRow.createCell(14).setCellValue("Longitude");
		int ountOfColumns = headerRow.getPhysicalNumberOfCells();
		int rowIndex = 1;
		int srNo = 0;
		for (school data : schoolList) {
			Row row = sheet.createRow(rowIndex++);
			srNo++;
			row.createCell(0).setCellValue(srNo);
			row.createCell(1).setCellValue(data.getDistrict());
			row.createCell(2).setCellValue(data.getZone());
			row.createCell(3).setCellValue(data.getSchoolID());
			row.createCell(4).setCellValue(data.getUDISE_Code());
			row.createCell(5).setCellValue(data.getBuildingid());
			row.createCell(6).setCellValue(data.getSchoolName());
			row.createCell(7).setCellValue(data.getAddress());
			row.createCell(8).setCellValue(data.getShift());
			row.createCell(9).setCellValue(data.getSchoolLevel());
			row.createCell(10).setCellValue(data.getGender());
			row.createCell(11).setCellValue(data.getPhone());
			row.createCell(12).setCellValue(data.getHosName());
			row.createCell(13).setCellValue(data.getLatitude());
			row.createCell(14).setCellValue(data.getLongitude());
		}
		for (int i = 0; i < ountOfColumns; i++) {
			sheet.autoSizeColumn(i);
		}

		Path downloadsPath = new File("C:/Users/Dell/Downloads/" + "schoolList.xlsx").toPath();

		File file = downloadsPath.toFile();

		try (FileOutputStream fos = new FileOutputStream(file)) {
			workbook.write(fos);
		}

		workbook.close();

		System.out.println("USchool List exported to Excel file at: " + file.getAbsolutePath());
	}

	public static int getFieldCount(Object obj) {
		Class<?> objClass = obj.getClass(); // Get the object's class
		Field[] fields = objClass.getDeclaredFields(); // Get all declared fields
		return fields.length; // Return the number of fields
	}
}
