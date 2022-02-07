package com.example.demo.controllers.dtos;

import java.math.BigDecimal;

public class CreateTransactionDTO {

	private Long sorceAccountId;

	private Long destinationAccountId;

	private BigDecimal amount;

	public Long getSorceAccountId() {
		return sorceAccountId;
	}

	public Long getDestinationAccountId() {
		return destinationAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setSorceAccountId(Long sorceAccountId) {
		this.sorceAccountId = sorceAccountId;
	}

	public void setDestinationAccountId(Long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
