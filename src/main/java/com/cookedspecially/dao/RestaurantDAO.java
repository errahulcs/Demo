/**
 * 
 */
package com.cookedspecially.dao;

import java.util.List;
import com.cookedspecially.domain.Restaurant;

/**
 * @author shashank
 *
 */
public interface RestaurantDAO {

	public void addRestaurant(Restaurant restaurant);
	public List<Restaurant> listRestaurant();
	public List<Restaurant> listRestaurantByUser(Integer userId);
	public void removeRestaurant(Integer id);
	public Restaurant getRestaurant(Integer id);
}
