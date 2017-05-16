package com.xw.supercar.sql.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xw.supercar.sql.page.Pageable;
import com.xw.supercar.sql.page.Pageable.Direction;

import java.util.Set;


/**
 * <p>
 * 查询条件接口
 * </p>
 */
public class Searchable implements Cloneable, Serializable {

	private static final long serialVersionUID = -7356528103192944743L;

	public static final String WHERE_SQL = "WHERE_SQL";
	
	private Map<String, SearchFilter> searchFilterMap = new LinkedHashMap<String, SearchFilter>();

	private Pageable page = new Pageable();

	private boolean converted = false;
	
	private boolean cacheable = true;

	public Searchable() {
		this(null, null, null);
	}

	public Searchable(final boolean converted) {
		this(null, null, null);
		this.converted = converted;
	}
	
	public Searchable(final Map<String, Object> searchParams) {
		this(searchParams, null, null);
	}

	public Searchable(final Map<String, Object> searchParams, final Pageable page) {
		this(searchParams, page, null);
	}

	public Searchable(final Map<String, Object> searchParams, final Map<String, Pageable.Direction> orders) {
		this(searchParams, null, orders);
	}

	public Searchable(final Map<String, Object> searchParams, final Pageable page, final Map<String, Pageable.Direction> orders) {
		this.toSearchFilters(searchParams);
		merge(page, orders);
	}

	public void setSearchFilterMap(Map<String, SearchFilter> searchFilterMap) {
		this.searchFilterMap = searchFilterMap;
		this.getCacheable();
	}
	
	public Map<String, SearchFilter> getSearchFilterMap() {
		return this.searchFilterMap;
	}
	
	public Pageable getPage() {
		return this.page;
	}
	
	public void setPage(Pageable page) {
		if (page == null) {
			this.page.setNumber(0);
			this.page.setSize(0);
			this.page.setSort(null);
		} else {
			this.page.setNumber(page.getNumber());
			this.page.setSize(page.getSize());
			this.page.setSort(page.getSort());
		}
	}

	public Map<String, Pageable.Direction> getSort() {
		return this.page == null ? null : this.page.getSort();
	}
	
	public void setSort(Map<String, Pageable.Direction> sort) {
		this.page.setSort(sort);
	}
	
	public void setConverted(boolean converted) {
		this.converted = converted;
	}
	
	public boolean getConverted() {
		return this.converted;
	}

	public boolean getCacheable() {
		boolean newCacheable = true;
		if (this.searchFilterMap != null) {
			for (String key : this.searchFilterMap.keySet()) {
				if (key.startsWith("_") || key.endsWith("_custom") || key.endsWith("_like") || key.endsWith("_notLike")) {
					newCacheable = false;
					break;
				}
			}
		}
		this.cacheable = newCacheable;
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	protected void merge(Pageable page, Map<String, Pageable.Direction> sort) {
		if (page != null || sort != null) {
			if (page != null) {
				this.page.setNumber(page.getNumber());
				this.page.setSize(page.getSize());
				this.page.addSort(page.getSort());
			}
			if (sort != null) this.page.addSort(sort);
		}
	}

	protected void merge(Pageable page, Pageable.Direction direction, String... properties) {
		if (page != null || (direction != null && properties != null)) {
			if (page != null) this.merge(page, null);
			if (direction != null && properties != null) this.page.addSort(direction, properties);
		}
	}
	
	public static Searchable newSearchable() {
		return new Searchable();
	}

	public static Searchable newSearchable(final Map<String, Object> searchParams) {
		return new Searchable(searchParams);
	}

	public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page) {
		return new Searchable(searchParams, page);
	}

	public static Searchable newSearchable(final Map<String, Object> searchParams, final Map<String, Pageable.Direction> orders) {
		return new Searchable(searchParams, orders);
	}

