package svrcon;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class Server {

	public Server() {
	}

	public static void main(String args[]) {

		try {
			SvConnectionUtils utils = new SvConnectionUtils();
			SvConnection svStub = (SvConnection) UnicastRemoteObject.exportObject(utils, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("PaymentSys", svStub);
			 
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
	 
	 public static class Watch extends Thread {
	        Calendar cal = Calendar.getInstance();
	        @SuppressWarnings("static-access")
			public void run() {
	            try {
	                while (true) {
	                   cal.add(Calendar.MONTH, 1);       
	                   System.out.println(cal.getTime());
	                    this.sleep(10000);
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	        }
	    }
}
