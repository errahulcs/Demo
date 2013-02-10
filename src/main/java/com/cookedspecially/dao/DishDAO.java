/**
 * 
 */
package com.cookedspecially.dao;

import java.util.List;

import com.cookedspecially.domain.Dish;

/**
 * @author sagarwal
 *
 */
public interface DishDAO {

	public void addDish(Dish dish);
	public List<Dish> listDish();
	public void removeDish(Integer id);
	public List<Dish> getDishes(Integer[] ids);
	public Dish getDish(Integer id);
}
