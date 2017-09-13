package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.entity.ClientCoupon;

/**
 * <p>
 * 客户优惠券dao层
 * </p>
 * 
 * @author wangsz
 * @date 2017-09-13 16:12:12
 * @version
 */
 @Repository
public class ClientCouponDao extends BaseDao<ClientCoupon>{

    @Override
	public Searchable getDefaultFiltersForSelect() {
				Searchable searchable = Searchable.newSearchable();
				
		return searchable;
	}

}