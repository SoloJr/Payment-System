package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Account;
import model.Bill;
import model.BillFX;
import model.Client;
import model.Contract;
import model.Provider;
import model.ProviderFX;
import server.ClientCall;
import server.RequestResponse;
import server.RequestType;

public class MainController implements Initializable {

	@FXML
	private Label lblClientName;

	@FXML
	private Label lblClientBalance;

	@FXML
	private CheckBox cbViewAllBills;

	@FXML
	private CheckBox cbAutoPay;

	@FXML
	private TableView<BillFX> table;

	@FXML
	private TableView<ProviderFX> myProvidersTable;

	@FXML
	private TableView<ProviderFX> providersTable;

	@FXML
	private Button btnPayBills;

	@FXML
	private Button btnAddProvider;

	@FXML
	private Button btnDeleteProvider;

	@FXML
	private TableColumn<BillFX, Number> id;

	@FXML
	private TableColumn<BillFX, Number> ammount;

	@FXML
	private TableColumn<BillFX, String> details;

	@FXML
	private TableColumn<BillFX, String> issueDate;

	@FXML
	private TableColumn<BillFX, String> dueDate;

	@FXML
	private TableColumn<BillFX, String> payDate;

	@FXML
	private TableColumn<ProviderFX, String> nameMyProvider;

	@FXML
	private TableColumn<ProviderFX, String> ibanMyProvider;

	@FXML
	private TableColumn<ProviderFX, String> nameProvider;

	@FXML
	private TableColumn<ProviderFX, String> ibanProvider;

	private ObservableList<BillFX> data;

	private ObservableList<ProviderFX> myProviders;

	private ObservableList<ProviderFX> providers;

	private Client currentClient;

	private SimpleDateFormat sdf;

