package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.PartDao;
import com.xw.supercar.entity.Part;

@Service
public class PartService extends BaseService<Part>{
	@Autowired
	private PartDao dao;
	
	@Override
	protected BaseDao<Part> getDao() {
		return dao;
	}
	
}
