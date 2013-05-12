/**
 * 
 */
package com.cookedspecially.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.CustomerDAO;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.Dish;

/**
 * @author shashank
 *
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCustomer(Customer customer) {
		sessionFactory.getCurrentSession().saveOrUpdate(customer);

	}

	@Override
	public void removeCustomer(Integer id) throws Exception {
		Customer customer = (Customer) sessionFactory.getCurrentSession().load(Customer.class, id);
		if (null != customer) {
			sessionFactory.getCurrentSession().delete(customer);
		}
	}

	@Override
	public Customer getCustomer(Integer id) {
		return (Customer)sessionFactory.getCurrentSession().get(Customer.class, id);
	}

}
