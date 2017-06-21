package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.OutPartDao;
import com.xw.supercar.entity.OutPart;

@Service
public class OutPartService extends BaseService<OutPart>{
	@Autowired
	private OutPartDao dao;
	
	@Override
	protected BaseDao<OutPart> getDao() {
		return dao;
	}
	
}
