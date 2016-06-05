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
import javafx.stage.Stage;

public class Main extends Application {
	public static ExecutorService clientExecutor = Executors.newCachedThreadPool();
	public static int portNumber;
	public static String host = "localhost";
	public static Socket clientSocket = null;
	
	public static void initSvConnection(String[] args){
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

	public static void main(String[] args) {
		initSvConnection(args);	
		launch(args);
	}
}
