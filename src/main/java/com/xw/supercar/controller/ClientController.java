package com.xw.supercar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Car;
import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CarService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.spring.util.SpringContextHolder;

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
			addAttributeToData(client,Client.DP.type.name(),LookupService.class);
		}
		else if(data.containsKey("entitys")){
			@SuppressWarnings("unchecked")
			List<Client> clients = (List<Client>) data.get("entitys");
			for (Client client : clients) {
				addAttributeToData(client,Client.DP.type.name(),LookupService.class);
			}
		}
	}
	
	/**
	 * 新增客户以及绑定的车辆
	 * @author  wangsz 2017-06-04
	 */
	@Transactional
	@RequestMapping(value = "/newClientAndCar",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult newClientAndCar(Client client, Car car){
		Client afterInsertEntity = getSevice().add(client);
		Car afterInsertCar = SpringContextHolder.getBean(CarService.class).add(car);
		
		if(afterInsertEntity == null || afterInsertCar == null)
			return ResponseResult.generateErrorResponse("", "新增失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("新增成功！");
		
		return result;
	}
	
}
