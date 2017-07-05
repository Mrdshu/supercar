package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.OutPart;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.OutPartService;

/**
 * <p>
 * 出库工单controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 12:04:03
 */
@Controller
@RequestMapping("/outPart")
public class OutPartController extends BaseController<OutPart>{

	@Autowired
	private OutPartService baseService;
	
	@Override
	protected BaseService<OutPart> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		
	}
	


}