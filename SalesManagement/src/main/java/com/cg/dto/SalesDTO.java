package com.cg.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cg.entity.Items;

public class SalesDTO {
	
	private Integer id;
	private String custName;
	private Integer totSalesItem;
	private Double totSalesAmount;
	private List<ItemsDTO> itemsList;
	
	public SalesDTO() {
		super();
	}
	
	public SalesDTO(Integer id, String custName, Integer totSalesItem, Double totSalesAmount, List<ItemsDTO> itemsList) {
		super();
		this.id = id;
		this.custName = custName;
		this.totSalesItem = totSalesItem;
		this.totSalesAmount = totSalesAmount;
		this.itemsList = itemsList;
	}
	
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
	public List<ItemsDTO> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemsDTO> itemsList) {
		this.itemsList = itemsList;
	}

	@Override
	public String toString() {
		return "SalesDTO [id=" + id + ", custName=" + custName + ", totSalesItem=" + totSalesItem + ", totSalesAmount="
				+ totSalesAmount + ", items=" + itemsList + "]";
	}

	public void copyItemsList(List<Items> itemsList) {
		if(itemsList != null) {
			List<ItemsDTO> itemsDtoList = new ArrayList<>();
			for(Items items: itemsList) {
				ItemsDTO itemsDto = new ItemsDTO();
				BeanUtils.copyProperties(items, itemsDto);
				itemsDtoList.add(itemsDto);
			}
			this.itemsList = itemsDtoList;
		}
	}
	
	public List<Items> getItemsListFromDtoList(List<ItemsDTO> itemsDtoList) {
		List<Items> itemsList = new ArrayList<>();
		if(itemsDtoList != null) {			
			for(ItemsDTO itemsDto: itemsDtoList) {
				if(itemsDto != null) {
					itemsDto.populateTotal(itemsDto);// Populate total by (quantity * Unit price).
					Items items = new Items();
					BeanUtils.copyProperties(itemsDto, items);
					itemsList.add(items);
				}
			}
		}
		return itemsList;
	}
	
}
