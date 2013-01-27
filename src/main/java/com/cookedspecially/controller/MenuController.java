/**
 * 
 */
package com.cookedspecially.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.service.DishService;
import com.cookedspecially.service.MenuService;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DishService dishService;
	
	
	@RequestMapping("/")
	public String listContacts(Map<String, Object> map) {

		map.put("menu", new Menu());
		map.put("menuList", menuService.listMenu());
		map.put("dishList", dishService.listDish());
		return "menu";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("menu")
	Menu menu, BindingResult result, @RequestParam("dishIds") Integer[] dishIds) {
		Set<Dish> dishes = new HashSet<Dish>();
		dishes.addAll(dishService.getDishes(dishIds));
		menu.setDishes(dishes);
		menuService.addMenu(menu);

		return "redirect:/menu/";
	}

	@RequestMapping("/delete/{menuId}")
	public String deleteContact(@PathVariable("menuId")
	Integer menuId) {

		menuService.removeMenu(menuId);
		return "redirect:/menu/";
	}
}
