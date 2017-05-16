package com.xw.supercar.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.dao.UserDao;
import com.xw.supercar.entity.User;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

@Controller
@RequestMapping("/hello")
public class TestController {

	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response) {/*
		UserService userService = SpringContextHolder.getBean(UserService.class);
		
		User user = new User("www", "123"); 
		System.out.println(user);
		userService.add(user);
		
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(User.DP.username.name(), SearchOperator.eq, "wsz");
		List<User> users = userService.searchBy(searchable, true);
		
		user.setPassword("111");
		userService.modify(user);
		
		userService.hardRemove(user);
		
		try {
			response.getWriter().write("hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/}
}
