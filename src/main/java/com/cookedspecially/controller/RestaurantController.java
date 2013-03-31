/**
 * 
 */
package com.cookedspecially.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Restaurant;
import com.cookedspecially.service.RestaurantService;

/**
 * @author shashank
 *
 */
@Component
@RequestMapping("/restaurant")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping("/")
	public String listRestaurants(Map<String, Object> map, HttpServletRequest request) {
		map.put("restaurant", new Restaurant());
		map.put("restaurantList", restaurantService.listRestaurantByUser((Integer) request.getSession().getAttribute("userId")));
		return "restaurant";
	}
	
	@RequestMapping("/edit/{restaurantId}")
	public String editRestaurant(Map<String, Object> map, HttpServletRequest request, @PathVariable("restaurantId") Integer restaurantId) {
		map.put("restaurant", restaurantService.getRestaurant(restaurantId));
		map.put("restaurantList", restaurantService.listRestaurantByUser((Integer) request.getSession().getAttribute("userId")));
		return "restaurant";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addRestaurant(@ModelAttribute("restaurant") Restaurant restaurant, BindingResult result) {
		
		restaurantService.addRestaurant(restaurant);
		return "redirect:/restaurant/";
	}

	@RequestMapping("/delete/{restaurantId}")
	public String deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) {
		restaurantService.removeRestaurant(restaurantId);
		return "redirect:/restaurant/";
	}

}
