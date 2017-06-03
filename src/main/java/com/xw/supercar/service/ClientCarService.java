package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.ClientCarDao;
import com.xw.supercar.entity.ClientCar;

@Service
public class ClientCarService extends BaseService<ClientCar>{
	@Autowired
	private ClientCarDao clientCarDao;
	
	@Override
	protected BaseDao<ClientCar> getDao() {
		return clientCarDao;
	}
	
}
