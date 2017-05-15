package com.xw.supercar.test;

import com.xw.supercar.entity.User;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;
import com.xw.supercar.sql.search.SearchableConvertUtils;

public class SearchableTest {
	public static void main(String[] args) {
		Searchable searchable = new Searchable()
				.addSearchFilter("id", SearchOperator.eq, "1");
		SearchableConvertUtils.convertSearchValueToEntityValue(searchable, User.class);
	}
}
