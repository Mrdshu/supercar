package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.RepairItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.RepairItemService;

/**
 * <p>
 * 维修服务项目controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 14:19:21
 */
@Controller
@RequestMapping("/repairItem")
public class RepairItemController extends BaseController<RepairItem>{

	@Autowired
	private RepairItemService baseService;
	
	@Override
	protected BaseService<RepairItem> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		
	}
	


}