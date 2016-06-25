package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProviderFX {
	private SimpleIntegerProperty idProvider;

	private SimpleStringProperty iban;

	private SimpleStringProperty name;

	private SimpleStringProperty password;

	public ProviderFX(int idProvider, String iban, String name, String password) {
		this.idProvider = new SimpleIntegerProperty(idProvider);
		this.iban = new SimpleStringProperty(iban);
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleStringProperty(password);
	}

	public int getIdProvider() {
		return idProvider.get();
	}

	public void setIdProvider(int idProvider) {
		this.idProvider = new SimpleIntegerProperty(idProvider);
	}

	public String getIban() {
		return iban.get();
	}

	public void setIban(String iban) {
		this.iban = new SimpleStringProperty(iban);
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
}
