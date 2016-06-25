package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import exceptions.RegisterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Client;
import model.Provider;
import server.ClientCall;
import server.RequestResponse;
import server.RequestType;

public class RegisterController implements Initializable {
	@FXML
	private Label lblLogo;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassOne;

	@FXML
	private PasswordField txtPassTwo;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtSurname;

	@FXML
	private TextField txtEmail;

	public void Register(ActionEvent event) {
		try {
			if (txtUsername.getText().isEmpty() == true && txtPassOne.getText().isEmpty() == true
					&& txtPassTwo.getText().isEmpty() == true && txtName.getText().isEmpty() == true
					&& txtSurname.getText().isEmpty() == true && txtEmail.getText().isEmpty() == true) {
				throw new RegisterException("You didn't complete all fields!");
			}
			verifyDataFromControls();
			addClient();

			Main.createAlert(AlertType.CONFIRMATION, "Register", "Registration successfully done!");

		} catch (RegisterException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Register Error");
			alert.setContentText(ex.getMessage());
			alert.show();
		}
	}

	public void verifyDataFromControls() throws RegisterException {
		if (txtPassOne.getText().compareTo(txtPassTwo.getText()) != 0) {
			throw new RegisterException("Passwords don't match!");
		}

		RequestResponse<List<Client>> lookup = new RequestResponse<List<Client>>(Main.host, Main.portNumber);
		lookup.request = RequestType.GET_CLIENT_BY_USERNAME;
		lookup.parameters.add(txtUsername.getText());
		ClientCall<List<Client>> callable = new ClientCall<List<Client>>(lookup);
		List<Client> clientIfExist = null;
		Future<List<Client>> future = Main.clientExecutor.submit(callable);
		try {
			clientIfExist = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		if (clientIfExist != null) {
			throw new RegisterException("This username already exists!");
		}

	}

	@SuppressWarnings("unused")
	public void addClient() throws RegisterException {
		List<Provider> providers = null;

		RequestResponse<List<Provider>> lookup = new RequestResponse<List<Provider>>(Main.host, Main.portNumber);
		lookup.request = RequestType.GET_PROVIDERS;
		ClientCall<List<Provider>> callable = new ClientCall<List<Provider>>(lookup);
		List<Provider> provider = null;
		Future<List<Provider>> future = Main.clientExecutor.submit(callable);
		try {
			providers = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		Client clientToAdd = new Client();
		clientToAdd.setUsername(txtUsername.getText());
		clientToAdd.setPassword(txtPassOne.getText());
		clientToAdd.setName(txtName.getText());
		clientToAdd.setSurname(txtSurname.getText());
		clientToAdd.setEmail(txtEmail.getText());
		clientToAdd.setContracts(null);

		RequestResponse<Client> lookup2 = new RequestResponse<Client>(Main.host, Main.portNumber);
		lookup2.request = RequestType.ADD_CLIENT;
		lookup2.parameters.add(clientToAdd);
		ClientCall<Client> callable2 = new ClientCall<Client>(lookup2);
		Main.clientExecutor.submit(callable2);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}