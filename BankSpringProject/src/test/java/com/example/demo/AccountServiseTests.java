package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Account;
import com.example.demo.services.AccountService;

@SpringBootTest
public class AccountServiseTests {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountService accountService;

	private Account testAccount;

	AccountServiseTests() {
		testAccount = new Account();
		testAccount.setOwnerName("test");
	}

	@Test
	@Transactional
	void getAccount_test_withExistingAccount() {

		em.persist(testAccount);

		Account account = accountService.getAccount(testAccount.getOwnerName());

		assertNotNull(account);
		assertEquals(account.getOwnerName(), testAccount.getOwnerName());
		assertNotNull(account.getId());
	}

	@Test
	@Transactional
	void getAccount_test_notValidName_or_nullName() {
		try {
			accountService.getAccount("Not_valid_name");
			accountService.getAccount(null);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

//	@Test
	@Transactional
	@Rollback(value = false)
	void deleteAccount_test_withExistingAccount() {

		em.persist(testAccount);

		try {
			accountService.deleteAccount(testAccount.getOwnerName());
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

//		Assert.assertEquals(em.createQuery("DELETE FROM Account a WHERE a.ownerName = :pOwnerName").setParameter("pOwnerName", testAccount.getOwnerName()).executeUpdate(), 1);
	}

	@Test
	@Transactional
	void deleteAccount_test_notValidName_or_nullName() {
		try {
			accountService.deleteAccount("Not_valid_name");
			accountService.deleteAccount(null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	@Transactional
	void createAccount_test_notValidName_or_nullName() {
		accountService.createAccount(testAccount);
		Account account = em.createQuery("SELECT a FROM Account a WHERE a.ownerName = :pName ", Account.class)
				.setParameter("pName", testAccount.getOwnerName()).getSingleResult();

		assertNotNull(account);
		assertEquals(testAccount.getOwnerName(), account.getOwnerName());
	}
	
	@Test
	@Transactional
	void getAllAccounts_test() {
		 List<Account> accountsFromService = accountService.getAllAccounts();
		 List<Account> accountsFromDB = em.createQuery("SELECT a FROM Account a",Account.class).getResultList();
		 
		 assertNotNull(accountsFromService);
		 assertEquals(accountsFromDB.size(), accountsFromService.size());
	}

}
