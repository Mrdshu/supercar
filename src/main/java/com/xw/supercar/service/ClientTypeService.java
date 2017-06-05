package com.xw.supercar.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.ClientDao;
import com.xw.supercar.dao.ClientTypeDao;
import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ClientType;

@Service
public class ClientTypeService extends BaseService<ClientType>{
	@Autowired
	private ClientTypeDao clientTypeDao;
	
	@Override
	protected BaseDao<ClientType> getDao() {
		return clientTypeDao;
	}
	
}
