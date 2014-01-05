/**
 * 
 */
package com.cookedspecially.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author shashank
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonDish {

	public int id;
	public String name;
	public float price;
	public int quantity;
	public String dishType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getQuantity() {
		if (quantity == 0)
			return 1;
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDishType() {
		return dishType;
	}
	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
	
	
}
