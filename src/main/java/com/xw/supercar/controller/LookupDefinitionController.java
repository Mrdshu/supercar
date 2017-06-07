package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupDefinitionService;

@Controller
@RequestMapping("/lookup_definition")
public class LookupDefinitionController extends BaseController<LookupDefinition>{
	@Autowired
	private LookupDefinitionService lookupDefinitionService;
	
	@Override
	protected BaseService<LookupDefinition> getSevice() {
		return lookupDefinitionService;
	}

}
