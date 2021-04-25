package com.cg.dto;

import com.cg.entity.Items;

public class ItemsDTO {

	private Integer id;
	private String name;
	private Integer qty;
	private Double unitprice;
	private Double total;
	private SalesDTO sales;
	
	public ItemsDTO() {
		super();
	}

	public ItemsDTO(Integer id, String name, Integer qty, Double unitprice, Double total) {
		super();
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.unitprice = unitprice;
		this.total = total;
	}

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

	public SalesDTO getSales() {
		return sales;
	}

	public void setSales(SalesDTO sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "ItemsDTO [id=" + id + ", name=" + name + ", qty=" + qty + ", unitprice=" + unitprice + ", total="
				+ total + "]";
	}
	
	/*
	 *  Populate total by (quantity * Unit price)
	 */
	public void populateTotal(ItemsDTO itemsDto) {
		if((this.total == null || this.total == 0) && itemsDto != null 
				&& itemsDto.getQty() != null && itemsDto.getQty() > 0
				&& itemsDto.getUnitprice() != null && itemsDto.getUnitprice() > 0) {
			
			this.total = itemsDto.getQty() * itemsDto.getUnitprice();
		}
		
	}
}
