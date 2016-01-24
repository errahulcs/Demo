/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.DishTypeDAO;
import com.cookedspecially.domain.DishType;
import com.cookedspecially.service.DishTypeService;

/**
 * @author shashank
 *
 */
@Service
public class DishTypeServiceImpl implements DishTypeService {

	@Autowired
	DishTypeDAO dishTypeDAO;
	
	@Override
	@Transactional
	public void addDishType(DishType dishType) {
		dishTypeDAO.addDishType(dishType);
	}

	@Override
	@Transactional
	public List<DishType> listDishTypes() {
		return dishTypeDAO.listDishTypes();
	}

	@Override
	@Transactional
	public List<DishType> listDishTypesByRestaurantId(Integer restaurantId) {
		return dishTypeDAO.listDishTypesByRestaurantId(restaurantId);
	}

	@Override
	@Transactional
	public void removeDishType(Integer id) {
		dishTypeDAO.removeDishType(id);
	}

	@Override
	@Transactional
	public DishType getDishType(Integer id) {
		return dishTypeDAO.getDishType(id);
	}

}
