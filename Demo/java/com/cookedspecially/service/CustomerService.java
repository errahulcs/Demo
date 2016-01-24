/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Customer;

/**
 * @author shashank
 *
 */
public interface CustomerService {

	public void addCustomer(Customer customer);
	public void removeCustomer(Integer id) throws Exception;
	public Customer getCustomer(Integer id);
	public List<Customer> getCustomerByParams(Integer custId, String email, String phone, Integer restaurantId);
}
