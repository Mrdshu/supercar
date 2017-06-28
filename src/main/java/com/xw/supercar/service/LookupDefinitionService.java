package com.xw.supercar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.LookupDefinitionDao;
import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

@Service
public class LookupDefinitionService extends BaseService<LookupDefinition>{
	@Autowired
	private LookupDefinitionDao lookupDefinitionDao;
	
	@Override
	protected BaseDao<LookupDefinition> getDao() {
		return lookupDefinitionDao;
	}
	
	
	/**
	 * 根据code返回数据字典定义
	 * @author  wangsz 2017-06-13
	 */
	public LookupDefinition getByCode(String code){
		if(StringUtils.isEmpty(code))
			throw new IllegalArgumentException("the LookupDefinition code can't be empty!");
		
		Searchable searchable = Searchable.newSearchable().addSearchFilter(LookupDefinition.DP.code.name(), SearchOperator.eq, code);
		List<LookupDefinition> lookupDefinitions = findBy(searchable, true);
		if(lookupDefinitions == null || lookupDefinitions.size() == 0)
			return null;
		else
			return lookupDefinitions.get(0);
	}
}
