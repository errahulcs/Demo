/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import org.hibernate.annotations.IndexColumn;

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
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
		
	@ManyToMany(fetch = FetchType.EAGER)
	@IndexColumn(name="DISHPOSITION")
    @JoinTable(name="MENU_DISH", 
                joinColumns={@JoinColumn(name="MENUID")}, 
                inverseJoinColumns={@JoinColumn(name="DISHID")})
    private List<Dish> dishes = new ArrayList<Dish>();

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

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
