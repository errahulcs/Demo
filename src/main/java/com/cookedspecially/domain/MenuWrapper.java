/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author sagarwal
 *
 */
public class MenuWrapper {

	private Integer menuId;
	
	private String restaurantId;
	
	private Integer userId;
	
	private String name;
	
	private String description;

	private List<CategoryWrapper> categories;

	public static MenuWrapper getMenuWrapper(Menu menu) {
		MenuWrapper menuWrapper = new MenuWrapper();
		menuWrapper.setMenuId(menu.getMenuId());
		menuWrapper.setUserId(menu.getUserId());
		menuWrapper.setName(menu.getName());
		menuWrapper.setDescription(menu.getDescription());
		List<Dish> dishes = menu.getDishes();
		HashMap<Integer, CategoryWrapper> categoryWrappers = new HashMap<Integer, CategoryWrapper>();
		for (Dish dish : dishes) {
			if (!categoryWrappers.containsKey(dish.getCategory().getCategoryId())) {
				categoryWrappers.put(dish.getCategory().getCategoryId(), CategoryWrapper.getCategoryWrapper(dish.getCategory()));
			}
			categoryWrappers.get(dish.getCategory().getCategoryId()).addDish(dish);
		}
		menuWrapper.setCategories(new ArrayList(categoryWrappers.values()));
		return menuWrapper;
	}
	
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CategoryWrapper> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryWrapper> categories) {
		this.categories = categories;
	}

	
	
}
