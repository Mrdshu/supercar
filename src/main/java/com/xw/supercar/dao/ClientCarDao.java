package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.ClientCar;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class ClientCarDao extends BaseDao<ClientCar>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable();
		return searchable;
	}

}
