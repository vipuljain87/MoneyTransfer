package com.revolut.moneytransfer.facts;

import java.util.List;

import com.revolut.moneytransfer.model.User;

public interface UserFacts {

	public List<User> getUsers();

	public User getUser(String userName);
}
