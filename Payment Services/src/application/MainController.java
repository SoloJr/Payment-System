package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Bill;
import model.BillFX;
import model.Client;

public class MainController implements Initializable {

	@FXML
	private Label lblClientName;

	@FXML
	private Label lblClientBalance;
	
	@FXML
	private TableView<BillFX> table;
	
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
		System.out.println(currentClient.getBills().get(0).getDueDate().toString());
		for (Bill bill : currentClient.getBills()) {
			data.add(new BillFX(bill.getIdBill(), bill.getAmmount(), bill.getDetails(), sdf.format(bill.getIssueDate()),
					sdf.format(bill.getDueDate()), sdf.format(bill.getPayDate())));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sdf = new SimpleDateFormat("dd.MM.yyyy");
		data = FXCollections.observableArrayList();
		table.setItems(data);
		id.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdBill()));
		ammount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmmount()));
		details.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetails()));
		issueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIssueDate().toString()));
		dueDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDueDate().toString()));
		payDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPayDate().toString()));
	}
}