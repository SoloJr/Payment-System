package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import consts.Constants;
import model.Account;
import model.Bill;
import model.Client;
import model.Contract;
import model.Provider;

public class PayServDAO {

	public List<Client> getAllClients() {
		return this.getEntityManager().createQuery("select c from model.Client c", Client.class).getResultList();
	}

	public List<Provider> getAllProviders() {
		return this.getEntityManager().createQuery("select p from model.Provider p", Provider.class).getResultList();
	}

	public List<Bill> getAllBills() {
		return this.getEntityManager().createQuery("select b from model.Bill b", Bill.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public Client getClientByUsername(String username, String password) {
		String query = "SELECT c FROM model.Client c WHERE c.username = :custName AND c.password =:custPass";
		List<Client> matchClients = this.getEntityManager().createQuery(query).setParameter("custName", username)
				.setParameter("custPass", password).getResultList();
		if (matchClients.size() == 1) {
			System.out.println(matchClients.get(0).getAccounts().get(0).toString());
			return matchClients.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Contract getContractById(int contractId) {
		String query = "SELECT c FROM model.Contract WHERE c.idContract = :custId";
		List<Contract> matchContracts = this.getEntityManager().createQuery(query).setParameter("custId", contractId)
				.getResultList();
		if (matchContracts.size() == 1) {
			System.out.println(matchContracts.get(0).getClient().getName());
			return matchContracts.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Provider getProviderByName(String name, String password) {
		String query = "SELECT p FROM model.Provider p WHERE p.name = :custName AND p.password = :custPass";
		List<Provider> matchProviders = this.getEntityManager().createQuery(query).setParameter("custName", name)
				.setParameter("custPass", password).getResultList();
		if (matchProviders.size() == 1) {
			System.out.println(matchProviders.get(0).getName().toString());
			return matchProviders.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Client> getClientByUsername(String username) {
		String query = "SELECT c FROM model.Client c WHERE c.username = :custName";
		List<Client> matchClients = this.getEntityManager().createQuery(query).setParameter("custName", username)
				.getResultList();
		if (matchClients.size() == 1) {
			System.out.println(matchClients.get(0).getAccounts().get(0).toString());
			return matchClients;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Provider> getProviderByName(String name) {
		String query = "SELECT p FROM model.Provider p WHERE p.name = :custName";
		List<Provider> matchProviders = this.getEntityManager().createQuery(query).setParameter("custName", name)
				.getResultList();
		if (matchProviders.size() == 1) {
			System.out.println(matchProviders.get(0).getName().toString());
			return matchProviders;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Client> getClientsByCriteria(short criteria, String searchedWord) {
		String type = null;
		switch (criteria) {
		case Constants.USERNAME:
			type = "username";
			break;
		case Constants.MAIL:
			type = "email";
			break;
		case Constants.NAME:
			type = "name";
			break;
		case Constants.SURNAME:
			type = "surname";
			break;
		default:
			type = "username";
			break;
		}
		String query = "SELECT c FROM model.Client c WHERE c." + type + " = :param";
		List<Client> matchClients = this.getEntityManager().createQuery(query).setParameter("param", searchedWord)
				.getResultList();

		return matchClients;
	}

	public void addClient(Client client) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
	}

	public void addContract(Contract contract) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(contract);
		em.getTransaction().commit();
	}

	public void deleteContract(int contractId) {
		EntityManager em = this.getEntityManager();
		Contract contract = em.find(Contract.class, contractId);
		em.getTransaction().begin();
		em.remove(contract);
		em.getTransaction().commit();
	}

	public void addBill(Bill bill) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(bill);
		em.getTransaction().commit();
	}

	public void addBillToClient(Bill bill, int idClient) {
		EntityManager em = this.getEntityManager();
		Client client = em.find(Client.class, idClient);
		em.getTransaction().begin();
		client.addBill(bill);
		em.getTransaction().commit();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PaymentServices");
		EntityManager em = emf.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		return em;
	}

	public void payBill(int billId) {
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, billId);
		em.getTransaction().begin();
		bill.setPayDate(Calendar.getInstance().getTime());
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void payBillByDetails(String details) {
		String query = "SELECT b FROM model.Bill b WHERE b.details = :custDetail";
		List<Bill> bills = this.getEntityManager().createQuery(query).setParameter("custDetail", details)
				.getResultList();
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, bills.get(0).getIdBill());
		em.getTransaction().begin();
		bill.setPayDate(Calendar.getInstance().getTime());
		em.getTransaction().commit();
	}

	public void updateBalance(int accountId, double newBalance) {
		EntityManager em = this.getEntityManager();
		Account account = em.find(Account.class, accountId);
		em.getTransaction().begin();
		account.setBalance(newBalance);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Contract> getContractsByProvider(Provider provider) {
		String query = "SELECT k FROM model.Contract k WHERE k.provider = :paramProviderId";
		List<Contract> contracts = this.getEntityManager().createQuery(query).setParameter("paramProviderId", provider)
				.getResultList();
		return contracts;
	}

	@SuppressWarnings("unchecked")
	public List<Bill> getBillsByClient(Client client) {
		String query = "SELECT b FROM model.Bill b WHERE b.client = :paramClient";
		List<Bill> bills = this.getEntityManager().createQuery(query).setParameter("paramClient", client)
				.getResultList();
		return bills;
	}

	@SuppressWarnings("unchecked")
	public List<Contract> getContractByDetails(Client client, Provider provider) {
		String query = "SELECT c FROM model.Contract c WHERE c.client = :paramClient AND c.provider = :paramProvider";
		List<Contract> contracts = this.getEntityManager().createQuery(query).setParameter("paramClient", client)
				.setParameter("paramProvider", provider).getResultList();
		System.out.println("Marime:" + contracts.size());
		if (contracts.size() == 1) {
			System.out.println(contracts.get(0).getProvider().getName() + " " + contracts.get(0).getClient().getName());
			return contracts;
		}
		
		return null;
	}

	public void deleteBillById(int billId) {
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, billId);
		em.getTransaction().begin();
		em.remove(bill);
		em.getTransaction().commit();
	}
}