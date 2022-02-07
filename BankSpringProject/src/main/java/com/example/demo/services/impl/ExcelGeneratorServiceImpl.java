package com.example.demo.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.services.ExcelGeneratorService;

@Service
public class ExcelGeneratorServiceImpl extends _BaseService implements ExcelGeneratorService {

	static String[] HEADERs = { "Account Name", "Balance" };

	public void getAllAccountsExcel(OutputStream out) throws IOException {

		try (Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("Accounts");

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				headerRow.createCell(col).setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			List<Account> accounts = entityManager.createNamedQuery(Account.FIND_ALL_ACCOUNDS, Account.class)
					.getResultList();

			for (Account account : accounts) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(account.getOwnerName());
				row.createCell(1).setCellValue(account.getBalance().doubleValue());
			}

			workbook.write(out);
		}
	}

	@Deprecated
	public ByteArrayOutputStream getAllAccountsExceld(ByteArrayOutputStream stream) throws IOException {

		try (Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("Accounts");

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				headerRow.createCell(col).setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			List<Account> accounts = entityManager.createNamedQuery(Account.FIND_ALL_ACCOUNDS, Account.class)
					.getResultList();

			for (Account account : accounts) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(account.getOwnerName());
				row.createCell(1).setCellValue(account.getBalance().doubleValue());
			}

			workbook.write(stream);
			return stream;
		}
	}

}
