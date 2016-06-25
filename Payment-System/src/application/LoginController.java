package application;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Provider;
import server.ClientCall;
import server.RequestResponse;
import server.RequestType;

/**
 * The LoginController class implements an application that opens a GUI. Its
 * options are: login, register and close.
 * 
 * @author Mircea Solovastru
 * @version 1.0
 * @since 2016-06-01
 */
public class LoginController {

	/**
	 * Label for logo.
	 * 
	 * @see Label
	 */
	@FXML
	private Label lblStatus;

	/**
	 * TextField for username.
	 * 
	 * @see TextField
	 */
	@FXML
	private TextField txtUsername;

	/**
	 * PasswordField for password.
	 * 
	 * @see PasswordField
	 */
	@FXML
	private PasswordField txtPassword;

	/**
	 * Button for closing the window.
	 * 
	 * @see Button
	 */
	@FXML
	private Button btnClose;

	/**
	 * CheckBox for logging as client or provider.
	 * 
	 * @see CheckBox
	 */
	@FXML
	private CheckBox cbProvider;

	/**
	 * Makes the login, wherever the user is a provider or a client.
	 */
	public void login() {
		if (cbProvider.isSelected() == false) {
			Client matchedClient = null;
			RequestResponse<List<Client>> lookup = new RequestResponse<List<Client>>(Main.host, Main.portNumber);
			lookup.request = RequestType.GET_CLIENT_BY_USERNAME;
			lookup.parameters.add(txtUsername.getText());
			ClientCall<List<Client>> callable = new ClientCall<List<Client>>(lookup);
			List<Client> client = null;
			try {
				Future<List<Client>> future = Main.clientExecutor.submit(callable);
				client = future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			if (client != null && client.get(0).getPassword().equals(txtPassword.getText())) {
				matchedClient = client.get(0);
				try {
					Stage mainStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/application/Client.fxml"));
					loader.load();
					Parent root = loader.getRoot();
					Scene scene = new Scene(root, 600, 500);
					mainStage.setScene(scene);
					mainStage.setTitle("Payment Services");

					MainController mainController = loader.getController();
					mainController.setCurrentClient(matchedClient);

					mainStage.show();
					this.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				Main.createAlert(AlertType.ERROR, "Login error!", "Username or password is wrong!");
			}
		} else {
			Provider matchedProvider = null;
			RequestResponse<List<Provider>> lookup = new RequestResponse<List<Provider>>(Main.host, Main.portNumber);
			lookup.request = RequestType.GET_PROVIDER_BY_NAME;
			lookup.parameters.add(txtUsername.getText());
			ClientCall<List<Provider>> callable = new ClientCall<List<Provider>>(lookup);
			List<Provider> provider = null;
			Future<List<Provider>> future = Main.clientExecutor.submit(callable);
			try {
				provider = future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			if (provider != null && provider.get(0).getPassword().equals(txtPassword.getText())) {
				matchedProvider = provider.get(0);
				try {
					Stage mainStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/application/Provider.fxml"));
					loader.load();
					Parent root = loader.getRoot();
					Scene scene = new Scene(root, 600, 500);
					mainStage.setScene(scene);
					mainStage.setTitle("Payment Services");

					ProviderController controller = loader.getController();
					controller.setCurrentProvider(matchedProvider);
					mainStage.show();
					this.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				Main.createAlert(AlertType.ERROR, "Login Error!", "Username or password is wrong!");
			}
		}

	}

	/**
	 * This method makes the registration using the fields from the gui.
	 */
	public void register() {
		try {
			Stage registerStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
			Scene scene = new Scene(root, 350, 500);
			registerStage.setScene(scene);
			registerStage.show();

			this.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Closes the GUI. Use this method to close the GUI.
	 */
	public void close() {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}
}
