/**
 * 
 */
package com.cookedspecially.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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

import com.cookedspecially.config.CSConstants;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.domain.MenuWrapper;
import com.cookedspecially.domain.Section;
import com.cookedspecially.service.DishService;
import com.cookedspecially.service.MenuService;
import com.cookedspecially.service.SectionService;
import com.cookedspecially.utility.StringUtility;

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
	
	//@Autowired
	//private SectionService sectionService;
	
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
		Set<String> dishIds = new HashSet<String>();
		List<Section> menuSections = menu.getSections();
		TreeMap<Integer, Section> sectionTree = new TreeMap<Integer, Section>();
		if (menuSections != null && menuSections.size() > 0) {
			for (Section menuSection : menuSections) {
				if (menuSection.isValid() && !StringUtility.isNullOrEmpty(menuSection.getDishIds())) {
					sectionTree.put(menuSection.getPosition(), menuSection);
					String[] dishIdsStrArr = menuSection.getDishIds().split(CSConstants.COMMA);
					if (dishIdsStrArr != null) {
						dishIds.addAll(Arrays.asList(dishIdsStrArr));
					}
				}
			}
		}
		Integer[] dishIdsArr = new Integer[dishIds.size()];
		
		HashMap<Integer, Dish> dishMap = new HashMap<Integer, Dish>();
		int dishCounter = 0;
		for (String dishId: dishIds) {
			dishIdsArr[dishCounter++] = Integer.parseInt(dishId);
		}
		List<Dish> dishes = dishService.getDishes(dishIdsArr);
		
		for (Dish dish : dishes) {
			dishMap.put(dish.getDishId(), dish);
		}
		
		ArrayList<Section> finalSections = new ArrayList<Section>();
		for (Integer key: sectionTree.keySet()) {
			finalSections.add(sectionTree.get(key));
		}
		//Iterator<Entry<Integer, Section>> sectionIterator = sectionTree.entrySet().iterator();
		//while(sectionIterator.hasNext()) {
		//	finalSections.add(sectionIterator.next().getValue());
		//}
		for (Section section : finalSections) {
			ArrayList<Dish> finalDishes = null;
			if (!StringUtility.isNullOrEmpty(section.getDishIds())) {
				String[] dishIdsStrArr = section.getDishIds().split(CSConstants.COMMA);
				if (dishIdsStrArr != null) {
					finalDishes = new ArrayList<Dish>();
					for (int i = 0; i < dishIdsStrArr.length; i++) {
						finalDishes.add(dishMap.get(Integer.parseInt(dishIdsStrArr[i])));
					} 
				}
			}
			section.setDishes(finalDishes);
			//sectionService.addSection(section);
		}
		menu.setSections(finalSections);
		menuService.addMenu(menu);
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
