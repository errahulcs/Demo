/**
 * 
 */
package com.cookedspecially.dao;

import com.cookedspecially.domain.Order;

/**
 * @author shashank
 *
 */
public interface OrderDAO {

	public void addOrder(Order order);
	public void removeOrder(Integer id) throws Exception;
	public Order getOrder(Integer id);

}
