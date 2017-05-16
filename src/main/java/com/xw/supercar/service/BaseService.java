package com.xw.supercar.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.entity.BaseEntity;
import com.xw.supercar.sql.search.Searchable;

/**
 * Service层的基础类，实现了基础的增、删、改、查(add、remove、modify、search)方法。
 * 继承即可使用（需指定泛型为对应实体）
 * 
 * @author wangsz 2017-05-14
 */
public abstract class BaseService<E extends BaseEntity> implements InitializingBean{
	
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
	 * 新增
	 * @author  wangsz 2017-05-14
	 */
	public E add(E entity){
		beforeAdd(entity);
		String id = entity.getId();
		//如果id未被设置，则采用uuid自动生成的16位随机数
		if(StringUtils.isEmpty(id)){
			id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			entity.setId(id);
		}
		baseDao.insert(entity);
		
		entity = searchById(id);
		afterAdd(entity);
		return entity;
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
	 * 根据id软删除
	 * @author  wangsz 2017-05-14
	 */
	public boolean removeById(String id) {
		E entity = searchById(id);
		if(entity == null)
			throw new IllegalArgumentException("can't find the entity of id:"+id);
		
		beforeRemove(entity);
		boolean rs = baseDao.deleteById(id);
		afterRemove(entity);
		
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
		E entity = searchById(id);
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
		if(!rs)
			throw new IllegalArgumentException("modify entity【"+entity.toString()+"】 fail");
		
		entity = searchById(entity.getId());
		afterModify(entity);
		return entity;
	}
	
	/**
	 * 根据id查询
	 * @author  wangsz 2017-05-14
	 */
	public E searchById(String id){
		return baseDao.selectById(id);
	}
	
	/**
	 * 根据过滤条件查询
	 * @author  wangsz 2017-05-14
	 */
	public List<E> searchBy(Searchable searchable,boolean useDefaultFilters){
		return baseDao.selectBy(searchable, useDefaultFilters);
	}
	
}