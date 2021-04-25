package com.cg.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import com.cg.dto.ItemsDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "header")
@JsonIgnoreProperties(value = {"createdOn", "updatedOn"}, allowGetters = false)
public class Sales {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name="cust_name" )
	private String custName;
	
	@Column(name="tot_item_sale" )
	private Integer totSalesItem;
	
	@Column(name="tot_amount_sale" )
	private Double totSalesAmount;
	
	@Column(name="created_on", nullable = false, updatable = false )
	@CreationTimestamp
	private Date createdOn;
	
	@Column(name="updated_on" )
	@UpdateTimestamp
	private Date updatedOn;
	
	@OneToMany(targetEntity = Items.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "header_id")
    private List<Items> itemsList = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Integer getTotSalesItem() {
		return totSalesItem;
	}
	public void setTotSalesItem(Integer totSalesItem) {
		this.totSalesItem = totSalesItem;
	}
	public Double getTotSalesAmount() {
		return totSalesAmount;
	}
	public void setTotSalesAmount(Double totSalesAmount) {
		this.totSalesAmount = totSalesAmount;
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
	
	public List<Items> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<Items> itemsList) {
		this.itemsList = itemsList;
	}
	
	public void copyItemsDtoList(List<ItemsDTO> itemsDtoList) {
		if(itemsDtoList != null) {
			List<Items> itemsList = new ArrayList<>();
			for(ItemsDTO itemsDto: itemsDtoList) {
				if(itemsDto != null) {
					itemsDto.populateTotal(itemsDto);// Populate total by (quantity * Unit price).
					Items items = new Items();
					BeanUtils.copyProperties(itemsDto, items);
					itemsList.add(items);
				}
			}
			this.itemsList = itemsList;
		}
	}
	

}
