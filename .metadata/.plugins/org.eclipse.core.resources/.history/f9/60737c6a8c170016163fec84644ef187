package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the provider database table.
 * 
 */
@Entity
@NamedQuery(name="Provider.findAll", query="SELECT p FROM Provider p")
public class Provider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idProvider;

	private String iban;

	private String name;

	//bi-directional many-to-one association to Bill
	@OneToMany(mappedBy="provider")
	private List<Bill> bills;

	//bi-directional many-to-many association to Client
	@ManyToMany
	@JoinTable(
		name="clientprovider"
		, joinColumns={
			@JoinColumn(name="idProvider")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idClient")
			}
		)
	private List<Client> clients;

	public Provider() {
	}

	public int getIdProvider() {
		return this.idProvider;
	}

	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bill> getBills() {
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public Bill addBill(Bill bill) {
		getBills().add(bill);
		bill.setProvider(this);

		return bill;
	}

	public Bill removeBill(Bill bill) {
		getBills().remove(bill);
		bill.setProvider(null);

		return bill;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}