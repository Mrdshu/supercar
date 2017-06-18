package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.User;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User>{
	@Autowired
	private UserService service;
	
	@Override
	protected BaseService<User> getSevice() {
		return service;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{User.DP.role.name(),User.DP.company.name()}
		, new Class[]{LookupService.class,CompanyService.class});
	}
}
