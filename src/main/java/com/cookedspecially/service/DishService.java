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
	public void updateMenuModificationTime(Integer dishId);
	public List<Dish> listDish();
	public List<Dish> listDishByUser(Integer userId);
	public void removeDish(Integer id);
	public List<Dish> getDishes(Integer[] ids);
	public Dish getDish(Integer id);
}
