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

import com.cookedspecially.enums.table.Status;

/**
 * @author shashank
 *
 */
@Entity
@Table(name="SEATINGTABLES")
public class SeatingTable {

	@Id
	@Column(name="SEATINGTABLEID")
	@GeneratedValue
	private Integer seatingTableId;
	
	@Column(name="RESTAURANTID")
	private Integer restaurantId;
	
	@Column(name="USERID")
	private Integer userId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SEATS")
	private Integer seats;

	@Column(name="STATUS")
	private Status status = Status.AVAILABLE;
	
	@Column(name="STARTTIME")
	private Date startTime;
	
	public Integer getSeatingTableId() {
		return seatingTableId;
	}

	public void setSeatingTableId(Integer seatingTableId) {
		this.seatingTableId = seatingTableId;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
}
