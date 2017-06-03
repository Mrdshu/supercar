package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Client;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class ClientDao extends BaseDao<Client>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Client.DP.isDeleted.name(), SearchOperator.eq, false);
		return searchable;
	}

}
