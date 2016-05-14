package svrcon;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import dao.PayServDAO;
import model.Client;

public class SvConnectionUtils implements SvConnection {
	

	@Override
	public Client getClientIfExist(String username) throws RemoteException {
		PayServDAO payServDAO = new PayServDAO();
		Client client = payServDAO.getClientByUsername(username);
		
		return client;
	}
	
	
}
