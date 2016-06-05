package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import consts.Constants;
import dao.PayServDAO;
import model.Client;

public class ClientTest {
	public static ExecutorService ex = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		int portNumber;
		String host = "localhost";

		if (args.length != 1) {
			System.out.println("Using default port");
			portNumber = Constants.DEFAULT_PORT;
		} else {
			portNumber = Integer.parseInt(args[0]);
		}
		Socket sock = null;
		try {
			sock = new Socket(host, portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		RequestResponse<List<Client>> lookup = new RequestResponse<List<Client>>(host, portNumber);
		lookup.request = RequestType.GET_CLIENTS;
		ClientCall<List<Client>> callable = new ClientCall<List<Client>>(lookup, sock);
		Future<List<Client>> future = ex.submit(callable);
		try {
			List<Client> clients = future.get();
			for(int i=0; i< clients.size(); i++){
				System.out.println((clients.get(i)).getName() );
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}