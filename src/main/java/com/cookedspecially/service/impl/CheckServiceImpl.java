/**
 * 
 */
package com.cookedspecially.service.impl;

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

}
