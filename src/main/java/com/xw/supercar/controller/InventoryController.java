package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Inventory;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InventoryService;

@Controller
@RequestMapping("/inventory")
public class InventoryController extends BaseController<Inventory>{
	@Autowired
	private InventoryService service;
	
	@Override
	protected BaseService<Inventory> getSevice() {
		return service;
	}

}
