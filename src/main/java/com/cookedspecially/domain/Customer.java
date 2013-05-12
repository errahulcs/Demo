/**
 * 
 */
package com.cookedspecially.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author shashank
 *
 */
@Entity
@Table(name="CUSTOMERS")
public class Customer {

	@Id
	@Column(name="CUSTOMERID")
	@GeneratedValue
	private Integer customerId;
	
	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="FIRSTNAME")
	private String firstName;
	
	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="EMAIL")
	private String email;

	@Column(name="NUMBEROFORDERS")
	private Long numberOfOrders;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDTIME")
	private Date createdTime;
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(Long numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}
	
	
}
