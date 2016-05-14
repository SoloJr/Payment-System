package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import dao.PayServDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import svrcon.EstablishConnectionSv;
import svrcon.SvConnection;

public class LoginController {
	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	public void Login(ActionEvent event) {
//		PayServDAO payServDAO = new PayServDAO();
//		List<Client> clients = payServDAO.getAllClients();
		boolean foundClient = false;
//		for (Client c : clients) {
//			if (c.isMatchingUser(txtUsername.getText(), txtPassword.getText()) == true) {
//				foundClient = true;
//				break;
//			}
//		}
		SvConnection stub = (new EstablishConnectionSv()).getConnectionToSv();
		try {
			Client matchedClient = stub.getClientIfExist(txtUsername.getText());
			if(matchedClient != null){
				foundClient = true;
			}
		} catch (RemoteException e1) {
			foundClient = false;
			//De logat eroarea
			//Connection To sv not found
		}
		if (foundClient == true) {
			try {
				Stage mainStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
				Scene scene = new Scene(root, 500, 500);
				mainStage.setScene(scene);
				mainStage.show();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setContentText("Username or password are incorrect!");
			alert.show();
		}
	}
	
	public void Register(ActionEvent event) {
		try {
			Stage registerStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
			Scene scene = new Scene(root, 350, 500);
			registerStage.setScene(scene);
			registerStage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
