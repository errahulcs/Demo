/**
 * 
 */
package com.cookedspecially.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cookedspecially.domain.Category;
import com.cookedspecially.domain.DishType;
import com.cookedspecially.service.DishTypeService;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shashank
 *
 */
@Controller
@RequestMapping("/dishTypes")
public class DishTypeController {

	@Autowired
	DishTypeService dishTypeService;
	
	@RequestMapping("/")
	public String listDishTypes(Map<String, Object> map, HttpServletRequest request) {

		map.put("dishType", new DishType());
		map.put("dishTypeList", dishTypeService.listDishTypesByRestaurantId((Integer) request.getSession().getAttribute("userId")));
		return "dishType";
	}

	@RequestMapping("/edit/{dishTypeId}")
	public String editDishType(Map<String, Object> map, HttpServletRequest request, @PathVariable("dishTypeId")	Integer dishTypeId) {

		DishType dishType = dishTypeService.getDishType(dishTypeId);
		map.put("dishType", dishType);
		map.put("dishTypeList", dishTypeService.listDishTypesByRestaurantId((Integer) request.getSession().getAttribute("userId")));
		return "dishType";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addDishType(@ModelAttribute("dishType")
	DishType dishType, BindingResult result) {
		dishTypeService.addDishType(dishType);

		return "redirect:/dishTypes/";
	}

	@RequestMapping("/delete/{dishTypeId}")
	public String deleteDishType(@PathVariable("dishTypeId")
	Integer dishTypeId) {
		dishTypeService.removeDishType(dishTypeId);
		return "redirect:/dishTypes/";
	}
}
