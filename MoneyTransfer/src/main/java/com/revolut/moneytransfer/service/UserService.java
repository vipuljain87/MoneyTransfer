package com.revolut.moneytransfer.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.exception.model.ExceptionResponse;
import com.revolut.moneytransfer.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	private static Logger LOGGER = Logger.getLogger(UserService.class);
	protected static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	@GET
	@Path("/all")
	public Response getUsers() {
		try {
			List<User> users = factory.getUserFactsImpl().getUsers();
			return Response.status(Response.Status.OK).entity(users).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).entity(new ExceptionResponse(e.getMessage(), 404)).build();
		}
	}

	@GET
	@Path("/{userName}")
	public Response getUser(@PathParam("userName") String userName) {
		try {
			User user = factory.getUserFactsImpl().getUser(userName);
			return Response.status(Response.Status.OK).entity(user).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).entity(new ExceptionResponse(e.getMessage(), 404)).build();
		}

	}

}
