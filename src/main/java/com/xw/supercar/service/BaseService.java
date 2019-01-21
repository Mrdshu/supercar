package com.xw.supercar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.xw.supercar.constant.DaoConstant;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.entity.BaseDateEntity;
import com.xw.supercar.entity.BaseEntity;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.ReflectUtil;

/**
 * Service层的基础类，实现了基础的增、删、改、查(add、remove、modify、find与get)方法。
 * 继承即可使用（需指定泛型为对应实体）
 * 
 * @author wangsz 2017-05-14
 */
public abstract class BaseService<E extends BaseEntity> implements InitializingBean{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private BaseDao<E> baseDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		baseDao = getDao();
		if(baseDao == null)
			throw new IllegalArgumentException("initial "+this.getClass().getName()+"'s bao fail");
	}
	
	/**
	 * 由子类实现，返回子类的dao
	 * 该方法必须实现，父类通过该方法获取到service对应的操作类dao，从而完成对应实体类的增删改查
	 * @author  wangsz 2017-05-14
	 */
	protected abstract BaseDao<E> getDao();
	
	/**
	 * 插入前的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void beforeAdd(E entity) {}
	/**
	 * 批量插入前的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void beforeAdd(List<E> entitys) {
		for (E e : entitys) {
			beforeAdd(e);
		}
	}
	/**
	 * 插入后的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void afterAdd(E entity) {}
	/**
	 * 删除前的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void beforeRemove(E entity) {}
	/**
	 * 删除后的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void afterRemove(E entity) {}
	/**
	 * 修改前的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void beforeModify(E entity) {}
	/**
	 * 修改后的操作，可由子类覆盖实现具体步骤
	 * @author  wangsz 2017-05-14
	 */
	protected void afterModify(E entity) {}
	/**
	 * 查询后的操作(单个查询)，可由子类覆盖实现具体步骤
	 * @param entity
	 * @author  wangsz 2017-05-16
	 */
	protected void afterSelect(E entity){}
	/**
	 * 查询后的操作（多个查询）
	 * @param entity
	 * @author  wangsz 2017-05-16
	 */
	protected void afterSelect(List<E> entitys){
		for (E e : entitys) {
			afterSelect(e);
		}
	}
	
	/**
	 * 新增
	 * @author  wangsz 2017-05-14
	 */
	public E add(E entity){
		String id = entity.getId();
		//如果id未被设置，则采用uuid自动生成的16位随机数
		if(StringUtils.isEmpty(id)){
			id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			entity.setId(id);
		}
		beforeAdd(entity);
		
		baseDao.insert(entity);
		
		entity = getById(id);
		afterAdd(entity);
		return entity;
	}
	
	/**
	 * 新增
	 * @author  wangsz 2017-05-14
	 */
	public List<E> add(List<E> entitys){
		beforeAdd(entitys);
		List<String> ids = new ArrayList<>();
		
		for (E entity : entitys) {
			String id = entity.getId();
			//如果id未被设置，则采用uuid自动生成的16位随机数
			if(StringUtils.isEmpty(id)){
				id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				entity.setId(id);
			}
			ids.add(id);
		}
		
		baseDao.insert(entitys);
		
		entitys = getByIds(ids);
		return entitys;
	}
	
	/**
	 * 根据实体类软删除，该实体类id不能为空
	 * @author  wangsz 2017-05-14
	 */
	public boolean remove(E entity) {
		beforeRemove(entity);
		boolean rs = baseDao.delete(entity);
		afterRemove(entity);
		return rs;
	}
	
	/**
	 * 批量删除
	 * @author  wangsz 2017-06-13
	 */
	public long remove(List<E> entitys){
		long rs = 0;
		
		List<String> ids = new ArrayList<>();
		for (E e : entitys) {
			String id = e.getId();
			if(!StringUtils.isEmpty(id))
				ids.add(id);
		}
		
		if (ids.size() > 0) {
			Searchable searchable = Searchable.newSearchable().addSearchFilter(DaoConstant.NAME_ID, SearchOperator.in, ids);
			rs = getDao().deleteBy(searchable);
		}
		
		return rs;
	}
	
	/**
	 * 条件批量删除
	 * @author  wangsz 2017-06-13
	 */
	public long removeBy(Searchable searchable){
		long rs = getDao().deleteBy(searchable);
		
		return rs;
	}
	
	/**
	 * 根据id软删除
	 * @author  wangsz 2017-05-14
	 */
	public boolean removeById(String id) {
		E entity = getById(id);
		if(entity == null)
			throw new IllegalArgumentException("can't find the entity of id:"+id);
		
		beforeRemove(entity);
		boolean rs = baseDao.deleteById(id);
		afterRemove(entity);
		
		return rs;
	}
	
	/**
	 * 根据id集合，批量删除
	 * @author  wangsz 2017-06-13
	 */
	public long removeByIds(List<String> ids){
		long rs = 0;
		
		if (ids != null && ids.size() > 0) {
			Searchable searchable = Searchable.newSearchable().addSearchFilter(DaoConstant.NAME_ID, SearchOperator.in, ids);
			rs = getDao().deleteBy(searchable);
		}
		
		return rs;
	}
	
	/**
	 * 根据实体类硬删除，该实体类id不能为空
	 * @author  wangsz 2017-05-14
	 */
	public boolean hardRemove(E entity) {
		beforeRemove(entity);
		boolean rs = baseDao.hardDelete(entity);
		afterRemove(entity);
		return rs;
	}
	
	/**
	 * 根据id硬删除
	 * @author  wangsz 2017-05-14
	 */
	public boolean hardRemoveById(String id) {
		E entity = getById(id);
		if(entity == null)
			throw new IllegalArgumentException("can't find the entity of id:"+id);
		
		beforeRemove(entity);
		boolean rs = baseDao.hardDeleteById(id);
		afterRemove(entity);
		
		return rs;
	}
	
	/**
	 * 修改
	 * @author  wangsz 2017-05-14
	 */
	public E modify(E entity) {
		beforeModify(entity);
		boolean rs = baseDao.update(entity);
		if(!rs){
			log.error("modify entity【"+entity.toString()+"】 fail");
		}

		entity = getById(entity.getId());
		afterModify(entity);
		return entity;
	}
	
	/**
	 * 根据id查询
	 * @author  wangsz 2017-05-14
	 */
	public E getById(String id){
		E entity =  baseDao.selectById(id);
		afterSelect(entity);
		
		return entity;
	}
	
	/**
	 * 根据id集合查询
	 * @author  wangsz 2017-05-14
	 */
	public List<E> getByIds(List<String> ids){
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(DaoConstant.NAME_ID, SearchOperator.in, ids);
		
		List<E> entitys =  baseDao.selectBy(searchable, true);
		
		for (E entity : entitys) {
			afterSelect(entity);
		}
		
		return entitys;
	}
	
	/**
	 * 根据查询条件查询
	 * @param searchable 查询条件
	 * @param useDefaultFilters 默认过滤条件是否开启
	 * @param check 为true时检查复核条件的结果条数，如果超过一条抛出异常
	 * @return
	 *
	 * @author wsz 2017-06-18
	 */
	public E getBy(Searchable searchable, Boolean useDefaultFilters, Boolean check){
		List<E> entitys =   baseDao.selectBy(searchable, useDefaultFilters);
		if(entitys == null || entitys.size() <= 0)
			return null;
		if(check && entitys.size() > 1)
			throw new IllegalArgumentException("getBy method find more than 1 row record");
		
		E entity = entitys.get(0);
		afterSelect(entity);
		
		return entity;
	}
	
	/**
	 * 根据过滤条件查询
	 * @author  wangsz 2017-05-14
	 */
	public List<E> findBy(Searchable searchable,boolean useDefaultFilters){
		//TODO --过滤条件为空时，会把所有数据查出来。此处要做控制，超过500条时只返回前500条数据
		List<E> entitys = baseDao.selectBy(searchable, useDefaultFilters);
		afterSelect(entitys);
		
		return entitys;
	}
	
	/**
	 * 根据过滤条件查询，此查询会返回关联属性。
	 * 注意！此方法使用要确定已在mapper文件写好 自定义关联查询sql
	 * @author  wangsz 2017-05-14
	 */
	public List<E> extendFindBy(Searchable searchable,boolean useDefaultFilters){
		//TODO --过滤条件为空时，会把所有数据查出来。此处要做控制，超过500条时只返回前500条数据
		List<E> entitys = baseDao.extendSelectBy(searchable, useDefaultFilters);
		afterSelect(entitys);
		
		return entitys;
	}
	
	/**
	 * 分页查询
	 * @author  wangsz 2017-05-16
	 */
	public Page<E> findPage(Searchable searchable,boolean useDefaultFilters){
		Page<E> page = baseDao.selectPage(searchable, useDefaultFilters);
		
		List<E> entitys = page.getContent();
		afterSelect(entitys);
		
		return page;
	}
	
	/**
	 * 分页查询，，此查询会返回关联属性
	 * 注意！此方法使用要确定已在mapper文件写好 自定义关联查询sql
	 * @author  wangsz 2017-05-16
	 */
	public Page<E> extendFindPage(Searchable searchable,boolean useDefaultFilters){
		Page<E> page = baseDao.extendSelectPage(searchable, useDefaultFilters);
		
		List<E> entitys = page.getContent();
		afterSelect(entitys);
		
		return page;
	}
	/**
	 * 将实体多个成员变量外键对应的对象增加进扩展map中
	 *
	 * @author wsz 2017-07-27
	 */
	public void addAttributesToExtendInfo(Map<String, Map<String, Object>> extendInfo, List<? extends BaseEntity> entities, String[] attributesName,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		for (int i = 0; i < attributesName.length; i++) {
			addAttributeToExtendInfo(extendInfo, entities, attributesName[i], attributeServicesClazz[i]);
		}
	}
	
	/**
	 * 将实体成员变量外键对应的对象增加进扩展map中
	 * 
	 * @param extendInfo 扩展属性map集合， key 实体类外键成员变量名称； value id-id对应的实体 map集合
	 * @param entities 实体集合
	 * @param attributeName 实体外键变量的名称
	 * @param attributeServiceClazz 实体外键对应的service的class
	 *
	 * @author wsz 2017-07-27
	 */
	public void addAttributeToExtendInfo(Map<String, Map<String, Object>> extendInfo, List<? extends BaseEntity> entities, String attributeName,Class<? extends BaseService<?>> attributeServiceClazz) {
		Set<String> attributesId = new HashSet<>();
		//获取实体集合对应成员变量的id集合
		for (BaseEntity entity : entities) {
			String attributeId = ReflectUtil.getPropertyValue(entity, attributeName);
			attributesId.add(attributeId);
		}
		//如果extendInfo中不存在该成员变量对应的map，创建一个
		Map<String, Object> attributeInfo = extendInfo.get(attributeName);
		if(attributeInfo == null){
			attributeInfo = new HashMap<>();
			extendInfo.put(attributeName, attributeInfo);
		}
		//获取成员变量id对应的实体，放入map中
		for (String attributeId : attributesId) {
			if(attributeInfo.get(attributeId) != null) continue;
			
			Object attribute = SpringContextHolder.getBean(attributeServiceClazz).getById(attributeId);
			attributeInfo.put(attributeId, attribute);
		}
	}
	
	/**
	 * 将实体成员变量多个外键对应的对象放入Data中
	 * @param object 实体类
	 * @param attributeNames 外键名数组
	 * @param attributeServicesClazz 外键对应的service的class数组
	 * @author  wangsz 2017-06-04
	 */
	public void addAttributesToData(BaseDateEntity object, String[] attributeNames,Class<? extends BaseService<?>>[] attributeServicesClazz) {
		if(object == null)
			return ;
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
	 * @param attributeServiceClass
	 * @author  wangsz 2017-06-04
	 */
	public void addAttributeToData(BaseDateEntity object, String attributeName,Class<? extends BaseService<?>> attributeServiceClass) {
 		String attributeId = ReflectUtil.getPropertyValue(object, attributeName);
		//如果该外键为空，返回
		if(StringUtils.isEmpty(attributeId))
			return ;
		Object type = SpringContextHolder.getBean(attributeServiceClass).getById(attributeId);
		
		object.getDate().put(attributeName, type);
	}
}