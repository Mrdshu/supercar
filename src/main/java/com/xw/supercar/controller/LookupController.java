package com.xw.supercar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.sql.page.Page;
@Controller
@RequestMapping("/lookup")
public class LookupController extends BaseController<Lookup>{
	@Autowired
	private LookupService lookupService;
	
	@Override
	protected BaseService<Lookup> getSevice() {
		return lookupService;
	}
	
	/**
	 * 获取某一数据字典定义下的数据字典
	 * @param searchable
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/getByDefineCode",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getByDefineCode(String lookupDefineCode){
		
		List<Lookup> lookups = lookupService.searchByDefineCode(lookupDefineCode);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("entitys", lookups);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
	/**
	 * 获取某一数据字典定义下的数据字典
	 * @param searchable
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/pageByDefineCode",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult pageByDefineCode(String lookupDefineCode, String pageNo, String pageSize){
		Page<Lookup> page= lookupService.searchPageByDefineCode(lookupDefineCode, pageNo, pageSize);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("page", page);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}

}
