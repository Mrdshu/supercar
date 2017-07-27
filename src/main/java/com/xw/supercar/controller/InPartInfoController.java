package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartInfoService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.PartService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.Searchable;

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
	
	/**
	 * 关联查询，显示出扩展属性的多条分页数据
	 * @author wsz 2017-06-26
	 */
	@RequestMapping(value = "/extendPage",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult extendPage(@SearchableDefaults Searchable searchable){
		ResponseResult result = ResponseResult.generateResponse();
		
		Page<InPartInfo> inPartInfos = SpringContextHolder.getBean(InPartInfoService.class).extendFindPage(searchable, true);
		result.addAttribute("page", inPartInfos);
		return result;
	}
}
