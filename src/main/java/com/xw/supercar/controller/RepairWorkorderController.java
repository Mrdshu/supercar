package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.RepairWorkorderService;

/**
 * <p>
 * 维修工单controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 08:32:37
 */
@Controller
@RequestMapping("/repairWorkorder")
public class RepairWorkorderController extends BaseController<RepairWorkorder>{

	@Autowired
	private RepairWorkorderService baseService;
	
	@Override
	protected BaseService<RepairWorkorder> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		
	}
	


}