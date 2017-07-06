package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.OutPartInfoDao;
import com.xw.supercar.entity.OutPartInfo;

@Service
public class OutPartInfoService extends BaseService<OutPartInfo>{
	@Autowired
	private OutPartInfoDao dao;
	
	@Override
	protected BaseDao<OutPartInfo> getDao() {
		return dao;
	}

	//暂时注释，代码中未解决多线程修改库存数目的问题，目前采用数据库触发器解决并发问题
	//关于多线程同步问题，可用RabbitMQ,或事务悲观锁解决
//	@Override
//	public List<OutPartInfo> add(List<OutPartInfo> entitys) {
//		List<OutPartInfo> outPartInfos = new ArrayList<>();
//		//单独处理每一个新增的出库配件信息
//		for (OutPartInfo outPartInfo : entitys) {
//			OutPartInfo outPI = add(outPartInfo);
//			if(outPI == null){
//				log.error("库存配件数目不足，无法出库");
//			}
//			outPartInfos.add(outPI);
//		}
//		
//		return outPartInfos;
//	}
//	
//	@Override
//	public OutPartInfo add(OutPartInfo entity) {
//		/*
//		 * 配件出库时，库存表对应的库存要减少
//		 */
//		Inventory inventory = SpringContextHolder.getBean(InventoryService.class).getById(entity.getInventoryId());
//		int count = inventory.getCount() - entity.getCount();
//		//如果此时库存数目<0，则出库失败
//		if(count <0){
//			return null;
//		}
//		return super.add(entity);
//	}
}
