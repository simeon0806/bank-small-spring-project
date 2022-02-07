package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Account;

public interface AccountService {

	public void createAccount(Account acc);

	public List<Account> getAllAccounts();

	public Account getAccount(String name);

	public void deleteAccount(String name);

}
