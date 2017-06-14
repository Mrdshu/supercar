package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class LookupDefinitionDao extends BaseDao<LookupDefinition>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(LookupDefinition.DP.isDeleted.name(), SearchOperator.eq, false);
				
		return searchable;
	}

}
