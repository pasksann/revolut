package com.revolut.sannino.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Account {
	
	@XmlElement	
	private final Customer customer;
	
	@XmlElement	
	private final long number;
	@XmlElement	
	private double balance;
	@XmlElement	
	private final Currency currency;
	private List<String> transactions;
	
//	public Account() {
//		this.number = -1;
//		this.currency = null;
//		this.customer = null;
//		this.balance = 0.0;
//		this.transactions = new ArrayList<String>();
//	}
	
	public Account(long number, Currency currency, Customer customer) {
		this.number = number;
		this.currency = currency;
		this.customer = customer;
		this.balance = 0.0;
		this.transactions = new ArrayList<String>();
	}
	
	public Account(long number, Currency currency, double balance, Customer customer) {
		this.number = number;
		this.currency = currency;
		this.customer = customer;
		this.balance = balance;
		this.transactions = new ArrayList<String>();
	}
	
	public long getNumber(){
		return this.number;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	public void setBalance(double balance){
		this.balance = balance;
	}
	
	public Currency getCurrency(){
		return this.currency;
	}
	
	public Customer getCustomer(){
		return this.customer;
	}
	
	public List<String> getTransactions() {
		return this.transactions;
	}
	
	public void addTransaction(String transaction){
		this.transactions.add(transaction);
	}
	
	public abstract void deposit(double amount, Currency currency);
	public abstract void deposit(double amount, Currency currency, Account from); 
	public abstract void withdraw(double amount, Currency currency);
	public abstract void transferTo(double amount, Account to);
	
	}
