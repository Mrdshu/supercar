package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.entity.RepairWorkorder;

/**
 * <p>
 * 维修工单dao层
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 17:45:03
 * @version
 */
 @Repository
public class RepairWorkorderDao extends BaseDao<RepairWorkorder>{

    @Override
	public Searchable getDefaultFiltersForSelect() {
				Searchable searchable = Searchable.newSearchable();
				
		return searchable;
	}

}