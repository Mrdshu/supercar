package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.LookupDefinitionDao;
import com.xw.supercar.entity.LookupDefinition;

@Service
public class LookupDefinitionService extends BaseService<LookupDefinition>{
	@Autowired
	private LookupDefinitionDao lookupDefinitionDao;
	
	@Override
	protected BaseDao<LookupDefinition> getDao() {
		return lookupDefinitionDao;
	}
	
}
