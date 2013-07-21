/**
 * 
 */
package com.cookedspecially.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cookedspecially.enums.WeekDayFlags;

/**
 * @author sagarwal
 *
 */
@Entity
@Table(name="DISHES")
public class Dish {

	@Id
	@Column(name="DISHID")
	@GeneratedValue
	private Integer dishId;
	
	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SHORTDESCRIPTION")
	private String shortDescription;
	
	@Column(name="IMAGEURL")
	private String imageUrl;
	
	@Column(name="PRICE")
	private Float price;
	

	@Column(name="DISHTYPE")
	private String dishType;
	
	@Column(name="VEGETARIAN")
	private boolean vegetarian;
	
	@Column(name="ALCOHOLIC")
	private boolean alcoholic;
	
	@Column(name="DISABLED")
	private boolean disabled;
	
	@Column(name="ACTIVEDAYS")
	private int activeDays;
	
	@Column(name="HAPPYHOURENABLED")
	private boolean happyHourEnabled;
	
	@Column(name="HAPPYHOURDAYS")
	private int happyHourDays;
	
	@Column(name="HAPPYHOURSTARTHOUR")
	private int happyHourStartHour;
	
	@Column(name="HAPPYHOURSTARTMIN")
	private int happyHourStartMin;
	
	@Column(name="HAPPYHOURENDHOUR")
	private int happyHourEndHour;
	
	@Column(name="HAPPYHOURENDMIN")
	private int happyHourEndMin;
	
	@Column(name="HAPPYHOURPRICE")
	private Float happyHourPrice;
	
	@Transient
	private boolean[] dishActiveDays = new boolean[7];
	
	@Transient
	private boolean[] happyHourActiveDays = new boolean[7];
	
	public Integer getDishId() {
		return dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	public boolean getVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public boolean getAlcoholic() {
		return alcoholic;
	}

	public void setAlcoholic(boolean alcoholic) {
		this.alcoholic = alcoholic;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public int getActiveDays() {
		return activeDays;
	}

	public void setActiveDays(int activeDays) {
		this.activeDays = activeDays;
	}

	public boolean getHappyHourEnabled() {
		return happyHourEnabled;
	}

	public void setHappyHourEnabled(boolean happyHourEnabled) {
		this.happyHourEnabled = happyHourEnabled;
	}

	public int getHappyHourDays() {
		return happyHourDays;
	}

	public void setHappyHourDays(int happyHourDays) {
		this.happyHourDays = happyHourDays;
	}

	public int getHappyHourStartHour() {
		return happyHourStartHour;
	}

	public void setHappyHourStartHour(int happyHourStartHour) {
		this.happyHourStartHour = happyHourStartHour;
	}

	public int getHappyHourStartMin() {
		return happyHourStartMin;
	}

	public void setHappyHourStartMin(int happyHourStartMin) {
		this.happyHourStartMin = happyHourStartMin;
	}

	public int getHappyHourEndHour() {
		return happyHourEndHour;
	}

	public void setHappyHourEndHour(int happyHourEndHour) {
		this.happyHourEndHour = happyHourEndHour;
	}

	public int getHappyHourEndMin() {
		return happyHourEndMin;
	}

	public void setHappyHourEndMin(int happyHourEndMin) {
		this.happyHourEndMin = happyHourEndMin;
	}

	public Float getHappyHourPrice() {
		return happyHourPrice;
	}

	public void setHappyHourPrice(Float happyHourPrice) {
		this.happyHourPrice = happyHourPrice;
	}

	public boolean[] getDishActiveDays() {
		return dishActiveDays;
	}

	public void setDishActiveDays(boolean[] dishActiveDays) {
		this.dishActiveDays = dishActiveDays;
	}

	public boolean[] getHappyHourActiveDays() {
		return happyHourActiveDays;
	}

	public void setHappyHourActiveDays(boolean[] happyHourActiveDays) {
		this.happyHourActiveDays = happyHourActiveDays;
	}
	
	public boolean isDishActive() {
		Calendar cal = Calendar.getInstance();
		String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
		HashSet<String> weekdayFlags = WeekDayFlags.getWeekDayFlags(getActiveDays());
		if (weekdayFlags.contains(dayOfWeek)) {
			return true;
		}
		return false;
	}

	public Float getPriceByHappyHour() {
		if (getHappyHourEnabled()) {
			Calendar cal = Calendar.getInstance();
			String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
			HashSet<String> weekdayFlags = WeekDayFlags.getWeekDayFlags(getHappyHourDays());
			if (weekdayFlags.contains(dayOfWeek)) {
				int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
				int minOfHour = cal.get(Calendar.MINUTE);
				if ( (hourOfDay > getHappyHourStartHour() || (hourOfDay == getHappyHourStartHour() && minOfHour >= getHappyHourStartMin()) && (hourOfDay < getHappyHourEndHour() || (hourOfDay == getHappyHourEndHour() && minOfHour < getHappyHourEndMin()))) ) {
					return getHappyHourPrice();
				}
			}
		}
		return getPrice();
	}
}
