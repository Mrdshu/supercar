package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Part;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.PartService;

@Controller
@RequestMapping("/part")
public class PartController extends BaseController<Part>{
	@Autowired
	private PartService baseService;
	
	@Override
	protected BaseService<Part> getSevice() {
		return baseService;
	}

	
}
