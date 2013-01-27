/**
 * 
 */
package com.cookedspecially.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookedspecially.domain.Dish;
import com.cookedspecially.service.CategoryService;
import com.cookedspecially.service.DishService;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/")
	public String listDishes(Map<String, Object> map) {

		map.put("dish", new Dish());
		map.put("dishList", dishService.listDish());
		map.put("categoryList", categoryService.listCategory());
		return "dish";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addDish(@ModelAttribute("dish")
	Dish dish, BindingResult result, @RequestParam("categoryId") Integer categoryId) {
		dish.setCategory(categoryService.getCategory(categoryId));
		dishService.addDish(dish);

		return "redirect:/dish/";
	}

	@RequestMapping("/delete/{dishId}")
	public String deleteDish(@PathVariable("dishId")
	Integer dishId) {

		dishService.removeDish(dishId);

		return "redirect:/dish/";
	}
}
