/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shashank
 *
 */
public class SectionWrapper {

	private Integer sectionId;
	
	private Integer userId;
	
	private String name;
	
	private String description;
	
	private String header;
	
	private String footer;
	
	private Float price;
	
    private List<DishWrapper> items;

	
	public static SectionWrapper getSectionWrapper(Section section) {
		SectionWrapper sectionWrapper = new SectionWrapper();
		sectionWrapper.setSectionId(section.getSectionId());
		sectionWrapper.setUserId(section.getUserId());
		sectionWrapper.setName(section.getName());
		sectionWrapper.setDescription(section.getDescription().replaceAll("'", "&#39;"));
		sectionWrapper.setHeader(section.getHeader());
		sectionWrapper.setFooter(section.getFooter());
		sectionWrapper.setPrice(section.getPrice());
		List<Dish> dishes = section.getDishes();
		if (dishes != null) {
			sectionWrapper.items = new ArrayList<DishWrapper>();
			for (Dish dish : dishes) {
				if (dish != null) {
					sectionWrapper.items.add(DishWrapper.getDishWrapper(dish));
				}
			}
		}
		return sectionWrapper;
	}


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


	public List<DishWrapper> getItems() {
		return items;
	}


	public void setItems(List<DishWrapper> items) {
		this.items = items;
	}
	
}
