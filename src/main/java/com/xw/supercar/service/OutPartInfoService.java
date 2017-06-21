package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.OutPartInfoDao;
import com.xw.supercar.entity.OutPartInfo;

@Service
public class OutPartInfoService extends BaseService<OutPartInfo>{
	@Autowired
	private OutPartInfoDao dao;
	
	@Override
	protected BaseDao<OutPartInfo> getDao() {
		return dao;
	}
	
}
