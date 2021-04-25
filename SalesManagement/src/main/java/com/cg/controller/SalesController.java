package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bean.APIResponse;
import com.cg.dto.ItemsDTO;
import com.cg.dto.SalesDTO;
import com.cg.entity.Items;
import com.cg.service.SalesService;

/*
 * This is Controller class. All Rest API's created here.  
 */
@RestController
@RequestMapping("/sales")
public class SalesController {

	@Autowired
	SalesService salesService;
	
	/*
	 * This API used to create Sales and Items data. Here all items are linked with Sales.
	 */
	@PostMapping("/create")
	public APIResponse createSales(@RequestBody final SalesDTO sales) {
		return new APIResponse(salesService.createSales(sales));
	}
	
	/*
	 * This API is used to create Items under Sales.
	 * Path variable salesId, used to link with this items.   
	 */
	@PostMapping("/{salesId}/items/create")
	public APIResponse createItems(@PathVariable("salesId") Integer salesId, @RequestBody final ItemsDTO itemsDto) {
		return new APIResponse(salesService.createItems(salesId, itemsDto));
	}
	
	/*
	 * This API is used to create and update Sales and Items. Here all items are linked with Sales.
	 */
	@PutMapping("/update")
	public APIResponse updateSales(@RequestBody final SalesDTO salesDto) {
		return new APIResponse(salesService.updateSales(salesDto));
	}
	
	/*
	 * This API is used to create and update Items.
	 */
	@PutMapping("/items/update")
	public APIResponse updateItems(@RequestBody final ItemsDTO itemsDto) {
		return new APIResponse(salesService.updateItems(itemsDto));
	}

	/*
	 * This API is used to get all sales data.
	 */
	@GetMapping("/get")
	public APIResponse getSales() {
		return new APIResponse(salesService.getSales());
	}
	
	/*
	 * This API is used to get all Items data.
	 */
	@GetMapping("/items/get")
	public APIResponse getItems() {
		return new APIResponse(salesService.getItems());
	}

	/*
	 * This API is used to delete particular Sales and all Items under this child.
	 */
	@DeleteMapping("/delete/{id}")
	public APIResponse deleteSales(@PathVariable("id") Integer id) {
		salesService.deleteSales(id);
		return new APIResponse("");
	}
	
	/*
	 * This API is used to delete particular Items.
	 */
	@DeleteMapping("/items/delete/{id}")
	public APIResponse deleteItems(@PathVariable("id") Integer id) {
		salesService.deleteItems(id);
		return new APIResponse("");
	}
}
