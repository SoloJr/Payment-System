package server;

import java.util.ArrayList;
import java.util.List;

import dao.PayServDAO;

public class ServerUtils {
	private static PayServDAO payServDAO = new PayServDAO();
	
		public static List getValue(RequestResponse look){
			List object = null;
		switch(look.request){
		case GET_CLIENTS:
			object = (List) payServDAO.getAllClients();
			break;	
		case GET_PROVIDERS:
			object = (ArrayList) payServDAO.getAllProviders();
			break;	
		case GET_CLIENT_BY_USERNAME:
			object = (ArrayList) payServDAO.getAllClients();
			
		}
			
		
		
		return object;
	}

}