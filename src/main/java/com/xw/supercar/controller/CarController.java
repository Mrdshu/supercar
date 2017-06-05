package com.xw.supercar.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.xw.supercar.entity.Car;
import com.xw.supercar.service.BaseService;
import com.xw.supercar.service.CarService;

public class CarController extends BaseController<Car>{
	@Autowired
	private CarService carService;
	
	@Override
	protected BaseService<Car> getSevice() {
		return carService;
	}

}
