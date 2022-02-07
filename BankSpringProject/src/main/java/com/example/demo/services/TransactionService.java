package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.models.Transaction;

public interface TransactionService {

	public void transaction(Long from, Long to, BigDecimal amount);

	public List<Transaction> getAllTransactions();
}
