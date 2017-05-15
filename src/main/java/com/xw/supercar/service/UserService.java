package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.UserDao;
import com.xw.supercar.entity.User;

@Service
public class UserService extends BaseService<User>{
	@Autowired
	private UserDao userDao;
	
	@Override
	protected BaseDao<User> getDao() {
		return userDao;
	}
	
}
