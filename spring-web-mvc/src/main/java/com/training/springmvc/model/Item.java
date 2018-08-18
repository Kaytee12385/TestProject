package com.training.springmvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item  implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer ITEM_ID;
	
	@Column
	private String ITEM_NAME;
	
	@Column
	private String ITEM_DESC;
	
	@Column
	private Float ITEM_PRICE; 

	@Column
	private String ITEM_IS_ON_SALE;
	
	@Column
	private Float ITEM_DISCOUNT;
	
	private Integer ITEM_QUANTITY;
	
	private Double TOTAL_PRICE;
	
	public Double getTOTAL_PRICE() {
		return TOTAL_PRICE;
	}

	public void setTOTAL_PRICE(Double tOTAL_PRICE) {
		TOTAL_PRICE = tOTAL_PRICE;
	}

	
	
	public Integer getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(Integer iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	public void setITEM_NAME(String iTEM_NAME) {
		ITEM_NAME = iTEM_NAME;
	}

	public String getITEM_DESC() {
		return ITEM_DESC;
	}

	public void setITEM_DESC(String iTEM_DESC) {
		ITEM_DESC = iTEM_DESC;
	}

	public Float getITEM_PRICE() {
		return ITEM_PRICE;
	}

	public void setITEM_PRICE(Float iTEM_PRICE) {
		ITEM_PRICE = iTEM_PRICE;
	}

	public String getITEM_IS_ON_SALE() {
		return ITEM_IS_ON_SALE;
	}

	public void setITEM_IS_ON_SALE(String iTEM_IS_ON_SALE) {
		ITEM_IS_ON_SALE = iTEM_IS_ON_SALE;
	}

	public Float getITEM_DISCOUNT() {
		return ITEM_DISCOUNT;
	}

	public void setITEM_DISCOUNT(Float iTEM_DISCOUNT) {
		ITEM_DISCOUNT = iTEM_DISCOUNT;
	}

	public Integer getITEM_QUANTITY() {
		return ITEM_QUANTITY;
	}

	public void setITEM_QUANTITY(Integer iTEM_QUANTITY) {
		ITEM_QUANTITY = iTEM_QUANTITY;
	}

	
}
