/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.hibernate.FetchMode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.cookedspecially.enums.check.PaymentMode;
import com.cookedspecially.enums.check.Status;

/**
 * @author shashank
 *
 */
@Entity
@Table(name="CHECKS")
public class Check {

	@Id
	@Column(name="ID")
	@GeneratedValue
	private Integer checkId;
	
	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="TABLEID")
	private Integer tableId;
	
	@Column(name="CUSTOMERID")
	private Integer customerId;
	
	@Column(name="GUESTS")
	private Integer guests;
	
	@Column(name="PAYMENT")
	private PaymentMode payment;
	
	@Column(name="STATUS")
	private Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OPENTIME")
	private Date openTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLOSETIME")
	private Date closeTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIEDTIME")
	private Date modifiedTime;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(orphanRemoval=true)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.REMOVE})
	@JoinTable(name="CHECK_ORDER", 
	                joinColumns={@JoinColumn(name="CHECKID")}, 
	                inverseJoinColumns={@JoinColumn(name="ORDERID")})
	private List<Order> orders = new ArrayList<Order>();

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
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

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public PaymentMode getPayment() {
		return payment;
	}

	public void setPayment(PaymentMode payment) {
		this.payment = payment;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Integer getGuests() {
		return guests;
	}

	public void setGuests(Integer guests) {
		this.guests = guests;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	
}
