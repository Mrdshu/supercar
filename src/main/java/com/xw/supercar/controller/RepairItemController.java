package com.xw.supercar.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.RepairItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.RepairItemService;

/**
 * <p>
 * 维修服务项目controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:14:07
 */
@Controller
@RequestMapping("/repairItem")
@Api(tags = "维修服务项目相关操作")
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
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{RepairItem.DP.typeLK.name(),RepairItem.DP.workTypeLK.name()}
		, new Class[]{LookupService.class,LookupService.class});
	}
	
}