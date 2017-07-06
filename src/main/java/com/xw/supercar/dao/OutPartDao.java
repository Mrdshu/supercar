package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.entity.OutPart;

/**
 * <p>
 * 出库工单dao层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 10:09:37
 * @version
 */
 @Repository
public class OutPartDao extends BaseDao<OutPart>{

    @Override
	public Searchable getDefaultFiltersForSelect() {
				Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPart.DP.isDeleted.name(), SearchOperator.eq, false);
				
		return searchable;
	}

}