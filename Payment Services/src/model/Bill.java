package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
	
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	@Temporal(TemporalType.DATE)
	private Date dueDate;
	
	@Temporal(TemporalType.DATE)
	private Date payDate;

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="idClient")
	private Client client;

	//bi-directional many-to-one association to Provider
	@ManyToOne
	@JoinColumn(name="idProvider")
	private Provider provider;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="bill")
	private List<Transaction> transactions;

	public Bill() {
	}
	
	public Bill(double ammount, Date dueDate) {
		this.issueDate = new Date();
		this.ammount = ammount;
		this.dueDate = dueDate;
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

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
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