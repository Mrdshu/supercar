package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.xw.supercar.entity.User;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.UserService;

public class UserController extends BaseController<User>{
	@Autowired
	private UserService service;
	
	@Override
	protected BaseService<User> getSevice() {
		return service;
	}

}
