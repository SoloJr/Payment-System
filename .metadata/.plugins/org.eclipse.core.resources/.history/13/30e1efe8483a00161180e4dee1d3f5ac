package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import consts.Constants;

public class Server {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		int portNumber;
		ServerSocket serverSocket;

		if (args.length != 1) {
			System.out.println("Using default port");
			portNumber = Constants.DEFAULT_PORT;
		} else {
			portNumber = Integer.parseInt(args[0]);
		}
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server Ready");
			while (true) {
				executor.execute(new ServerThread(serverSocket.accept()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
