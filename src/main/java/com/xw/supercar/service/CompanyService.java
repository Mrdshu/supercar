package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.CompanyDao;
import com.xw.supercar.entity.Company;

@Service
public class CompanyService extends BaseService<Company>{
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	protected BaseDao<Company> getDao() {
		return companyDao;
	}
	
}
