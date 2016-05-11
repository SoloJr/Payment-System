package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idTransaction;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="idAccount")
	private Account account;

	//bi-directional many-to-one association to Bill
	@ManyToOne
	@JoinColumn(name="idBill")
	private Bill bill;

	public Transaction() {
	}

	public int getIdTransaction() {
		return this.idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}