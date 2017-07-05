package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.OutPart;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.OutPartDao;
/**
 * <p>
 * 出库工单service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 12:04:03
 * @version
 */
@Service
public class OutPartService extends BaseService<OutPart>{

  	@Autowired
	private OutPartDao dao;
	
	@Override
	protected BaseDao<OutPart> getDao() {
		return dao;
	}

}