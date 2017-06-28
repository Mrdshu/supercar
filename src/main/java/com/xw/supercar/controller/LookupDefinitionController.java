package com.xw.supercar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupDefinitionService;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
/**
 * 数据字典定义controller逻辑类
 * @author wsz 2017-06-28
 */
@Controller
@RequestMapping("/lookup_definition")
public class LookupDefinitionController extends BaseController<LookupDefinition>{
	@Autowired
	private LookupDefinitionService lookupDefinitionService;
	
	@Override
	protected BaseService<LookupDefinition> getSevice() {
		return lookupDefinitionService;
	}
	
	@Override
	protected ResponseResult beforeNew(LookupDefinition entity) {
		
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(LookupDefinition.DP.code.name(), SearchOperator.eq, entity.getCode());
		List<LookupDefinition> lookupDefinitions = lookupDefinitionService.findBy(searchable, true);
		if(lookupDefinitions != null && lookupDefinitions.size() > 0)
			return ResponseResult.generateErrorResponse("", "代码重复，请重新输入");
		
		return ResponseResult.generateResponse();
	}

}
