package com.xw.supercar.spring.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xw.supercar.annotation.SearchableDefaults;
import com.xw.supercar.sql.page.Pageable;
import com.xw.supercar.sql.search.Searchable;

/**
 * 请求查询参数字符串及分页/排序参数绑定到Searchable
 * 
 * <pre>
 *     查询参数格式如下：
 *     1.1、默认查询字符串
 *         search.baseInfo.realname_like=zhang
 *         search.age_lt=12
 *         排序及分页请参考 {@link PageableMethodArgumentResolver}
 *     1.2、控制器处理方法写法
 *        public void test(Searchable searchable);
 * 
 *     2.1、自定义查询字符串
 *         foo_search.baseInfo.realname_like=zhang
 *         foo_search.age_lt=12
 *         test_search.age_gt=12
 *         排序及分页请参考 {@link PageableMethodArgumentResolver}
 *     2.2、控制器处理方法写法
 *        public void test(@Qualifier("foo") Searchable searchable1, @Qualifier("test") Searchable searchable2);
 * 
 *     3.1、禁用查询时分页及排序
 *        public void test(@SearchableDefaults(needPage=false, needSort=false) Searchable searchable);
 * </pre>
 */
public class SearchableMethodArgumentResolver extends BaseMethodArgumentResolver {

	private static final PageableMethodArgumentResolver DEFAULT_PAGEABLE_RESOLVER = new PageableMethodArgumentResolver();
	private static final String DEFAULT_SEARCH_PREFIX = "search";

	private String prefix = DEFAULT_SEARCH_PREFIX;

	/**
	 * 分页参数解析器
	 */
	private PageableMethodArgumentResolver pageableMethodArgumentResolver = DEFAULT_PAGEABLE_RESOLVER;
	
	/**
	 * 设置查询参数前缀
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setPageableMethodArgumentResolver(PageableMethodArgumentResolver pageableMethodArgumentResolver) {
		this.pageableMethodArgumentResolver = pageableMethodArgumentResolver;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Searchable.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		String searchPrefix = this.getSearchPrefix(parameter);
		//获取方法形参上的注解
		SearchableDefaults searchDefaults = this.getSearchableDefaults(parameter);
		//获取注解上的参数
		boolean needPage = searchDefaults == null ? false : searchDefaults.needPage();
		boolean needSort = searchDefaults == null ? false : searchDefaults.needSort();
		
		Pageable pageable = null;
		Searchable searchable = null;		
		Map<String, String[]> searcheableMap = getPrefixParameterMap(searchPrefix, webRequest, true);

		// 自定义覆盖默认
		if (searcheableMap.size() == 0) {
			searchable = Searchable.newSearchable();
		} else {
			searchable = Searchable.newSearchable();
			for (String name : searcheableMap.keySet()) {
				String[] mapValues = this.filterSearchValues(searcheableMap.get(name));

				if (mapValues.length == 1) {
					if (name.endsWith("_in") || name.endsWith("_notIn")) {
						searchable.addSearchParam(name, Arrays.asList(mapValues[0].split(",")));
					} else {
						searchable.addSearchParam(name, mapValues[0]);
					}
				} else {
					searchable.addSearchParam(name, mapValues);
				}
			}
		}

		pageable = (Pageable) pageableMethodArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

		// 分页及排序
		if (needPage)
			searchable.addPage(pageable);
		//不要分页，但排序
		else if (needPage && !needSort)
			searchable.addSort(pageable.getSort());

		return searchable;
	}

	private String[] filterSearchValues(String[] values) {
		List<String> result = new ArrayList<String>();
		for (String value : values) {
			if (value != null && !"".equals(value.trim())) {
				result.add(value);
			}
		}
		return result.toArray(values);
	}

	private String getSearchPrefix(MethodParameter parameter) {
		Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

		if (qualifier != null) {
			return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(this.prefix).toString();
		}

		return prefix;
	}

	private SearchableDefaults getSearchableDefaults(MethodParameter parameter) {
		// 首先从参数上找
		SearchableDefaults searchDefaults = parameter.getParameterAnnotation(SearchableDefaults.class);
		// 找不到从方法上找
		if (searchDefaults == null) {
			searchDefaults = parameter.getMethodAnnotation(SearchableDefaults.class);
		}
		return searchDefaults;
	}

	/**
	 * 将默认过滤注解转换为Searchable
	 */
	private Searchable getDefaultFromAnnotation(SearchableDefaults searchableDefaults) {
		Searchable searchable = defaultSearchable(searchableDefaults);
		if (searchable != null) {
			return searchable;
		}

		return Searchable.newSearchable();
	}

	/**
	 * 抽离默认过滤注解中的  过滤条件，将其转换为Searchable
	 */
	private Searchable defaultSearchable(SearchableDefaults searchableDefaults) {

		if (searchableDefaults == null) {
			return null;
		}

		Searchable searchable = Searchable.newSearchable();
		for (String searchParam : searchableDefaults.value()) {
			String[] searchPair = searchParam.split("=");
			String paramName = searchPair[0];
			String paramValue = searchPair[1];
			if (paramName.endsWith("_in") || paramName.endsWith("_notIn")) {
				searchable.addSearchParam(paramName, Arrays.asList(paramValue.split(",")));
			} else {
				searchable.addSearchParam(paramName, paramValue);
			}
		}

		return searchable;
	}
}
