package com.xw.supercar.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.User;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

import sun.launcher.resources.launcher;

@Controller
@RequestMapping("/hello")
public class TestController {
	private static Logger logger = Logger.getLogger(TestController.class);
	private static Logger selfNameLogger = Logger.getLogger("selfName");
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response) {
		logger.info("=============test log4j=================");
		selfNameLogger.info("tttttt");
//		UserService userService = SpringContextHolder.getBean(UserService.class);
//		
//		User user = new User("wsz", "123"); 
//		System.out.println(user);
//		userService.add(user);
//		
//		Searchable searchable = Searchable.newSearchable()
//				.addSearchFilter(User.DP.username.name(), SearchOperator.eq, "wsz");
//		List<User> users = userService.searchBy(searchable, true);
//		
//		searchable.addPage(0, 1);
//		Page<User> usersPage = userService.searchPage(searchable, true);
//		
//		try {
//			response.getWriter().write("hello");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
