package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface ExcelGeneratorService {

	public void getAllAccountsExcel(OutputStream out) throws IOException;
	
	@Deprecated
	public ByteArrayOutputStream getAllAccountsExceld(ByteArrayOutputStream stream) throws IOException;

}
