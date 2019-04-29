package com.revolut.moneytransfer.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.exception.GeneralException;
import com.revolut.moneytransfer.exception.model.ExceptionResponse;
import com.revolut.moneytransfer.model.Account;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
	protected static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	private static Logger LOGGER = Logger.getLogger(AccountService.class);

	@GET
	@Path("/all")
	public Response getAccounts() {
		try {
			List<Account> accounts = factory.getAccountFactsImpl().getAccounts();
			return Response.status(Response.Status.OK).entity(accounts).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).entity(new ExceptionResponse(e.getMessage(), 404)).build();
		}
	}

	@GET
	@Path("/{accountNumber}")
	public Response getAccountByAccountNumber(@PathParam("accountNumber") Integer accountNumber) {
		try {
			Account account = factory.getAccountFactsImpl().getAccountByAccountNumber(accountNumber);
			return Response.status(Response.Status.OK).entity(account).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).entity(new ExceptionResponse(e.getMessage(), 404)).build();
		}
	}

	@GET
	@Path("/{accountNumber}/balance")
	public Response getAccountBalance(@PathParam("accountNumber") Integer accountNumber) {
		try {
			BigDecimal balance = factory.getAccountFactsImpl().getAccountBalance(accountNumber);
			return Response.status(Response.Status.OK).entity(balance).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).entity(new ExceptionResponse(e.getMessage(), 404)).build();
		}

	}

	@PUT
	@Path("/add")
	public Response createAccount(Account account) throws GeneralException {
		try {
			Integer accountNumber = factory.getAccountFactsImpl().createAccount(account);
			Account newAccount = factory.getAccountFactsImpl().getAccountByAccountNumber(accountNumber);
			return Response.status(Response.Status.OK).entity(newAccount).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ExceptionResponse(e.getMessage(), 500))
					.build();
		}
	}

	@DELETE
	@Path("/delete/{accountNumber}")
	public Response deleteAccount(@PathParam("accountNumber") Integer accountNumber) throws GeneralException {
		try {
			Boolean isAccountDeleted = factory.getAccountFactsImpl().deleteAccount(accountNumber);
			LOGGER.info("Account Deleted Successfully::" + isAccountDeleted);
			return Response.status(Response.Status.OK).entity("Account Deleted Successfully.").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ExceptionResponse(e.getMessage(), 500))
					.build();
		}
	}

}
