package com.xw.supercar.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.OutPartComposite;
import com.xw.supercar.entity.composite.RepairWorkOrderComposite;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.OutPartService;
import com.xw.supercar.service.RepairWorkorderItemService;
import com.xw.supercar.service.RepairWorkorderService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

/**
 * <p>
 * 维修工单controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:35:59
 */
@Controller
@RequestMapping("/repairWorkorder")
public class RepairWorkorderController extends BaseController<RepairWorkorder>{

	@Autowired
	private RepairWorkorderService service;
	
	@Override
	protected BaseService<RepairWorkorder> getSevice() {
		return service;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将外键对应的实体放入data
		addAttributesToData(data, new String[]{RepairWorkorder.DP.repairTypeLK.name(),RepairWorkorder.DP.clientId.name(),RepairWorkorder.DP.clerk.name()}
		, new Class[]{LookupService.class,ClientService.class,UserService.class});
	}
	
	/**
	 * 新增维修工单，包括绑定的维修项目和领料信息
	 * @author  wangsz 2017-07-07
	 */
	@RequestMapping(value = "/newRepairWorkorder",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult newRepairWorkorder(@RequestBody RepairWorkOrderComposite repairWOComposite) {
		ResponseResult result = ResponseResult.generateResponse();
		
		Client client = repairWOComposite.getClient();
		RepairWorkorder repairWorkorder = repairWOComposite.getRepairWorkorder();
		List<RepairWorkorderItem> repairWorkorderItems = repairWOComposite.getRepairWorkorderItems();
		OutPartComposite outPartComposite = repairWOComposite.getOutPartComposite();
		
		/*
		 * 新增维修工单
		 */
		//如果客户为已存在客户，则修改客户信息
		if(!StringUtils.isEmpty(client.getId())){
			SpringContextHolder.getBean(ClientService.class).modify(client);
		}
		//否则新增客户
		else{
			client = SpringContextHolder.getBean(ClientService.class).add(client);
		}
		//新增维修工单信息
		repairWorkorder.setClientId(client.getId());
		repairWorkorder = SpringContextHolder.getBean(RepairWorkorderService.class).add(repairWorkorder);
		
		/*
		 * 新增维修工单-项目中间信息
		 */
		//设置维修工单-项目 中间表的工单id
		for (RepairWorkorderItem repairWorkorderItem : repairWorkorderItems) {
			repairWorkorderItem.setWorkorderId(repairWorkorder.getId());
		}
		if(repairWorkorderItems != null && repairWorkorderItems.size() > 0)
			SpringContextHolder.getBean(RepairWorkorderItemService.class).add(repairWorkorderItems);
		
		/*
		 * 新增出库工单以及对应的出库配件信息
		 */
		if(outPartComposite != null && outPartComposite.getOutPart() != null){
			outPartComposite.getOutPart().setRepairWorkorderNo(repairWorkorder.getWorkorderNo());
			result = SpringContextHolder.getBean(OutPartController.class).newOutPart(outPartComposite);
		}
		
		return result;
	}
	
	/**
	 * 修改维修工单，包括绑定的维修项目和领料信息
	 * @author  wangsz 2017-07-07
	 */
	@RequestMapping(value = "/editRepairWorkOrder",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult editRepairWorkOrder(@RequestBody RepairWorkOrderComposite repairWOComposite) {
		ResponseResult result = ResponseResult.generateResponse();
		
		Client client = repairWOComposite.getClient();
		RepairWorkorder repairWorkorder = repairWOComposite.getRepairWorkorder();
		List<RepairWorkorderItem> repairWorkorderItems = repairWOComposite.getRepairWorkorderItems();
		/*
		 * 修改维修工单信息
		 */
		//修改客户信息
		if(StringUtils.isEmpty(client.getId())){
			return ResponseResult.generateErrorResponse("", "修改时客户id不能为空！");
		}
		SpringContextHolder.getBean(ClientService.class).modify(client);
		SpringContextHolder.getBean(RepairWorkorderService.class).modify(repairWorkorder);
		
		/*
		 * 修改维修工单-项目中间信息
		 */
		//删除维修工单原有绑定的维修项目
		RepairWorkorderItemService rWOItemService = SpringContextHolder.getBean(RepairWorkorderItemService.class);
		List<RepairWorkorderItem> deleteRWOItems = rWOItemService.getByRWOId(repairWorkorder.getId());
		rWOItemService.remove(deleteRWOItems);
		//重新添加维修工单的维修项目
		rWOItemService.add(repairWorkorderItems);
		
		result.addAttribute("", "修改成功！");
		return result;
	}
	
	/**
	 * 查看维修工单的详细信息（包括服务项目和领料信息两部分）
	 * @param repairWorkOrderNo
	 * @return
	 * @author  wangsz 2017-07-07
	 */
	@RequestMapping(value = "/getItemsAndParts",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getItemsAndParts(String repairWorkOrderNo){
		ResponseResult result = ResponseResult.generateResponse();
		if(StringUtils.isEmpty(repairWorkOrderNo))
			return ResponseResult.generateErrorResponse("", "维修工单号不能为空！");
		
		RepairWorkorder repairWorkorder = service.getByCode(repairWorkOrderNo);
		//获取维修工单关联的维修服务项目
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(RepairWorkorderItem.DP.workorderId.name(), SearchOperator.eq, repairWorkorder.getId());
		List<RepairWorkorderItem> items = SpringContextHolder.getBean(RepairWorkorderItemService.class).findBy(searchable, true);
		//获取维修工单关联的出货配件信息
		OutPartComposite outPartComposite = SpringContextHolder.getBean(OutPartService.class).findOutPartInfosByRWO(repairWorkOrderNo);
		
		result.addAttribute("items", items);
		result.addAttribute("outPartComposite", outPartComposite);
		
		return result;
	}
	
}