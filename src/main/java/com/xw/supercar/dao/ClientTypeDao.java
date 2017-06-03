package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.ClientType;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class ClientTypeDao extends BaseDao<ClientType>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable();
		return searchable;
	}

}
