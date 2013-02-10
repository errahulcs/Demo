/**
 * 
 */
package com.cookedspecially.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