	public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page, final Map<String, Pageable.Direction> orders) {
		return new Searchable(searchParams, page, orders);
	}
	
	public static Searchable newSearchable(Searchable searchable) {
		return Searchable.newSearchable(searchable, true, true, true);
	}
	
	public static Searchable newSearchable(Searchable searchable, boolean includeSearchFilter, boolean includePage, boolean includeSort) {
		return searchable == null ? Searchable.newSearchable() : searchable.clone(includeSearchFilter, includePage, includeSort);
	}
	
	public Searchable addSearchParam(final String key, final Object value) {
		this.addSearchFilter(SearchFilter.newSearchFilter(key, value));
		return this;
	}

	public Searchable addSearchParams(Map<String, Object> searchParams) {
		toSearchFilters(searchParams);
		return this;
	}

	public Searchable addSearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
		if (operator.equals(SearchOperator.isNull) || operator.equals(SearchOperator.isNotNull)) {
			throw new IllegalArgumentException("Invalid search operator, not support SearchOperator.isNull or SearchOperator.isNotNull");
		}
		SearchFilter searchFilter = SearchFilter.newSearchFilter(searchProperty, operator, value);
		return addSearchFilter(searchFilter);
	}

	public Searchable addSearchFilter(final String searchProperty, final SearchOperator operator) {
		if (operator.equals(SearchOperator.isNull) || operator.equals(SearchOperator.isNotNull)) {
			SearchFilter searchFilter = SearchFilter.newSearchFilter(searchProperty, operator, true);
			return addSearchFilter(searchFilter);
		} else {
			throw new IllegalArgumentException("Invalid search operator, just support SearchOperator.isNull or SearchOperator.isNotNull");
		}
	}

	public Searchable mergeSearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
		Collection<SearchFilter> searchFilters = this.toSearchFilters();
		if (searchFilters.size() > 0 && (operator.equals(SearchOperator.in) || operator.equals(SearchOperator.notIn))) {
			List<Object> values = null;
			for (SearchFilter searchFilter : searchFilters) {
				if (searchFilter.getSearchProperty().equals(searchProperty) && searchFilter.getOperator().equals(operator)) {
					if (searchFilter.getValue() != null) {
						if (Collection.class.isAssignableFrom(searchFilter.getValue().getClass()) || searchFilter.getValue().getClass().isArray()) {
							Collection<Object> list = new ArrayList<Object>();
							if (List.class.isAssignableFrom(searchFilter.getValue().getClass())) {
								list.addAll((List<?>) searchFilter.getValue());
							} else if (Set.class.isAssignableFrom(searchFilter.getValue().getClass())) {
								list.addAll(Arrays.asList(((Set<?>) searchFilter.getValue()).toArray()));
							} else if (searchFilter.getValue().getClass().isArray()) {
								list.addAll(Arrays.asList((Object[]) searchFilter.getValue()));
							} else {
								list.addAll((Collection<?>) searchFilter.getValue());
							}
							values = new ArrayList<Object>();
							values.addAll(list);
						} else {
							List<Object> list = new ArrayList<Object>();
							list.add(searchFilter.getValue());
							values = list;
						}
					}
					break;
				}
			}
			if (values != null) {
				if (value != null && (Collection.class.isAssignableFrom(value.getClass()) || value.getClass().isArray())) {
					Collection<Object> list = new ArrayList<Object>();
					if (List.class.isAssignableFrom(value.getClass())) {
						list.addAll((List<?>) value);
					} else if (Set.class.isAssignableFrom(value.getClass())) {
						list.addAll(Arrays.asList(((Set<?>) value).toArray()));
					} else if (value.getClass().isArray()) {
						list.addAll(Arrays.asList((Object[]) value));
					} else {
						list.addAll((Collection<?>) value);
					}
					values.addAll(list);
				} else {
					List<Object> list = new ArrayList<Object>();
					list.add(value);
					values.addAll(list);
				}
				return addSearchFilter(searchProperty, operator, values);
			}
		}
		return addSearchFilter(searchProperty, operator, value);
	}

	public Searchable addSearchFilters(Collection<SearchFilter> searchFilters) {
		if (searchFilters == null || searchFilters.isEmpty()) {
			return this;
		}
		for (SearchFilter searchFilter : searchFilters) {
			this.addSearchFilter(searchFilter);
		}
		return this;
	}

	public Searchable addOrSearchFilters(SearchFilter first, Collection<SearchFilter> others) {

		Iterator<SearchFilter> iter = others.iterator();

		while (iter.hasNext()) {
			first.or(iter.next());
		}

		addSearchFilter(first);

		return this;
	}

	public Searchable addSearchFilter(SearchFilter searchFilter) {
		if (searchFilter == null) {
			return this;
		}
		if (!SearchOperator.isNull.equals(searchFilter.getOperator()) && !SearchOperator.isNotNull.equals(searchFilter.getOperator())) {
			if (searchFilter.getValue() != null && (SearchOperator.in.equals(searchFilter.getOperator()) || SearchOperator.notIn.equals(searchFilter.getOperator()))) {
				if (Collection.class.isAssignableFrom(searchFilter.getValue().getClass()) || searchFilter.getValue().getClass().isArray()) {
					Collection<Object> list = new ArrayList<Object>();
					if (List.class.isAssignableFrom(searchFilter.getValue().getClass())) {
						list.addAll((List<?>) searchFilter.getValue());
					} else if (Set.class.isAssignableFrom(searchFilter.getValue().getClass())) {
						list.addAll(Arrays.asList(((Set<?>) searchFilter.getValue()).toArray()));
					} else if (searchFilter.getValue().getClass().isArray()) {
						list.addAll(Arrays.asList((Object[]) searchFilter.getValue()));
					} else {
						list.addAll((Collection<?>) searchFilter.getValue());
					}
					if (list.isEmpty()) {
						return this;
					}
				}
			} else  if (searchFilter.getValue() == null || "".equals(searchFilter.getValue())) {
				return this;
			}
		}
		String key = searchFilter.toKey();
		this.searchFilterMap.put(key, searchFilter);
		this.getCacheable();
		return this;
	}
	
	public Searchable removeSearchFilter(final String searchProperty, final SearchOperator operator) {
		if (searchProperty == null || operator == null) {
			return this;
		}
		return this.removeSearchFilter(searchProperty + SearchFilter.separator + operator.name());
	}

	public Searchable removeSearchFilter(final String key) {
		if (key == null) {
			return this;
		}

		SearchFilter searchFilter = this.searchFilterMap.remove(key);

		if (searchFilter == null) {
			searchFilter = this.searchFilterMap.remove(getCustomKey(key));
		}

		if (searchFilter == null) {
			this.getCacheable();
			return this;
		}

		// 如果移除的有or查询 则删除第一个 后续的跟上
		if (searchFilter != null && searchFilter.hasOrSearchFilters()) {
			List<SearchFilter> orSearchFilters = searchFilter.getOrFilters();
			SearchFilter first = orSearchFilters.remove(1);// 变成新的根
			orSearchFilters.remove(0);// 第一个移除即可
			for (SearchFilter orSearchFilter : orSearchFilters) {
				first.or(orSearchFilter);
			}
			addSearchFilter(first);
		}

		// 考虑or的情况
		for (SearchFilter obj : toSearchFilters()) {
			if (obj.hasOrSearchFilters()) {
				Iterator<SearchFilter> orIter = obj.getOrFilters().iterator();

				if (orIter.hasNext()) {
					SearchFilter orSearchFilter = orIter.next();
					String orKey = orSearchFilter.toKey();
					if (key.equals(orKey) || (getCustomKey(key)).equals(orKey)) {
						orIter.remove();
					}
				}
			}
		}

		return this;
	}

	private String getCustomKey(String key) {
		return key + SearchFilter.separator + SearchOperator.custom;
	}

	public Searchable addPage(int pageNumber, int pageSize) {
		merge(new Pageable(pageNumber, pageSize), this.getSort());
		return this;
	}

	public Searchable addPage(final Pageable page) {
		merge(page, this.getSort());
		return this;
	}
	
	public Searchable addSort(final Map<String, Pageable.Direction> sort) {
		merge(page, sort);
		return this;
	}

	public Searchable addSort(final Pageable.Direction direction, final String... properties) {
		merge(page, direction, properties);
		return this;
	}

	public Searchable markConverted() {
		this.converted = true;
		return this;
	}

	public Collection<SearchFilter> toSearchFilters() {
		return Collections.unmodifiableCollection(this.searchFilterMap.values());
	}

	public boolean hasSearchFilter() {
		return this.searchFilterMap != null && this.searchFilterMap.size() > 0;
	}

	public boolean hasPage() {
		return this.page != null && this.page.getNumber() >= 0 && this.page.getSize() > 0;
	}
	
	public boolean hasSort() {
		return this.page != null && this.page.getSort().size() > 0;
	}

	public void removeSort() {
		if (this.page != null) {
			this.page.setSort(null);
		}
	}

	public void removePage() {
		if (this.page != null) {
			this.page.setNumber(0);
			this.page.setSize(0);
		}
	}

	public boolean containsSearchKey(String key) {
		return this.searchFilterMap.containsKey(key) || this.searchFilterMap.containsKey(getCustomKey(key));
	}

	public boolean containsSearchKey(final String searchProperty, final SearchOperator operator) {
		return this.searchFilterMap.containsKey(searchProperty + SearchFilter.separator + operator.name());
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		SearchFilter searchFilter = this.searchFilterMap.get(key);
		if (searchFilter == null) {
			searchFilter = this.searchFilterMap.get(getCustomKey(key));
		}
		if (searchFilter == null) {
			return null;
		}
		return (T) searchFilter.getValue();
	}
	
	public <T> T getValue(final String searchProperty, final SearchOperator operator) {
		return this.getValue(searchProperty + SearchFilter.separator + operator.name());
	}

	private void toSearchFilters(final Map<String, Object> searchParams) {
		if (searchParams == null || searchParams.size() == 0) {
			return;
		}
		for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			this.addSearchFilter(SearchFilter.newSearchFilter(key, value));
		}
	}

	public Map<String, Object> toFilterMapping() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (this.searchFilterMap != null) {
			Set<String> keys = this.searchFilterMap.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				SearchFilter searchFilter = this.searchFilterMap.get(key);
				if (key != null && !key.isEmpty() && searchFilter != null) {
					Object value = null;
					Object values = searchFilter.getValue();
					SearchOperator op = searchFilter.getOperator();
					if (op.equals(SearchOperator.in) || op.equals(SearchOperator.notIn)) {
						List<List<Object>> lists = new ArrayList<List<Object>>();
						if (values != null) {
							if (Collection.class.isAssignableFrom(values.getClass()) || values.getClass().isArray()) {
								Collection<Object> list = new ArrayList<Object>();
								if (List.class.isAssignableFrom(values.getClass())) {
									list.addAll((List<?>) values);
								} else if (Set.class.isAssignableFrom(values.getClass())) {
									list.addAll(Arrays.asList(((Set<?>) values).toArray()));
								} else if (values.getClass().isArray()) {
									list.addAll(Arrays.asList((Object[]) values));
								} else {
									list.addAll((Collection<?>) values);
								}

								value = formatIdsList(list);
							} else {
								List<Object> list = new ArrayList<Object>();
								list.add(values);
								lists.add(list);
								value = lists;
							}
						}
					} else {
						if (values != null && !op.equals(SearchOperator.custom) && (Collection.class.isAssignableFrom(values.getClass()) || values.getClass().isArray())) {
							throw new IllegalArgumentException("Invalid search value, does not allow collection or array.");
						} else {
							value = values;
						}
					}
					if (value != null) {
						map.put(key, value);
					}
				}
			}
		}
		return map;
	}

	public Map<String, String> toSortMapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, Direction> sort = this.getSort();
		if (sort != null && sort.size() > 0) {
			for (Entry<String, Direction> entry : sort.entrySet()) {
				map.put(entry.getKey(), entry.getValue().toString());
			}
		}
		return map;
	}

	public static <T> List<List<T>> formatIdsList(Collection<T> ids) {
		List<List<T>> idsLists = new ArrayList<List<T>>();
		Set<T> temps = new LinkedHashSet<T>();
		temps.addAll(ids);
		if (temps.size() > 1000) {
			List<T> idsList = new ArrayList<T>();
			int i = 0;
			for (T temp : temps) {
					if (i % 1000 == 0) {
						if (idsList.size() > 0)
							idsLists.add(idsList);
						idsList = new ArrayList<T>();
					}
					idsList.add(temp);
					i++;
			}
			if (idsList.size() > 0)
				idsLists.add(idsList);
		} else {
			List<T> idsList = new ArrayList<T>();
			idsList.addAll(temps);
			idsLists.add(idsList);
		}
		
		return idsLists;
	}
	
	public Searchable clone(boolean includeSearchFilter, boolean includePage, boolean includeSort) {
		Searchable newSearchable = Searchable.newSearchable();
		if (includeSearchFilter) {
			if (this.searchFilterMap != null && this.searchFilterMap.size() > 0) {
				for (Entry<String, SearchFilter> entry : this.searchFilterMap.entrySet()) {
					newSearchable.getSearchFilterMap().put(entry.getKey(), entry.getValue().clone());
				}
			}
		}
		if (this.page != null) {
			Pageable newPage = new Pageable();
			if (includePage) {
				newPage.setNumber(this.page.getNumber());
				newPage.setSize(this.page.getSize());
			}
			if (includeSort && this.page.getSort() != null && this.page.getSort().size() > 0)
				newPage.addSort(this.page.getSort());
			newSearchable.setPage(newPage);
		}
		newSearchable.setConverted(this.converted);
		newSearchable.setCacheable(this.cacheable);
		return newSearchable;
	}
	
	@Override
	public Searchable clone() {
		return this.clone(true, true, true);
	}
	
	@Override
	public String toString() {
		return String.format("searchFilterMap:%s, page:%s", searchFilterMap, page);
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * 判断Searchable条件是否为空
	 * @author  wangsz 2017-05-12
	 */
	public boolean isEmpty() {
		if(searchFilterMap == null || searchFilterMap.size() <= 0)
			return true;
		
		return false;
	}
}
