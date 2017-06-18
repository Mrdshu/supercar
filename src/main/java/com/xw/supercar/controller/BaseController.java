package com.xw.supercar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.LookupService;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.ReflectUtil;
/**
 * Controller层的基础类，实现了基础的增、删、改、查(new、remove、edit、list(get)方法。
 * 继承即可使用（需指定泛型为对应实体）
 * 
 * @author wangsz 2017-05-14
 */
public abstract class BaseController<E extends BaseEntity> implements InitializingBean {
	protected final Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 获取controller层对应的service层对象
	 * @author wsz 2017-06-18
	 */
	protected abstract BaseService<E> getSevice();
	
	public void afterPropertiesSet() throws Exception {
		
	}

	//================注释内容为controller转发请求用的，现在前后端分离，故此项目不需要=================
//	/**controller的url前缀*/
//	protected String viewPrefix;
//	protected String parentPath = "";
//	

//	
//	/**
//	 * 当前模块 视图的前缀
//	 * 默认
//	 * 1、获取当前类头上的@RequestMapping中的value作为前缀
//	 * 2、如果没有就使用当前模型小写的简单类名
//	 */
//	public void setViewPrefix(String viewPrefix) {
//		if(!viewPrefix.startsWith("/")) {
//			viewPrefix = "/" + viewPrefix;
//		}
//		this.viewPrefix = viewPrefix;
//	}
//
//	public String getViewPrefix() {
//		return viewPrefix;
//	}
//	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		this.setViewPrefix(this.getDefaultViewPrefix());
//		RequestMapping requestMapping = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
//		if (requestMapping != null && requestMapping.value().length > 0) {
//			this.parentPath = requestMapping.value()[0];
//		}
//	}
//
//	protected String getDefaultViewPrefix() {
//		String currentViewPrefix = "";
//		RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
//		if (requestMapping != null && requestMapping.value().length > 0) {
//			currentViewPrefix = requestMapping.value()[0];
//		}
//		if (currentViewPrefix == null || "".equals(currentViewPrefix)) {
//			currentViewPrefix = this.getClass().getSimpleName().toLowerCase();
//			if (currentViewPrefix.endsWith("controller")) currentViewPrefix = currentViewPrefix.substring(0, currentViewPrefix.length() - "controller".length());
//		}
//		if (!currentViewPrefix.startsWith("/")) currentViewPrefix = "/" + currentViewPrefix;
//
//		return currentViewPrefix;
//	}
	
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
		Page<E> page = getSevice().findPage(searchable, true);
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
	@RequestMapping(value = "/list",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult list(Searchable searchable){
		List<E> entitys = getSevice().findBy(searchable, true);
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
	@RequestMapping(value = "/getById",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseResult getById(@NotNull String id){
		E entity = getSevice().getById(id);
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
	 * 将data数组中实体成员类的变量多个外键对应的对象放入Data中
	 * @param data map集合，实体类存放其中
	 * @param attributeNames 外键名数组
	 * @param attributeServicesClazz 外键对应的service的class数组
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributesToData(Map<String, Object> data, String[] attributeNames,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		if(attributeNames.length != attributeServicesClazz.length)
			throw new IllegalArgumentException("attributeNames length must equal attributeServicesClazz length");
		
		if(data.containsKey("entity")){
			BaseDateEntity entity = (BaseDateEntity) data.get("entity");
			addAttributesToData(entity, attributeNames, attributeServicesClazz);
		}
		else if(data.containsKey("entitys")){
			@SuppressWarnings("unchecked")
			List<? extends BaseDateEntity> entities = (List<? extends BaseDateEntity>) data.get("entitys");
			for (BaseDateEntity entity : entities) {
				addAttributesToData(entity, attributeNames, attributeServicesClazz);
			}
		}
		else if(data.containsKey("page")){
			@SuppressWarnings("unchecked")
			Page<? extends BaseDateEntity> page = (Page<? extends BaseDateEntity>) data.get("page");
			List<? extends BaseDateEntity> entities = (List<? extends BaseDateEntity>) page.getContent();
			for (BaseDateEntity entity : entities) {
				addAttributesToData(entity, attributeNames, attributeServicesClazz);
			}
		}
	}
	
	/**
	 * 将实体成员变量多个外键对应的对象放入Data中
	 * @param object 实体类
	 * @param attributeNames 外键名数组
	 * @param attributeServicesClazz 外键对应的service的class数组
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributesToData(BaseDateEntity object, String[] attributeNames,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		if(attributeNames.length != attributeServicesClazz.length)
			throw new IllegalArgumentException("attributeNames length must equal attributeServicesClazz length");
		
		for (int i = 0; i < attributeNames.length; i++) {
			addAttributeToData(object, attributeNames[i], attributeServicesClazz[i]);
		}
	}
	
	/**
	 * 将实体成员变量外键对应的对象放入Data中
	 * @param object 实体类
	 * @param attributeName 外键名
	 * @param attributeService 外键对应的service的class
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributeToData(BaseDateEntity object, String attributeName,Class<? extends BaseService<?>> attributeServiceClass) {
		String attributeId = ReflectUtil.getPropertyValue(object, attributeName);
		Object type = SpringContextHolder.getBean(attributeServiceClass).getById(attributeId);
		
		object.getDate().put(attributeName, type);
	}
	
}
