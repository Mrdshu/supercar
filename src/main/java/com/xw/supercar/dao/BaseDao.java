package com.xw.supercar.dao;

import static com.xw.supercar.constant.DaoConstant.NAME_ID;
import static com.xw.supercar.constant.DaoConstant.NAME_IS_DELETED;
import static com.xw.supercar.constant.DaoConstant.STMT_DELETE;
import static com.xw.supercar.constant.DaoConstant.STMT_SELECT_BY;
import static com.xw.supercar.constant.DaoConstant.STMT_INSERT;
import static com.xw.supercar.constant.DaoConstant.STMT_UPDATE;
import static com.xw.supercar.constant.DaoConstant.whereSqlCustomKey;
import static com.xw.supercar.constant.DaoConstant.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.xw.supercar.entity.BaseEntity;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.sql.search.SearchableConvertUtils;
import com.xw.supercar.util.ReflectUtil;


/**
 * Dao层的基础类，实现了基础的增、删、改、查(insert、delete、update、select)方法。
 * 继承即可使用（需指定泛型为对应实体）
 * 
 * @author wangsz 2017-05-11
 */
public abstract class BaseDao<E extends BaseEntity> implements InitializingBean{
	protected final Log log = LogFactory.getLog(this.getClass());
	/**mybatis的sqlSession模板类，由spring管理，自动打开和关闭资源*/
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**Dao层对应的实体类class*/
	private Class<E> entityClass;
	/**Dao层实体类的属性描述map集合*/
	private final Map<String, PropertyDescriptor> entityPropDescriptorMap = new LinkedHashMap<String, PropertyDescriptor>();
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	
	/**
	 * 初始化参数
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		//获取dao层对应的entity的class
		entityClass = (Class<E>) ReflectUtil.getSingleGenericClass(getClass());
		//获取entity的属性map,默认属性都需要有get()方法
		PropertyDescriptor[]  propertyDescriptors = ReflectUtil.getPropertyDescriptors(entityClass,false,true);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			entityPropDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);
		}
		if (!entityPropDescriptorMap.containsKey(NAME_ID)) {
	       	throw new IllegalArgumentException("Entity class does not have a primary key property.");
	    }
	}
	
	/**
	 * 新增
	 * @author  wangsz 2017-05-10
	 */
	public boolean insert(E entity){
		Assert.notNull(entity,"'entity' can't be null");
		boolean result = false;
		result = getSqlSessionTemplate().insert(getInsertStatement(), entity) == 1;
		
		return result;
	}
	
	/**
	 * 修改
	 * @author  wangsz 2017-05-10
	 */
	public boolean update(E entity){
		Assert.notNull(entity,"'entity' can't be null");
		boolean result = false;
		//更新时id不能为空
		String id = getId(entity);
		if (id == null || "".equals(id)) 
			throw new IllegalArgumentException("'entity.id' must be not empty");
		
		result = getSqlSessionTemplate().update(getUpdateStatement(), entity) == 1;
		return result;
	}
	
	/**
	 * 批量修改
	 * @author  wangsz 2017-05-12
	 */
	public long updateBy(List<E> entitys){
		long result = 0;
		
		for (E e : entitys) {
			if(update(e))
				result ++;
		}
		
		return result;
	}
	
