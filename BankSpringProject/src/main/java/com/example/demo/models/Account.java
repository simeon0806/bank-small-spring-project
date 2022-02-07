package com.example.demo.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = Account.FIND_ALL_ACCOUNDS, query = "SELECT a FROM Account a"),
	@NamedQuery(name = Account.FIND_ACCOUNT_BY_NAME, query = "SELECT a FROM Account a WHERE a.ownerName = :name") 
})
public class Account extends _BaseEntity {

	public static final String FIND_ALL_ACCOUNDS = "Account.findAll";
	public static final String FIND_ACCOUNT_BY_NAME = "Account.findAccountByName";

	private String ownerName;

	private BigDecimal balance;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
