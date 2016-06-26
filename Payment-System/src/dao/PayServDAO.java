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

/**
 * Class used for handling database access objects.
 * 
 * @author Mircea Solovastru and Florin Sia
 * @since 2016-06-01
 * @version 1.0
 */
public class PayServDAO {

	/**
	 * @return All the clients from the database.
	 */
	public List<Client> getAllClients() {
		return this.getEntityManager().createQuery("select c from model.Client c", Client.class).getResultList();
	}

	/**
	 * @return All the providers from the database.
	 */
	public List<Provider> getAllProviders() {
		return this.getEntityManager().createQuery("select p from model.Provider p", Provider.class).getResultList();
	}

	/**
	 * @return All the bills from the database.
	 */
	public List<Bill> getAllBills() {
		return this.getEntityManager().createQuery("select b from model.Bill b", Bill.class).getResultList();
	}
	
	/**
	 * @param contractId
	 *            for returning the contract.
	 * @return Contract.
	 */
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

	/**
	 * @param name
	 *            for login
	 * @param password
	 *            login
	 * @return provider for login
	 */
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

	/**
	 * @param username
	 *            for login.
	 * @return username.
	 */
	@SuppressWarnings("unchecked")
	public List<Client> getClientByUsername(String username) {
		String query = "SELECT c FROM model.Client c WHERE c.username = :custName";
		List<Client> matchClients = this.getEntityManager().createQuery(query).setParameter("custName", username)
				.getResultList();
		if (matchClients.size() == 1) {
//			System.out.println(matchClients.get(0).getAccounts().get(0).toString());
			return matchClients;
		}

		return null;
	}

	/**
	 * @param name
	 *            used for getting the object.
	 * @return provider
	 */
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

	/**
	 * @param criteria
	 * @param searchedWord
	 * @return a client having a criteria.
	 */
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

	/**
	 * Add a client to the database.
	 * 
	 * @param client
	 *            to be added.
	 */
	public void addClient(Client client) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		createAccount(client);
	}

	public void createAccount(Client client){
		Client cl = getClientByUsername(client.getName()).get(0);
		Account clAccount = new Account();
		clAccount.setClient(cl);
		addAccount(clAccount);
	}
	
	/**
	 * Adds an account to the database
	 * 
	 * @param account
	 *            to be added.
	 */
	public void addAccount(Account account) {
		EntityManager em = this.getEntityManager();
		System.out.println(account.toString());
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
	}

	/**
	 * Add a contract to the database.
	 * 
	 * @param contract
	 *            to be added.
	 */
	public void addContract(Contract contract) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(contract);
		em.getTransaction().commit();
	}

	/**
	 * Deletes a contract from the database.
	 * 
	 * @param contractId
	 *            used to find the object in order to be deleted.
	 */
	public void deleteContract(int contractId) {
		EntityManager em = this.getEntityManager();
		Contract contract = em.find(Contract.class, contractId);
		em.getTransaction().begin();
		em.remove(contract);
		em.getTransaction().commit();
	}

	/**
	 * Adds a bill to the database.
	 * 
	 * @param bill
	 *            to be added.
	 */
	public void addBill(Bill bill) {
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		em.persist(bill);
		em.getTransaction().commit();
	}

	/**
	 * Adds a bill to client.
	 * 
	 * @param bill
	 *            to be added.
	 * @param idClient
	 *            the referenced client.
	 */
	public void addBillToClient(Bill bill, int idClient) {
		EntityManager em = this.getEntityManager();
		Client client = em.find(Client.class, idClient);
		em.getTransaction().begin();
		client.addBill(bill);
		em.getTransaction().commit();
	}

	/**
	 * Method used to get the entity manager.
	 * 
	 * @return the entity manager.
	 */
	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PaymentServices");
		EntityManager em = emf.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		return em;
	}

	/**
	 * Method to pay the bill.
	 * 
	 * @param billId
	 *            used to find the bill.
	 */
	public void payBill(int billId) {
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, billId);
		em.getTransaction().begin();
		bill.setPayDate(Calendar.getInstance().getTime());
		em.getTransaction().commit();
	}

	/**
	 * Method used to pay the bill.
	 * 
	 * @param details
	 *            is the parameter to find the bill.
	 */
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

	/**
	 * Updates the balance of the account.
	 * 
	 * @param accountId
	 *            used to find the account
	 * @param newBalance
	 *            balance to be updated.
	 */
	public void updateBalance(int accountId, double newBalance) {
		EntityManager em = this.getEntityManager();
		Account account = em.find(Account.class, accountId);
		em.getTransaction().begin();
		account.setBalance(newBalance);
		em.getTransaction().commit();
	}

	/**
	 * Method used to get the contracts for the provider.
	 * 
	 * @param provider
	 * @return the contracts
	 */
	@SuppressWarnings("unchecked")
	public List<Contract> getContractsByProvider(Provider provider) {
		String query = "SELECT k FROM model.Contract k WHERE k.provider = :paramProviderId";
		List<Contract> contracts = this.getEntityManager().createQuery(query).setParameter("paramProviderId", provider)
				.getResultList();
		return contracts;
	}

	/**
	 * Method to get all the bills for a client.
	 * 
	 * @param client
	 * @return all the bills for a client
	 */
	@SuppressWarnings("unchecked")
	public List<Bill> getBillsByClient(Client client) {
		String query = "SELECT b FROM model.Bill b WHERE b.client = :paramClient";
		List<Bill> bills = this.getEntityManager().createQuery(query).setParameter("paramClient", client)
				.getResultList();
		return bills;
	}

	/**
	 * Method used to find a contract in order to delete it.
	 * 
	 * @param client
	 *            that has the contract
	 * @param provider
	 *            that has the contract
	 * @return a contract.
	 */
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

	/**
	 * Method to delete the bill from the database
	 * 
	 * @param billId
	 *            used to find in order to delete.
	 */
	public void deleteBillById(int billId) {
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, billId);
		em.getTransaction().begin();
		em.remove(bill);
		em.getTransaction().commit();
	}
}
