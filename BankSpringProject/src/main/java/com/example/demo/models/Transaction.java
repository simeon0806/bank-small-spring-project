package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = Transaction.FIND_ALL_TRANSACTION, query = "SELECT t FROM Transaction t"),
		@NamedQuery(name = Transaction.DELETE_BY_SORCE_ACCOUNT, query = "DELETE FROM Transaction t WHERE t.sorceAccount.id = :pId"),
		@NamedQuery(name = Transaction.DELETE_BY_DESTINATION_ACCOUNT, query = "DELETE FROM Transaction t WHERE t.destinationAccount.id = :pId") })
public class Transaction extends _BaseEntity {

	public static final String FIND_ALL_TRANSACTION = "Transaction.findAll";
	public static final String DELETE_BY_SORCE_ACCOUNT = "Transaction.deleteBySorceAccoutID";
	public static final String DELETE_BY_DESTINATION_ACCOUNT = "Transaction.deleteByDestinationAccoutID";

	@ManyToOne
	private Account sorceAccount;

	@ManyToOne
	private Account destinationAccount;

	private BigDecimal amount;

	private LocalDateTime makedAt;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getSrcAccount() {
		return sorceAccount;
	}

	public Account getDestAccount() {
		return destinationAccount;
	}

	public LocalDateTime getMakedAt() {
		return makedAt;
	}

	public void setSrcAccount(Account sorceAccount) {
		this.sorceAccount = sorceAccount;
	}

	public void setDistAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public void setMakedAt(LocalDateTime makedAt) {
		this.makedAt = makedAt;
	}

}
