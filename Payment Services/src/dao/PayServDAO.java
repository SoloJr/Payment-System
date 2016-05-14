package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Client;
import model.Provider;

public class PayServDAO {
	
	public List<Client> getAllClients() {
		return this.getEntityManager().createQuery("select c from model.Client c", Client.class).getResultList();
	}
	
	public List<Provider> getProviders() {
		return this.getEntityManager().createQuery("select p from model.Provider p", Provider.class).getResultList();
	}
	
	public Client getClientByUsername(String username){
		List<Client> matchClients  = this.getEntityManager().createQuery(
				"SELECT c FROM model.Client c WHERE c.username = :custName")
				.setParameter("custName", username)
				.getResultList();
		 if(matchClients.size() == 1){
			return matchClients.get(0);
		}
		return null;
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
