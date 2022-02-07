package com.example.demo.services;

import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.DocumentException;

public interface PdfGeneratorService {

	void getAllAccountsPDF(OutputStream out) throws DocumentException, IOException;

}
