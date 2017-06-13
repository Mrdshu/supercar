package com.xw.supercar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.LookupDao;
import com.xw.supercar.entity.Lookup;
import com.xw.supercar.entity.LookupDefinition;
import com.xw.supercar.spring.util.SpringContextHolder;
import com.xw.supercar.sql.page.Page;
import com.xw.supercar.sql.search.SearchOperator;
import com.xw.supercar.sql.search.Searchable;

@Service
public class LookupService extends BaseService<Lookup> {
	@Autowired
	private LookupDao lookupDao;

	@Override
	protected BaseDao<Lookup> getDao() {
		return lookupDao;
	}
	
	/**
	 * 根据数据字段定义code，查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public List<Lookup> searchByDefineCode(String lookupDefineCode) {
		List<Lookup> lookups = new ArrayList<>();

		LookupDefinition lookupDefinition = SpringContextHolder.getBean(LookupDefinitionService.class)
				.searchByCode(lookupDefineCode);
		if (lookupDefinition != null) {
			lookups = searchByDefineId(lookupDefinition.getId());
		}

		return lookups;
	}

	/**
	 * 根据数据字段定义id，查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public List<Lookup> searchByDefineId(String lookupDefineId) {
		List<Lookup> lookups = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lookupDefineId)) {
			Searchable searchable = Searchable.newSearchable().addSearchFilter(Lookup.DP.definitionId.name(),
					SearchOperator.eq, lookupDefineId);
			lookups = searchBy(searchable, true);
		}

		return lookups;
	}
	
	/**
	 * 根据数据字段定义code，分页查询数据字典
	 * @author  wangsz 2017-06-13
	 */
	public Page<Lookup> searchPageByDefineCode(String lookupDefineCode, String pageNo, String pageSize) {
		Page<Lookup> page = null;

		LookupDefinition lookupDefinition = SpringContextHolder.getBean(LookupDefinitionService.class)
				.searchByCode(lookupDefineCode);
		if (lookupDefinition != null) {
			Searchable searchable = Searchable.newSearchable()
					.addSearchFilter(Lookup.DP.definitionId.name(),SearchOperator.eq, lookupDefinition.getId())
					.addPage(pageNo, pageSize);
			page = searchPage(searchable, true);
		}

		return page;
	}


}
