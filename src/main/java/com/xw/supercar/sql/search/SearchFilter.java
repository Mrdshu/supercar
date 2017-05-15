package com.xw.supercar.sql.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 查询过滤条件
 * </p>
 */
public class SearchFilter implements Cloneable, Serializable {

	private static final long serialVersionUID = -5389665478405798044L;

	// 查询参数分隔符
	public static final String separator = "_";

	private String searchProperty;
	private SearchOperator operator;
	private Object value;

	private List<SearchFilter> orFilters;

	/**
	 * 根据查询key和值生成SearchFilter
	 * 
	 * @param key
	 *            如 name_like
	 * @param value
	 * @return
	 */
	public static SearchFilter newSearchFilter(final String key, final Object value) {
		if (key == null)
			throw new IllegalArgumentException("SearchFilter key must not null");

		String searchProperty = key;
		SearchOperator operator = null;
		if (key.contains(separator)) {
			int i = key.lastIndexOf(separator);
			String operatorStr = key.substring(i + 1);
			try {
				operator = SearchOperator.valueOf(operatorStr);
				searchProperty = key.substring(0, i);
			} catch (Exception e) {
			}
		}
		if (operator == null) 
			operator = SearchOperator.custom;
		
		boolean allowBlankValue = SearchOperator.isAllowBlankValue(operator);
		boolean isValueBlank = value == null;
		isValueBlank = isValueBlank || (value instanceof String && (value == null || "".equals(((String) value).trim())));
		isValueBlank = isValueBlank || (value instanceof List && ((List<?>) value).size() == 0);
		// 过滤掉空值，即不参与查询
		if (!allowBlankValue && isValueBlank) {
			return null;
		}

		SearchFilter searchFilter = SearchFilter.newSearchFilter(searchProperty, operator, value);

		return searchFilter;
	}

	/**
	 * 根据查询属性、操作符和值生成SearchFilter
	 * 
	 * @param searchProperty
	 * @param operator
	 * @param value
	 * @return
	 */
	public static SearchFilter newSearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
		return new SearchFilter(searchProperty, operator, value);
	}

	public SearchFilter() {
	}
	
	/**
	 * @param searchProperty
	 *            属性名
	 * @param operator
	 *            操作
	 * @param value
	 *            值
	 */
	@SuppressWarnings("unchecked")
	public SearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
		this.searchProperty = searchProperty;
		this.operator = operator;
		if (value != null && (Collection.class.isAssignableFrom(value.getClass()) || value.getClass().isArray())) {
			List<Object> list = new ArrayList<Object>();
			if (List.class.isAssignableFrom(value.getClass())) {
				list = (List<Object>) value;
			} else if (Set.class.isAssignableFrom(value.getClass())) {
				list = Arrays.asList(((Set<?>) value).toArray());
			} else if (value.getClass().isArray()) {
				list = Arrays.asList((Object[]) value);
			}
			this.value = list;
		} else {
			this.value = value;
		}
	}

	/**
	 * 目前仅支持一级或操作 或 条件运算
	 * 
	 * @param orSearchFilter
	 * @return
	 */
	public SearchFilter or(SearchFilter orSearchFilter) {
		if (orFilters == null) {
			orFilters = new ArrayList<SearchFilter>();
		}
		orFilters.add(orSearchFilter);
		return this;
	}

	public List<SearchFilter> getOrFilters() {
		return orFilters;
	}

	public boolean hasOrSearchFilters() {
		return this.getOrFilters() != null && !this.getOrFilters().isEmpty();
	}

	public String toKey() {
		return this.searchProperty + separator + this.operator;
	}

	public String getSearchProperty() {
		return searchProperty;
	}

	/**
	 * 获取 操作符
	 * 
	 * @return
	 */
	public SearchOperator getOperator() {
		return operator;
	}

	public Object getValue() {
		return value;
	}
	
	/**
	 * 获取自定义查询使用的操作符 1、首先获取前台传的 2、获取SearchPropertyMappingInfo中定义的默认的 3、返回空
	 * 
	 * @return
	 */
	public String toOperatorStr() {
		if (operator != null) {
			return operator.getSymbol();
		}
		return "";
	}

	
	public void setSearchProperty(final String searchProperty) {
		this.searchProperty = searchProperty;
	}

	public void setOperator(final SearchOperator operator) {
		this.operator = operator;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public void setOrFilters(final List<SearchFilter> orFilters) {
		this.orFilters = orFilters;
	}

	/**
	 * 是否是一元过滤 如is null is not null
	 * 
	 * @return
	 */
	public boolean toIsUnaryFilter() {
		String operatorStr = getOperator().getSymbol();
		return operatorStr.startsWith("is");
	}
	
	@Override
	public SearchFilter clone() {
		SearchFilter newSearchFilter = new SearchFilter();
		newSearchFilter.setSearchProperty(new String(this.searchProperty));
		newSearchFilter.setOperator(this.operator);
		newSearchFilter.setValue(this.value);
		if (this.orFilters != null && this.orFilters.size() > 0) {
			List<SearchFilter> newOrFilters = new ArrayList<SearchFilter>();
			for (SearchFilter orFilter : orFilters) {
				SearchFilter newOrFilter = orFilter.clone();
				newOrFilters.add(newOrFilter);
			}
			newSearchFilter.setOrFilters(newOrFilters);
		}
		return newSearchFilter;
	}

	@Override
	public String toString() {
		return String.format("searchProperty:%s, operator:%s, value:%s",
				searchProperty, operator, value);
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
