package dao;

import java.util.List;

import model.Client;
import model.Provider;

public class PayServDAOTest {
	public static void main(String[] args) {
		PayServDAO payServDAO = new PayServDAO();
		List<Client> allClients = payServDAO.getAllClients();
		List<Provider> allProviders = payServDAO.getProviders();
		
		for (Client c : allClients) {
			System.out.println(c.toString());
		}
		
		for (Provider a : allProviders) {
			System.out.println(a.toString());
		}
	}
}
