package com.xw.supercar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.entity.User;
import com.xw.supercar.excel.ExcelUtil;
import com.xw.supercar.excel.exports.UserExport;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.Searchable;

@Controller
@RequestMapping("/hello")
public class TestController {
	
	@RequestMapping("/test")
	public void test(@SearchableDefaults(needPage = false) Searchable searchable) {
		UserExport userExport = new UserExport();
		List<User> users = SpringContextHolder.getBean(UserService.class).findBy(Searchable.newSearchable(), true);
		userExport.setPoiList(users);
		
		ExcelUtil.export(userExport, "D://导出记录.xls", "yyyy/MM/dd HH:mm:ss");
	}
	
}
