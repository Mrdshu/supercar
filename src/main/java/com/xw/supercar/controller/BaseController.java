package com.xw.supercar.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xw.supercar.shiro.core.IContextProvider;
import com.xw.supercar.util.DateFormateEditor;

@Controller
@SessionAttributes
public class BaseController {
	
	@Resource(name="contextProvider")
	protected IContextProvider contextProvider;
	
	// 第一种：获取通用的请求对对象的方式
	// @Autowired
	// protected HttpServletResponse response;
	// @Autowired
	// protected HttpServletRequest request;

	// 第二种
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		this.response = response;
		this.request = request;
		this.session = session;
	}

	/* 统一处理日期格式化的问题 */
	@InitBinder
	public void initDataBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Date.class, new DateFormateEditor());
	}

	/* 统一异常处理 */
	 /*@ExceptionHandler(RuntimeException.class)
		 public String handlerException(RuntimeException runtimeException){
		 return "redirect:fronts/common/error.html";
	 }*/

	// /*统一异常处理*/
	// @ExceptionHandler(NullPointerException.class)
	// public String hand(RuntimeException runtimeException){
	// return "redirect:/nulll.jsp";
	// }

	/**
	 * json格式转化字符串
	 * @Title: jsonToString 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param obj  参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void jsonToString(Object obj) {
		try {
			contextProvider.setResponse(response);
			PrintWriter writer = contextProvider.getResponse().getWriter();
			//PrintWriter writer = response.getWriter();
//			writer.print(JSONUtil.serialize(obj));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
