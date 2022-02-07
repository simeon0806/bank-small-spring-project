package com.example.demo.controllers.dtos;

import java.math.BigDecimal;

public class TransactionDTO {

	private String sorceAccountOwnerName;

	private String destinationAccountOwnerName;

	private BigDecimal amount;

	public String getSorceAccountOwnerName() {
		return sorceAccountOwnerName;
	}

	public String getDestinationAccountOwnerName() {
		return destinationAccountOwnerName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setSorceAccountOwnerName(String sorceAccountOwnerName) {
		this.sorceAccountOwnerName = sorceAccountOwnerName;
	}

	public void setDestinationAccountOwnerName(String destinationAccountOwnerName) {
		this.destinationAccountOwnerName = destinationAccountOwnerName;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
