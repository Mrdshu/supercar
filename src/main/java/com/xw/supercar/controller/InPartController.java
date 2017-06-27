package com.xw.supercar.controller;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.constant.DaoConstant;
import com.xw.supercar.entity.InPart;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.entity.composite.InPartComposite;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.InPartInfoService;
import com.xw.supercar.service.InPartService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

/**
 * 入库工单controller层
 * @author wsz 2017-06-26
 */
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
	public ResponseResult newEntity(@RequestBody InPartComposite inPartComposite){
		ResponseResult result = ResponseResult.generateResponse();
		InPart entity = inPartComposite.getInPart();
		List<InPartInfo> inPartInfos = inPartComposite.getInpartInfos();
		//新增入库工单
		entity = service.add(entity);
		//新增入库配件集合
		if(entity != null)
			SpringContextHolder.getBean(InPartInfoService.class).add(inPartInfos);
		
		return result;
	}
	
	/**
	 * 查看指定入库工单的配件信息
	 * @param inWorkOrderNo
	 * @return 指定入库工单的配件信息
	 *
	 * @author wsz 2017-06-26
	 */
	@RequestMapping(value = "/getInPartInfos",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getInPartInfos(String inWorkOrderNo){
		ResponseResult result = ResponseResult.generateResponse();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(InPartInfo.DP.workOrderNo.name(), SearchOperator.eq, inWorkOrderNo);
		
		Page<InPartInfo> inPartInfos = SpringContextHolder.getBean(InPartInfoService.class).findPage(searchable, true);
		result.addAttribute("page", inPartInfos);
		return result;
	}
	
	@RequestMapping(value = "/removeInPart",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult removeInPart(String id){
		InPart entity = service.getById(id);
		if(entity == null)
			return ResponseResult.generateErrorResponse("", "该id没有查到数据");
		//删除入库工单
		Boolean rs = service.remove(entity);
		if(!rs)
			return ResponseResult.generateErrorResponse("", "删除失败");
		//删除入库工单对应的入库配件
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(InPartInfo.DP.workOrderNo.name(), SearchOperator.eq, entity.getWorkOrderNo());
		long deleteCount = SpringContextHolder.getBean(InPartInfoService.class).removeBy(searchable);
		if(deleteCount <= 0)
			return ResponseResult.generateErrorResponse("", "删除失败");
		//返回成功结果
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
	/**
	 * 批量删除
	 * @param entity
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removeInParts",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@Transactional
	public ResponseResult removeInParts(String[] ids){
		//批量删除入库工单
		List<String> idsList = Arrays.asList(ids);
		long rs = getSevice().removeByIds(idsList);
		
		if(rs != idsList.size())
			return ResponseResult.generateErrorResponse("", "删除失败");
		
		//批量删除入库工单对应的入库配件信息
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(InPartInfo.DP.workOrderNo.name(), SearchOperator.in, idsList);
		long deleteCount = SpringContextHolder.getBean(InPartInfoService.class).removeBy(searchable);
		
		if(deleteCount != ids.length){
			return ResponseResult.generateErrorResponse("", "删除失败");
		}
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}

}
