package com.xw.supercar.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.User;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
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
		if(!result.getSuccess())
			return ;
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{User.DP.role.name(),User.DP.company.name()}
		, new Class[]{LookupService.class,CompanyService.class});
	}
	
	/**
	 * 登录
	 * @author wsz 2017-06-18
	 */
	@RequestMapping(value = "/login",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult login(HttpSession session, String username,String password,String company){
		ResponseResult result = ResponseResult.generateResponse();
		
		//查询出指定用户名、公司的用户
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(User.DP.username.name(), SearchOperator.eq, username)
				.addSearchFilter(User.DP.company.name(), SearchOperator.eq, company);
		User user = getSevice().getBy(searchable, true, true);
	
		//=====加盐哈希加密算法暂时弃用，直接比较前台传来的密码与数据库的密码====
//		if(user == null || !PasswordHash.validatePassword(password, user.getPassword())){
//			result = ResponseResult.generateErrorResponse("", "账号或密码错误");
//		}
		
		if(user == null || !password.equals(user.getPassword())){
			result = ResponseResult.generateErrorResponse("", "账号或密码错误");
		}
		else{
			//在用户第一次登录时，将用户放入session
			session.setAttribute("loginUser", user);
			result.addAttribute("entity", user);
			result.setErrorMsg("登录成功！");
		}
			
		//进行后后处理
		afterReturn(result);
		return result;
	}
	
	/**
	 * 登出
	 * @author wsz 2017-06-18
	 */
	@RequestMapping(value = "/loginout",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult loginout(HttpSession session, String username,String company){
		ResponseResult result = ResponseResult.generateResponse();
		//清空该客户端session中的登录信息
		session.removeAttribute("username");
		result.setErrorMsg("注销成功！");
		//进行后后处理
		afterReturn(result);
		return result;
	}
	
	/**
	 * 注册
	 * @author wsz 2017-06-18
	 */
	@RequestMapping(value = "/register",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult register(User user){
		ResponseResult result = ResponseResult.generateResponse();
		
		//验证用户名是否重复
		String username = user.getUsername();
		Searchable searchable = Searchable.newSearchable().addSearchFilter(User.DP.username.name(), SearchOperator.eq,username);
		User usernameUser = service.getBy(searchable, true, false);
		if(usernameUser != null)
			return ResponseResult.generateErrorResponse("", "用户名已经存在，请重新输入");
		
		//=====加盐哈希加密算法暂时弃用，直接存入前台传来的md5加密密码====
//		String password = user.getPassword();
//				
//		//将密码用PBKDF2（加盐哈希算法）加密后存入数据库
//		try {
//			password = PasswordHash.createHash(password);
//			user.setPassword(password);
//		} catch (Exception e) {
//			log.error("password encoding fail.\n"+CommonUtil.getExceptionInfo(e));
//			return ResponseResult.generateErrorResponse("", "注册失败，密码格式有误");
//		}
		
		result = newEntity(user);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
}
