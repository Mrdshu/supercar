package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.RepairWorkorderDao;
/**
 * <p>
 * 维修工单service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 08:32:37
 * @version
 */
@Service
public class RepairWorkorderService extends BaseService<RepairWorkorder>{

  	@Autowired
	private RepairWorkorderDao dao;
	
	@Override
	protected BaseDao<RepairWorkorder> getDao() {
		return dao;
	}

}