	/**
	 * 批量修改
	 * @author  wangsz 2017-05-12
	 */
	public long updateBy(Searchable searchable,Map<String, Object> setParams){
		long result = 0;
		//如果过滤条件为空，直接返回
		if(searchable == null || searchable.isEmpty())
			return result;
		
		E newEntity = null;
		Boolean isUpdate = false;
		try {
			newEntity = entityClass.newInstance();
			for (Entry<String, Object> setParam : setParams.entrySet()) {
				String paramName = setParam.getKey();
				if(!entityPropDescriptorMap.containsKey(paramName))
					continue;
				isUpdate = true;
				Object value = setParam.getValue();
				ReflectUtil.setPropertyValue(newEntity, paramName, value);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//如果没有修改到实体类，直接返回
		if(!isUpdate)
			return 0;
		
		Map<String, Object> params = convertToMap(entityClass, null, searchable, newEntity, null);
		result = getSqlSessionTemplate().update(getUpdateByStatement(), params);
		
		return result;
	}
	
	/**
	 * 软删除
	 * @author  wangsz 2017-05-11
	 */
	public boolean delete(E entity) {
		Assert.notNull(entity,"'entity' can't be null");
		
		String id = getId(entity);
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("'entity.id' must be not empty");
		/*
		 * 软删除其实就是更新isDeleted字段为true的过程
		 */
		//如果该实体没有'idDeleted'字段，则默认为硬删除
		PropertyDescriptor isDeletedProp = entityPropDescriptorMap.get(NAME_IS_DELETED);
		if(isDeletedProp == null)
			return hardDeleteById(id);
		
		boolean result = false;
		try {
			E newEntity = entityClass.newInstance();
			ReflectUtil.setPropertyValue(newEntity, NAME_IS_DELETED, true);
			ReflectUtil.setPropertyValue(newEntity, NAME_ID, id);
			result = update(newEntity);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 根据id软删除
	 * @param id
	 * @return
	 * @author  wangsz 2017-05-11
	 */
	public boolean deleteById(String id) {
		//id不能为空
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("entity's id can't be empty");
		//id必须可查到对应的实体
		E deleteEntity = selectById(id);
		if(deleteEntity == null)
			throw new IllegalArgumentException("can't find the entity of id");
		
		return delete(deleteEntity);
	}
	
	/**
	 * 根据搜索条件软删除
	 * @author  wangsz 2017-05-12
	 */
	public long deleteBy(Searchable searchable){
		long result = 0;
		//如果过滤条件为空，直接返回
		if(searchable == null || searchable.isEmpty())
			return result;
		
		PropertyDescriptor isDeletedProp = entityPropDescriptorMap.get(NAME_IS_DELETED);
		//如果该实体没有'idDeleted'字段，则默认为硬删除
		if(isDeletedProp == null)
			result = hardDeleteBy(searchable);
		else{
			//批量软删除
			Map<String, Object>  setParams = new HashMap<>();
			setParams.put(NAME_IS_DELETED, true);
			result = updateBy(searchable, setParams);
		}
		
		return result;
	}
	
	/**
	 * 硬删除
	 * @author  wangsz 2017-05-11
	 */
	public boolean hardDelete(E entity){
		Assert.notNull(entity,"'entity' can't be null");
		
		String id = getId(entity);
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("'entity.id' must be not empty");
		
		boolean result = getSqlSessionTemplate().delete(getDeleteStatement(), id) == 1;
		return result;
	}
	
	/**
	 * 根据搜索条件硬删除
	 * @author  wangsz 2017-05-11
	 */
	public long hardDeleteBy(Searchable searchable){
		long result = 0;
		//如果过滤条件为空，直接返回
		if(searchable == null || searchable.isEmpty())
			return result;
		
		//获取过滤条件map
		Map<String, Object> parameter = convertToMap(entityClass, null, searchable, null, null);
		result = getSqlSessionTemplate().delete(getDeleteByStatement(), parameter);
		
		return result;
	}
	
	/**
	 * 根据id硬删除
	 * @param id
	 * @return
	 * @author  wangsz 2017-05-11
	 */
	public boolean hardDeleteById(String id) {
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("entity's id can't be empty");
		
		E deleteEntity = selectById(id);
		if(deleteEntity == null)
			throw new IllegalArgumentException("can't find the entity of id");
		
		boolean result = hardDelete(deleteEntity);
		return result;
	}
	
	/**
	 * 根据id获取实体
	 * @author  wangsz 2017-05-11
	 */
	public E selectById(String id){
		if(StringUtils.isEmpty(id))
			throw new IllegalArgumentException("entity's id can't be empty");
		//mapper文件中操作对应的声明
		String statement = getFindStatement();
		//过滤条件map
		Map<String, Object> parameter = convertToMap(entityClass, null, null, null, id);
		E result = getSqlSessionTemplate().selectOne(statement, parameter);
		return result;
	}
	
	/**
	 * 根据条件查询-searchable
	 * @author  wangsz 2017-05-12
	 */
	public List<E> selectBy(Searchable searchable, boolean useDefaultFilters) {
		return this.selectBy(getFindStatement(), searchable, null, useDefaultFilters);
	}
	
	/**
	 * 根据条件查询-entity
	 * @author  wangsz 2017-05-12
	 */
	public List<E> selectBy(E entity, boolean useDefaultFilters) {
		return this.selectBy(getFindStatement(), null, entity, useDefaultFilters);
	}
	
	/**
	 * 根据条件查询-entity和searchable
	 * @author  wangsz 2017-05-12
	 */
	public List<E> selectBy(Searchable searchable, E entity, boolean useDefaultFilters) {
		return this.selectBy(getFindStatement(), searchable, entity, useDefaultFilters);
	}
	
	private List<E> selectBy(String statement, Searchable searchable, E entity, boolean useDefaultFilters) {
		Searchable defaultFilters = useDefaultFilters ? this.getDefaultFiltersForSelect() : null;
		Map<String, Object> filters = this.convertToMap(this.entityClass, defaultFilters, searchable, entity, null);
		List<E> results = new ArrayList<E>();
		//如果没有设置分页
		if(searchable == null || !searchable.hasPage()){
			return getSqlSessionTemplate().selectList(statement, filters);
		}
		//如果设置了分页
//		results = this.getSqlSessionTemplate().selectList(statement, filters, 
//				new RowBounds(searchable.getPage().toOffset(), searchable.getPage().getSize()));
		if (results == null)
			results = new ArrayList<E>();
		//TODO-分页以及不分页的两种写法
		return results;
	}
	
	/**
	 * 获取默认过滤条件，由子类完成
	 * @return
	 * @author  wangsz 2017-05-12
	 */
	public abstract Searchable getDefaultFiltersForSelect();
	
	/**
	 * 通过反射获取entity中的id，默认entity的id字段名为NAME_ID
	 * @author  wangsz 2017-05-10
	 */
	private String getId(E entity) {
		PropertyDescriptor propertyDescriptor = entityPropDescriptorMap.get(NAME_ID);
		Method getIdMethod = propertyDescriptor.getReadMethod();
		
		return (String)ReflectUtil.invokeMethod(getIdMethod, entity);
	}
	
	/**
	 * 将entity和Searchable转换为过滤条件的map
	 * 
	 * @param entityClazz 实体class
	 * @param defaultFilters 默认过滤条件
	 * @param searchable 过滤条件
	 * @param entity 实体对象
	 * @param id 实体id
	 * 
	 * @author  wangsz 2017-05-11
	 */
	private <T> Map<String, Object> convertToMap(Class<T> entityClazz, Searchable defaultFilters,
			Searchable searchable, T entity, String id) {
		Map<String, Object> filters = new LinkedHashMap<String, Object>();
		if (entity != null)
			filters = this.transBean2Map(entity);
		
		if (defaultFilters != null || searchable != null) {
			if (entityClazz != null && searchable != null && !searchable.getConverted())
				SearchableConvertUtils.convertSearchValueToEntityValue(searchable, entityClazz);
			
			Searchable newSearchable = Searchable.newSearchable(searchable);
			if (defaultFilters != null) 
				newSearchable.addSearchFilters(defaultFilters.toSearchFilters());
			Map<String, Object> filterMapping = newSearchable.toFilterMapping();
			for (String key : filterMapping.keySet()) {
				Object value = filterMapping.get(key);
				if (key.endsWith("_like") || key.endsWith("_noLike") && value != null && String.class.isAssignableFrom(value.getClass())) {
					String valueString = (String) value;
					if (valueString.startsWith("*"))
						valueString = "%" + valueString.substring(1);
					if (valueString.endsWith("*"))
						valueString = valueString.substring(0, valueString.length() - 1) + "%";
					
					value = valueString;
				}
				filterMapping.put(key, value);
			}
			
			if (filterMapping.containsKey(whereSqlCustomKey)) {
				if (filterMapping.get(whereSqlCustomKey) != null) {
					 String whereSql = filterMapping.get(whereSqlCustomKey).toString();
					 if (whereSql != null && !"".equals(whereSql.trim()))
						 filterMapping.put(whereSqlCustomKey, whereSql);
					 else
						 filterMapping.remove(whereSqlCustomKey);
				}
			}
			filters.put("filter", filterMapping);
			Map<String, String> sort = newSearchable.toSortMapping();
			if (sort != null && !sort.isEmpty()) filters.put("sort", sort);
		}
		if (id != null && !"".equals(id)) filters.put(NAME_ID, id);
		this.log.trace("filters=" + filters);
		return filters;
	}
	
	/**
	 * 将entity的属性转换为map
	 * @author  wangsz 2017-05-11
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> transBean2Map(Object entity) {
		if (entity == null) {
			return null;
		}
		//如果entity为map，直接返回
		if (Map.class.isAssignableFrom(entity.getClass()))
			return (Map<String, Object>) entity;
		//通过反射调用get()方法，将entity中的元素转换为map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PropertyDescriptor[] propertyDescriptors = ReflectUtil.getPropertyDescriptors(entity.getClass(), true, true);
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Object value = ReflectUtil.getPropertyValue(entity, propertyDescriptor);
					map.put(name, value);
			}
		} catch (Exception e) {
			ReflectUtil.handleReflectionException(e);
		}

		return map;
	}
	
	/**
	 * 返回mapper文件中对应操作的statement
	 * (statement格式为  实体类路径+操作)
	 * @param entityClass 实体class
	 * @param statementPostfix 操作字符串
	 * @return
	 * @author  wangsz 2017-05-10
	 */
	public String getStatementForEntityClass(Class<?> entityClass, String statementPostfix) {
		return entityClass.getName() + statementPostfix;
	}
	
	/**
	 * 返回插入操作在mapper文件中的statement
	 * @author  wangsz 2017-05-11
	 */
	private String getInsertStatement() {
		return getStatementForEntityClass(entityClass, STMT_INSERT);
	}
	/**
	 * 返回修改操作在mapper文件中的statement
	 * @author  wangsz 2017-05-11
	 */
	private String getUpdateStatement() {
		return getStatementForEntityClass(entityClass, STMT_UPDATE);
	}
	/**
	 * 返回批量修改操作在mapper文件中的statement
	 * @author  wangsz 2017-05-11
	 */
	private String getUpdateByStatement() {
		return getStatementForEntityClass(entityClass, STMT_UPDATE_BY);
	}
	/**
	 * 返回删除操作在mapper文件中的statement
	 * @author  wangsz 2017-05-11
	 */
	private String getDeleteStatement() {
		return getStatementForEntityClass(entityClass, STMT_DELETE);
		
	}
	/**
	 * 返回批量删除操作在mapper文件中的statement
	 * @author  wangsz 2017-05-12
	 */
	private String getDeleteByStatement(){
		return getStatementForEntityClass(entityClass, STMT_DELETE_BY);
	}
	/**
	 * 返回查询操作在mapper文件中的statement
	 * @author  wangsz 2017-05-11
	 */
	private String getFindStatement() {
		return getStatementForEntityClass(entityClass, STMT_SELECT_BY);
	}
	
	
	
}
