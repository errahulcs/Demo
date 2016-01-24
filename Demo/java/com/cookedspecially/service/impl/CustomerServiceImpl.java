/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.CustomerDAO;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.service.CustomerService;

/**
 * @author shashank
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public void addCustomer(Customer customer) {
		customerDAO.addCustomer(customer);
		
	}

	@Override
	@Transactional
	public void removeCustomer(Integer id) throws Exception {
		customerDAO.removeCustomer(id);
		
	}

	@Override
	@Transactional
	public Customer getCustomer(Integer id) {
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerByParams(Integer custId, String email, String phone, Integer restaurantId) {
		return customerDAO.getCustomerByParams(custId, email, phone, restaurantId);
	}
}
