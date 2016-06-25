package server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import consts.Constants;
import model.Client;

public class ClientTest {
	public static ExecutorService clientExecutor = Executors.newCachedThreadPool();

	@SuppressWarnings("unused")
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
		lookup.request = RequestType.GET_CLIENT_BY_USERNAME;
		lookup.parameters.add("fu");
		lookup.parameters.add("floasu");
		ClientCall<List<Client>> callable = new ClientCall<List<Client>>(lookup);
		Future<List<Client>> future = clientExecutor.submit(callable);
		
		try {
			List<Client> client = future.get();
			if(client != null)
			System.out.println(client.get(0).getName());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("jumped");
		
	}
	
}