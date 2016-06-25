package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Field for id.
	 */
	@Id
	private int idAccount;

	/**
	 * Field for balance.
	 */
	private double balance;

	/**
	 * Bi-directional many-to-one association to Client
	 */
	@ManyToOne
	@JoinColumn(name = "idClient")
	private Client client;

	/**
	 * Default CTOR for Account class.
	 */
	public Account() {
	}

	/**
	 * Gets the id.
	 * 
	 * @return
	 */
	public int getIdAccount() {
		return this.idAccount;
	}

	/**
	 * Sets the id.
	 * 
	 * @param idAccount
	 */
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	/**
	 * Gets the balance.
	 * 
	 * @return
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * Sets the balance.
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Gets the clients.
	 * 
	 * @return
	 */
	public Client getClient() {
		return this.client;
	}

	/**
	 * Sets the client.
	 * 
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [" + idAccount + ", " + balance + "]";
	}
}