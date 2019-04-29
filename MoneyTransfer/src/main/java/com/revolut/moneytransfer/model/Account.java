package com.revolut.moneytransfer.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {

	private String accountName;
	private Integer accountNumber;
	private String userName;
	private BigDecimal accountBalance;
	private Boolean isSoftDeleted;
	private Timestamp created;
	private Timestamp lastUpdated;

	public Account() {
	}

	public Account(String accountName, Integer accountNumber, String userName, BigDecimal accountBalance,
			Boolean isSoftDeleted, Timestamp created, Timestamp lastUpdated) {
		super();
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.userName = userName;
		this.accountBalance = accountBalance;
		this.isSoftDeleted = isSoftDeleted;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}

	public String getAccountName() {
		return accountName;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public String getUserName() {
		return userName;
	}

	public Boolean getIsSoftDeleted() {
		return isSoftDeleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountBalance == null) ? 0 : accountBalance.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((isSoftDeleted == null) ? 0 : isSoftDeleted.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
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
		Account other = (Account) obj;
		if (accountBalance == null) {
			if (other.accountBalance != null)
				return false;
		} else if (!accountBalance.equals(other.accountBalance))
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (isSoftDeleted == null) {
			if (other.isSoftDeleted != null)
				return false;
		} else if (!isSoftDeleted.equals(other.isSoftDeleted))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
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
		return "Account [accountName=" + accountName + ", accountNumber=" + accountNumber + ", userName=" + userName
				+ ", accountBalance=" + accountBalance + ", isSoftDeleted=" + isSoftDeleted + ", created=" + created
				+ ", lastUpdated=" + lastUpdated + "]";
	}

}
