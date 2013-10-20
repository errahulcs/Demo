/**
 * 
 */
package com.cookedspecially.domain;

import javax.persistence.Column;

import com.cookedspecially.enums.restaurant.ChargesType;

/**
 * @author shashank
 *
 */
public class RestaurantInfo {

	private Integer userId;
	private String email;
	private Integer restaurantId;
	private String businessName;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private String zip;
	private String businessPortraitImageUrl;
	private String businessLandscapeImageUrl;
	private String appCacheIconUrl;
	private String buttonIconUrl;
	private String additionalChargesName1;
	private ChargesType additionalChargesType1;
	private String additionalChargesName2;
	private ChargesType additionalChargesType2;
	private String additionalChargesName3;
	private ChargesType additionalChargesType3;
	private float additionalChargesValue1 = 0.0f;
	private float additionalChargesValue2 = 0.0f;
	private float additionalChargesValue3 = 0.0f;
	
	public RestaurantInfo(User user) {
		if (user != null) {
			this.userId = user.getUserId();
			this.restaurantId = user.getUserId();
			this.email = user.getUsername();
			this.businessName = user.getBusinessName();
			this.address1 = user.getAddress1();
			this.address2 = user.getAddress2();
			this.city = user.getCity();
			this.country = user.getCountry();
			this.zip = user.getZip();
			this.businessPortraitImageUrl = user.getBusinessPortraitImageUrl();
			this.businessLandscapeImageUrl = user.getBusinessLandscapeImageUrl();
			this.appCacheIconUrl = user.getAppCacheIconUrl();
			this.buttonIconUrl = user.getButtonIconUrl();
			this.additionalChargesName1 = user.getAdditionalChargesName1();
			this.additionalChargesName2 = user.getAdditionalChargesName2();
			this.additionalChargesName3 = user.getAdditionalChargesName3();
			this.additionalChargesType1 = user.getAdditionalChargesType1();
			this.additionalChargesType2 = user.getAdditionalChargesType2();
			this.additionalChargesType3 = user.getAdditionalChargesType3();
			this.additionalChargesValue1 = user.getAdditionalChargesValue1();
			this.additionalChargesValue2 = user.getAdditionalChargesValue2();
			this.additionalChargesValue3 = user.getAdditionalChargesValue3();
		}
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
