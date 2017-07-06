package com.xw.supercar.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.InPartInfoDao;
import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.entity.Inventory;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

@Service
public class InPartInfoService extends BaseService<InPartInfo>{
	@Autowired
	private InPartInfoDao dao;
	
	@Override
	protected BaseDao<InPartInfo> getDao() {
		return dao;
	}
	
	@Override
	@Transactional
	public InPartInfo add(InPartInfo entity) {
		//暂时注释，采用数据库触发器进行库存表的关联处理
//		InventoryService inventoryService = SpringContextHolder.getBean(InventoryService.class);
//		/*
//		 * 入库时，库存表对应的配件需要增加
//		 */
//		//查询出入库商品对应的库存商品
//		Searchable searchable = Searchable.newSearchable()
//				.addSearchFilter(Inventory.DP.partId.name(), SearchOperator.eq,entity.getPartId())
//				.addSearchFilter(Inventory.DP.supplierLK.name(), SearchOperator.eq,entity.getSupplierLK());
//		Inventory inventory = inventoryService.getBy(searchable, true, true);
//		//如果库存商品为空，则新增库存
//		if(inventory == null){
//			inventory = new Inventory();
//			inventory.setCost(entity.getCost());
//			inventory.setPartId(entity.getPartId());
//			inventory.setSupplierLK(entity.getSupplierLK());
//			inventory.setRepCodeLK(entity.getRepositoryCodeLK());
//			inventory.setCount(entity.getCount());
//			inventoryService.add(inventory);
//		}
//		//否则将该库存商品的数目增加
//		else{
//			int count = inventory.getCount() + entity.getCount();
//			inventory.setCount(count);
//			inventoryService.modify(inventory);
//		}
		
		return super.add(entity);
	}
}
