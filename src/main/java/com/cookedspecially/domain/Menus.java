/**
 * 
 */
package com.cookedspecially.domain;

import java.util.List;

import com.cookedspecially.enums.Status;

/**
 * @author shashank
 *
 */
public class Menus {

	List<MenuWrapper> menus;
	Integer restaurantId;
	
	Status status;
	public List<MenuWrapper> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuWrapper> menus) {
		this.menus = menus;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
}
