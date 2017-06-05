package com.xw.supercar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.ClientTypeService;

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
		//将type对应的实体放入data
		if(data.containsKey("entity")){
			Client client = (Client) data.get("entity");
			addAttributeToData(client,Client.DP.type.name(),ClientTypeService.class);
		}
		else if(data.containsKey("entitys")){
			List<Client> clients = (List<Client>) data.get("entitys");
			for (Client client : clients) {
				addAttributeToData(client,Client.DP.type.name(),ClientTypeService.class);
			}
		}
	}
	
}
