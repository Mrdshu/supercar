package com.xw.supercar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.TreeNode;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Controller
@RequestMapping("/lookup")
public class LookupController extends BaseController<Lookup>{
	@Autowired
	private LookupService service;
	
	@Override
	protected BaseService<Lookup> getSevice() {
		return service;
	}
	
	@Override
	protected ResponseResult beforeNew(Lookup entity) {
		
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Lookup.DP.definitionId.name(), SearchOperator.eq, entity.getDefinitionId())
				.addSearchFilter(Lookup.DP.code.name(), SearchOperator.eq, entity.getCode());
		List<Lookup> lookups = service.findBy(searchable, true);
		if(lookups != null && lookups.size() > 0)
			return ResponseResult.generateErrorResponse("", "代码重复，请重新输入");
		
		return ResponseResult.generateResponse();
	}

	
	/**
	 * 获取某一数据字典定义下的数据字典
	 * @param lookupDefineCode
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/getByDefineCode",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getByDefineCode(String lookupDefineCode){
		List<Lookup> lookups = service.searchByDefineCode(lookupDefineCode,null);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("entitys", lookups);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
	/**
	 * 分页显示某一数据字典定义下的数据字典
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/pageByDefineCode",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult pageByDefineCode(String lookupDefineCode, String pageNo, String pageSize){
		Page<Lookup> page= service.searchPageByDefineCode(lookupDefineCode, pageNo, pageSize);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("page", page);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
	@RequestMapping(value = "/checkCodeRepeat",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult checkCodeRepeat(String definitionId, String lookupCode){
		if(StringUtils.isEmpty(definitionId) || StringUtils.isEmpty(lookupCode))
			return ResponseResult.generateErrorResponse("", "数据字典code以及定义id不能为空！");
			
		Lookup lookup = new Lookup();
		lookup.setCode(lookupCode);
		lookup.setDefinitionId(definitionId);
		return beforeNew(lookup);
	}
	
	/**
	 * 返回某定义下数据字典的树状json
	 * @author wsz 2017-06-28
	 */
	@RequestMapping(value = "/getTree",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getTree(String lookupDefineCode){
		ResponseResult result = ResponseResult.generateResponse();
		
		List<TreeNode> treeNodes = service.getTree(lookupDefineCode);
		if(treeNodes == null || treeNodes.isEmpty())
			return ResponseResult.generateErrorResponse("", "获取树状数据字典失败！");
			
		result.addAttribute("entitys", treeNodes);
		
		return result;
	}

}
