package com.example.demo.controllers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controllers.dtos.AccountDTO;
import com.example.demo.controllers.dtos.CreateAccountDTO;
import com.example.demo.controllers.dtos.MessageDTO;
import com.example.demo.models.Account;
import com.itextpdf.text.DocumentException;

@Controller
@RequestMapping("/account")
public class AccountController extends _BaseController {

	@PostMapping("/add")
	public ResponseEntity<String> addAccount(@RequestBody CreateAccountDTO accountDTO) {
		Account account = mapper.map(accountDTO, Account.class);
		accountService.createAccount(account);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		logger.info("Poiskan metot getAllAccount");
		List<AccountDTO> accounts = accountService.getAllAccounts().stream()
				.map(account -> mapper.map(account, AccountDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<List<AccountDTO>>(accounts, HttpStatus.OK);
	}

	@PostMapping("/get/{name}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable String name) {
		Account account = accountService.getAccount(name);
		AccountDTO accountDTO = mapper.map(account, AccountDTO.class);
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{name}")
	public ResponseEntity<MessageDTO> deleteAccount(@PathVariable String name) {
		accountService.deleteAccount(name);
		return new ResponseEntity<MessageDTO>(new MessageDTO("Sucessfuly deleted account with name " + name),
				HttpStatus.OK);
	}

	@GetMapping(path = "/allInPDF", produces = MediaType.APPLICATION_PDF_VALUE)
	public void accountsReporsPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=accounts_report.pdf");
		pdfGeneratorService.getAllAccountsPDF(response.getOutputStream());
	}

	@GetMapping(path = "/allInExcel")
	public void accountsReporsExcel(HttpServletResponse response, @RequestParam boolean ziped)
			throws DocumentException, IOException {

		OutputStream out = response.getOutputStream();

		if (ziped) {
			try (ZipOutputStream zipStream = new ZipOutputStream(out)) {
				zipStream.setLevel(Deflater.BEST_COMPRESSION);
				zipStream.putNextEntry(new ZipEntry("accounts_report.xlsx"));
				generateExcel(response, zipStream, "application/zip", "attachment; filename=accounts_report.zip");
			}
		} else {
			generateExcel(response, out, "application/vnd.ms-excel", "attachment; filename=accounts_report.xlsx");
		}

	}

	private void generateExcel(HttpServletResponse response, OutputStream out, String contentType,
			String contentHederValue) throws IOException {
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", contentHederValue);
		excelGeneratorService.getAllAccountsExcel(out);
	}

	@GetMapping(path = "/huj")
//	@ApiOperation(value = "huj", authorizations = { @Authorization(value = "JWT Token") })
//	@PreAuthorize("hasAuthority('Administrator')")
	public void huj(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		Arrays.asList(Objects.isNull(request.getCookies()) ? new Cookie[] {} : request.getCookies()).forEach(cookie -> {
			logger.info(cookie.getName() + " :  " + cookie.getValue());
		});
		response.addCookie(new Cookie("simo_i_batko_pravat_coockie", "cookie2"));
		response.getOutputStream().print("huj");
	}

	// TODO: Don't use this method
	@Deprecated
	@GetMapping(path = "/allInExceld")
	public synchronized ResponseEntity<ByteArrayResource> accountsReporsExcelD() throws DocumentException, IOException {

		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application.xlsx", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=accounts_report.xlsx");

			return new ResponseEntity<>(
					new ByteArrayResource(excelGeneratorService.getAllAccountsExceld(stream).toByteArray()), header,
					HttpStatus.OK);
		} 
		finally {
			stream.reset();
		}
	}

	// TODO: Don't use this method
	@Deprecated
	@GetMapping(path = "/allInPDFd1")
	public void accountsReporsPDF1(HttpServletResponse response) throws DocumentException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=accounts_report.pdf");

		BufferedOutputStream bufferedOut = new BufferedOutputStream(response.getOutputStream(), 8192);
		pdfGeneratorService.getAllAccountsPDF(bufferedOut);

	}

	@Deprecated
	@GetMapping(path = "/allInPDFd2")
	public synchronized void accountsReporsPDF2(HttpServletResponse response) throws DocumentException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=accounts_report.pdf");

		try {
			pdfGeneratorService.getAllAccountsPDF(stream);
			IOUtils.write(stream.toByteArray(), response.getOutputStream());
		} finally {
			stream.reset();
		}

	}

}
