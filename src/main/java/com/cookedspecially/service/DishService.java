/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Dish;

/**
 * @author sagarwal
 *
 */
public interface DishService {

	public void addDish(Dish dish);
	public List<Dish> listDish();
	public void removeDish(Integer id);
	public List<Dish> getDishes(Integer[] ids);
}
