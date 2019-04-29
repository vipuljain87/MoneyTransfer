package com.revolut.moneytransfer.db.model;

import java.sql.Connection;
import java.util.Optional;

public interface Database {

	Optional<String> getDriver();

	Optional<String> getConnectionURL();

	Optional<String> getUsername();

	Optional<String> getPassword();

	Optional<Connection> getConnection();

}
