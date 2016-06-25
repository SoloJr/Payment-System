package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class used to display values in the table.
 * 
 * @see Provider
 * @author Mircea Solovastru
 *
 */
public class ProviderFX {
	/**
	 * Field for if.
	 */
	private SimpleIntegerProperty idProvider;

	/**
	 * Field for IBAN.
	 */
	private SimpleStringProperty iban;

	/**
	 * Field for name.
	 */
	private SimpleStringProperty name;

	/**
	 * Field for password.
	 */
	private SimpleStringProperty password;

	/**
	 * CTOR to set values for fields.
	 * 
	 * @param idProvider
	 * @param iban
	 * @param name
	 * @param password
	 */
	public ProviderFX(int idProvider, String iban, String name, String password) {
		this.idProvider = new SimpleIntegerProperty(idProvider);
		this.iban = new SimpleStringProperty(iban);
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleStringProperty(password);
	}

	/**
	 * Gets the id.
	 * 
	 * @return
	 */
	public int getIdProvider() {
		return idProvider.get();
	}

	/**
	 * Sets the id.
	 * 
	 * @param idProvider
	 */
	public void setIdProvider(int idProvider) {
		this.idProvider = new SimpleIntegerProperty(idProvider);
	}

	/**
	 * Gets the IBAN.
	 * 
	 * @return
	 */
	public String getIban() {
		return iban.get();
	}

	/**
	 * Sets the IBAN.
	 * 
	 * @param iban
	 */
	public void setIban(String iban) {
		this.iban = new SimpleStringProperty(iban);
	}

	/**
	 * Gets the name.
	 * 
	 * @return
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	/**
	 * Gets the password.
	 * 
	 * @return
	 */
	public String getPassword() {
		return password.get();
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}
}
