package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import dao.PayServDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Bill;
import model.BillFX;
import model.Client;
import server.ClientCall;
import server.RequestResponse;
import server.RequestType;

public class MainController implements Initializable {

	private PayServDAO payServDAO;

	@FXML
	private Label lblClientName;

	@FXML
	private Label lblClientBalance;

	@FXML
	private CheckBox cbViewAllBills;

	@FXML
	private TableView<BillFX> table;

	@FXML
	private Button btnPayBills;

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

	private ObservableList<BillFX> data;

	private Client currentClient;

	private SimpleDateFormat sdf;

	public void setCurrentClient(Client currentClient) {
		this.lblClientName.setText(currentClient.getName() + " " + currentClient.getSurname());
		this.lblClientBalance.setText(Double.toString(currentClient.getAccounts().get(0).getBalance()));
		if (currentClient.getBills().isEmpty() == false) {
			for (Bill bill : currentClient.getBills()) {
				if (bill.getPayDate() == null) {
					data.add(new BillFX(bill.getIdBill(), bill.getAmmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		}

		this.currentClient = currentClient;
	}

	public Client getCurrentClient() {
		return this.currentClient;
	}

	public void Close() {
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
					data.add(new BillFX(bill.getIdBill(), bill.getAmmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()),
							sdf.format(bill.getPayDate())));
				} else {
					data.add(new BillFX(bill.getIdBill(), bill.getAmmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		} else {
			System.out.println(this.currentClient.getBills().size());
			data.clear();
			for (Bill bill : currentClient.getBills()) {
				if (bill.getPayDate() == null) {
					data.add(new BillFX(bill.getIdBill(), bill.getAmmount(), bill.getDetails(),
							sdf.format(bill.getIssueDate()), sdf.format(bill.getDueDate()), "-"));
				}
			}
		}
	}

	public void payBills() {
		BillFX bill = (BillFX) table.getSelectionModel().getSelectedItem();
		System.out.println("payBills: " + this.currentClient.getBills().size());
		if (bill.getDueDate() != null) {
			int accountId = this.currentClient.getAccounts().get(0).getIdAccount();
			double newBalance = this.currentClient.getAccounts().get(0).getBalance() - bill.getAmmount();
			if (newBalance >= 0) {
				
				RequestResponse look = new RequestResponse(Main.host, Main.portNumber);
				look.request = RequestType.UPDATE_BALANCE;
				look.parameters.add(accountId);
				look.parameters.add(newBalance);
				ClientCall callable = new ClientCall(look);
				Main.clientExecutor.submit(callable);
				
				
				RequestResponse<Bill> lookup = new RequestResponse<Bill>(Main.host, Main.portNumber);
				lookup.request = RequestType.PAY_BILL;
				lookup.parameters.add(bill.getIdBill());
				ClientCall<Bill> callable2 = new ClientCall<Bill>(lookup);
				Main.clientExecutor.submit(callable2);
				
				table.getColumns().get(0).setVisible(false);
				table.getColumns().get(0).setVisible(true);
			} else {
				System.out.println("no money to pay!");
			}

		} else {
			System.out.println("Bill already paid!");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.payServDAO = new PayServDAO();
		sdf = new SimpleDateFormat("dd.MM.yyyy");
		data = FXCollections.observableArrayList();
		table.setPlaceholder(new Label("No unpaid bills!"));
		table.setItems(data);
		id.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdBill()));
		ammount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmmount()));
		details.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetails()));
		issueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIssueDate().toString()));
		dueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDueDate().toString()));
		payDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPayDate().toString()));
	}
}
