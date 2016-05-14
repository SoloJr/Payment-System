package application;

import java.util.List;

import dao.PayServDAO;
import exceptions.RegisterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Client;
import model.Provider;

public class RegisterController {
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
			
			if (txtPassOne.getText().compareTo(txtPassTwo.getText()) != 0) {
				throw new RegisterException("Passwords don't match!");
			}
			
			PayServDAO payServDAO = new PayServDAO();
			List<Client> clients = payServDAO.getAllClients();
			for (Client c : clients) {
				if (c.isMatchingUsername(txtUsername.getText()) == true) {
					throw new RegisterException("This username already exists!");
				}
				
				if (c.isMathingEmail(txtEmail.getText()) == true) {
					throw new RegisterException("This email is already in use!");
				}
			}
			
			List<Provider> providers = payServDAO.getProviders();
			Client clientToAdd = new Client();
			clientToAdd.setUsername(txtUsername.getText());
			clientToAdd.setPassword(txtPassOne.getText());
			clientToAdd.setName(txtName.getText());
			clientToAdd.setSurname(txtSurname.getText());
			clientToAdd.setEmail(txtEmail.getText());
			clientToAdd.setProviders(providers);
			payServDAO.addClient(clientToAdd);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Register Success");
			alert.setContentText("You successfully registered this account!");
			alert.show();
			
		} catch (RegisterException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Register Error");
			alert.setContentText(ex.getMessage());
			alert.show();
		}
	}
}