package com.revolut.sannino.model;

import com.revolut.sannino.service.CurrencyService;

public class PersonalAccount extends Account {

	public PersonalAccount(long number, Currency currency, Customer customer) {
		super(number, currency, customer);
	}
	
	public PersonalAccount(long number, Currency currency, double balance, Customer customer) {
		super(number, currency, balance, customer);
	}

	@Override
	public void deposit(double amount, Currency currency) {
		if (this.getCurrency() != currency ) amount = CurrencyService.CurrencyConverter(amount, currency, this.getCurrency());
		this.setBalance(this.getBalance() + amount);
		this.addTransaction("Added " + amount + " " + currency );
	}
	
	@Override
	public void deposit(double amount, Currency currency, Account from) {
		if (this.getCurrency() != currency ) amount = CurrencyService.CurrencyConverter(amount, currency, this.getCurrency());
		this.setBalance(this.getBalance() + amount);
		this.addTransaction("Added " + amount + " " + currency + " from Account " + from.getNumber() );
	}

	@Override
	public void withdraw(double amount, Currency currency) {
		if (this.getCurrency() != currency ) amount = CurrencyService.CurrencyConverter(amount, currency, this.getCurrency());
	    this.setBalance(this.getBalance() - amount);
		this.addTransaction("Withdrawed " + amount + " " + currency );
	}
	
	@Override
	public void transferTo(double amount, Account to) {
	    if (amount <= this.getBalance()) {
	    	if (this.getCurrency() != to.getCurrency() ) 
				amount = CurrencyService.CurrencyConverter(amount, this.getCurrency(), to.getCurrency());
	    	this.setBalance(this.getBalance() - amount);
	        to.deposit(amount,this.getCurrency(),this);
	        this.addTransaction("Tansfered: " + this.getCurrency() + " " + amount + " to Account " + to.getNumber() );
	        
	    } else if (amount > this.getBalance() ) {
	    	this.addTransaction("Failed Tansfered: " + this.getCurrency() + " " + amount + " to Account " + to.getNumber() );
	    }
	}

}
