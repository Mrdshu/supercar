package com.xw.supercar.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.sql.page.Page;

@Controller
@RequestMapping("/client")
public class ClientController extends BaseController<Client>{
	@Autowired
	private ClientService baseService;
	
	@Override
	protected BaseService<Client> getSevice() {
		return baseService;
	}

	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将数据字典对应的实体放入data
		if(data.containsKey("entity")){
			Client client = (Client) data.get("entity");
			addAttributeToData(client,Client.DP.type.name(),LookupService.class);
			addAttributeToData(client,Client.DP.carBrand.name(),LookupService.class);
		}
		else if(data.containsKey("entitys")){
			@SuppressWarnings("unchecked")
			List<Client> clients = (List<Client>) data.get("entitys");
			for (Client client : clients) {
				addAttributeToData(client,Client.DP.type.name(),LookupService.class);
				addAttributeToData(client,Client.DP.carBrand.name(),LookupService.class);
			}
		}
		else if(data.containsKey("page")){
			@SuppressWarnings("unchecked")
			Page<Client> page = (Page<Client>) data.get("page");
			List<Client> clients = page.getContent();
			for (Client client : clients) {
				addAttributeToData(client,Client.DP.type.name(),LookupService.class);
				addAttributeToData(client,Client.DP.carBrand.name(),LookupService.class);
			}
		}
	}
	
}
