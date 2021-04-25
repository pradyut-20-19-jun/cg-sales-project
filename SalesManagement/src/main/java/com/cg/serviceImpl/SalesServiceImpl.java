package com.cg.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dto.ItemsDTO;
import com.cg.dto.SalesDTO;
import com.cg.entity.Items;
import com.cg.entity.Sales;
import com.cg.exceptions.RecordNotFondException;
import com.cg.repository.ItemsRepository;
import com.cg.repository.SalesRepository;
import com.cg.service.SalesService;
/*
 * This class used to write all business logic.
 */
@Service
public class SalesServiceImpl implements SalesService {

	private final Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);
	
	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	ItemsRepository itemsRepository;
	
	/*
	 * This method used to create Sales and Items data. Here all items are linked with Sales.
	 */
	@Override
	public SalesDTO createSales(SalesDTO salesDto) {
		
		/**	Copy from DTO to Entity **/
		Sales sales = new Sales();
		BeanUtils.copyProperties(salesDto, sales);
		if(salesDto.getItemsList() != null && !salesDto.getItemsList().isEmpty()) {
			sales.copyItemsDtoList(salesDto.getItemsList());
			
			int totSalesItem = salesDto.getItemsList().stream().filter(f -> f.getQty() != null).mapToInt(ItemsDTO::getQty).sum();
			sales.setTotSalesItem(totSalesItem);
			
			double totSalesAmount = salesDto.getItemsList().stream().filter(f -> f.getTotal() != null).mapToDouble(ItemsDTO::getTotal).sum();
			sales.setTotSalesAmount(totSalesAmount);
		}
		sales = salesRepository.save(sales);
		
		SalesDTO newSalesDto = new SalesDTO();
		BeanUtils.copyProperties(sales, newSalesDto);
		newSalesDto.copyItemsList(sales.getItemsList());
		
		return newSalesDto;
	}

	/*
	 * This method is used to create Items under Sales.
	 * Path variable salesId, used to link with this items.   
	 */
	@Override
	public ItemsDTO createItems(Integer salesId, ItemsDTO itemsDto) {
		ItemsDTO newItemsDto = null;
		Sales oldSales = null;
		SalesDTO salesDto = null;
		if(salesId != null && salesId > 0 && itemsDto != null) {
			itemsDto.populateTotal(itemsDto);// Populate total by (quantity * Unit price).
			
			oldSales = salesRepository.findById(salesId).orElseThrow(() -> new RecordNotFondException("Sales record not found."));
			
			Items items = new Items();
			BeanUtils.copyProperties(itemsDto, items);
			oldSales.setTotSalesItem( oldSales.getTotSalesItem() + itemsDto.getQty());
			oldSales.setTotSalesAmount( oldSales.getTotSalesAmount() + itemsDto.getTotal());
			items.setSales(oldSales);
			items = itemsRepository.save(items);
			
			newItemsDto = new ItemsDTO();
			BeanUtils.copyProperties(items, newItemsDto);
			
			salesDto = new SalesDTO();
			BeanUtils.copyProperties(items.getSales(), salesDto);
			newItemsDto.setSales(salesDto);
			
		}
		return newItemsDto;
	}

	/*
	 * This method is used to create and update Sales and Items. Here all items are linked with Sales.
	 */
	@Transactional
	@Override
	public SalesDTO updateSales(SalesDTO salesDto) {
		SalesDTO updatedSalesDto = null;
		Sales oldSales = null;
		Sales updatedSales = null;
		List<Items> updatedItemsList = new ArrayList<>();
		List<Items> itemsList = null;
		try {
			logger.info("Updating starts..");
			if(salesDto != null && salesDto.getId() != null && salesDto.getId() > 0) {
				
				//START:	Child records updating here.
				oldSales = salesRepository.findById(salesDto.getId()).orElseThrow(() -> new RecordNotFondException());
				
				if(salesDto.getItemsList() != null && !salesDto.getItemsList().isEmpty()) {
					itemsList = salesDto.getItemsListFromDtoList(salesDto.getItemsList());
					for(Items items : itemsList) {
						items.setSales(oldSales);
						updatedItemsList.add(itemsRepository.save(items));
					}					
				}
				//END:	Child records updating here.
				
				
				//START:	Parent record updating here with total sales count & total sales amount.
				if(oldSales.getItemsList() != null && !salesDto.getItemsList().isEmpty()) {
					List<Integer> updatedItemsIdList = updatedItemsList.stream().map(Items::getId).collect(Collectors.toList());
					List<Items> oldItemsList = oldSales.getItemsList().stream()
													.filter(f -> !updatedItemsIdList.contains(f.getId()))
													.collect(Collectors.toList());
					updatedItemsList.addAll(oldItemsList);
					int totSalesItem = updatedItemsList.stream().filter(f -> f.getQty() != null).mapToInt(Items::getQty).sum();
					salesDto.setTotSalesItem(totSalesItem);
					
					double totSalesAmount = updatedItemsList.stream().filter(f -> f.getTotal() != null).mapToDouble(Items::getTotal).sum();
					salesDto.setTotSalesAmount(totSalesAmount);
				}
				BeanUtils.copyProperties(salesDto, oldSales, "id");
				updatedSales = salesRepository.save(oldSales);
				//END:	Parent record updating here with total sales count & total sales amount.
				
				
				//START:	Setting data to DTO for API response.
				updatedSalesDto = new SalesDTO();
				BeanUtils.copyProperties(updatedSales, updatedSalesDto);
				updatedSalesDto.copyItemsList(updatedItemsList);
				//END:	Setting data to DTO for API response.
				
			} else {
				logger.error("Sales id not found.");
			}
			logger.info("Updating ends..");
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
			throw e;
		} finally {
			oldSales = null;
			updatedSales = null;
			updatedItemsList = null;
			itemsList = null;
		}
		return updatedSalesDto;
	}
	
	/*
	 * This method is used to create and update Items.
	 */
	@Transactional
	@Override
	public ItemsDTO updateItems(ItemsDTO itemsDto) {
		Items oldItems = null;
		Sales sales = null;
		logger.info("Items updating starts..");
		if(itemsDto != null && itemsDto.getId() != null && itemsDto.getId() > 0) {
			itemsDto.populateTotal(itemsDto);
			
			oldItems = itemsRepository.findById(itemsDto.getId()).orElseThrow(() -> new RecordNotFondException());
			if(itemsDto.getQty() != null && (!itemsDto.getQty().equals(oldItems.getQty()) || !itemsDto.getTotal().equals(oldItems.getTotal()))) {
				sales = oldItems.getSales();
				int totalSalesItem = sales.getTotSalesItem() + (itemsDto.getQty() - oldItems.getQty());
				sales.setTotSalesItem(totalSalesItem);
				
				double totalSalesAmount = sales.getTotSalesAmount() + (itemsDto.getTotal() - oldItems.getTotal());
				sales.setTotSalesAmount(totalSalesAmount);
			}
			BeanUtils.copyProperties(itemsDto, oldItems, "id");
			oldItems = itemsRepository.save(oldItems);
			
			if(sales != null) {
				salesRepository.save(sales);
			}
			
			BeanUtils.copyProperties(oldItems, itemsDto);
			
		} else {
			logger.error("Sales id not found.");
		}
		logger.info("Updating ends..");
		return itemsDto;
	}

	/*
	 * This method is used to get all sales data.
	 */
	@Override
	public List<SalesDTO> getSales() {
		List<SalesDTO> salesListDto = new ArrayList<>();
		List<Sales> salesList = salesRepository.findAll();
		if(salesList != null && !salesList.isEmpty()) {
			for(Sales sales : salesList) {
				SalesDTO salesDto = new SalesDTO();
				BeanUtils.copyProperties(sales, salesDto);
				salesDto.copyItemsList(sales.getItemsList());
				salesListDto.add(salesDto);
			}
		}
		salesList = null;			
		return salesListDto;
	}

	/*
	 * This method is used to get all Items data.
	 */
	@Override
	public List<ItemsDTO> getItems() {
		List<ItemsDTO> itemsListDto = new ArrayList<>();
		List<Items> itemsList = null;
		ItemsDTO itemsDto = null;
		SalesDTO salesDto = null;
		try {
			itemsList = itemsRepository.findAll();
			if(itemsList != null && !itemsList.isEmpty()) {
				for(Items items : itemsList) {
					itemsDto = new ItemsDTO();
					BeanUtils.copyProperties(items, itemsDto);
					
					if(items.getSales() != null) {
						salesDto = new SalesDTO();
						BeanUtils.copyProperties(items.getSales(), salesDto);
						itemsDto.setSales(salesDto);
					}										 
					itemsListDto.add(itemsDto);
				}
			}
		} finally {
			itemsList = null;
			itemsDto = null;
			salesDto = null;
		}
		return itemsListDto;
	}

	@Override
	public void deleteSales(Integer id) {
		Sales sales = null;
		try {
			if(id != null && id > 0) {
				sales = salesRepository.findById(id).orElseThrow(() -> new RecordNotFondException());
				salesRepository.delete(sales);
			} else {
				logger.error("Id not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			sales = null;
		}		
	}
	
	@Override
	public void deleteItems(Integer id) {
		Items items = null;
		try {
			if(id != null && id > 0) {
				items = itemsRepository.findById(id).orElseThrow(() -> new RecordNotFondException());;
				itemsRepository.delete(items);
			} else {
				logger.error("Id not found.");
			}
		} finally {
			items = null;
		}		
	}

}
