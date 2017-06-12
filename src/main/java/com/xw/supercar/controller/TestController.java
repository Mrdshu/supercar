package com.xw.supercar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.sql.search.Searchable;

@Controller
@RequestMapping("/hello")
public class TestController {
	
	@RequestMapping("/test")
	public void test(@SearchableDefaults(needPage = false) Searchable searchable) {
		System.out.println(searchable);
	}
	
}
