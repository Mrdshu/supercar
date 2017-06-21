package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartInfoService;

@Controller
@RequestMapping("/inPartInfo")
public class InPartInfoController extends BaseController<InPartInfo>{
	@Autowired
	private InPartInfoService service;
	
	@Override
	protected BaseService<InPartInfo> getSevice() {
		return service;
	}

}
