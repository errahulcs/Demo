/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.RestaurantDAO;
import com.cookedspecially.domain.Restaurant;
import com.cookedspecially.service.RestaurantService;

/**
 * @author shashank
 *
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@Override
	@Transactional
	public void addRestaurant(Restaurant restaurant) {
		restaurantDAO.addRestaurant(restaurant);
	}

	@Override
	@Transactional
	public List<Restaurant> listRestaurant() {
		return restaurantDAO.listRestaurant();
	}

	@Override
	@Transactional
	public List<Restaurant> listRestaurantByUser(Integer userId) {
		return restaurantDAO.listRestaurantByUser(userId);
	}

	
	@Override
	@Transactional
	public void removeRestaurant(Integer id) {
		restaurantDAO.removeRestaurant(id);
	}

	@Override
	@Transactional
	public Restaurant getRestaurant(Integer id) {
		return restaurantDAO.getRestaurant(id);
	}

}
