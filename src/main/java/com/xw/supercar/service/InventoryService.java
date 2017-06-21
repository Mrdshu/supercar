package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.InventoryDao;
import com.xw.supercar.entity.Inventory;

@Service
public class InventoryService extends BaseService<Inventory>{
	@Autowired
	private InventoryDao dao;
	
	@Override
	protected BaseDao<Inventory> getDao() {
		return dao;
	}
	
}