	public void setCurrentClient(Client currentClient) {
		this.lblClientName.setText(currentClient.getName() + " " + currentClient.getSurname());
		this.lblClientBalance.setText(Double.toString(currentClient.getAccounts().get(0).getBalance()));
		if (currentClient.getBills().isEmpty() == false) {
			for (Bill bill : currentClient.getBills()) {
				if (bill.getPayDate() == null) {
					data.add(new BillFX(bill.getIdBill(), bill.getAmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		}

		System.out.println(currentClient.getContracts().size());

		RequestResponse<List<Provider>> lookup = new RequestResponse<List<Provider>>(Main.host, Main.portNumber);
		lookup.request = RequestType.GET_PROVIDERS;
		ClientCall<List<Provider>> callable = new ClientCall<List<Provider>>(lookup);
		List<Provider> provs = null;
		Future<List<Provider>> future = Main.clientExecutor.submit(callable);
		try {
			provs = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		if (currentClient.getContracts().isEmpty() == false) {
			System.out.println(currentClient.getContracts().size());
			for (Contract contract : currentClient.getContracts()) {
				Provider prov = contract.getProvider();
				for (int i = 0; i < provs.size(); i++) {
					if (provs.get(i).getName().compareTo(prov.getName()) == 0) {
						provs.remove(i);
					}
				}
				System.out.println(prov.getName());
				myProviders
						.add(new ProviderFX(prov.getIdProvider(), prov.getIban(), prov.getName(), prov.getPassword()));
			}
			for (Provider prov : provs) {
				providers.add(new ProviderFX(prov.getIdProvider(), prov.getIban(), prov.getName(), prov.getPassword()));
			}
		}

		this.currentClient = currentClient;
		WatchForChanges watch = new WatchForChanges();
		watch.start();
	}

	public Client getCurrentClient() {
		return this.currentClient;
	}

	public void close() {
		Platform.exit();
	}

	public void onAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Payment Services");
		alert.setHeaderText("Developers");
		alert.setContentText("This application was developed by Florin Sia and Mircea Solovastru");
		alert.show();
	}

	public void checkBoxHandle() {
		if (cbViewAllBills.isSelected() == true) {
			System.out.println(this.currentClient.getBills().size());
			data.clear();
			for (Bill bill : currentClient.getBills()) {
				if (bill.getPayDate() != null) {
					data.add(new BillFX(bill.getIdBill(), bill.getAmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()),
							sdf.format(bill.getPayDate())));
				} else {
					data.add(new BillFX(bill.getIdBill(), bill.getAmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		} else {
			System.out.println(this.currentClient.getBills().size());
			data.clear();
			for (Bill bill : currentClient.getBills()) {
				if (bill.getPayDate() == null) {
					data.add(new BillFX(bill.getIdBill(), bill.getAmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		}
	}

	public void addProvider(ActionEvent event) {
		ProviderFX providerFX = (ProviderFX) providersTable.getSelectionModel().getSelectedItem();
		if (providerFX != null) {
			System.out.println(providerFX.getIban());
			Contract contract = new Contract();
			if (cbAutoPay.isSelected() == true) {
				contract.setAutoPay(true);
			} else {
				contract.setAutoPay(false);
			}
			contract.setClient(currentClient);
			contract.setDate(Calendar.getInstance().getTime());

			Provider provider = null;
			RequestResponse<List<Provider>> lookup = new RequestResponse<List<Provider>>(Main.host, Main.portNumber);
			lookup.request = RequestType.GET_PROVIDER_BY_NAME;
			lookup.parameters.add(providerFX.getName());
			ClientCall<List<Provider>> callable = new ClientCall<List<Provider>>(lookup);
			List<Provider> provs = null;
			try {
				Future<List<Provider>> future = Main.clientExecutor.submit(callable);
				provs = future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			provider = provs.get(0);
			contract.setProvider(provider);

			RequestResponse<Contract> lookup2 = new RequestResponse<Contract>(Main.host, Main.portNumber);
			lookup2.request = RequestType.ADD_CONTRACT;
			lookup2.parameters.add(contract);
			ClientCall<Contract> callable2 = new ClientCall<>(lookup2);
			Main.clientExecutor.submit(callable2);
			System.out.println("Contract: " + contract.getProvider().getName());
			for (int i = 0; i < providers.size(); i++) {
				System.out.println(" --" + providers.get(i).getName());
				if (providers.get(i).getName().compareTo(contract.getProvider().getName()) == 0) {
					providers.remove(i);
				}
			}

			myProviders.add(providerFX);
		} else {
			Main.createAlert(AlertType.ERROR, "Providers", "Choose a provider!");
		}
	}

	public void deleteProvider(ActionEvent event) {
		ProviderFX providerFX = (ProviderFX) myProvidersTable.getSelectionModel().getSelectedItem();
		if (providerFX != null) {
			Provider provider = null;
			RequestResponse<List<Provider>> lookup = new RequestResponse<List<Provider>>(Main.host, Main.portNumber);
			lookup.request = RequestType.GET_PROVIDER_BY_NAME;
			lookup.parameters.add(providerFX.getName());
			ClientCall<List<Provider>> callable = new ClientCall<List<Provider>>(lookup);
			List<Provider> provs = null;
			try {
				Future<List<Provider>> future = Main.clientExecutor.submit(callable);
				provs = future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			provider = provs.get(0);
			
			boolean hasPaidAllBills = true;
			
			for (Bill b : currentClient.getBills()) {
				if (b.getProvider().getName().compareTo(provider.getName()) == 0) {
					if (b.getPayDate() == null) {
						hasPaidAllBills = false;
						break;
					}
				}
			}
			
			if (hasPaidAllBills == true) {
				Contract contract = null;

				RequestResponse<List<Contract>> lookup3 = new RequestResponse<>(Main.host, Main.portNumber);
				lookup3.request = RequestType.GET_CONTRACT_BY_DETAILS;
				lookup3.parameters.add(currentClient);
				lookup3.parameters.add(provider);
				ClientCall<List<Contract>> callable3 = new ClientCall<>(lookup3);
				List<Contract> contracts = null;
				try {
					Future<List<Contract>> future = Main.clientExecutor.submit(callable3);
					contracts = future.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
				System.out.println(contracts.size());
				
				contract = contracts.get(0);

				RequestResponse<Contract> lookup2 = new RequestResponse<Contract>(Main.host, Main.portNumber);
				lookup2.request = RequestType.DELETE_CONTRACT;
				lookup2.parameters.add(contract.getIdContract());
				ClientCall<Contract> callable2 = new ClientCall<>(lookup2);
				Main.clientExecutor.submit(callable2);
				myProviders.remove(providerFX);
				providers.add(providerFX);
			} else {
				Main.createAlert(AlertType.ERROR, "Providers", "Pay your bills first!");
			}

		} else {
			Main.createAlert(AlertType.ERROR, "Providers", "Choose one provider!");
		}
	}

	public void payBills() {
		BillFX bill = (BillFX) table.getSelectionModel().getSelectedItem();
		System.out.println("payBills: " + this.currentClient.getBills().size());
		if (bill.getDueDate() != null) {
			int accountId = this.currentClient.getAccounts().get(0).getIdAccount();
			double newBalance = this.currentClient.getAccounts().get(0).getBalance() - bill.getAmmount();
			if (newBalance >= 0) {
				RequestResponse<Account> lookup = new RequestResponse<>(Main.host, Main.portNumber);
				lookup.request = RequestType.UPDATE_BALANCE;
				lookup.parameters.add(accountId);
				lookup.parameters.add(newBalance);
				ClientCall<Account> callable = new ClientCall<Account>(lookup);
				Main.clientExecutor.submit(callable);

				RequestResponse<Bill> lookup2 = new RequestResponse<Bill>(Main.host, Main.portNumber);
				lookup2.request = RequestType.PAY_BILL;
				lookup2.parameters.add(bill.getIdBill());
				ClientCall<Bill> callable2 = new ClientCall<Bill>(lookup2);
				Main.clientExecutor.submit(callable2);

				this.lblClientBalance.setText(Double.toString(newBalance));
				for (int i = 0; i < currentClient.getBills().size(); i++) {
					if (currentClient.getBills().get(i).getIdBill() == bill.getIdBill()) {
						currentClient.getBills().get(i).setPayDate(Calendar.getInstance().getTime());
					}
				}
				data.remove(bill);

			} else {
				Main.createAlert(AlertType.ERROR, "Bills", "No money to pay the selected bill!");
			}

		} else {
			Main.createAlert(AlertType.ERROR, "Bills", "Bill already paid!");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sdf = new SimpleDateFormat("dd.MM.yyyy");
		data = FXCollections.observableArrayList();
		myProviders = FXCollections.observableArrayList();
		providers = FXCollections.observableArrayList();
		table.setPlaceholder(new Label("No bill!"));
		table.setItems(data);
		myProvidersTable.setPlaceholder(new Label("No providers!"));
		myProvidersTable.setItems(myProviders);
		providersTable.setPlaceholder(new Label("No providers!"));
		providersTable.setItems(providers);
		id.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdBill()));
		ammount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmmount()));
		details.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetails()));
		issueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIssueDate().toString()));
		dueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDueDate().toString()));
		payDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPayDate().toString()));
		nameMyProvider.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName().toString()));
		nameProvider.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName().toString()));
		ibanMyProvider.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIban().toString()));
		ibanProvider.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIban().toString()));
	}

	private class WatchForChanges extends Thread {
		@SuppressWarnings("static-access")
		public void run() {
			try {
				while (true) {
					refreshTable();
					this.sleep(15000);
				}
			} catch (InterruptedException e) {
				System.out.println();
			}

		}
	}

	public void refreshTable() {
		RequestResponse<List<Bill>> lookup = new RequestResponse<List<Bill>>(Main.host, Main.portNumber);
		lookup.request = RequestType.GET_BILLS_BY_CLIENT;
		lookup.parameters.add(currentClient);
		ClientCall<List<Bill>> callable = new ClientCall<List<Bill>>(lookup);
		List<Bill> bills = null;
		try {
			Future<List<Bill>> future = Main.clientExecutor.submit(callable);
			bills = future.get();
		} catch (Exception e) {
			System.out.println();
		}
		currentClient.setBills(bills);
		checkBoxHandle();
	}
}