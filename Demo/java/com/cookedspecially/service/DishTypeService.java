/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.DishType;

/**
 * @author shashank
 *
 */
public interface DishTypeService {
	public void addDishType(DishType dishType);
	public List<DishType> listDishTypes();
	public List<DishType> listDishTypesByRestaurantId(Integer restaurantId);
	public void removeDishType(Integer id);
	public DishType getDishType(Integer id);
}
