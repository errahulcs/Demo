package com.cookedspecially.dao;

import com.cookedspecially.domain.Customer;

public interface CustomerDAO {

	public void addCustomer(Customer customer);
	public void removeCustomer(Integer id) throws Exception;
	public Customer getCustomer(Integer id);
}
