package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.RepairWorkorderDao;
/**
 * <p>
 * 维修工单service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:35:59
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
	
	/**
	 * 根据工单号获取维修工单
	 * @param rWorkOrderNo
	 * @return
	 * @author  wangsz 2017-07-07
	 */
	public RepairWorkorder getByCode(String rWorkOrderNo) {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(RepairWorkorder.DP.workorderNo.name(), SearchOperator.eq, rWorkOrderNo);
		RepairWorkorder repairWorkorder = getBy(searchable, true, false);
		return repairWorkorder;
	}

}