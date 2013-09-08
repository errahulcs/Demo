/**
 * 
 */
package com.cookedspecially.domain;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author shashank
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonOrder implements Serializable {

	public int checkId;
	public int number;
	public int tableId;
	public int custId;
	public float price;
	
	public List<JsonDish> items;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public List<JsonDish> getItems() {
		return items;
	}
	public void setItems(List<JsonDish> items) {
		this.items = items;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	
}
