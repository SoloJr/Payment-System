package svrcon;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

import dao.PayServDAO;
import model.Bill;
import model.Provider;

public class SendInvoices extends Thread {
	Calendar cal = Calendar.getInstance();
	PayServDAO dao = new PayServDAO();
	List<Provider> providers = dao.getAllProviders();

	public void run() {
		try {
			while (true) {
				for (Provider prov : providers) {
					System.out.println(cal.getInstance().getTime());
					List<Bill> bills = prov.issueInvoices();
					SvConnection stub = (new EstablishConnectionSv()).getConnectionToSv();
					stub.sendBillToSystem(bills);
					this.sleep(3000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		SendInvoices svi = new SendInvoices();
		svi.start();
	}
}
