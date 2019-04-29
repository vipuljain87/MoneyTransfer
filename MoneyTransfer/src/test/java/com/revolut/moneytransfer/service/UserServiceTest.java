package com.revolut.moneytransfer.service;

import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revolut.moneytransfer.base.TestBase;
import com.revolut.moneytransfer.model.User;

@RunWith(JUnit4.class)
public class UserServiceTest extends TestBase {

	@InjectMocks
	UserService userService;

	@Mock
	User user;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllUsers() {
		Response res = userService.getUsers();
		List<User> users = (List<User>) res.getEntity();
		Assert.assertEquals(200, res.getStatus());
		Assert.assertEquals(3, users.size());
	}

	@Test
	public void testGetUserByUsername() {
		when(user.getUserName()).thenReturn("vipul");
		Response res = userService.getUser(user.getUserName());
		User user = (User) res.getEntity();
		Assert.assertEquals(1, user.getUserID().intValue());
	}

}
