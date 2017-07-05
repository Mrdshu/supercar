package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.RepairWorkorderItemService;

/**
 * <p>
 * 维修工单-服务项目controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 14:19:21
 */
@Controller
@RequestMapping("/repairWorkorderItem")
public class RepairWorkorderItemController extends BaseController<RepairWorkorderItem>{

	@Autowired
	private RepairWorkorderItemService baseService;
	
	@Override
	protected BaseService<RepairWorkorderItem> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		
	}
	


}