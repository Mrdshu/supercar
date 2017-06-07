package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.LookupDao;
import com.xw.supercar.entity.Lookup;

@Service
public class LookupService extends BaseService<Lookup>{
	@Autowired
	private LookupDao lookupDao;
	
	@Override
	protected BaseDao<Lookup> getDao() {
		return lookupDao;
	}
	
}
