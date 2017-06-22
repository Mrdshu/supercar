package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Part;
import com.xw.supercar.entity.User;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class PartDao extends BaseDao<Part>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(User.DP.isDeleted.name(), SearchOperator.eq, false);
		return searchable;
	}

}
