/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Check;

/**
 * @author shashank
 *
 */
public interface CheckService {
	
	public void addCheck(Check check);
	public void removeCheck(Integer id);
	public Check getCheck(Integer id);
	public Check getCheckByTableId(Integer restaurantId, Integer tableId);
	public Check getCheckByCustId(Integer restaurantId, Integer custId);
	public List<Check> getAllOpenChecks(Integer restaurantId);
	public List<Integer> getAllCheckIds();
}
