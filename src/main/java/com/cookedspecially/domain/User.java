/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.List;

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

import com.cookedspecially.enums.restaurant.ChargesType;

/**
 * @author sagarwal
 *
 */
@Entity
@Table(name="USERS")
public class User {

	@Id
	@Column(name="USERID")
	@GeneratedValue
	private Integer userId;

	@Column(name="USERNAME")
	private String username;
	
	
	@Column(name="PASSWORDHASH")
	private String passwordHash;
	

	@Column(name="FIRSTNAME")
	private String firstName;
	
	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="BUSINESSNAME")
	private String businessName;
	
	@Column(name="BUSINESSPORTRAITIMAGEURL")
	private String businessPortraitImageUrl;
	
	@Column(name="BUSINESSLANDSCAPEIMAGEURL")
	private String businessLandscapeImageUrl;
	
	@Column(name="additionalChargesName1")
	private String additionalChargesName1;
	
	@Column(name="additionalChargesType1")
	private ChargesType additionalChargesType1;
	
	@Column(name="additionalChargesName2")
	private String additionalChargesName2;
	
	@Column(name="additionalChargesType2")
	private ChargesType additionalChargesType2;
	
	@Column(name="additionalChargesName3")
	private String additionalChargesName3;
	
	@Column(name="additionalChargesType3")
	private ChargesType additionalChargesType3;
	
	@Column(name="additionalChargesValue1")
	private int additionalChargesValue1 = 0;
	
	@Column(name="additionalChargesValue2")
	private int additionalChargesValue2 = 0;
	
	@Column(name="additionalChargesValue3")
	private int additionalChargesValue3 = 0;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="USER_RESTAURANT", 
                joinColumns={@JoinColumn(name="USERID")}, 
                inverseJoinColumns={@JoinColumn(name="RESTAURANTID")})
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public String getBusinessPortraitImageUrl() {
		return businessPortraitImageUrl;
	}

	public void setBusinessPortraitImageUrl(String businessPortraitImageUrl) {
		this.businessPortraitImageUrl = businessPortraitImageUrl;
	}

	public String getBusinessLandscapeImageUrl() {
		return businessLandscapeImageUrl;
	}

	public void setBusinessLandscapeImageUrl(String businessLandscapeImageUrl) {
		this.businessLandscapeImageUrl = businessLandscapeImageUrl;
	}

	public final String getAdditionalChargesName1() {
		return additionalChargesName1;
	}

	public final void setAdditionalChargesName1(String additionalChargesName1) {
		this.additionalChargesName1 = additionalChargesName1;
	}

	public final ChargesType getAdditionalChargesType1() {
		return additionalChargesType1;
	}

	public final void setAdditionalChargesType1(ChargesType additionalChargesType1) {
		this.additionalChargesType1 = additionalChargesType1;
	}

	public final String getAdditionalChargesName2() {
		return additionalChargesName2;
	}

	public final void setAdditionalChargesName2(String additionalChargesName2) {
		this.additionalChargesName2 = additionalChargesName2;
	}

	public final ChargesType getAdditionalChargesType2() {
		return additionalChargesType2;
	}

	public final void setAdditionalChargesType2(ChargesType additionalChargesType2) {
		this.additionalChargesType2 = additionalChargesType2;
	}

	public final String getAdditionalChargesName3() {
		return additionalChargesName3;
	}

	public final void setAdditionalChargesName3(String additionalChargesName3) {
		this.additionalChargesName3 = additionalChargesName3;
	}

	public final ChargesType getAdditionalChargesType3() {
		return additionalChargesType3;
	}

	public final void setAdditionalChargesType3(ChargesType additionalChargesType3) {
		this.additionalChargesType3 = additionalChargesType3;
	}

	public final int getAdditionalChargesValue1() {
		return additionalChargesValue1;
	}

	public final void setAdditionalChargesValue1(int additionalChargesValue1) {
		this.additionalChargesValue1 = additionalChargesValue1;
	}

	public final int getAdditionalChargesValue2() {
		return additionalChargesValue2;
	}

	public final void setAdditionalChargesValue2(int additionalChargesValue2) {
		this.additionalChargesValue2 = additionalChargesValue2;
	}

	public final int getAdditionalChargesValue3() {
		return additionalChargesValue3;
	}

	public final void setAdditionalChargesValue3(int additionalChargesValue3) {
		this.additionalChargesValue3 = additionalChargesValue3;
	}

	
}
