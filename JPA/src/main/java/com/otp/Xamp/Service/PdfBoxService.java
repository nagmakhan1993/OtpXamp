package com.otp.Xamp.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otp.Xamp.Model.TextCoordinate;

@Service
public class PdfBoxService {

	public List<TextCoordinate> extractTextWithCoordinates(MultipartFile file) throws IOException {

		Set<String> processedWords = new LinkedHashSet<>();

		Map<Float, String> p = new HashMap<>();
		List<TextCoordinate> textCoordinates = new ArrayList<>();

		try (PDDocument document = PDDocument.load(file.getInputStream())) {
			PDFTextStripper textStripper = new PDFTextStripper() {
				@Override
				protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
					for (TextPosition textPosition : textPositions) {

						String[] words = string.split(" ");
						for (String word : words) {
							System.out.println("Word: " + word);
							String normalizedWord = word.trim().toLowerCase();

							if (!processedWords.contains(normalizedWord)) {
								TextCoordinate coordinate = new TextCoordinate();

								coordinate.setText(word);
								coordinate.setX(textPosition.getXDirAdj());
								coordinate.setY(textPosition.getYDirAdj());
								textCoordinates.add(coordinate);
								processedWords.add(normalizedWord);
								p.put(textPosition.getYDirAdj(), word);
							}
						}
						for (Map.Entry<Float, String> data : p.entrySet()) {

							System.out.println("  word:  " + data.getValue() + "   y:  " + data.getKey());

						}
					}
				}
			};
			textStripper.setSortByPosition(true);
			textStripper.getText(document);

			document.close();
		}

		return textCoordinates;
	}

	public String mergePdfs(List<MultipartFile> files, String outputFilePath) throws IOException {
		PDFMergerUtility pdfMerger = new PDFMergerUtility();

		pdfMerger.setDestinationFileName(outputFilePath);

		for (MultipartFile file : files) {
			pdfMerger.addSource(file.getInputStream());
		}

		pdfMerger.mergeDocuments(null);

		return outputFilePath;
	}
}
