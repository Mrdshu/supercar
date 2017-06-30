package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartInfoService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.PartService;

@Controller
@RequestMapping("/inPartInfo")
public class InPartInfoController extends BaseController<InPartInfo>{
	@Autowired
	private InPartInfoService service;
	
	@Override
	protected BaseService<InPartInfo> getSevice() {
		return service;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{InPartInfo.DP.supplierLK.name(),InPartInfo.DP.repositoryCodeLK.name()
				,InPartInfo.DP.partId.name()}
		, new Class[]{LookupService.class,LookupService.class,PartService.class});
	}
}
