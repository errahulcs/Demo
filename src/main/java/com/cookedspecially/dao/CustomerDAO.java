package com.cookedspecially.dao;

import java.util.List;

import com.cookedspecially.domain.Customer;

public interface CustomerDAO {

	public void addCustomer(Customer customer);
	public void removeCustomer(Integer id) throws Exception;
	public Customer getCustomer(Integer id);
	public List<Customer> getCustomerByParams(Integer custId, String email, String phone);
}
