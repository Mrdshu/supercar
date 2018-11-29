package com.xw.supercar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InventoryService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.PartService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

@Controller
@RequestMapping("/inventory")
@Api(tags = "库存相关操作")
public class InventoryController extends BaseController<Inventory>{
	@Autowired
	private InventoryService service;
	
	@Override
	protected BaseService<Inventory> getSevice() {
		return service;
	}
	
	/**
	 * 查询多条数据
	 * @param searchable
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/listInventory",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult listInventory(Searchable searchable,String partName, String partCode, String carModel){
		ResponseResult result = ResponseResult.generateResponse();
		Map<String, Map<String, Object>>  extendInfo = result.getExtendInfo();
		
		//获取搜索条件对应的配件集合
		Searchable newsearchable = Searchable.newSearchable()
				.addSearchFilter(Part.DP.name.name(), SearchOperator.eq, partName)
				.addSearchFilter(Part.DP.code.name(), SearchOperator.eq, partCode)
				.addSearchFilter(Part.DP.carModel.name(), SearchOperator.like, carModel);
		List<Part> parts = SpringContextHolder.getBean(PartService.class).findBy(newsearchable, true);
		
		//获取配件集合的id,同时将配件信息放入扩展属性
		List<String> partsId = new ArrayList<>();
		Map<String, Object> partIdExtendInfo =  new HashMap<>();
		extendInfo.put(Inventory.DP.partId.name(), partIdExtendInfo);
		for (Part part : parts) {
			partsId.add(part.getId());
			partIdExtendInfo.put(part.getId(), part);
		}
		
		if(partsId == null || partsId.size() == 0)
			return ResponseResult.generateErrorResponse("", "没有符合条件的数据");
		//根据配件id，搜索出对应的库存信息
		searchable = Searchable.newSearchable(searchable).addSearchFilter(Inventory.DP.partId.name(), SearchOperator.in, partsId);
		List<Inventory> entitys = getSevice().findBy(searchable, true);
		//生成返回实体类
		result.addAttribute("entitys", entitys);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
//		Map<String, Object> data = result.getData();
		
//		addAttributesToData(data, new String[]{Inventory.DP.repCodeLK.name(),Inventory.DP.partId.name()}
//		, new Class[]{LookupService.class,CompanyService.class,PartService.class});
		
		//将数据字典对应的实体放入data
		addAttributesToExtendInfo(result, new String[]{Inventory.DP.repCodeLK.name(),Inventory.DP.partId.name()}
		, new Class[]{LookupService.class,PartService.class});
		//获取额外属性part中的单元字段的额外属性
		List<Part> parts = new ArrayList<>();
		Map<String, Object> partsMap = result.getExtendInfo().get("partId");
		for (Object object : partsMap.values()) {
			Part part = (Part) object;
			parts.add(part);
		}
		service.addAttributeToExtendInfo(result.getExtendInfo(), parts, Part.DP.unitLK.name(), LookupService.class);
	}
	
}
