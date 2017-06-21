package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.OutPart;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.OutPartService;

@Controller
@RequestMapping("/outPart")
public class OutPartController extends BaseController<OutPart>{
	@Autowired
	private OutPartService service;
	
	@Override
	protected BaseService<OutPart> getSevice() {
		return service;
	}

}
