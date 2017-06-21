package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.InPartDao;
import com.xw.supercar.entity.InPart;

@Service
public class InPartService extends BaseService<InPart>{
	@Autowired
	private InPartDao dao;
	
	@Override
	protected BaseDao<InPart> getDao() {
		return dao;
	}
	
}
