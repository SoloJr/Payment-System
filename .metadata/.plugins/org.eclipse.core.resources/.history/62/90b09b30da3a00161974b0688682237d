package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientFX {
	private SimpleIntegerProperty idClient;

	private SimpleStringProperty email;

	private SimpleStringProperty name;

	private SimpleStringProperty password;

	private SimpleStringProperty surname;

	private SimpleStringProperty username;

	public ClientFX(int idClient, String email, String name, String password, String surname, String username) {
		this.idClient = new SimpleIntegerProperty(idClient);
		this.email = new SimpleStringProperty(email);
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleStringProperty(password);
		this.surname = new SimpleStringProperty(surname);
		this.username = new SimpleStringProperty(username);
	}

	public int getIdClient() {
		return idClient.get();
	}

	public void setIdClient(int idClient) {
		this.idClient = new SimpleIntegerProperty(idClient);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public String getSurname() {
		return surname.get();
	}

	public void setSurname(String surname) {
		this.surname = new SimpleStringProperty(surname);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username = new SimpleStringProperty(username);
	}
}
