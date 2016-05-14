package svrcon;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
}

