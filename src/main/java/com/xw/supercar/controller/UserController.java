package com.xw.supercar.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.User;
import com.xw.supercar.excel.exports.UserExport;
import com.xw.supercar.excel.imports.UserImport;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
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
				.addSearchFilter(User.DP.isDeleted.name(), SearchOperator.eq, false)
				.addSearchFilter(User.DP.company.name(), SearchOperator.eq, company);
		
		User user = getSevice().getBy(searchable, false, true);
		
		if(user == null){
			result = ResponseResult.generateErrorResponse("", "该账号不存在！");
		}else if(!password.equals(user.getPassword())){
			result = ResponseResult.generateErrorResponse("", "输入的密码有误！");
		}else if(user.getIsDisable() == true){
			result = ResponseResult.generateErrorResponse("", "该账号已被禁用！");
		}else{
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
	
	/**
	 * 所有用户信息导出为excel
	 * 
	 * @param searchable 筛选用户的过滤条件
	 * @param exportFilePath 导出路径
	 *
	 * @author wsz 2017-09-20
	 */
	@RequestMapping(value = "/export",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult export(@SearchableDefaults(needPage = false) Searchable searchable, String exportFilePath) {
		ResponseResult result = ResponseResult.generateResponse();
		//校验导出路径地址是否正确合法
		try {
			File file = new File(exportFilePath);
			if(!file.exists()) file.createNewFile();
		} catch (Exception e) {
			logger.error("所有用户信息导出为excel exception...", e);
			return ResponseResult.generateErrorResponse("导出路径【"+exportFilePath+"】错误，无法正常导出", "");
		}
		//获取数据库用户表中所有的用户
		List<User> users = SpringContextHolder.getBean(UserService.class).findBy(Searchable.newSearchable(), true);
		UserExport userExport = new UserExport();
		userExport.setPoiList(users);
		
		//将所有用户导出成excel
		Boolean exportRs = userExport.export(exportFilePath, null);
		if(!exportRs)
			return ResponseResult.generateErrorResponse("导出失败！", "");
		return result;
	}
	
	/**
	 * 将excel表中数据导入。
	 * 
	 * @param importFilePath　导入文件路径
	 * @return
	 *
	 * @author wsz 2017-09-20
	 */
	@RequestMapping(value = "/imports",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult imports(String importFilePath) {
		ResponseResult result = ResponseResult.generateResponse();
		
		UserImport userImport = new UserImport();
		Boolean importRs = userImport.imports(importFilePath);
		
		if(!importRs)
			return ResponseResult.generateErrorResponse("导入失败！", "");
		return result;
	}
	
}
