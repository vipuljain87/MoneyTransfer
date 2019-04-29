package com.revolut.moneytransfer.service;

import java.math.BigDecimal;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.exception.model.ExceptionResponse;
import com.revolut.moneytransfer.model.Transaction;
import com.revolut.moneytransfer.model.TransactionStatus;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {

	private static Logger LOGGER = Logger.getLogger(UserService.class);
	protected static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	@POST
	@Path("{fromAccount}/{toAccount}/{amount}")
	public Response transfer(@PathParam("fromAccount") Integer fromAccount, @PathParam("toAccount") Integer toAccount,
			@PathParam("amount") BigDecimal amount) {
		Transaction transaction = null;
		try {
			transaction = factory.getTransactionFactsImpl().transfer(fromAccount, toAccount, amount);
			LOGGER.info("Transaction ID::" + transaction.getTransactionID());
			if (TransactionStatus.FAILED.equals(transaction.getStatus()))
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(transaction).build();
		} catch (MoneyTransferException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ExceptionResponse(e.getMessage(), 500))
					.build();
		}

		return Response.status(Response.Status.OK).entity(transaction).build();
	}
}
