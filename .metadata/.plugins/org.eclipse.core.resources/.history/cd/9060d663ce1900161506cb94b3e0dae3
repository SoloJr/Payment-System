package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Client;
import model.Provider;

public class PayServDAO {
	
	public List<Client> getAllClients() {
		return this.getEntityManager().createQuery("select c from model.Client c", Client.class).getResultList();
	}
	
	public List<Provider> getProviders() {
		return this.getEntityManager().createQuery("select p from model.Provider p", Provider.class).getResultList();
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
}
