package com.xw.supercar.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xw.supercar.shiro.IShiroSecurityService;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private IShiroSecurityService shiroService;
	
	/**
	 * 前台页面
	 * @Title: index 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	@RequestMapping("/tologin")
	public String index(){
		System.out.println("进入登录页...");
		return "login";
	}
	
	
	@RequestMapping("/login")
	public String login(@RequestParam("userName") String username, @RequestParam("password") String password){
		
		//获取当前用户信息
		Subject currentUser = SecurityUtils.getSubject();
		
		//判断当前用户是否已认证
		if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
           
            // rememberme
            token.setRememberMe(true);
            
            try {
            	System.out.println("1. " + token.hashCode());
            	// 执行登录. 
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败: " + ae.getMessage());
            }
        }
		
		//若已登录则重定向到list页面
		return "index";
	}
}
