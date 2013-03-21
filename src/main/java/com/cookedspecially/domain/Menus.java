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

	List<Menu> menus;
	Status status;
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
