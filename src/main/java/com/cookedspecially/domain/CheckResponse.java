/**
 * 
 */
package com.cookedspecially.domain;

import java.util.ArrayList;
import java.util.List;

import com.cookedspecially.enums.check.Status;

/**
 * @author shashank
 *
 */
public class CheckResponse {

	private Integer id;
	private Integer tableId;
	private Integer customerId;
	private Integer guests;
	private Status status;
	private float amount;
	private String additionalChargeName1;
	private String additionalChargeName2;
	private String additionalChargeName3;
	private float additionalCharge1;
	private float additionalCharge2;
	private float additionalCharge3;
	private float total;
	private List<CheckDishResponse> items;
	
	public CheckResponse(Check check) {
		this.id = check.getCheckId();
		this.tableId = check.getTableId();
		this.customerId = check.getCustomerId();
		this.guests = check.getGuests();
		this.status = check.getStatus();
		this.amount = check.getBill();
		this.additionalChargeName1 = check.getAdditionalChargesName1();
		this.additionalChargeName2 = check.getAdditionalChargesName2();
		this.additionalChargeName3 = check.getAdditionalChargesName3();
		this.additionalCharge1 = check.getAdditionalChargesValue1();
		this.additionalCharge2 = check.getAdditionalChargesValue2();
		this.additionalCharge3 = check.getAdditionalChargesValue3();
		this.total = this.amount + this.additionalCharge1 + this.additionalCharge2 + this.additionalCharge3;
		this.items = new ArrayList<CheckDishResponse>();
		
		List<Order> orders = check.getOrders();
		if (orders != null) {
			for(Order order : orders) {
				List<OrderDish> orderDishes = order.getOrderDishes();
				if (orderDishes != null) {
					for (OrderDish orderDish : orderDishes) {
						items.add(new CheckDishResponse(orderDish));
					}
				}
 			}
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public Integer getGuests() {
		return guests;
	}
	public void setGuests(Integer guests) {
		this.guests = guests;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getAdditionalChargeName1() {
		return additionalChargeName1;
	}
	public void setAdditionalChargeName1(String additionalChargeName1) {
		this.additionalChargeName1 = additionalChargeName1;
	}
	public String getAdditionalChargeName2() {
		return additionalChargeName2;
	}
	public void setAdditionalChargeName2(String additionalChargeName2) {
		this.additionalChargeName2 = additionalChargeName2;
	}
	public String getAdditionalChargeName3() {
		return additionalChargeName3;
	}
	public void setAdditionalChargeName3(String additionalChargeName3) {
		this.additionalChargeName3 = additionalChargeName3;
	}
	public float getAdditionalCharge1() {
		return additionalCharge1;
	}
	public void setAdditionalCharge1(float additionalCharge1) {
		this.additionalCharge1 = additionalCharge1;
	}
	public float getAdditionalCharge2() {
		return additionalCharge2;
	}
	public void setAdditionalCharge2(float additionalCharge2) {
		this.additionalCharge2 = additionalCharge2;
	}
	public float getAdditionalCharge3() {
		return additionalCharge3;
	}
	public void setAdditionalCharge3(float additionalCharge3) {
		this.additionalCharge3 = additionalCharge3;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public List<CheckDishResponse> getItems() {
		return items;
	}
	public void setItems(List<CheckDishResponse> items) {
		this.items = items;
	}
	
}
