package svrcon;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EstablishConnectionSv {
	Registry registry;
	SvConnection stub;

	public EstablishConnectionSv(){
		setConnectionToSv();
	}
	
	public void setConnectionToSv() {
		try {
			registry = LocateRegistry.getRegistry();
			stub = (SvConnection) registry.lookup("PaymentSys");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
	}
	
	public SvConnection getConnectionToSv(){
		return stub;
	}
}
