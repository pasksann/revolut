package com.revolut.sannino.service;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.revolut.sannino.dao.AccountList;
import com.revolut.sannino.exception.JsonError;
import com.revolut.sannino.exception.OperationException;
import com.revolut.sannino.model.Account;
import com.revolut.sannino.model.Operation;

public class OperationService {

	public static void MakeOperation(Operation operation) {
		
		switch (operation.operation) {
			case DEPOSIT  : DepositOperation(operation) ; break;
			case WITHDRAW : WithdrawOperation(operation); break;
			case TRANSFER : TransferOperation(operation); break;
			default       : throw new OperationException(new JsonError("Error", "Operation undefined"));
		}
		
	}
	
	private static void DepositOperation (Operation operation) {
		
		CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == operation.accountNumber)
							.findFirst();
		if (!match.isPresent()) 
			throw new OperationException(new JsonError("Error", "Account " + operation.accountNumber + " not found"));
		
		Account account = match.get();
		
		account.deposit(operation.amount, operation.currency);
		
	}
	
	private static void WithdrawOperation (Operation operation) {
		
		CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == operation.accountNumber)
							.findFirst();
		if (!match.isPresent()) 
			throw new OperationException(new JsonError("Error", "Account " + operation.accountNumber + " not found"));
		
		Account account = match.get();
		
		account.withdraw(operation.amount, operation.currency);
		
	}
	
	private static void TransferOperation (Operation operation) {

		CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == operation.accountNumber)
							.findFirst();
		if (!match.isPresent()) 
			throw new OperationException(new JsonError("Error", "Account " + operation.accountNumber + " not found"));
		
		Account from = match.get();
		
		match = accounts.stream()
				.filter(a -> a.getNumber() == operation.toAccountNumber)
				.findFirst();
		if (!match.isPresent()) 
		throw new OperationException(new JsonError("Error", "Account " + operation.toAccountNumber + " not found"));
		
		Account to = match.get();
		
		from.transferTo(operation.amount, to);
				
	}
}
