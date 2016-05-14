package svrcon;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Client;

public interface SvConnection extends Remote {

	public Client getClientIfExist(String Username) throws RemoteException; 
	
}
