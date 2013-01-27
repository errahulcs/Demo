/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.DishDAO;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.service.DishService;

/**
 * @author sagarwal
 *
 */
@Service
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDAO dishDAO;
	
	@Override
	@Transactional
	public void addDish(Dish dish) {
		dishDAO.addDish(dish);
	}


	@Override
	@Transactional
	public List<Dish> listDish() {
		return dishDAO.listDish();
	}


	@Override
	@Transactional
	public void removeDish(Integer id) {
		dishDAO.removeDish(id);

	}


	@Override
	@Transactional
	public List<Dish> getDishes(Integer[] ids) {
		return dishDAO.getDishes(ids);
	}

}
