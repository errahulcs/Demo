/**
 * 
 */
package com.cookedspecially.domain;

/**
 * @author shashank
 *
 */
public class CheckDishResponse {

	private Integer dishId;
	private String name;
	private float price;
	private String dishType;
	
	public CheckDishResponse(OrderDish orderDish) {
		this.dishId = orderDish.getDishId();
		this.name = orderDish.getName();
		this.price = orderDish.getPrice();
		this.dishType = orderDish.getDishType();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public String getDishType() {
		return dishType;
	}
	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
	
}
