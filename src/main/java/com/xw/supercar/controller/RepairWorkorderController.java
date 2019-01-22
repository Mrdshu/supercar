package com.xw.supercar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctc.wstx.util.StringUtil;
import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.Company;
import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.User;
import com.xw.supercar.entity.composite.OutPartComposite;
import com.xw.supercar.entity.composite.RepairWorkOrderComposite;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.ClientService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.InventoryService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.OutPartService;
import com.xw.supercar.service.PartService;
import com.xw.supercar.service.RepairWorkorderItemService;
import com.xw.supercar.service.RepairWorkorderService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.CommonUtil;

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
@Api(tags = "维修工单相关操作")
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
		addAttributesToData(data, new String[]{RepairWorkorder.DP.company.name(),RepairWorkorder.DP.workorderState.name(),RepairWorkorder.DP.repairTypeLK.name(),RepairWorkorder.DP.clientId.name(),RepairWorkorder.DP.clerk.name()}
		, new Class[]{CompanyService.class,LookupService.class,LookupService.class,ClientService.class,UserService.class});
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
		String workOrderNo = CommonUtil.getTimeStampRandom();
		repairWorkorder.setWorkorderNo(workOrderNo);
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
			//出库操作为嵌套事务，若出库不成功则父事务也回滚
			if(!result.getSuccess())
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
		if(!StringUtils.isEmpty(client)){
			SpringContextHolder.getBean(ClientService.class).modify(client);
		}
		
		//修改工单信息		
		if(!StringUtils.isEmpty(repairWorkorder)){
			SpringContextHolder.getBean(RepairWorkorderService.class).modify(repairWorkorder);
		}
		
		/*
		 * 修改维修工单-项目中间信息
		 */
		//删除维修工单原有绑定的维修项目
		if(!StringUtils.isEmpty(repairWorkorderItems)){
			RepairWorkorderItemService rWOItemService = SpringContextHolder.getBean(RepairWorkorderItemService.class);
			List<RepairWorkorderItem> deleteRWOItems = rWOItemService.getByRWOId(repairWorkorder.getId());
			rWOItemService.remove(deleteRWOItems);
			//重新添加维修工单的维修项目
			rWOItemService.add(repairWorkorderItems);
		}
		
		result.addAttribute("errorMsg", "修改成功！");
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
		Map<String, Map<String, Object>>  extendInfo = result.getExtendInfo();
		
		if(StringUtils.isEmpty(repairWorkOrderNo)){
			return ResponseResult.generateErrorResponse("", "维修工单号不能为空！");
		}

		//获取维修工单信息
		RepairWorkorder repairWorkorder = service.getByCode(repairWorkOrderNo);
		
		//获取客户信息
		Searchable clientSearchable = Searchable.newSearchable()
				.addSearchFilter(Client.DP.id.name(), SearchOperator.eq, repairWorkorder.getClientId());
		Client client = SpringContextHolder.getBean(ClientService.class).getBy(clientSearchable, true, true);
		
		//获取维修工单关联的维修服务项目
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(RepairWorkorderItem.DP.workorderId.name(), SearchOperator.eq, repairWorkorder.getId());
		List<RepairWorkorderItem> items = SpringContextHolder.getBean(RepairWorkorderItemService.class).findBy(searchable, true);
		
		//获取维修工单关联的出货配件信息
		OutPartComposite outPartComposite = SpringContextHolder.getBean(OutPartService.class).findOutPartInfosByRWO(repairWorkOrderNo);
		
		//获取维修项目集合的id,同时将维修项目信息放入扩展属性
		Map<String, Object> carBrandExtendInfo = new HashMap<>();
		Map<String, Object> clientLevelExtendInfo = new HashMap<>();
		//Map<String, Object> repairWorkorderExtendInfo = new HashMap<>();
		Map<String, Object> repairTypeLKExtendInfo = new HashMap<>();
		Map<String, Object> mechanicExtendInfo = new HashMap<>();
		Map<String, Object> clerkExtendInfo = new HashMap<>();
		Map<String, Object> companyExtendInfo = new HashMap<>();
		
		for (RepairWorkorderItem item : items) {
			mechanicExtendInfo = getAccountById(mechanicExtendInfo,item.getMechanic());
		}
		
		if(outPartComposite != null){
			Map<String, Object> partExtendInfo = new HashMap<>();
			for (OutPartInfo outPartInfo : outPartComposite.getOutPartInfos()) {
				Inventory inventory = SpringContextHolder.getBean(InventoryService.class).getById(outPartInfo.getInventoryId());
				partExtendInfo = getPartById(partExtendInfo,inventory.getPartId());
			}
			extendInfo.put(Inventory.DP.partId.name(), partExtendInfo);
		}
		
		extendInfo.put(Client.DP.type.name(), getByLookUp(carBrandExtendInfo,client.getCarBrand()));
		extendInfo.put(Client.DP.level.name(), getByLookUp(clientLevelExtendInfo,client.getLevel()));
		//extendInfo.put(RepairWorkorder.DP.workorderState.name(), getByLookUp(repairWorkorderExtendInfo,repairWorkorder.getWorkorderState()));
		extendInfo.put(RepairWorkorder.DP.repairTypeLK.name(), getByLookUp(repairTypeLKExtendInfo,repairWorkorder.getRepairTypeLK()));
		extendInfo.put(RepairWorkorder.DP.clerk.name(), getByLookUp(clerkExtendInfo,repairWorkorder.getClerk()));
		extendInfo.put(RepairWorkorderItem.DP.mechanic.name(), mechanicExtendInfo);
		extendInfo.put(RepairWorkorder.DP.company.name(),getCompanyById(companyExtendInfo,repairWorkorder.getCompany()));
		result.addAttribute("repairWorkorder", repairWorkorder);
		result.addAttribute("client", client);
		result.addAttribute("items", items);
		result.addAttribute("outPartComposite", outPartComposite);
		
		return result;
	}
	
	//根据扩展字段查询对应数据字典的数据
	public Map<String, Object> getByLookUp(Map<String, Object> itemExtendInfo,String lookUpId){
		if(itemExtendInfo == null)
			itemExtendInfo = new HashMap<>();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Lookup.DP.id.name(), SearchOperator.eq, lookUpId);
		Lookup lookup = SpringContextHolder.getBean(LookupService.class).getBy(searchable, true, true);
		itemExtendInfo.put(lookUpId, lookup);
		return itemExtendInfo;
	}
	
	//根据扩展字段查询对应账号的数据
	public Map<String, Object> getAccountById(Map<String, Object> itemExtendInfo,String userId){
		if(itemExtendInfo == null)
			itemExtendInfo = new HashMap<>();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(User.DP.id.name(), SearchOperator.eq, userId);
		User user = SpringContextHolder.getBean(UserService.class).getBy(searchable, true, true);
		itemExtendInfo.put(userId, user);
		return itemExtendInfo;
	}
	
	//根据扩展字段查询对应账号的数据
	public Map<String, Object> getPartById(Map<String, Object> itemExtendInfo,String partId){
		if(itemExtendInfo == null)
			itemExtendInfo = new HashMap<>();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Part.DP.id.name(), SearchOperator.eq, partId);
		Part part = SpringContextHolder.getBean(PartService.class).getBy(searchable, true, true);
		itemExtendInfo.put(partId, part);
		return itemExtendInfo;
	}
	
	//根据扩展字段查询对应账号的数据
	public Map<String, Object> getCompanyById(Map<String, Object> itemExtendInfo,String companyId){
		if(itemExtendInfo == null)
			itemExtendInfo = new HashMap<>();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Company.DP.id.name(), SearchOperator.eq, companyId);
		Company part = SpringContextHolder.getBean(CompanyService.class).getBy(searchable, true, true);
		itemExtendInfo.put(companyId, part);
		return itemExtendInfo;
	}
	
}