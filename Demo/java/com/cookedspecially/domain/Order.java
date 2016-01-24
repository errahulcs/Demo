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

import org.hibernate.annotations.Cascade;

import com.cookedspecially.enums.order.DestinationType;
import com.cookedspecially.enums.order.SourceType;
import com.cookedspecially.enums.order.Status;

/**
 * @author shashank
 *
 */
@Entity
@Table(name="ORDERS")
public class Order {

	@Id
	@Column(name="ORDERID")
	@GeneratedValue
	private Integer orderId;

	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;

	@Column(name="CHECKID")
	private Integer checkId;
	
	@Column(name="SOURCETYPE")
	private SourceType sourceType;
	
	/*
	 * This will be: 
	 * -> TableId if order from table
	 * -> CustomerId if order from online/phone
	 * -> Can be CustomerId if order from Counter
	 */
	@Column(name="SOURCEID")
	private Integer sourceId;
	
	@Column(name="DESTINATIONTYPE")
	private DestinationType destinationType;
	
	/*
	 * This will be : 
	 * -> CustomerId if order has to be delivered at home
	 * -> TableId if it has to be delivered at Table
	 * -> NULL if on counter 
	 */
	@Column(name="DESTINATIONID")
	private Integer destinationId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDTIME")
	private Date createdTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIEDTIME")
	private Date modifiedTime;
	
	@Column(name="BILL")
	private Float bill;
	
	@Column(name="PAID")
	private Float paid;
	
	@Column(name="STATUS")
	private Status status;

	@OneToMany(fetch=FetchType.EAGER, orphanRemoval=true)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.REMOVE})
	//@IndexColumn(name="SECTIONPOSITION")
	@JoinTable(name="ORDER_ORDERDISH", 
	                joinColumns={@JoinColumn(name="ORDERID")}, 
	                inverseJoinColumns={@JoinColumn(name="ORDERDISHID")})
	private List<OrderDish> orderDishes = new ArrayList<OrderDish>();

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Float getBill() {
		return bill;
	}

	public void setBill(Float bill) {
		this.bill = bill;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public DestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Float getPaid() {
		return paid;
	}

	public void setPaid(Float paid) {
		this.paid = paid;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<OrderDish> getOrderDishes() {
		return orderDishes;
	}

	public void setOrderDishes(List<OrderDish> orderDishes) {
		this.orderDishes = orderDishes;
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

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	
	

}
