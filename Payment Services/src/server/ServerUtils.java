package server;

import java.util.List;

import dao.PayServDAO;
import model.Client;

public class ServerUtils {
	private static PayServDAO payServDAO = new PayServDAO();

	public static List getValue(RequestResponse look) {
		List object = null;
		String username;
		String pass;
		switch (look.request) {
		case GET_CLIENTS:
			object = (List) payServDAO.getAllClients();
			break;
		case GET_PROVIDERS:
			object = (List) payServDAO.getAllProviders();
			break;
		case GET_CLIENT_BY_USERNAME:
			username = look.parameters.get(0).toString();
			object = (List) payServDAO.getClientByUsername(username);
			break;
		case GET_PROVIDER_BY_NAME:
			username = look.parameters.get(0).toString();
			object = (List) payServDAO.getProviderByName(username);
			break;
		case ADD_CLIENT:
			payServDAO.addClient((Client)look.parameters.get(0));
		}

		return object;
	}

}
