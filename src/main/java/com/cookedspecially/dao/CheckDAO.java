/**
 * 
 */
package com.cookedspecially.dao;

import com.cookedspecially.domain.Check;

/**
 * @author shashank
 *
 */
public interface CheckDAO {

	public void addCheck(Check check);
	public void removeCheck(Integer id);
	public Check getCheck(Integer id);
	public Check getCheckByTableId(Integer restaurantId, Integer tableId);
	public Check getCheckByCustId(Integer restaurantId, Integer custId);
}
