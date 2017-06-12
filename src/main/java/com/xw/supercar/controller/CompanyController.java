package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.xw.supercar.entity.Company;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;

public class CompanyController extends BaseController<Company>{
	@Autowired
	private CompanyService service;
	
	@Override
	protected BaseService<Company> getSevice() {
		return service;
	}

}
