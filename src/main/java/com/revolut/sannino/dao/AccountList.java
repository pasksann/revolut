package com.revolut.sannino.dao;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.revolut.sannino.model.Account;
import com.revolut.sannino.model.Currency;
import com.revolut.sannino.model.Customer;
import com.revolut.sannino.model.PersonalAccount;

public class AccountList {
  private static final CopyOnWriteArrayList<Account> aList = new CopyOnWriteArrayList<>();
  private static final AtomicLong counter = new AtomicLong(1000);
  
  static {
    // Create list of customers
    aList.add( new PersonalAccount(counter.getAndIncrement(), Currency.GBP, 1000.0, 
        new Customer.CustomerBuilder().id()
        .firstName("George")
        .lastName("Washington")
        .email("gwash@example.com")
        .city("Mt Vernon")
        .state("VA")
        .birthday("1732-02-23")
        .build()
    ));

    aList.add( new PersonalAccount(counter.getAndIncrement(), Currency.GBP, 1000.0,
        new Customer.CustomerBuilder().id()
        .firstName("John")
        .lastName("Adams")
        .email("jadams@example.com")
        .city("Braintree")
        .state("MA")
        .birthday("1735-10-30")
        .build()
    ));

    aList.add( new PersonalAccount(counter.getAndIncrement(), Currency.GBP, 1000.0,
        new Customer.CustomerBuilder().id()
        .firstName("Thomas")
        .lastName("Jefferson")
        .email("tjeff@example.com")
        .city("CharlottesVille")
        .state("VA")
        .birthday("1743-04-13")
        .build()
    ));

    aList.add( new PersonalAccount(counter.getAndIncrement(), Currency.GBP, 1000.0,
        new Customer.CustomerBuilder().id()
        .firstName("James")
        .lastName("Madison")
        .email("jmad@example.com")
        .city("Orange")
        .state("VA")
        .birthday("1751-03-16")
        .build()
    ));

    aList.add( new PersonalAccount(counter.getAndIncrement(), Currency.GBP, 1000.0,
        new Customer.CustomerBuilder().id()
        .firstName("James")
        .lastName("Monroe")
        .email("jmo@example.com")
        .city("New York")
        .state("NY")
        .birthday("1758-04-28")
        .build()
    ));

  }
  
  private AccountList(){}
  
  public static CopyOnWriteArrayList<Account> getInstance(){
    return aList;
  }
  
}
