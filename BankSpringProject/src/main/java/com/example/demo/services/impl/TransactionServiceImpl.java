package com.example.demo.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.models.TransactionType;
import com.example.demo.services.TransactionService;

@Service
public class TransactionServiceImpl extends _BaseService implements TransactionService {

	@Override
	public List<Transaction> getAllTransactions() {
		return entityManager.createNamedQuery(Transaction.FIND_ALL_TRANSACTION, Transaction.class).getResultList();
	}

	@Override
	@Transactional
	public void transaction(Long from, Long to, BigDecimal amount) {

		Account fromAcc = entityManager.find(Account.class, from, LockModeType.PESSIMISTIC_WRITE);
		Account toAcc = entityManager.find(Account.class, to, LockModeType.PESSIMISTIC_WRITE);

		fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
		toAcc.setBalance(toAcc.getBalance().add(amount));

		LocalDateTime createTime = LocalDateTime.now();

		createTransaction(fromAcc, toAcc, amount, createTime, TransactionType.DEBIT);
		createTransaction(toAcc, fromAcc, amount, createTime, TransactionType.CREDIT);
	}

	private void createTransaction(Account fromAcc, Account toAcc, BigDecimal amount, LocalDateTime createTime,
			TransactionType type) {
		Transaction transaction = new Transaction();
		transaction.setSrcAccount(fromAcc);
		transaction.setDistAccount(toAcc);
		transaction.setAmount(amount);
		transaction.setMakedAt(createTime);
		transaction.setType(type);
		entityManager.persist(transaction);
	}

}
