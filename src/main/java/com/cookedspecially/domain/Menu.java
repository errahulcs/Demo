/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.IndexColumn;

import com.cookedspecially.enums.Status;

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
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIEDTIME")
	private Date modifiedTime;
	
	
	@Column(name="STATUS")
	private Status status = Status.ACTIVE;
	
	@Column(name="IMAGEURL")
	private String imageUrl;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@IndexColumn(name="SECTIONPOSITION")
    @JoinTable(name="MENU_SECTION", 
                joinColumns={@JoinColumn(name="MENUID")}, 
                inverseJoinColumns={@JoinColumn(name="SECTIONID")})
    private List<Section> sections = new ArrayList<Section>();
	
	
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
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



	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
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
	
}
