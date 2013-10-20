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
	
	@Column(name="ADDRESS1")
	private String address1;
	
	@Column(name="ADDRESS2")
	private String address2;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="ZIP")
	private String zip;
	
	@Column(name="BUSINESSPORTRAITIMAGEURL")
	private String businessPortraitImageUrl;
	
	@Column(name="BUSINESSLANDSCAPEIMAGEURL")
	private String businessLandscapeImageUrl;
	
	@Column(name="APPCACHEICONURL")
	private String appCacheIconUrl;
	
	@Column(name="BUTTONICONURL")
	private String buttonIconUrl;
	
	
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
	private float additionalChargesValue1 = 0.0f;
	
	@Column(name="additionalChargesValue2")
	private float additionalChargesValue2 = 0.0f;
	
	@Column(name="additionalChargesValue3")
	private float additionalChargesValue3 = 0.0f;
	
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

	public String getAdditionalChargesName1() {
		return additionalChargesName1;
	}

	public void setAdditionalChargesName1(String additionalChargesName1) {
		this.additionalChargesName1 = additionalChargesName1;
	}

	public ChargesType getAdditionalChargesType1() {
		return additionalChargesType1;
	}

	public void setAdditionalChargesType1(ChargesType additionalChargesType1) {
		this.additionalChargesType1 = additionalChargesType1;
	}

	public String getAdditionalChargesName2() {
		return additionalChargesName2;
	}

	public void setAdditionalChargesName2(String additionalChargesName2) {
		this.additionalChargesName2 = additionalChargesName2;
	}

	public ChargesType getAdditionalChargesType2() {
		return additionalChargesType2;
	}

	public void setAdditionalChargesType2(ChargesType additionalChargesType2) {
		this.additionalChargesType2 = additionalChargesType2;
	}

	public String getAdditionalChargesName3() {
		return additionalChargesName3;
	}

	public void setAdditionalChargesName3(String additionalChargesName3) {
		this.additionalChargesName3 = additionalChargesName3;
	}

	public ChargesType getAdditionalChargesType3() {
		return additionalChargesType3;
	}

	public void setAdditionalChargesType3(ChargesType additionalChargesType3) {
		this.additionalChargesType3 = additionalChargesType3;
	}

	public float getAdditionalChargesValue1() {
		return additionalChargesValue1;
	}

	public void setAdditionalChargesValue1(float additionalChargesValue1) {
		this.additionalChargesValue1 = additionalChargesValue1;
	}

	public float getAdditionalChargesValue2() {
		return additionalChargesValue2;
	}

	public void setAdditionalChargesValue2(float additionalChargesValue2) {
		this.additionalChargesValue2 = additionalChargesValue2;
	}

	public float getAdditionalChargesValue3() {
		return additionalChargesValue3;
	}

	public void setAdditionalChargesValue3(float additionalChargesValue3) {
		this.additionalChargesValue3 = additionalChargesValue3;
	}
	
	public float getAdditionalCharge(float bill, ChargesType chargeType, float additionalChargeValue) {
		float retVal = 0;
		if (chargeType == ChargesType.RAW) {
			retVal = additionalChargeValue;
		} else if (chargeType == ChargesType.PERCENT) {
			retVal = bill * additionalChargeValue / 100;
		}
		
		return retVal;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAppCacheIconUrl() {
		return appCacheIconUrl;
	}

	public void setAppCacheIconUrl(String appCacheIconUrl) {
		this.appCacheIconUrl = appCacheIconUrl;
	}

	public String getButtonIconUrl() {
		return buttonIconUrl;
	}

	public void setButtonIconUrl(String buttonIconUrl) {
		this.buttonIconUrl = buttonIconUrl;
	}
}
