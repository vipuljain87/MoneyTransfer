package com.revolut.moneytransfer.model;

import java.sql.Timestamp;

public class User {

	private Integer userID;

	private String userName;

	private Timestamp created;

	private Timestamp lastUpdated;

	public User() {
	}

	public User(Integer userID, String userName, Timestamp created, Timestamp lastUpdated) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}

	public Integer getUserID() {
		return userID;
	}

	public String getUserName() {
		return userName;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", created=" + created + ", lastUpdated="
				+ lastUpdated + "]";
	}

}
