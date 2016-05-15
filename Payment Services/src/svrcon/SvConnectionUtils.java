package svrcon;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import dao.PayServDAO;
import model.Client;
import model.Provider;
import consts.Constants;

public class SvConnectionUtils implements SvConnection {
	private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
	private static ExecutorService executor = Executors.newCachedThreadPool();
	PayServDAO payServDAO = new PayServDAO();

	@Override
	public Client getClientIfExist(String username) throws RemoteException {
		Callable<Client> task = () -> {
			return payServDAO.getClientByUsername(username);
		};
		Future<Client> future = executor.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void addNewClient(Client newClient) throws RemoteException {
		executor.execute(() -> {
			payServDAO.addClient(newClient);
		});
	}

	@Override
	public List<Provider> getProviders() throws RemoteException {
		Callable<List<Provider>> task = () -> {
			return payServDAO.getAllProviders();
		};
		Future<List<Provider>> future = executor.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Client> getClients() throws RemoteException {
		Callable<List<Client>> task = () -> {
			return payServDAO.getAllClients();
		};
		Future<List<Client>> future = executor.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Client> searchClients(short criteria, String searchedWord) throws RemoteException {
		Callable<List<Client>> task = () -> {
			return payServDAO.getClientsByCriteria(criteria, searchedWord);
		};
		Future<List<Client>> future = executor.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

}