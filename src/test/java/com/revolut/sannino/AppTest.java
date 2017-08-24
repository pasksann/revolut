package com.revolut.sannino;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revolut.sannino.dao.AccountList;
import com.revolut.sannino.model.Account;
import com.revolut.sannino.model.Currency;
import com.revolut.sannino.model.Operation;
import com.revolut.sannino.model.Operation.operation;

import static org.junit.Assert.*;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class AppTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = App.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(App.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testServicesUp() {
        String responseMsg = target.path("TestApi").request().get(String.class);
        assertEquals("Running!", responseMsg);
    }    
    
    @Test
    public void testAccountList() {    	
        Response response = target
                .path("account/all")
                .request().accept(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());

        assertNotNull(response.getEntity());

    }
    
    @Test
    public void testOperationDeposit() {    	
    	
    	Operation op = new Operation();
    	op.accountNumber = 1001;
    	op.currency = Currency.GBP;
    	op.operation = operation.DEPOSIT;
    	op.amount = 1234.0;
    	
    	//Find account
    	CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == op.accountNumber)
							.findFirst();
		
		Account account = match.get();
		
		double initialBalance = account.getBalance();
		
        Response response = target
                .path("account/operation")
                .request().accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(op, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        assertEquals(initialBalance+op.amount, account.getBalance(),0.001);
    }
    
    
    @Test
    public void testOperationWithdraw() {    	
    	
    	Operation op = new Operation();
    	op.accountNumber = 1001;
    	op.currency = Currency.GBP;
    	op.operation = operation.WITHDRAW;
    	op.amount = 1234.0;
    	
    	//Find account
    	CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == op.accountNumber)
							.findFirst();
		
		Account account = match.get();
		double initialBalance = account.getBalance();
		
        Response response = target
                .path("account/operation")
                .request().accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(op, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        assertEquals(initialBalance-op.amount, account.getBalance(),0.001);

    }
    
    
    @Test
    public void testOperationTransfer() {    	
    	
    	Operation op = new Operation();
    	op.accountNumber = 1001;
    	op.toAccountNumber = 1002;
    	op.currency = Currency.GBP;
    	op.operation = operation.TRANSFER;
    	op.amount = 1234.0;
    	
    	//Find account FROM
    	CopyOnWriteArrayList<Account> accounts = AccountList.getInstance();
		Optional<Account> match = accounts.stream()
							.filter(a -> a.getNumber() == op.accountNumber)
							.findFirst();
		
		Account accountFrom = match.get();
		double initialBalanceFrom = accountFrom.getBalance();
		
		//Find account TO
		match = accounts.stream()
				.filter(a -> a.getNumber() == op.toAccountNumber)
				.findFirst();
		
		Account accountTo = match.get();
		double initialBalanceTo = accountTo.getBalance();
		
        Response response = target
                .path("account/operation")
                .request().accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(op, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        
        assertEquals(initialBalanceFrom-op.amount, accountFrom.getBalance(),0.001);
        assertEquals(initialBalanceTo+op.amount, accountTo.getBalance(),0.001);
    }
}
