package com.example.demo.services.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.services.PdfGeneratorService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfGeneratorServiceImpl extends _BaseService implements PdfGeneratorService {

	@Override
	public void getAllAccountsPDF(OutputStream out) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, out);
		document.open();

		Paragraph titleParagraph = new Paragraph("All Accounds");
		titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(titleParagraph);

		PdfPTable table = new PdfPTable(new float[] { 1.5f, 5f, 5f });
		Font font = new Font(FontFamily.TIMES_ROMAN, 12);

		insertRow(table, "", "Account Name", "Balance", font);

		Integer i = 1;
		List<Account> accounts = entityManager.createNamedQuery(Account.FIND_ALL_ACCOUNDS, Account.class).getResultList();
		for (Account account : accounts) {
			insertRow(table, i.toString(), account.getOwnerName(), account.getBalance().toString(), font);
			i++;
		}
		
		document.add(table);

		document.close();
	}

	private void insertRow(PdfPTable table, String row1, String row2, String row3, Font font) {
		insertCell(table, row1, Element.ALIGN_LEFT, font);
		insertCell(table, row2, Element.ALIGN_LEFT, font);
		insertCell(table, row3, Element.ALIGN_RIGHT, font);
	}

	private void insertCell(PdfPTable table, String text, int align, Font font) {

		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(1);
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		table.addCell(cell);
	}

}
