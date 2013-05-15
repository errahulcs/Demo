/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;
import java.util.Map;

import com.cookedspecially.domain.Order;

/**
 * @author shashank
 *
 */
public interface OrderService {

	public List<Order> listOrders(Map<String, Object> queryMap);
	public void addOrder(Order order);
	public void removeOrder(Integer id) throws Exception;
	public Order getOrder(Integer id);

}
