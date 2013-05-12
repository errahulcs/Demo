/**
 * 
 */
package com.cookedspecially.service;

import com.cookedspecially.domain.Order;

/**
 * @author shashank
 *
 */
public interface OrderService {

	public void addOrder(Order order);
	public void removeOrder(Integer id) throws Exception;
	public Order getOrder(Integer id);

}
