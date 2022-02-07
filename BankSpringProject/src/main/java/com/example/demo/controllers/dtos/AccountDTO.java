package com.example.demo.controllers.dtos;

import java.math.BigDecimal;

public class AccountDTO {

	private String ownerName;

	private BigDecimal balance;

	public String getOwnerName() {
		return ownerName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
