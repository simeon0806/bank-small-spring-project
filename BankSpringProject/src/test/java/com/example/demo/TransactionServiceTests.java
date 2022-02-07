package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.services.TransactionService;

@SpringBootTest
public class TransactionServiceTests {

	@PersistenceContext
	EntityManager em;

	@Autowired
	TransactionService transactionService;

	@Test
	public void getAllTransactions_test() {

		List<Transaction> transactionsFromService = transactionService.getAllTransactions();
		List<Transaction> transactionsFromDB = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();

		assertNotNull(transactionsFromService);
		assertEquals(transactionsFromDB.size(), transactionsFromService.size());

	}
	
	@Test
	@Transactional
	public void createTransaction_test() {
		
		List<Account> accounts = em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
		
		if(!accounts.isEmpty()) {
			transactionService.transaction(accounts.get(0).getId(),accounts.get(1).getId(),BigDecimal.valueOf(6));
//			em.createQuery("SELECT t FROM Transaction t WHERE t.id = :pId",Transaction.class)
		}
		
	}

}
