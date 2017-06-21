package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.InPartInfoDao;
import com.xw.supercar.entity.InPartInfo;

@Service
public class InPartInfoService extends BaseService<InPartInfo>{
	@Autowired
	private InPartInfoDao dao;
	
	@Override
	protected BaseDao<InPartInfo> getDao() {
		return dao;
	}
	
}
