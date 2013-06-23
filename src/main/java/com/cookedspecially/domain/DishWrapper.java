/**
 * 
 */
package com.cookedspecially.domain;

import com.cookedspecially.utility.ImageUtility;


/**
 * @author sagarwal
 *
 */
public class DishWrapper {
	
	private Integer itemId;
	
	private Integer restaurantId;
	
	private Integer userId;
	
	private String name;
	
	private String description;
	
	private String shortDescription;
	
	private String imageUrl;
	
	private String smallImageUrl;
	
	private Float price;

	private String itemType;
	
	private Boolean vegetarian;
	
	private Boolean alcoholic;


	public static DishWrapper getDishWrapper(Dish dish) {
		DishWrapper dishWrapper = new DishWrapper();
		dishWrapper.setItemId(dish.getDishId());
		dishWrapper.setRestaurantId(dish.getRestaurantId());
		dishWrapper.setUserId(dish.getUserId());
		dishWrapper.setName(dish.getName());
		dishWrapper.setDescription(dish.getDescription().replaceAll("'", "&#39;"));
		dishWrapper.setShortDescription(dish.getShortDescription().replaceAll("'", "&#39;"));
		dishWrapper.setImageUrl(dish.getImageUrl());
		dishWrapper.setSmallImageUrl(ImageUtility.getSmallImageUrl(dish.getImageUrl(), 200, 200));
		dishWrapper.setPrice(dish.getPrice());
		dishWrapper.setItemType(dish.getDishType());
		dishWrapper.setVegetarian(dish.getVegetarian());
		dishWrapper.setAlcoholic(dish.getAlcoholic());
		return dishWrapper;
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

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Boolean getVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(Boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public Boolean getAlcoholic() {
		return alcoholic;
	}

	public void setAlcoholic(Boolean alcoholic) {
		this.alcoholic = alcoholic;
	}


	public String getSmallImageUrl() {
		return smallImageUrl;
	}


	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

}
