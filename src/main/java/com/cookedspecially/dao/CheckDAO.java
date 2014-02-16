/**
 * 
 */
package com.cookedspecially.dao;

import java.util.Date;
import java.util.List;

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
	public List<Check> getAllOpenChecks(Integer restaurantId);
	public List<Integer> getAllCheckIds();
	public List getClosedChecksByDate(Integer restaurantId, Date startDate, Date endDate);
	public List<Check> getDailyInvoice(Integer restaurantId, Date startDate);
	public List<String> getUniqueDishTypes(Integer restaurantId);
	public List getDailySalesRecords(Integer restaurantId, Date startDate);
	public List<Check> getAllChecks(List<Integer> ids);
}
