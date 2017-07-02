package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.InventoryService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.PartService;

@Controller
@RequestMapping("/inventory")
public class InventoryController extends BaseController<Inventory>{
	@Autowired
	private InventoryService service;
	
	@Override
	protected BaseService<Inventory> getSevice() {
		return service;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{Inventory.DP.supplierLK.name(),Inventory.DP.repCodeLK.name()
				,Inventory.DP.company.name(),Inventory.DP.partId.name()}
		, new Class[]{LookupService.class,LookupService.class,CompanyService.class,PartService.class});
	}
}
