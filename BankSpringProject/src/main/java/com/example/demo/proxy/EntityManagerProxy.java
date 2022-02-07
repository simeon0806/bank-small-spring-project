package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.example.demo.models.Account;

public class EntityManagerProxy implements InvocationHandler {

	EntityManager original;

	public EntityManagerProxy(EntityManager original) {
		this.original = original;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//		if (("createNamedQuery".equals(method.getName())) && (args[0].equals(Account.FIND_ALL_ACCOUNDS))) {
//			
//			System.out.println("sadsadsadsa");
//			
//			checkForEmtyCache();
//
//			List<Account> accounts = CacheAccounts.getCachedAccounts();
//
//			accounts.addAll(original.createNamedQuery(
//					Account.FIND_ALL_ACCOUNDS, Account.class).setFirstResult(accounts.size()) // CacheAccounts.CACHE_LIMIT ?
//					.getResultList());
//			
//			return accounts;
//		}
		
		if(("find".equals(method.getName())) && (args[0].equals(Account.class))) {
			
			checkForEmtyCache();
						
			if(CacheAccounts.getCache().containsKey(args[1])) {
				return CacheAccounts.getCache().get(args[1]);
			}
			
		}
		
		if("remove".equals(method.getName())) {
			
			if(CacheAccounts.getCache().containsKey(args[0])) {
				method.invoke(original, args);
				fillCache();
				return null;
			}
			
		}
		
		if("persist".equals(method.getName())) {
			
			if(CacheAccounts.getCache().size()<CacheAccounts.CACHE_LIMIT) {
				method.invoke(original, args);
				fillCache();
				return null;
			}
			
		}

		return method.invoke(original, args);
	}

	private void fillCache() {
		CacheAccounts.fillCache(original.createQuery("SELECT a FROM Account a", Account.class).setMaxResults(CacheAccounts.CACHE_LIMIT)
				.getResultList());
	}

	private void checkForEmtyCache() {
		if (CacheAccounts.getCachedAccounts().isEmpty()) {
			fillCache();
		}
	}
	
	
}
