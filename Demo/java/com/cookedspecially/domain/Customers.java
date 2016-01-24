/**
 * 
 */
package com.cookedspecially.domain;

import java.util.List;

/**
 * @author shashank
 *
 */
public class Customers {

	boolean exactMatch;
	boolean newCustomer;
	
	List<Customer> customers;

	public boolean getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	public boolean getNewCustomer() {
		return newCustomer;
	}

	public void setNewCustomer(boolean newCustomer) {
		this.newCustomer = newCustomer;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
}
