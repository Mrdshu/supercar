package com.xw.supercar.spring.resolver;

import static com.xw.supercar.constant.DaoConstant.DEFAULT_PAGE_NUMBER;
import static com.xw.supercar.constant.DaoConstant.DEFAULT_PAGE_SIZE;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xw.supercar.sql.page.Pageable;

/**
 * <p>请求分页数据绑定到Pageable，支持请求参数和uri template数据的绑定</p>
 * <p>使用指南：
 * <pre>
 *   1.1、简单的分页请求参数格式如下：
 *     page.size=10  分页大小
 *     page.pn=1    页码 从1开始
 *   1.2、控制器处理方法写法
 *     public void test(Pageable page);
 * </pre>
 * <pre>
 *   2.1、带排序的分页请求参数格式如下：
 *     page.size=10  分页大小
 *     page.pn=1    页码 从1开始
 *     sort.a.b=desc
 *     sort.c=asc
 *   默认按照排序关键词的字典顺序排（因为Map存储是无序） 如果想有序排 可以在sort之后跟一个顺序号
 *     sort2.a.b=desc
 *     sort1.c=asc
 *   2.2、控制器处理方法写法
 *     public void test(Pageable page);
 * </pre>
 * <pre>
 *   3.1、带前缀的 排序分页请求参数格式如下：
 *     test_page.size=10  分页大小
 *     test_page.pn=1    页码 从1开始
 *     test_sort.a.b=desc
 *     test_sort.c=asc
 *     foo_page.size=10  分页大小
 *     foo_page.pn=1    页码 从1开始
 *     foo_sort.a.b=desc
 *     foo_sort.c=asc
 *
 *   排序默认按照请求时顺序排
 *   3.2、控制器处理方法写法
 *     public void test(@Qualifier("test") Pageable page1, @Qualifier("test") Pageable page2);
 * </pre>
 * <p/>
 * <pre>
 *     错误的用法，如果有多个请使用@Qualifier指定前缀
 *     public void fail(Pageable page1, Pageable page2);
 * </pre>
 * <p/>
 */
public class PageableMethodArgumentResolver extends BaseMethodArgumentResolver {

    private static final Pageable DEFAULT_PAGE_REQUEST = new Pageable(0, 25);
    private static final String DEFAULT_PAGE_PREFIX = "page";
    private static final String DEFAULT_SORT_PREFIX = "sort";

    private Pageable fallbackPagable = DEFAULT_PAGE_REQUEST;
    private String pagePrefix = DEFAULT_PAGE_PREFIX;
    private String sortPrefix = DEFAULT_SORT_PREFIX;
    
    private int minPageSize = 5;
    private int maxPageSize = 9999999;

    /**
     * 设置最小分页大小 默认10
     *
     * @param minPageSize
     */
    public void setMinPageSize(int minPageSize) {
        this.minPageSize = minPageSize;
    }

