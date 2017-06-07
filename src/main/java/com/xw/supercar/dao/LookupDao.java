package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Lookup;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class LookupDao extends BaseDao<Lookup>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable();
				
		return searchable;
	}

}
