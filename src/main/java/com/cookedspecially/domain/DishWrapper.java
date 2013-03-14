/**
 * 
 */
package com.cookedspecially.domain;

/**
 * @author sagarwal
 *
 */
public class DishWrapper {

	private Integer dishId;
	
	private Integer restaurantId;
	
	private Integer userId;
	
	private String name;
	
	private String description;
	
	private String shortDescription;
	
	private String imageUrl;
	
	private Float price;

	public static DishWrapper getDishWrapper(Dish dish) {
		DishWrapper dishWrapper = new DishWrapper();
		dishWrapper.setDishId(dish.getDishId());
		dishWrapper.setRestaurantId(dish.getRestaurantId());
		dishWrapper.setUserId(dish.getUserId());
		dishWrapper.setName(dish.getName());
		dishWrapper.setDescription(dish.getDescription());
		dishWrapper.setShortDescription(dish.getShortDescription());
		dishWrapper.setImageUrl(dish.getImageUrl());
		dishWrapper.setPrice(dish.getPrice());
		return dishWrapper;
	}
	
	public Integer getDishId() {
		return dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
