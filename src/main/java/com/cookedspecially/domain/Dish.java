/**
 * 
 */
package com.cookedspecially.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sagarwal
 *
 */
@Entity
@Table(name="DISHES")
public class Dish {

	@Id
	@Column(name="DISHID")
	@GeneratedValue
	private Integer dishId;
	
	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SHORTDESCRIPTION")
	private String shortDescription;
	
	@Column(name="IMAGEURL")
	private String imageUrl;
	
	@Column(name="PRICE")
	private Float price;
	

	@Column(name="DISHTYPE")
	private String dishType;
	
	@Column(name="VEGETARIAN")
	private Boolean vegetarian;
	
	@Column(name="ALCOHOLIC")
	private Boolean alcoholic;
	
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
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
	


}
