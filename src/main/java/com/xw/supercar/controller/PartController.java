package com.xw.supercar.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.PartService;

@Controller
@RequestMapping("/part")
@Api(tags = "配件相关操作")
public class PartController extends BaseController<Part>{
	@Autowired
	private PartService baseService;
	
	@Override
	protected BaseService<Part> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{Part.DP.unitLK.name(),Part.DP.specificationLK.name(),Part.DP.pCategoryLK.name()}
		, new Class[]{LookupService.class,LookupService.class,LookupService.class});
	}
}
