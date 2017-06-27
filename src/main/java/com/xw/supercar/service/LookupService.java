package com.xw.supercar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.LookupDao;
import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.util.ReflectUtil;

@Service
public class LookupService extends BaseService<Lookup> {
	@Autowired
	private LookupDao lookupDao;

	@Override
	protected BaseDao<Lookup> getDao() {
		return lookupDao;
	}
	
	@Override
	protected void beforeAdd(Lookup entity) {
		String parentId = entity.getParentId();
		//如果数据字典父节点不为空，即为树状结构时，补全树状信息
		if(!StringUtils.isEmpty(parentId)){
			Lookup parent = getById(parentId);
			if(parent == null)
				return ;
			//修改父节点为非叶子节点
			parent.setZzIsLeaf(false);
			modify(parent);
			//完善子节点信息
			Integer parentLevel = parent.getZzLevel();
			entity.setZzLevel(parentLevel+1);
			entity.setZzIsLeaf(true);
			setParentIdOfLK(entity, parent);
		}
		else{
			entity.setZzIsLeaf(true);
			entity.setZzLevel(1);
			entity.setZzLevel1Id(entity.getId());
		}
	}
	
	/**
	 * 根据数据字段定义code，查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public List<Lookup> searchByDefineCode(String lookupDefineCode) {
		List<Lookup> lookups = new ArrayList<>();

		LookupDefinition lookupDefinition = SpringContextHolder.getBean(LookupDefinitionService.class)
				.searchByCode(lookupDefineCode);
		if (lookupDefinition != null) {
			lookups = searchByDefineId(lookupDefinition.getId());
		}

		return lookups;
	}

	/**
	 * 根据数据字段定义id，查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public List<Lookup> searchByDefineId(String lookupDefineId) {
		List<Lookup> lookups = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lookupDefineId)) {
			Searchable searchable = Searchable.newSearchable().addSearchFilter(Lookup.DP.definitionId.name(),
					SearchOperator.eq, lookupDefineId);
			lookups = findBy(searchable, true);
		}

		return lookups;
	}
	
	/**
	 * 根据数据字段定义code，分页查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public Page<Lookup> searchPageByDefineCode(String lookupDefineCode, String pageNo, String pageSize) {
		Page<Lookup> page = null;

		LookupDefinition lookupDefinition = SpringContextHolder.getBean(LookupDefinitionService.class)
				.searchByCode(lookupDefineCode);
		if (lookupDefinition != null) {
			Searchable searchable = Searchable.newSearchable()
					.addSearchFilter(Lookup.DP.definitionId.name(),SearchOperator.eq, lookupDefinition.getId())
					.addPage(pageNo, pageSize);
			page = findPage(searchable, true);
		}

		return page;
	}

	
	/**
	 * 填充数据字典的父节点
	 *
	 * @author wsz 2017-06-27
	 */
	private void setParentIdOfLK(Lookup entity, Lookup parentLK){
		if(entity == null || parentLK == null)
			throw new IllegalArgumentException("lookup can't be null!");
		
		Integer level = parentLK.getZzLevel();
		for (int i = 1,n =level; i <= n+1; i++) {
			String setMethodName = "setZzLevel"+i+"Id";
			String getMethodName = "getZzLevel"+i+"Id";
			
			//设置n层节点的n层父节点为自己
			if(i == n+1){
				ReflectUtil.exeMethodOfObj(entity, setMethodName, new Class[]{String.class}, new String[]{entity.getId()});
				break;
			}
			//获取父节点第i层父节点的id
			String levelId = (String) ReflectUtil.exeMethodOfObj(parentLK, getMethodName, null, null);
			//设置该数据字典的第i层父节点
			ReflectUtil.exeMethodOfObj(entity, setMethodName, new Class[]{String.class}, new String[]{levelId});
		}
		
	}
}
