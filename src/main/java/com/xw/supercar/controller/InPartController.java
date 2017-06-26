package com.xw.supercar.controller;

import java.util.List;

import javax.swing.Spring;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.InPart;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartInfoService;
import com.xw.supercar.service.InPartService;
import com.xw.supercar.spring.util.SpringContextHolder;

@Controller
@RequestMapping("/inPart")
public class InPartController extends BaseController<InPart>{
	@Autowired
	private InPartService service;
	
	@Override
	protected BaseService<InPart> getSevice() {
		return service;
	}
	
	/**
	 * 新增入库工单以及入库配件
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/newInPart",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult newEntity(InPart entity, List<InPartInfo> inPartInfos){
		ResponseResult result = ResponseResult.generateResponse();
		entity = service.add(entity);
		
		if(entity != null)
			SpringContextHolder.getBean(InPartInfoService.class).add(inPartInfos);
		
		return result;
	}

}
