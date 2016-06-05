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
import model.Provider;

public class PayServDAO {

	public List<Client> getAllClients() {
		return this.getEntityManager().createQuery("select c from model.Client c", Client.class).getResultList();
	}

	public List<Provider> getAllProviders() {
		return this.getEntityManager().createQuery("select p from model.Provider p", Provider.class).getResultList();
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

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Payment Services");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	public void payBill(int billId) {
		EntityManager em = this.getEntityManager();
		Bill bill = em.find(Bill.class, billId);
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

	public void addBills(List<Bill> bills) {
		// for (Bill bill : bills) {
		// EntityManager em = this.getEntityManager();
		// em.getTransaction().begin();
		//
		// em.persist(bill);
		// em.getTransaction().commit();
		// }

	}

}
