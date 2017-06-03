package com.xw.supercar.dao;

import org.springframework.stereotype.Repository;

import com.xw.supercar.entity.Car;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
@Repository
public class CarDao extends BaseDao<Car>{

	@Override
	public Searchable getDefaultFiltersForSelect() {
		Searchable searchable = Searchable.newSearchable()
				.addSearchFilter(Car.DP.isDeleted.name(), SearchOperator.eq, false);
		return searchable;
	}

}
