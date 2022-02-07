package com.example.demo.controllers;

import java.io.ByteArrayOutputStream;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.controllers.dtos.MessageDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.ExcelGeneratorService;
import com.example.demo.services.PdfGeneratorService;
import com.example.demo.services.TransactionService;

@CrossOrigin(origins = "http://localhost:4200")
public class _BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(_BaseController.class);

	@Autowired
	protected ModelMapper mapper;

	@Autowired
	protected TransactionService transactionService;

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected PdfGeneratorService pdfGeneratorService;

	@Autowired
	protected ExcelGeneratorService excelGeneratorService;

	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<MessageDTO> exceptionHandler() {
		return new ResponseEntity<>(new MessageDTO("Stana bela"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected static final ByteArrayOutputStream stream = new ByteArrayOutputStream(4096);
	
}
