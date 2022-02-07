package com.example.demo.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.services.AccountService;

@Service
public class AccountServiceImpl extends _BaseService implements AccountService {

	@Override
	@Transactional
	public void createAccount(Account acc) {
		entityManager.persist(acc);
	}

	@Override
	public List<Account> getAllAccounts() {
		return entityManager.createNamedQuery(Account.FIND_ALL_ACCOUNDS, Account.class).getResultList();
	}

	@Override
	public Account getAccount(String name) {
		return entityManager.createNamedQuery(Account.FIND_ACCOUNT_BY_NAME, Account.class).setParameter("name", name)
				.getSingleResult();
	}

	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteAccount(String name) {
		Account a = getAccount(name);
		entityManager.createNamedQuery(Transaction.DELETE_BY_SORCE_ACCOUNT).setParameter("pId", a.getId())
				.executeUpdate();
		entityManager.createNamedQuery(Transaction.DELETE_BY_DESTINATION_ACCOUNT).setParameter("pId", a.getId())
				.executeUpdate();
		entityManager.remove(getAccount(name));
	}

}
