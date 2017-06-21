package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.OutPartInfoService;

@Controller
@RequestMapping("/outPartInfo")
public class OutPartInfoController extends BaseController<OutPartInfo>{
	@Autowired
	private OutPartInfoService service;
	
	@Override
	protected BaseService<OutPartInfo> getSevice() {
		return service;
	}

}
