package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Lookup;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
@Controller
@RequestMapping("/lookup")
public class LookupController extends BaseController<Lookup>{
	@Autowired
	private LookupService lookupService;
	
	@Override
	protected BaseService<Lookup> getSevice() {
		return lookupService;
	}

}
