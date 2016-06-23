package svrcon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Client;
import model.Provider;

public interface SvConnection extends Remote {

	public List<Client> getClients() throws RemoteException;
	public List<Client> searchClients(short criteria, String searchedWord) throws RemoteException;
	public Client getClientIfExist(String username, String password) throws RemoteException; 
	public Provider getProviderIfExist(String name, String password) throws RemoteException;
	public void addNewClient(Client newClient) throws RemoteException; 
	public List<Provider> getProviders() throws RemoteException; 
}
