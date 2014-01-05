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
 * @author shashank
 *
 */
@Entity
@Table(name="ORDERDISHES")
public class OrderDish {

	@Id
	@Column(name="ORDERDISHID")
	@GeneratedValue
	private Integer orderDishId;
	
	@Column(name="DISHID")
	private Integer dishId;
	
	@Column(name="QUANTITY")
	private Integer quantity;
	
	@Column(name="PRICE")
	private Float price;

	@Column(name="name")
	private String name;
	
	@Column(name="dishType")
	private String dishType;
	
	public Integer getOrderDishId() {
		return orderDishId;
	}

	public void setOrderDishId(Integer orderDishId) {
		this.orderDishId = orderDishId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}


	public Integer addMore(Integer additionalQuantity) {
		this.quantity += additionalQuantity;
		return this.quantity;
	}

	public Integer getDishId() {
		return dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
	
}
