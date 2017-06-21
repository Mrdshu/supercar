package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.InPart;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartService;

@Controller
@RequestMapping("/inPart")
public class InPartController extends BaseController<InPart>{
	@Autowired
	private InPartService service;
	
	@Override
	protected BaseService<InPart> getSevice() {
		return service;
	}

}
