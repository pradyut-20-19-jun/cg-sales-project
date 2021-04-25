package com.cg.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.cg.dto.ItemsDTO;
import com.cg.dto.SalesDTO;
import com.cg.entity.Sales;

public interface SalesService {

	public SalesDTO createSales(SalesDTO salesDto);
	public ItemsDTO createItems(Integer salesId, ItemsDTO itemsDto);
	public SalesDTO updateSales(SalesDTO salesDto);
	public ItemsDTO updateItems(ItemsDTO itemsDto);
	public List<SalesDTO> getSales();
	public List<ItemsDTO> getItems();
	public void deleteSales(Integer id);
	public void deleteItems(Integer id);
}
