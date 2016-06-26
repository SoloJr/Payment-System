package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.Main;
import consts.Constants;
import dao.PayServDAO;
import model.Bill;
import model.Client;
import model.Contract;
import model.Provider;

/**
 * Class used to send bills to the client/s
 * 
 * @author Florin Sia
 */
public class AutomaticInvoice {
	/**
	 * Field for the amount.
	 */
	private static double amount = 10;

	/**
	 * Field for the bill number.
	 */
	private static int billNumber = 600;

	/**
	 * Field for the port.
	 */
	private static int port = Constants.DEFAULT_PORT;

	/**
	 * Field for the host.
	 */
	private static String host = "localhost";

	/**
	 * Calendar used at bills creation.
	 */
	Calendar cal = Calendar.getInstance();

	/**
	 * DAO object.
	 */
	PayServDAO dao = null;

	/**
	 * The list of providers.
	 */
	List<Provider> providers = null;

	/**
	 * The list of clients.
	 */
	List<Client> clients = null;

	/**
	 * Field for the executor.
	 */
	ExecutorService pool = Executors.newFixedThreadPool(3);

	/**
	 * Field for the socket.
	 */
	public Socket socket = null;

	/**
	 * CTOR used to initialize the server and creates the objects for DAO and
	 * providers.
	 */
	public AutomaticInvoice() {
		initSvConnection();
		dao = new PayServDAO();
		providers = dao.getAllProviders();
	}

	/**
	 * Method to start a thread.
	 */
	public void startThread() {
		EmitInvoice em = new EmitInvoice();
		em.start();
	}

	/**
	 * Method to start a connection to the server
	 */
	public void initSvConnection() {
		try {
			socket = new Socket(host, port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method used to create a bill.
	 * 
	 * @param currentProvider
	 *            -> the provider that sends the bill.
	 * @param client
	 *            -> that will receive the bill
	 * @return the bill
	 */
	public static Bill createBill(Provider currentProvider, Client client) {
		Bill bill = new Bill();
		bill.setAmount(amount);
		Date issueDate = Calendar.getInstance().getTime();
		bill.setIssueDate(issueDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(issueDate);
		cal.add(Calendar.DATE, 10);
		bill.setDueDate(cal.getTime());
		bill.setProvider(currentProvider);
		bill.setDetails("Factura " + billNumber++ + " " + currentProvider.getName());
		bill.setClient(client);
		return bill;
	}

	/**
	 * Method used to get the clients of a provider.
	 * 
	 * @param provider
	 *            -> the specified provider
	 * @return a list of clients.
	 */
	public List<Client> getSubsribers(Provider provider) {
		List<Client> subscribers = new ArrayList<Client>();

		RequestResponse<List<Contract>> lookup = new RequestResponse<List<Contract>>(host, port);
		lookup.request = RequestType.GET_CONTRACT_BY_PROVIDER;
		lookup.parameters.add(provider);
		ClientCall<List<Contract>> callable = new ClientCall<List<Contract>>(lookup);
		List<Contract> contracts = null;
		Future<List<Contract>> future = pool.submit(callable);
		try {
			contracts = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		if (contracts == null) {
			return null;
		}
		for (Contract contract : contracts) {
			subscribers.add(contract.getClient());
		}
		return subscribers;
	}
	
	/**
	 * Gets the contract between a client and a provider
	 * @param provider
	 * @param client
	 * @return
	 */
	public static List<Contract> getContracts(Provider provider, Client client) { 
		RequestResponse<List<Contract>> lookup3 = new RequestResponse<>(host, port);
		lookup3.request = RequestType.GET_CONTRACT_BY_DETAILS;
		lookup3.parameters.add(client);
		lookup3.parameters.add(provider);
		ClientCall<List<Contract>> callable3 = new ClientCall<>(lookup3);
		List<Contract> contracts = null;
		try {
			Future<List<Contract>> future = Main.clientExecutor.submit(callable3);
			contracts = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return contracts;
	}

	/**
	 * Method used to send bills.
	 * 
	 * @param provider
	 *            -> he sends the bill
	 * @param subscribers
	 *            -> to the clients.
	 */
	public void sendBills(Provider provider, List<Client> subscribers) {
		for (Client client : subscribers) {
			Bill bill = createBill(provider, client);
			RequestResponse<Bill> lookup2 = new RequestResponse<Bill>(host, port);
			lookup2.request = RequestType.ADD_BILL;
			lookup2.parameters.add(bill);
			ClientCall<Bill> callable2 = new ClientCall<Bill>(lookup2);
			pool.submit(callable2);
		}
	}

	/**
	 * Nested class to send bills.
	 * 
	 * @author Florin Sia
	 */
	public class EmitInvoice extends Thread {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@SuppressWarnings("static-access")
		public void run() {
			try {
				while (true) {
					for (Provider provider : providers) {
						List<Client> subscr = getSubsribers(provider);
						if (subscr != null) {
							sendBills(provider, subscr);
							System.out.println("Invoice Emited, provider: " + provider.getName());
						}
					}
					this.sleep(15000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Launches the thread.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AutomaticInvoice sendInvoices = new AutomaticInvoice();
		sendInvoices.startThread();
	}

}
