package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.entity.RepairItem;

/**
 * <p>
 * 维修服务项目dao层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 17:45:03
 * @version
 */
 @Repository
public class RepairItemDao extends BaseDao<RepairItem>{

    @Override
	public Searchable getDefaultFiltersForSelect() {
				Searchable searchable = Searchable.newSearchable();
				
		return searchable;
	}

}