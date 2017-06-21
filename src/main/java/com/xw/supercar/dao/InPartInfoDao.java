package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.InPartInfo;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class InPartInfoDao extends BaseDao<InPartInfo>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(InPartInfo.DP.isDeleted.name(), SearchOperator.eq, false);
		return searchable;
	}

}