    /**
     * 设置最大分页大小 默认100
     *
     * @param maxPageSize
     */
    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }


    /**
     * Setter to configure a fallback instance of {@link Pageable} that is being used to back missing parameters. Defaults
     * to {@value #DEFAULT_PAGE_REQUEST}.
     *
     * @param fallbackPagable the fallbackPagable to set
     */
    public void setFallbackPagable(Pageable fallbackPagable) {
        this.fallbackPagable = null == fallbackPagable ? DEFAULT_PAGE_REQUEST : fallbackPagable;
    }

    /**
     * Setter to configure the prefix of request parameters to be used to retrieve paging information. Defaults to
     * {@link #DEFAULT_PAGE_PREFIX}.
     *
     * @param pagePrefix the prefix to set
     */
    public void setPagePrefix(String pagePrefix) {
        this.pagePrefix = null == pagePrefix ? DEFAULT_PAGE_PREFIX : pagePrefix;
    }

    public void setSortPrefix(String sortPrefix) {
        this.sortPrefix = null == sortPrefix ? DEFAULT_SORT_PREFIX : sortPrefix;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        //默认的page request
        Pageable defaultPageRequest = new Pageable(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

        String pageableNamePrefix = getPagePrefix(parameter);
        String sortNamePrefix = getSortPrefix(parameter);
        Map<String, String[]> pageableMap = getPrefixParameterMap(pageableNamePrefix, webRequest, true);
        Map<String, String[]> sortMap = getDirectSortParameterCouple(sortNamePrefix, webRequest);
        if (sortMap.size() == 0) {
        	sortMap = getPrefixParameterMap(sortNamePrefix, webRequest, false);
        }

        Map<String, Pageable.Direction> sort = getSort(sortNamePrefix, sortMap, defaultPageRequest, webRequest);
        if (pageableMap.size() == 0) {
            return new Pageable(defaultPageRequest.getNumber(), defaultPageRequest.getSize(), sort == null ? defaultPageRequest.getSort() : sort);
        }

        int pn = getPn(pageableMap, defaultPageRequest);
        int pageSize = getPageSize(pageableMap, defaultPageRequest);

        return new Pageable(pn - 1, pageSize, sort);

    }

    private Map<String, Pageable.Direction> getSort(String sortNamePrefix, Map<String, String[]> sortMap, Pageable defaultPageRequest, NativeWebRequest webRequest) {
    	Map<String, Pageable.Direction> sort = new LinkedHashMap<String, Pageable.Direction>();
        List<OrderedSort> orderedSortList = new ArrayList<OrderedSort>();
        
        for (String name : sortMap.keySet()) {
            //sort1.abc
            int propertyIndex = name.indexOf(".") + 1;

            int order = 0;
            String orderStr = name.substring(sortNamePrefix.length(), propertyIndex - 1);
            try {
                if(!StringUtils.isEmpty(orderStr)) {
                    order = Integer.valueOf(orderStr);
                 }
            } catch (Exception e) {
            }

            String property = name.substring(propertyIndex);
            assertSortProperty(property);
            Pageable.Direction direction = Pageable.Direction.fromString(sortMap.get(name)[0]);

            orderedSortList.add(new OrderedSort(property, direction, order));
        }

        Collections.sort(orderedSortList);
        for(OrderedSort orderedSort : orderedSortList) {
        	sort.put(orderedSort.property, orderedSort.direction);
        }

        if(sort.size() == 0) {
            return defaultPageRequest.getSort();
        }

        return sort;
    }

    /**
     * 防止sql注入，排序字符串只能包含字符 数字 下划线 点 ` "
     *
     * @param property
     */
    private void assertSortProperty(String property) {
        if (!property.matches("[a-zA-Z0-9_、.`\"]*")) {
            throw new IllegalStateException("Sort property error, only contains [a-zA-Z0-9_.`\"]");
        }
    }

    private int getPageSize(Map<String, String[]> pageableMap, Pageable defaultPageRequest) {
        int pageSize = 0;
        try {
            String pageSizeStr = pageableMap.get("size")[0];
            if (pageSizeStr != null) {
                pageSize = Integer.valueOf(pageSizeStr);
            } else {
                pageSize = defaultPageRequest.getSize();
            }
        } catch (Exception e) {
            pageSize = defaultPageRequest.getSize();
        }

        if (pageSize < minPageSize) {
            pageSize = minPageSize;
        }

        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }
        return pageSize;
    }

    private int getPn(Map<String, String[]> pageableMap, Pageable defaultPageRequest) {
        int pn = 1;
        try {
            String pnStr = pageableMap.get("pn")[0];
            if (pnStr != null) {
                pn = Integer.valueOf(pnStr);
            } else {
                pn = defaultPageRequest.getNumber();
            }
        } catch (Exception e) {
            pn = defaultPageRequest.getNumber();
        }

        if (pn < 1) {
            pn = 1;
        }

        return pn;
    }


    /**
     * Resolves the prefix to use to bind properties from. Will prepend a possible {@link Qualifier} if available or
     * return the configured prefix otherwise.
     *
     * @param parameter
     * @return
     */
    private String getPagePrefix(MethodParameter parameter) {

        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

        if (qualifier != null) {
            return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(pagePrefix).toString();
        }

        return pagePrefix;
    }

    private String getSortPrefix(MethodParameter parameter) {

        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

        if (qualifier != null) {
            return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(sortPrefix).toString();
        }

        return sortPrefix;
    }

    /**
     * Asserts uniqueness of all {@link Pageable} parameters of the method of the given {@link MethodParameter}.
     *
     * @param parameter
     */
    @SuppressWarnings("unused")
	private void assertPageableUniqueness(final MethodParameter parameter) {
        Method method = parameter.getMethod();

        if (containsMoreThanOnePageableParameter(method)) {
            Annotation[][] annotations = method.getParameterAnnotations();
            assertQualifiersFor(method.getParameterTypes(), annotations);
        }
    }

    /**
     * Returns whether the given {@link Method} has more than one {@link Pageable} parameter.
     *
     * @param method
     * @return
     */
    private boolean containsMoreThanOnePageableParameter(Method method) {

        boolean pageableFound = false;

        for (Class<?> type : method.getParameterTypes()) {

            if (pageableFound && type.equals(Pageable.class)) {
                return true;
            }

            if (type.equals(Pageable.class)) {
                pageableFound = true;
            }
        }

        return false;
    }

    /**
     * Asserts that every {@link Pageable} parameter of the given parameters carries an {@link org.springframework.beans.factory.annotation.Qualifier} annotation to
     * distinguish them from each other.
     *
     * @param parameterTypes
     * @param annotations
     */
    private void assertQualifiersFor(Class<?>[] parameterTypes, Annotation[][] annotations) {

        Set<String> values = new HashSet<String>();

        for (int i = 0; i < annotations.length; i++) {

            if (Pageable.class.equals(parameterTypes[i])) {

                Qualifier qualifier = findAnnotation(annotations[i]);

                if (null == qualifier) {
                    throw new IllegalStateException(
                            "Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
                }

                if (values.contains(qualifier.value())) {
                    throw new IllegalStateException("Values of the user Qualifiers must be unique!");
                }

                values.add(qualifier.value());
            }
        }
    }

    /**
     * Returns a {@link Qualifier} annotation from the given array of {@link Annotation}s. Returns {@literal null} if the
     * array does not contain a {@link Qualifier} annotation.
     *
     * @param annotations
     * @return
     */
    private Qualifier findAnnotation(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof Qualifier) {
                return (Qualifier) annotation;
            }
        }

        return null;
    }

    protected final Map<String, String[]> getDirectSortParameterCouple(String sortNamePrefix, NativeWebRequest request) {
    	Map<String, String[]> result = new LinkedHashMap<String, String[]>();
    	
    	String sortNameParameter = sortNamePrefix + ".name";
    	String sortOrderParameter = sortNamePrefix + ".order";
    	
    	Map<String, String> variables = getUriTemplateVariables(request);
    	String name = variables.get(sortNameParameter);
    	String value = variables.get(sortOrderParameter);
    	if (name != null && !name.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
    		result.put(name.trim(), new String[] {value.trim()});
    	}
    	
    	name = request.getParameter(sortNameParameter);
    	value = request.getParameter(sortOrderParameter);
    	if (name != null && !name.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
    		try {
    			Pageable.Direction.fromString(value.trim());
        		result.put(sortNamePrefix + "." + name.trim(), new String[] {value.trim()});
    		} catch (Exception e) {
    		}
    	}
    	
    	return result;
    }

    static class OrderedSort implements Comparable<OrderedSort> {
        private String property;
        private Pageable.Direction direction;
        private int order = 0; //默认0 即无序

        OrderedSort(String property, Pageable.Direction direction, int order) {
            this.property = property;
            this.direction = direction;
            this.order = order;
        }

        @Override
        public int compareTo(OrderedSort o) {
            if(o == null) {
                return -1;
            }
            if(this.order > o.order) {
                return 1;
            } else if(this.order < o.order) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}

