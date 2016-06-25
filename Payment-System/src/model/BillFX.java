package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class used to display bills in table views.
 * 
 * @author Mircea Solovastru
 *
 */
public class BillFX {
	/**
	 * Field for id.
	 */
	private SimpleIntegerProperty idBill;

	/**
	 * Field for amount.
	 */
	private SimpleDoubleProperty ammount;

	/**
	 * Field for details
	 */
	private SimpleStringProperty details;

	/**
	 * Field for issue date.
	 */
	private SimpleStringProperty issueDate;

	/**
	 * Field for due date.
	 */
	private SimpleStringProperty dueDate;

	/**
	 * Field for pay date.
	 */
	private SimpleStringProperty payDate;

	/**
	 * CTOR that sets the values for the fields.
	 * 
	 * @param idBill
	 * @param ammount
	 * @param details
	 * @param issueDate
	 * @param dueDate
	 * @param payDate
	 */
	public BillFX(int idBill, double ammount, String details, String issueDate, String dueDate, String payDate) {
		this.idBill = new SimpleIntegerProperty(idBill);
		this.ammount = new SimpleDoubleProperty(ammount);
		this.details = new SimpleStringProperty(details);
		this.issueDate = new SimpleStringProperty(issueDate);
		this.dueDate = new SimpleStringProperty(dueDate);
		this.payDate = new SimpleStringProperty(payDate);
	}

	/**
	 * Gets the id.
	 * 
	 * @return
	 */
	public int getIdBill() {
		return idBill.get();
	}

	/**
	 * Sets the id.
	 * 
	 * @param idBill
	 */
	public void setIdBill(int idBill) {
		this.idBill = new SimpleIntegerProperty(idBill);
	}

	/**
	 * Gets the amount.
	 * 
	 * @return
	 */
	public double getAmmount() {
		return ammount.get();
	}

	/**
	 * Sets the amount.
	 * 
	 * @param ammount
	 */
	public void setAmmount(double ammount) {
		this.ammount = new SimpleDoubleProperty(ammount);
	}

	/**
	 * Gets the details.
	 * 
	 * @return
	 */
	public String getDetails() {
		return details.get();
	}

	/**
	 * Sets the details.
	 * 
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = new SimpleStringProperty(details);
	}

	/**
	 * Gets the issue date.
	 * 
	 * @return
	 */
	public String getIssueDate() {
		return issueDate.get();
	}

	/**
	 * Sets the issue date.
	 * 
	 * @param issueDate
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = new SimpleStringProperty(issueDate);
	}

	/**
	 * Gets the due date.
	 * 
	 * @return
	 */
	public String getDueDate() {
		return dueDate.get();
	}

	/**
	 * Sets the due date.
	 * 
	 * @param dueDate
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = new SimpleStringProperty(dueDate);
	}

	/**
	 * Gets the pay date.
	 * 
	 * @return
	 */
	public String getPayDate() {
		return payDate.get();
	}

	/**
	 * Sets the pay date.
	 * 
	 * @param payDate
	 */
	public void setPayDate(String payDate) {
		this.payDate = new SimpleStringProperty(payDate);
	}
}
