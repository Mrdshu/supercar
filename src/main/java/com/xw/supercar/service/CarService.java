package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.CarDao;
import com.xw.supercar.entity.Car;

@Service
public class CarService extends BaseService<Car>{
	@Autowired
	private CarDao carDao;
	
	@Override
	protected BaseDao<Car> getDao() {
		return carDao;
	}
	
}
