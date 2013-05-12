/**
 * 
 */
package com.cookedspecially.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.OrderDAO;
import com.cookedspecially.domain.Order;

/**
 * @author shashank
 *
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addOrder(Order order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);

	}

	@Override
	public void removeOrder(Integer id) throws Exception {
		Order order = (Order) sessionFactory.getCurrentSession().load(Order.class, id);
		if (order != null) {
			sessionFactory.getCurrentSession().delete(order);
		}
	}

	@Override
	public Order getOrder(Integer id) {
		return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
	}

}
