/**
 * 
 */
package com.cookedspecially.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.domain.MenuWrapper;
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
	public String listMenus(Map<String, Object> map, HttpServletRequest request) {

		//map.put("menu", new Menu());
		//System.out.println(request.getSession().getAttribute("userId"));
		List<Menu> menus = menuService.listMenuByUser((Integer) request.getSession().getAttribute("userId"));
		map.put("menuList", menus);
		//map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "listMenu";
	}

		
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public String createMenu(Map<String, Object> map, HttpServletRequest request) {

		map.put("menu", new Menu());
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "newMenu";
	}
	@RequestMapping("/edit/{menuId}")
	public String editMenu(Map<String, Object> map, HttpServletRequest request, @PathVariable("menuId") Integer menuId) {

		Menu menu = menuService.getMenu(menuId);
		map.put("menu", menu);
		//System.out.println(request.getSession().getAttribute("userId"));
		List<Menu> menus = menuService.listMenuByUser((Integer) request.getSession().getAttribute("userId"));
		map.put("menuList", menus);
		/*
		List<Dish> dishes = menu.getDishes();
		HashMap<Integer, Integer> existingDishIds = new HashMap<Integer,Integer>((dishes != null) ? dishes.size(): 0);
		if(dishes != null) {
			for (Dish dish: dishes) {
				existingDishIds.put(dish.getDishId(), 1);
			}
		}
		map.put("existingDishIds", existingDishIds);
		*/
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "menu";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addMenu(@ModelAttribute("menu")
	Menu menu, BindingResult result, @RequestParam("dishIds") Integer[] dishIds) {
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		if (dishIds != null && dishIds.length > 0) {
			List<Dish> dishesArr = dishService.getDishes(dishIds);
			HashMap<Integer, Dish> dishesMap = new HashMap<Integer, Dish>();
			
			if (dishesArr != null) {
				for (Dish dish: dishesArr) {
					dishesMap.put(dish.getDishId(), dish);
				}
			}
			for (Integer dishId : dishIds) {
				Dish dish = dishesMap.get(dishId);
				if (dish != null) {
					dishes.add(dish);
				}
			}
		}
		//menu.setDishes(dishes);
		menuService.addMenu(menu);

		return "redirect:/menu/";
	}

	@RequestMapping(value = "/addNew", method = RequestMethod.POST)
	public String addNewMenu(@ModelAttribute("menu")
	Menu menu, BindingResult result) {
		System.out.println(menu);
		return "redirect:/menu/";
	}
	@RequestMapping("/delete/{menuId}")
	public String deleteMenu(@PathVariable("menuId")
	Integer menuId) {

		menuService.removeMenu(menuId);
		return "redirect:/menu/";
	}
	
	@RequestMapping("/show/{menuId}")
	public String showMenu(Map<String, Object> map, @PathVariable("menuId") Integer menuId) {
		Menu menu = menuService.getMenu(menuId);
		map.put("menu", menu);
		return "showMenu";
	}
	
	@RequestMapping("/getjson/{menuId}")
	public @ResponseBody MenuWrapper showMenuJson(Map<String, Object> map, @PathVariable("menuId") Integer menuId) {
		return MenuWrapper.getMenuWrapper(menuService.getMenu(menuId));
		
		
	}
}
