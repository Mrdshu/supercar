package com.xw.supercar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.RepairWorkorderItem;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.RepairWorkorderItemDao;
/**
 * <p>
 * 维修工单-服务项目service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:35:59
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
	
	/**
	 * 根据维修工单id，返回对应的维修工单-服务项目
	 * @param rWorkorderId
	 * @return
	 * @author  wangsz 2017-07-07
	 */
	public List<RepairWorkorderItem> getByRWOId(String rWorkorderId) {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(RepairWorkorderItem.DP.workorderId.name(), SearchOperator.eq, rWorkorderId);
		List<RepairWorkorderItem> repairWorkorderItems = findBy(searchable, true);
		
		return repairWorkorderItems;
	}
	
	@Override
	protected void afterSelect(RepairWorkorderItem entity) {
		addAttributeToData(entity, RepairWorkorderItem.DP.itemId.name(), RepairItemService.class);
	}
}