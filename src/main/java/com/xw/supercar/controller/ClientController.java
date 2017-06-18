package com.xw.supercar.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;

@Controller
@RequestMapping("/client")
public class ClientController extends BaseController<Client>{
	@Autowired
	private ClientService baseService;
	
	@Override
	protected BaseService<Client> getSevice() {
		return baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		addAttributesToData(data, new String[]{Client.DP.type.name(),Client.DP.carBrand.name()}
		, new Class[]{LookupService.class,LookupService.class});
	}
	
}
