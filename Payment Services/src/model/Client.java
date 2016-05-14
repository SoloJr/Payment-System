package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idClient;

	private String email;

	private String name;

	private String password;

	private String surname;

	private String username;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="client")
	private List<Account> accounts;

	//bi-directional many-to-one association to Bill
	@OneToMany(mappedBy="client")
	private List<Bill> bills;

	//bi-directional many-to-many association to Provider
	@ManyToMany(mappedBy="clients")
	private List<Provider> providers;

	public Client() {
	}

	public int getIdClient() {
		return this.idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setClient(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setClient(null);

		return account;
	}

	public List<Bill> getBills() {
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public Bill addBill(Bill bill) {
		getBills().add(bill);
		bill.setClient(this);

		return bill;
	}

	public Bill removeBill(Bill bill) {
		getBills().remove(bill);
		bill.setClient(null);

		return bill;
	}

	public List<Provider> getProviders() {
		return this.providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public boolean isMatchingUser(String username, String password) {
		if (this.username.compareTo(username) != 0) {
			return false;
		}
		
		if (this.password.compareTo(password) != 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean isMatchingUsername(String username) {
		if (this.username.compareTo(username) != 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean isMathingEmail(String email) {
		if (this.email.compareTo(email) != 0) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "Client [name=" + name + ", surname=" + surname + ", username=" + username + "]";
	}

}