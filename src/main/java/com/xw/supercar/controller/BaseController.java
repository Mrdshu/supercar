package com.xw.supercar.controller;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.constant.DaoConstant;
import com.xw.supercar.entity.BaseDateEntity;
import com.xw.supercar.entity.BaseEntity;
import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.Searchable;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;
/**
 * Controller层的基础类，实现了基础的增、删、改、查(new、remove、edit、list(get) )方法。
 * 继承即可使用（需指定泛型为对应实体）
 * 
 * @author wangsz 2017-05-14
 */
@CrossOrigin
public abstract class BaseController<E extends BaseEntity> implements InitializingBean {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获取controller层对应的service层对象
	 * @author wsz 2017-06-18
	 */
	protected abstract BaseService<E> getSevice();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	/**
	 * 分页查询
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/page",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@ApiOperation(httpMethod = "GET", value = "分页查询")
	public ResponseResult page(@SearchableDefaults Searchable searchable){
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
	@ApiOperation(httpMethod = "GET", value = "查询多条数据")
	public ResponseResult list(@SearchableDefaults Searchable searchable){
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
	 * @param id
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/getById",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "根据id查询数据")
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
	@ApiOperation(httpMethod = "POST", value = "新增")
	public ResponseResult newEntity(E entity){
		ResponseResult result = beforeNew(entity);
		if(!result.getSuccess())
			return result;
		
		E afterInsertEntity = getSevice().add(entity);
		
		if(afterInsertEntity == null)
			return ResponseResult.generateErrorResponse("", "新增失败");
		
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
	@ApiOperation(httpMethod = "POST", value = "删除")
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
	 * @param entitys
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removes",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@ApiOperation(httpMethod = "GET", value = "批量删除")
	public ResponseResult removes(@RequestBody List<E> entitys){
		long rs = getSevice().remove(entitys);
		
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
	@ApiOperation(httpMethod = "GET", value = "根据id删除")
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
	 * @param ids
	 * @return
	 * @author  wangsz 2017-06-04
	 */
	@RequestMapping(value = "/removeByIds",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	@ApiOperation(httpMethod = "GET", value = "根据id集合批量删除")
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
	@ApiOperation(httpMethod = "POST", value = "修改")
	public ResponseResult edit(E entity){
		E afterModifyEntity = getSevice().modify(entity);
		
		if(afterModifyEntity == null) {
			return ResponseResult.generateErrorResponse("", "修改失败");
		}
		
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
	 * 插入前处理方法，主要用于插入数据校验。子类可覆盖进行后处理
	 * @author  wangsz 2017-06-04
	 * @return 
	 */
	protected ResponseResult beforeNew(E entity) {
		return ResponseResult.generateResponse();
	}
	
	/**
	 * 将data数组中实体成员类的变量多个外键对应的对象放入Data中
	 * @param data map集合，实体类存放其中
	 * @param attributesName 外键名数组
	 * @param attributeServicesClazz 外键对应的service的class数组
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributesToData(Map<String, Object> data, String[] attributesName,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		if(attributesName.length != attributeServicesClazz.length)
			throw new IllegalArgumentException("attributeNames length must equal attributeServicesClazz length");
		
		if(data.containsKey("entity")){
			BaseDateEntity entity = (BaseDateEntity) data.get("entity");
			getSevice().addAttributesToData(entity, attributesName, attributeServicesClazz);
		}
		else if(data.containsKey("entitys")){
			@SuppressWarnings("unchecked")
			List<? extends BaseDateEntity> entities = (List<? extends BaseDateEntity>) data.get("entitys");
			for (BaseDateEntity entity : entities) {
				getSevice().addAttributesToData(entity, attributesName, attributeServicesClazz);
			}
		}
		else if(data.containsKey("page")){
			@SuppressWarnings("unchecked")
			Page<? extends BaseDateEntity> page = (Page<? extends BaseDateEntity>) data.get("page");
			List<? extends BaseDateEntity> entities = (List<? extends BaseDateEntity>) page.getContent();
			for (BaseDateEntity entity : entities) {
				getSevice().addAttributesToData(entity, attributesName, attributeServicesClazz);
			}
		}
	}
	
	/**
	 * 将data数组中实体成员类的变量多个外键对应的对象放入Data中
	 * @param result map集合，实体类存放其中
	 * @param attributesName 外键名数组
	 * @param attributeServicesClazz 外键对应的service的class数组
	 * @author  wangsz 2017-06-04
	 */
	protected void addAttributesToExtendInfo(ResponseResult result, String[] attributesName,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		if(attributesName.length != attributeServicesClazz.length)
			throw new IllegalArgumentException("attributeNames length must equal attributeServicesClazz length");
		
		Map<String, Object> data = result.getData();
		Map<String, Map<String, Object>> extendInfo = result.getExtendInfo();
		if(extendInfo == null) {
			extendInfo = new HashMap<>();
			result.setExtendInfo(extendInfo);
		}
		
		if(data.containsKey("entity")){
			BaseEntity entity = (BaseEntity) data.get("entity");
			List<BaseEntity> entities = new ArrayList<>();
			entities.add(entity);
			
			getSevice().addAttributesToExtendInfo(extendInfo, entities, attributesName, attributeServicesClazz);
		}
		else if(data.containsKey("entitys")){
			@SuppressWarnings("unchecked")
			List<? extends BaseEntity> entities = (List<? extends BaseEntity>) data.get("entitys");
			getSevice().addAttributesToExtendInfo(extendInfo, entities, attributesName, attributeServicesClazz);
			
		}
		else if(data.containsKey("page")){
			@SuppressWarnings("unchecked")
			Page<? extends BaseEntity> page = (Page<? extends BaseEntity>) data.get("page");
			List<? extends BaseEntity> entities = (List<? extends BaseEntity>) page.getContent();
			getSevice().addAttributesToExtendInfo(extendInfo, entities, attributesName, attributeServicesClazz);
		}
	}
}
