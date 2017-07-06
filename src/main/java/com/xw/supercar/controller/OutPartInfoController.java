package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InventoryService;
import com.xw.supercar.service.OutPartInfoService;
import com.xw.supercar.service.PartService;

@Controller
@RequestMapping("/outPartInfo")
public class OutPartInfoController extends BaseController<OutPartInfo>{
	@Autowired
	private OutPartInfoService service;
	
	@Override
	protected BaseService<OutPartInfo> getSevice() {
		return service;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将外键对应的实体放入data
		addAttributesToData(data, new String[]{OutPartInfo.DP.inventoryId.name()}
		, new Class[]{InventoryService.class});
		//将维修工单对应的维修项目查询放入data
		//TODO
	}
	

}
