/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

/**
 * @author sagarwal
 *
 */
@Entity
@Table(name="SECTIONS")
public class Section {

	@Id
	@Column(name="SECTIONID")
	@GeneratedValue
	private Integer sectionId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="HEADER")
	private String header;
	
	@Column(name="FOOTER")
	private String footer;
	
	@Column(name="PRICE")
	private Float price;
	
	private boolean valid = true;
	
	private String dishIds = "";
	
	@ManyToMany
	@IndexColumn(name="DISHPOSITION")
    @JoinTable(name="SECTION_DISH", 
                joinColumns={@JoinColumn(name="SECTIONID")}, 
                inverseJoinColumns={@JoinColumn(name="DISHID")})
    private List<Dish> dishes = new ArrayList<Dish>();

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getDishIds() {
		return dishIds;
	}

	public void setDishIds(String dishIds) {
		this.dishIds = dishIds;
	}

	
}
