package com.xw.supercar.controller;

import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.entity.User;
import com.xw.supercar.excel.exports.UserExport;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.Searchable;

@Controller
@RequestMapping("/hello")
@Api(tags = "测试接口")
public class TestController {
	
	@RequestMapping("/test")
	public void test(@SearchableDefaults(needPage = false) Searchable searchable) {
		List<User> users = SpringContextHolder.getBean(UserService.class).findBy(Searchable.newSearchable(), true);
		UserExport userExport = new UserExport();
		userExport.setPoiList(users);
				
		userExport.export("D://导出记录.xls", "yyyy/MM/dd HH:mm:ss");
	}
	
}
