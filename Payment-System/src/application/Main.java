package application;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import consts.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This class has the role to open the GUI. This is the main frame.
 * 
 * @author Mircea Solovastru & Florin Sia
 * @version 1.0
 * @since 2016-06-01
 */
public class Main extends Application {
	/**
	 * Static field for thread handling.
	 */
	public static ExecutorService clientExecutor = Executors.newCachedThreadPool();

	/**
	 * Static field for the port number.
	 */
	public static int portNumber;

	/**
	 * Static field for the host.
	 */
	public static String host = "localhost";

	/**
	 * Static field for the socket.
	 */
	public static Socket clientSocket = null;

	/**
	 * Method for starting the connection to server.
	 * 
	 * @param args
	 *            used if the application is opened from command line.
	 */
	public static void initSvConnection(String[] args) {
		if (args.length != 1) {
			portNumber = Constants.DEFAULT_PORT;
		} else {
			portNumber = Integer.parseInt(args[0]);
		}

		try {
			clientSocket = new Socket(host, portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Static method used for displaying alerts.
	 * 
	 * @param type
	 *            enum object for alert type
	 * @param title
	 *            alert
	 * @param content
	 *            of the alert
	 * @see AlertType
	 */
	public static void createAlert(AlertType type, String title, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root, 300, 350);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The main of the application.
	 * 
	 * @param args
	 *            for command line parameters.
	 */
	public static void main(String[] args) {
		initSvConnection(args);
		launch(args);
	}
}
