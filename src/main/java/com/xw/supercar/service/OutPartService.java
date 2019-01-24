package com.xw.supercar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.OutPartDao;
import com.xw.supercar.entity.OutPart;
import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.entity.composite.OutPartComposite;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
/**
 * <p>
 * 出库工单service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 10:09:37
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
	
	/**
	 * 根据维修工单号，获取指定出库工单对应的配件信息
	 * @param repairWorkOrderNo
	 * @return
	 * @author  wangsz 2017-07-07
	 */
	public OutPartComposite findOutPartInfosByRWO(String repairWorkOrderNo) {
		List<OutPartInfo> outPartInfos = new ArrayList<>();
		
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPart.DP.repairWorkorderNo.name(), SearchOperator.eq, repairWorkOrderNo);
		//获取维修工单对应的出库工单
		OutPart outPart = getBy(searchable, true, false);
		if(outPart == null)
			return null;
		//获取出库工单绑定的出库配件信息
		outPartInfos = findOutPartInfosByWO(outPart.getWorkOrderNo());
		
		OutPartComposite outPartComposite = new OutPartComposite(outPart, outPartInfos);
		return outPartComposite;
	}
	
	/**
	 * 根据出库工单号，获取对应的配件信息
	 * @param outWorkOrderNo
	 * @return
	 * @author  wangsz 2017-07-07
	 */
	@SuppressWarnings("unchecked")
	public List<OutPartInfo> findOutPartInfosByWO(String outWorkOrderNo) {
		List<OutPartInfo> outPartInfos = new ArrayList<>();
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPartInfo.DP.workOrderNo.name(), SearchOperator.eq, outWorkOrderNo);
		//获取出库工单对应的配件
		outPartInfos = SpringContextHolder.getBean(OutPartInfoService.class).findBy(searchable, true);
		addAttributesToData(outPartInfos, new String[]{OutPartInfo.DP.inventoryId.name()}
				, new Class[]{InventoryService.class});
		
		return outPartInfos;
	}
}