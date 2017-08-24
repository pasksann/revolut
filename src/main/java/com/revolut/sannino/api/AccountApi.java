package com.revolut.sannino.api;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.sannino.dao.AccountList;
import com.revolut.sannino.exception.JsonError;
import com.revolut.sannino.exception.NotFoundException;
import com.revolut.sannino.model.Account;
import com.revolut.sannino.model.Currency;
import com.revolut.sannino.model.Customer;
import com.revolut.sannino.model.Operation;
import com.revolut.sannino.service.OperationService;

@Path("/account")
public class AccountApi {

  private final CopyOnWriteArrayList<Account> cList = AccountList.getInstance();

  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public Account[] getAllCustomers() {
	  
	 return cList.toArray(new Account[0]);
  }
  
  @GET
  @Path("{number}")
  @Produces(MediaType.APPLICATION_JSON)
  public Account getCustomer(@PathParam("number") long number){
    Optional<Account> match
        = cList.stream()
        .filter(c -> c.getNumber() == number)
        .findFirst();
    if (match.isPresent()) {
      return match.get();
    } else {
      throw new NotFoundException(new JsonError("Error", "Account " + number + " not found"));
    }
  }
  
  @POST
  @Path("/add")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addCustomer(Account account){
    cList.add(account);
    return Response.status(201).build();
  }
  
  @PUT
  @Path("{number}/update")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateCustomer(Account account){
    int matchIdx = 0;
    Optional<Account> match = cList.stream()
        .filter(c -> c.getNumber() == account.getNumber())
        .findFirst();
    if (match.isPresent()) {
      matchIdx = cList.indexOf(match.get());
      cList.set(matchIdx, account);
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();      
    }
  }
  
  @DELETE
  @Path("/remove/{number}")
  public Response deleteCustomer(@PathParam("number") long number){
    Predicate <Account> account = a -> a.getNumber() == number;
    if (cList.removeIf(account)) {
        return Response.status(Response.Status.OK).build();     
    }else {
      return Response.status(Response.Status.NOT_FOUND).build();      
    }
  }

  
  @POST
  @Path("/operation")
  @Produces(MediaType.APPLICATION_JSON)
  public Response operation(Operation operation){

	OperationService.MakeOperation(operation);    
    return Response.status(Response.Status.OK).build();
  }
  
}
