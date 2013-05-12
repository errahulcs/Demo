/**
 * 
 */
package com.cookedspecially.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.OrderDAO;
import com.cookedspecially.domain.Order;
import com.cookedspecially.service.OrderService;

/**
 * @author shashank
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	@Transactional
	public void addOrder(Order order) {
		orderDAO.addOrder(order);
	}

	@Override
	@Transactional
	public void removeOrder(Integer id) throws Exception {
		orderDAO.removeOrder(id);
	}

	
	@Override
	@Transactional
	public Order getOrder(Integer id) {
		return orderDAO.getOrder(id);
	}

}
