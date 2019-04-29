package com.revolut.moneytransfer.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Transaction {

	private Integer transactionID;
	private Integer fromAccountNumber;
	private Integer toAccountNumber;
	private BigDecimal amount;
	private TransactionStatus status;
	private Timestamp created;
	private Timestamp lastUpdated;

	public Transaction() {
	}

	public Transaction(Integer transactionID,Integer fromAccountNumber, Integer toAccountNumber, BigDecimal amount, TransactionStatus status,
			Timestamp created, Timestamp lastUpdated) {
		super();
		this.transactionID=transactionID;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
		this.status = status;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public Integer getFromAccountNumber() {
		return fromAccountNumber;
	}

	public Integer getToAccountNumber() {
		return toAccountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((fromAccountNumber == null) ? 0 : fromAccountNumber.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((toAccountNumber == null) ? 0 : toAccountNumber.hashCode());
		result = prime * result + ((transactionID == null) ? 0 : transactionID.hashCode());
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
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (fromAccountNumber == null) {
			if (other.fromAccountNumber != null)
				return false;
		} else if (!fromAccountNumber.equals(other.fromAccountNumber))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (status != other.status)
			return false;
		if (toAccountNumber == null) {
			if (other.toAccountNumber != null)
				return false;
		} else if (!toAccountNumber.equals(other.toAccountNumber))
			return false;
		if (transactionID == null) {
			if (other.transactionID != null)
				return false;
		} else if (!transactionID.equals(other.transactionID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", fromAccountNumber=" + fromAccountNumber
				+ ", toAccountNumber=" + toAccountNumber + ", amount=" + amount + ", status=" + status + ", created="
				+ created + ", lastUpdated=" + lastUpdated + "]";
	}

}
