package com.example.demo.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.models.Account;

public class CacheAccounts {
	public static final int CACHE_LIMIT = 5;

	private static Map<Long, Account> cache = new HashMap<>();

	public static void fillCache(List<Account> accounts) {
		cache.clear();
		cache = accounts.stream().collect(Collectors.toMap(Account::getId, Function.identity()));
	}

	public static Account getAccountByID(Long id) {
		return cache.get(id);
	}

	public static List<Account> getCachedAccounts() {
		return new ArrayList<Account>(cache.values());
	}

	public static Map<Long, Account> getCache() {
		return cache;
	}

}
