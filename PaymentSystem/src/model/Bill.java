package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bill database table.
 * 
 */
@Entity
@NamedQuery(name="Bill.findAll", query="SELECT b FROM Bill b")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idBill;

	private double ammount;

	private String details;

	//bi-directional many-to-one association to Provider
	@ManyToOne
	@JoinColumn(name="idProvider")
	private Provider provider;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="idClient")
	private Client client;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="bill")
	private List<Transaction> transactions;

	public Bill() {
	}

	public int getIdBill() {
		return this.idBill;
	}

	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}

	public double getAmmount() {
		return this.ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setBill(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setBill(null);

		return transaction;
	}

}