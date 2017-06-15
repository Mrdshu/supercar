package com.xw.supercar.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xw.supercar.constant.DaoConstant;
import com.xw.supercar.entity.BaseDateEntity;
import com.xw.supercar.entity.BaseEntity;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.ReflectUtil;

public abstract class BaseController<E extends BaseEntity> implements InitializingBean {
	protected final Logger log = Logger.getLogger(this.getClass());
	
	/**controller的url前缀*/
	protected String viewPrefix;
	protected String parentPath = "";
	
	protected abstract BaseService<E> getSevice();
	
	/**
	 * 当前模块 视图的前缀
	 * 默认
	 * 1、获取当前类头上的@RequestMapping中的value作为前缀
	 * 2、如果没有就使用当前模型小写的简单类名
	 */
	public void setViewPrefix(String viewPrefix) {
		if(!viewPrefix.startsWith("/")) {
			viewPrefix = "/" + viewPrefix;
		}
		this.viewPrefix = viewPrefix;
	}

	public String getViewPrefix() {
		return viewPrefix;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.setViewPrefix(this.getDefaultViewPrefix());
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
		if (requestMapping != null && requestMapping.value().length > 0) {
			this.parentPath = requestMapping.value()[0];
		}
	}

	protected String getDefaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
		if (requestMapping != null && requestMapping.value().length > 0) {
			currentViewPrefix = requestMapping.value()[0];
		}
		if (currentViewPrefix == null || "".equals(currentViewPrefix)) {
			currentViewPrefix = this.getClass().getSimpleName().toLowerCase();
			if (currentViewPrefix.endsWith("controller")) currentViewPrefix = currentViewPrefix.substring(0, currentViewPrefix.length() - "controller".length());
		}
		if (!currentViewPrefix.startsWith("/")) currentViewPrefix = "/" + currentViewPrefix;

		return currentViewPrefix;
	}
	
	/**
	 * 分页查询
	 * @param pageNo 第几页，为空时为默认值
	 * @param pageSize 页大小，为空时为默认值
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/page",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult page(Searchable searchable){
		if(searchable == null)
			searchable = Searchable.newSearchable()
				.addPage(DaoConstant.DEFAULT_PAGE_NUMBER, DaoConstant.DEFAULT_PAGE_SIZE);
		Page<E> page = getSevice().searchPage(searchable, true);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("page", page);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
	/**
	 * 查询多条数据
	 * @param searchable
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/get_list",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getList(Searchable searchable){
		List<E> entitys = getSevice().searchBy(searchable, true);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("entitys", entitys);
		//进行后后处理
		afterReturn(result);
		
		return result;
	}
	
	/**
	 * 根据id查询数据
	 * @param searchable
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/getBy_id",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getById(@NotNull String id){
		
		E entity = getSevice().searchById(id);
		//生成返回实体类
		ResponseResult result = ResponseResult.generateResponse();
		result.addAttribute("entity", entity);
		//进行后处理
		afterReturn(result);
		
		return result;
	}
	
	/**
	 * 新增
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/new",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult newEntity(E entity){
		E afterInsertEntity = getSevice().add(entity);
		
		if(afterInsertEntity == null)
			return ResponseResult.generateErrorResponse("", "新增失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("新增成功！");
		
		return result;
	}
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/remove",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult remove(E entity){
		Boolean rs = getSevice().remove(entity);
		
		if(!rs)
			return ResponseResult.generateErrorResponse("", "删除失败");
		
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
	@RequestMapping(value = "/removes",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult removes(@RequestBody List<E> entitys){
		long rs = getSevice().removeBy(entitys);
		
		if(rs != entitys.size())
			return ResponseResult.generateErrorResponse("", "删除失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removeById",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult removeById(String id){
		Boolean rs = getSevice().removeById(id);
		
		if(!rs)
			return ResponseResult.generateErrorResponse("", "删除失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
	/**
	 * 根据id集合批量删除
	 * @param id
	 * @return
	 * @author  wangsz 2017-06-04
	 */
//	@RequestMapping(value = "/removeByIds",produces={MediaType.APPLICATION_JSON_VALUE})
//	@ResponseBody
//	public ResponseResult removeByIds(@RequestBody List<String> ids){
//		long rs = getSevice().removeByIds(ids);
//		
//		if(rs != ids.size())
//			return ResponseResult.generateErrorResponse("", "删除失败");
//		
//		ResponseResult result = ResponseResult.generateResponse();
//		result.setErrorMsg("删除成功！");
//		
//		return result;
//	}
	
	/**
	 * 根据id集合批量删除
	 * @param id
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removeByIds",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult removeByIds(String[] ids){
		List<String> idsList = Arrays.asList(ids);
		long rs = getSevice().removeByIds(idsList);
		
		if(rs != idsList.size())
			return ResponseResult.generateErrorResponse("", "删除失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("删除成功！");
		
		return result;
	}
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult edit(E entity){
		E afterModifyEntity = getSevice().modify(entity);
		
		if(afterModifyEntity == null)
			return ResponseResult.generateErrorResponse("", "修改失败");
		
		ResponseResult result = ResponseResult.generateResponse();
		result.setErrorMsg("修改成功！");
		
		return result;
	}
	
	/**
	 * 查询后处理方法。子类可覆盖进行后处理
	 * @author  wangsz 2017-06-04
	 */
	protected void afterReturn(ResponseResult result) {}
	
	/**
	 * 将实体成员变量外键对应的对象放入Data中
	 * @param object
	 * @param attributeName
	 * @param attributeService
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributeToData(BaseDateEntity object, String attributeName,Class<? extends BaseService> attributeService) {
		String attributeId = ReflectUtil.getPropertyValue(object, attributeName);
		Object type = SpringContextHolder.getBean(BaseService.class).searchById(attributeId);
		
		object.getDate().put("type", type);
	}
	
}
