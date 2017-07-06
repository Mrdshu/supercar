package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.RepairItem;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.RepairItemDao;
/**
 * <p>
 * 维修服务项目service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 17:45:03
 * @version
 */
@Service
public class RepairItemService extends BaseService<RepairItem>{

  	@Autowired
	private RepairItemDao dao;
	
	@Override
	protected BaseDao<RepairItem> getDao() {
		return dao;
	}

}