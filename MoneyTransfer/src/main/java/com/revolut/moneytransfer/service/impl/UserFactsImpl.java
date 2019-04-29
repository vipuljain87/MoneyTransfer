package com.revolut.moneytransfer.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.db.H2Factory;
import com.revolut.moneytransfer.exception.GeneralException;
import com.revolut.moneytransfer.facts.UserFacts;
import com.revolut.moneytransfer.model.User;

public class UserFactsImpl implements UserFacts {

	private static Logger LOGGER = Logger.getLogger(UserFactsImpl.class);
	private static final String GET_ALL_USERS = "SELECT * FROM User";
	private static final String GET_USER_BY_NAME = "SELECT * FROM User WHERE UserName = ? ";
	private static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	public List<User> getUsers() {
		Optional<Connection> conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			conn = ((H2Factory) factory).getConnection();
			stmt = conn.get().prepareStatement(GET_ALL_USERS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("userID"), rs.getString("UserName"), rs.getTimestamp("Created"),
						rs.getTimestamp("LastUpdated"));
				users.add(user);
				LOGGER.info("Fetched User: " + user);
			}
			return users;
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading user data", e);
		}
	}

	public User getUser(String userName) {

		Optional<Connection> conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection();
			stmt = conn.get().prepareStatement(GET_USER_BY_NAME);
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getInt("userID"), rs.getString("UserName"), rs.getTimestamp("Created"),
						rs.getTimestamp("LastUpdated"));
				LOGGER.info("Fetched User: " + user);
				return user;
			} else {
				throw new GeneralException("No account for the user::" + userName);
			}
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading user data", e);
		}
	}

}
