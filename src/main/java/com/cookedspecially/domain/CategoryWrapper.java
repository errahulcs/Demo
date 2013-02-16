/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sagarwal
 *
 */
public class CategoryWrapper {

	private Integer categoryId;
	
	private Integer userId;
	
	private String name;
	
	private String description;

	private List<DishWrapper> dishes;
	
	public static CategoryWrapper getCategoryWrapper(Category category) {
		CategoryWrapper categoryWrapper =  new CategoryWrapper();
		categoryWrapper.setCategoryId(category.getCategoryId());
		categoryWrapper.setUserId(category.getUserId());
		categoryWrapper.setName(category.getName());
		categoryWrapper.setDescription(category.getDescription());
		return categoryWrapper;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public List<DishWrapper> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishWrapper> dishes) {
		this.dishes = dishes;
	}
	
	public void addDish(Dish dish) {
		if (this.dishes == null) {
			this.dishes = new ArrayList<DishWrapper>();
		}
		this.dishes.add(DishWrapper.getDishWrapper(dish));
	}

}
