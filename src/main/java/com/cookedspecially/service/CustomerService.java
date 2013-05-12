/**
 * 
 */
package com.cookedspecially.service;

import com.cookedspecially.domain.Customer;

/**
 * @author shashank
 *
 */
public interface CustomerService {

	public void addCustomer(Customer customer);
	public void removeCustomer(Integer id) throws Exception;
	public Customer getCustomer(Integer id);
}
