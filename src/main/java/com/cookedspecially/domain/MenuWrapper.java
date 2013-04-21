/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cookedspecially.enums.Status;

/**
 * @author sagarwal
 *
 */
public class MenuWrapper {

	private Integer menuId;
	
	private String restaurantId;
	
	private Integer userId;
	
	private String name;
	
	private String description;

	private Date modifiedTime;
	
	private Status status;
	
	private String imageUrl;
	
	private List<SectionWrapper> sections;

	public static MenuWrapper getMenuWrapper(Menu menu) {
		MenuWrapper menuWrapper = new MenuWrapper();
		menuWrapper.setMenuId(menu.getMenuId());
		menuWrapper.setUserId(menu.getUserId());
		menuWrapper.setName(menu.getName());
		menuWrapper.setDescription(menu.getDescription().replaceAll("'", "&#39;"));
		menuWrapper.setModifiedTime(menu.getModifiedTime());
		menuWrapper.setStatus(menu.getStatus());
		menuWrapper.setImageUrl(menu.getImageUrl());
		List<Section> existingSections = menu.getSections();
		if (existingSections != null) {
			menuWrapper.sections = new ArrayList<SectionWrapper>();
			for (Section section : existingSections) {
				menuWrapper.sections.add(SectionWrapper.getSectionWrapper(section));
			}
		}
		return menuWrapper;
	}
	
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

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<SectionWrapper> getSections() {
		return sections;
	}

	public void setSections(List<SectionWrapper> sections) {
		this.sections = sections;
	}

	
}
