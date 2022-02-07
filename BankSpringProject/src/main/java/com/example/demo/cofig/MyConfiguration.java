package com.example.demo.cofig;

import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.proxy.EntityManagerProxy;

@Configuration
public class MyConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

//		 TypeMap<Transaction, TransactionDTO> transactionTypeMap = modelMapper.createTypeMap(Transaction.class, TransactionDTO.class);
//		 transactionTypeMap.addMapping(Transaction::getSrcAccount::ge, TransactionDTO::setSorceAccount);
//		 transactionTypeMap.addMapping(Transaction::getDestAccount, TransactionDTO::setDestinationAccount);
//		 transactionTypeMap.addMapping(Transaction::getAmount, TransactionDTO::setAmount);
//		 
//		 TypeMap<CreateTransactionDTO, Transaction> createTransactionTypeMap = modelMapper.createTypeMap(CreateTransactionDTO.class, Transaction.class);
//		 createTransactionTypeMap.addMapping(CreateTransactionDTO::getSorceAccount, Transaction::setSrcAccount);
//		 createTransactionTypeMap.addMapping(CreateTransactionDTO::getDestinationAccount, (dest , id) -> {
//			 Account account = new Account();
//			 account.setId((Long)id);
//			 dest.setDistAccount(account);
//		 });
//		 createTransactionTypeMap.addMapping(CreateTransactionDTO::getAmount, Transaction::setAmount);

		return modelMapper;
	}
	
	@PersistenceContext
	EntityManager em;
	
	@Bean
	@Primary
	EntityManager entityManager() {	
		return (EntityManager) Proxy.newProxyInstance(EntityManager.class.getClassLoader(), new Class[] {EntityManager.class}, new EntityManagerProxy(em));
	}

}
