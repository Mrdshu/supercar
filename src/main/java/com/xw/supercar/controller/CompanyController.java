package com.xw.supercar.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Company;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
@Controller
@RequestMapping("/company")
@Api(tags = "公司相关操作")
public class CompanyController extends BaseController<Company>{
	@Autowired
	private CompanyService service;
	
	@Override
	protected BaseService<Company> getSevice() {
		return service;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{Company.DP.type.name()}
		, new Class[]{LookupService.class});
	}
}
