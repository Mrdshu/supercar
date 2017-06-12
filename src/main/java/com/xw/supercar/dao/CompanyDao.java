package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Company;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class CompanyDao extends BaseDao<Company>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable();

		return searchable;
	}

}
