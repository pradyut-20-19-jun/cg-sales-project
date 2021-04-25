package com.cg.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "items")
@JsonIgnoreProperties(value = {"createdOn", "updatedOn"}, allowGetters = false)
public class Items {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="qty" )
	private Integer qty;
	
	@Column(name="unit_price" )
	private Double unitprice;
	
	@Column(name="total" )
	private Double total;
	
	@Column(name="created_on", nullable = false, updatable = false )
	@CreationTimestamp
	private Date createdOn;
	
	@Column(name="updated_on")
	@UpdateTimestamp
	private Date updatedOn; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "header_id", referencedColumnName = "id")
    private Sales sales;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
	/*
	public void populateToUpdate(Items items) {
		if(items != null) {
			this.name = items.getName();			
			this.qty = items.getQty();
			this.unitprice = items.getUnitprice();
			this.total = items.getTotal();
		}
	}

	public void populateTotal(Items items) {
		if((this.total == null || this.total == 0) && items != null 
				&& items.getQty() != null && items.getQty() > 0
				&& items.getUnitprice() != null && items.getUnitprice() > 0) {
			
			this.total = items.getQty() * items.getUnitprice();
		}
		
	}*/
}
