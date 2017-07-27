package com.xw.supercar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.entity.Inventory;
import com.xw.supercar.entity.OutPart;
import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.OutPartComposite;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CompanyService;
import com.xw.supercar.service.InventoryService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.service.OutPartInfoService;
import com.xw.supercar.service.OutPartService;
import com.xw.supercar.service.PartService;
import com.xw.supercar.service.UserService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.CollectionUtils;

/**
 * <p>
 * 出库工单controller层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 10:09:37
 */
@Controller
@RequestMapping("/outPart")
public class OutPartController extends BaseController<OutPart>{
	/**手动事务管理对象*/
	@Autowired
	public PlatformTransactionManager transactionManager;
	
	@Autowired
	private OutPartService service;
	
	@Override
	protected BaseService<OutPart> getSevice() {
		return service;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void afterReturn(ResponseResult result) {
		Map<String, Object> data = result.getData();
		//将外键对应的实体放入data
		addAttributesToData(data, new String[]{OutPart.DP.receiver.name(),OutPart.DP.departmentLK.name(),OutPart.DP.company.name()}
		, new Class[]{UserService.class,LookupService.class,CompanyService.class});
	}
	
	/**
	 * 查看指定出库工单的配件信息
	 * @param inWorkOrderNo
	 * @return 指定入库工单的配件信息
	 *
	 * @author wsz 2017-06-26
	 */
	@RequestMapping(value = "/getOutPartInfos",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getOutPartInfos(String outWorkOrderNo){
		ResponseResult result = ResponseResult.generateResponse();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPartInfo.DP.workOrderNo.name(), SearchOperator.eq, outWorkOrderNo);
		
		Page<OutPartInfo> outPartInfos = SpringContextHolder.getBean(OutPartInfoService.class).extendFindPage(searchable, true);
		result.addAttribute("page", outPartInfos);
		return result;
	}
	
	/**
	 * 新增出库工单以及出库配件
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/newOutPart",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult newOutPart(@RequestBody OutPartComposite outPartComposite){
		ResponseResult result = ResponseResult.generateResponse();
		OutPart entity = outPartComposite.getOutPart();
		List<OutPartInfo> outPartInfos = outPartComposite.getOutPartInfos();
		
		//定义一个默认事务，事务隔离、传播等都是默认
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
		
		try {
			/*
			 * 新增出库工单
			 */
			entity = service.add(entity);
			//新增出库配件集合
			if(entity == null)
				return ResponseResult.generateErrorResponse("", "新增出库工单失败！");
			
			/*
			 * 新增出库配件
			 */
			//设置出库配件的工单号
			for (OutPartInfo outPartInfo : outPartInfos) {
				outPartInfo.setWorkOrderNo(entity.getWorkOrderNo());
			}
			SpringContextHolder.getBean(OutPartInfoService.class).add(outPartInfos);
			
			/*
			 * 配件出库时，数据库的触发器会减少对应配件的库存。最后校验减少后库存的数目
			 */
			for (OutPartInfo outPartInfo : outPartInfos) {
				String inventoryId = outPartInfo.getInventoryId();
				Inventory inventory = SpringContextHolder.getBean(InventoryService.class).getById(inventoryId);
				Part part = SpringContextHolder.getBean(PartService.class).getById(inventory.getPartId());
				//如果商品出库后，库存小于0，事务回滚
				if(inventory.getCount() < 0){
					transactionManager.rollback(status);
					return ResponseResult.generateErrorResponse("", "库存商品【"+part.getName()+"】库存不足，无法出库");
				}
				//如果商品出库后，库存等于0，删除书屋
				else if(inventory.getCount() == 0){
					SpringContextHolder.getBean(InventoryService.class).remove(inventory);
				}
			}
			
			//最后提交事务
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			return ResponseResult.generateErrorResponse("", "出库失败，请重新提交");
		}
		

		return result;
	}
	
	/**
	 * 删除入库工单以及级联的入库配件
	 * @author wsz 2017-06-27
	 */
	@RequestMapping(value = "/removeOutPart",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult removeOutPart(String id){
		OutPart entity = service.getById(id);
		if(entity == null)
			return ResponseResult.generateErrorResponse("", "该id没有查到数据");
		//删除入库工单
		Boolean rs = service.remove(entity);
		if(!rs)
			return ResponseResult.generateErrorResponse("", "删除失败");
		//删除出库工单对应的出库配件
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPartInfo.DP.workOrderNo.name(), SearchOperator.eq, entity.getWorkOrderNo());
		SpringContextHolder.getBean(OutPartInfoService.class).removeBy(searchable);

		//返回成功结果
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
	/**
	 * 批量级联删除删除
	 * @param entity
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removeOutParts",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult removeOutParts(String[] ids){
		//批量删除入库工单
		List<String> idsList = Arrays.asList(ids);
		List<OutPart> outparts = service.getByIds(idsList);
		long rs = service.remove(outparts);
		
		if(rs != idsList.size())
			return ResponseResult.generateErrorResponse("", "删除失败");
		
		//批量删除入库工单对应的入库配件信息
		List<String> workorders = CollectionUtils.extractToList(outparts, OutPart.DP.workOrderNo.name(), true);
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPartInfo.DP.workOrderNo.name(), SearchOperator.in, workorders);
		SpringContextHolder.getBean(OutPartInfoService.class).removeBy(searchable);
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
}