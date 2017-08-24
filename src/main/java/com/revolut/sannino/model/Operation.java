package com.revolut.sannino.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Operation {
	
	public enum operation { DEPOSIT, WITHDRAW, TRANSFER } ;
	
	@XmlElement
	public operation operation;
	
	public long accountNumber;
	@XmlElement
	public long toAccountNumber;
	@XmlElement
	public double amount;
	@XmlElement
	public Currency currency;

}
