/**
 * 
 */
package com.cookedspecially.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.CheckDAO;
import com.cookedspecially.domain.Check;
import com.cookedspecially.service.CheckService;

/**
 * @author shashank
 *
 */
@Service
public class CheckServiceImpl implements CheckService {

	@Autowired
	private CheckDAO checkDAO;
	
	@Override
	@Transactional
	public void addCheck(Check check) {
		checkDAO.addCheck(check);
	}

	@Override
	@Transactional
	public void removeCheck(Integer id) {
		checkDAO.removeCheck(id);
	}

	@Override
	@Transactional
	public Check getCheck(Integer id) {
		return checkDAO.getCheck(id);
	}

	@Override
	@Transactional
	public Check getCheckByTableId(Integer restaurantId, Integer tableId) {
		return checkDAO.getCheckByTableId(restaurantId, tableId);
	}

	@Override
	@Transactional
	public Check getCheckByCustId(Integer restaurantId, Integer custId) {
		return checkDAO.getCheckByCustId(restaurantId, custId);
	}

	@Override
	@Transactional
	public List<Check> getAllOpenChecks(Integer restaurantId) {
		return checkDAO.getAllOpenChecks(restaurantId);
	}
	
	@Override
	@Transactional
	public List<Integer> getAllCheckIds() {
		return checkDAO.getAllCheckIds();
	}
	
	@Override
	@Transactional
	public List getClosedChecksByDate(Integer restaurantId, Date startDate, Date endDate) {
		return checkDAO.getClosedChecksByDate(restaurantId, startDate, endDate);
	}
	
	@Override
	@Transactional
	public List<Check> getDailyInvoice(Integer restaurantId, Date startDate) {
		return checkDAO.getDailyInvoice(restaurantId, startDate);
	}
	
	@Override
	@Transactional
	public List<String> getUniqueDishTypes(Integer restaurantId) {
		return checkDAO.getUniqueDishTypes(restaurantId);
	}
	
	@Override
	@Transactional
	public List getDailySalesRecords(Integer restaurantId, Date startDate) {
		return checkDAO.getDailySalesRecords(restaurantId, startDate);
	}
}
