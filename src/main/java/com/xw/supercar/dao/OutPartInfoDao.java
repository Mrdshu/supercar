package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.OutPartInfo;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class OutPartInfoDao extends BaseDao<OutPartInfo>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(OutPartInfo.DP.isDeleted.name(), SearchOperator.eq, false);
		return searchable;
	}

}
