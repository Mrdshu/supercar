package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.RepairWorkorderItemDao;
/**
 * <p>
 * 维修工单-服务项目service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 17:45:03
 * @version
 */
@Service
public class RepairWorkorderItemService extends BaseService<RepairWorkorderItem>{

  	@Autowired
	private RepairWorkorderItemDao dao;
	
	@Override
	protected BaseDao<RepairWorkorderItem> getDao() {
		return dao;
	}

}