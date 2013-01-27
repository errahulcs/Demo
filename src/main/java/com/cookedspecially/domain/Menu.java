/**
 * 
 */
package com.cookedspecially.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author sagarwal
 *
 */
@Entity
@Table(name="MENUS")
public class Menu {
	@Id
	@Column(name="MENUID")
	@GeneratedValue
	private Integer menuId;
	
	@Column(name="RESTAURANTID")
	private String restaurantId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
		
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="MENU_DISH", 
                joinColumns={@JoinColumn(name="MENUID")}, 
                inverseJoinColumns={@JoinColumn(name="DISHID")})
    private Set<Dish> dishes = new HashSet<Dish>();

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

	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}
	
}
