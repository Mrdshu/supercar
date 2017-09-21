package com.xw.supercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xw.supercar.entity.ClientCoupon;
import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.ClientCouponDao;
/**
 * <p>
 * 客户优惠券service层
 * </p>
 * 
 * @author wangsz
 * @date 2017-09-13 16:12:12
 * @version
 */
@Service
public class ClientCouponService extends BaseService<ClientCoupon>{

  	@Autowired
	private ClientCouponDao dao;
	
	@Override
	protected BaseDao<ClientCoupon> getDao() {
		return dao;
	}

}