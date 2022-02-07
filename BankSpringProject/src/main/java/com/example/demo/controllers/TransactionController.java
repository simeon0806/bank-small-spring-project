package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.dtos.CreateTransactionDTO;
import com.example.demo.controllers.dtos.MessageDTO;
import com.example.demo.controllers.dtos.TransactionDTO;

@Controller
@RequestMapping("/transaction")
public class TransactionController extends _BaseController {

	@PostMapping("/add")
	public ResponseEntity<MessageDTO> transaction(@RequestBody CreateTransactionDTO transaction) {

		transactionService.transaction(transaction.getSorceAccountId(), transaction.getDestinationAccountId(),
				transaction.getAmount());

		return new ResponseEntity<MessageDTO>(new MessageDTO("Transaction created sucessfuly!"), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
		List<TransactionDTO> transactions = transactionService.getAllTransactions().stream()
				.map(transaction -> mapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);
	}

}
