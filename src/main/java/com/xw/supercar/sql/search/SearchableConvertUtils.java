package com.xw.supercar.sql.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

public final class SearchableConvertUtils {

	protected final static Log log = LogFactory.getLog(SearchableConvertUtils.class);
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_PATTERN = "HH:mm:ss";
	private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_PATTERN);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> datetimeFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_PATTERN);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> timeFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(TIME_PATTERN);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> timestampFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(TIMESTAMP_PATTERN);
		}
	};    

   /**
     *
     * @param search 查询条件
     * @param entityClass 实体类型
     * @param <T>
     */
    public static <T> void convertSearchValueToEntityValue(final Searchable search, final Class<T> entityClass) {
    	
        if(search.getConverted()) {
            return;
        }

        Collection<SearchFilter> searchFilters = search.toSearchFilters();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(entityClass);
        beanWrapper.setAutoGrowNestedPaths(true);

        for (SearchFilter searchFilter : searchFilters) {
            convert(beanWrapper, searchFilter);
            if(searchFilter.hasOrSearchFilters()) {
                for(SearchFilter orSearchFilter : searchFilter.getOrFilters()) {
                    convert(beanWrapper, orSearchFilter);
                }
            }
        }
        search.markConverted();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static void convert(BeanWrapperImpl beanWrapper, SearchFilter searchFilter) {
        String searchProperty = searchFilter.getSearchProperty();

        //自定义的也不转换
        if(searchFilter.getSearchProperty().startsWith("_") || searchFilter.getOperator() == SearchOperator.custom) {
            return;
        }

        //一元运算符不需要计算
        if(searchFilter.toIsUnaryFilter()) {
            return;
        }

        String entityProperty = searchFilter.getSearchProperty();
        Object value = searchFilter.getValue();
        Class<?> propertyType = beanWrapper.getPropertyType(entityProperty);
        Object newValue = null;
        if (propertyType == null) {
        	newValue = value;
        } else {
            boolean isCollection = value instanceof Collection;
            boolean isArray = value != null && value.getClass().isArray();
            if(isCollection || isArray) {
                List<Object> list = new ArrayList<Object>();
                if(isCollection) {
                    list.addAll((Collection) value);
                } else {
                    list = CollectionUtils.arrayToList(value);
                }
                
                if (propertyType.isArray() || Collection.class.isAssignableFrom(propertyType)) {
                	newValue = list;
                } else {
                    List<Object> newList = new ArrayList<Object>();
                    int length = list.size();
                    for (int i = 0; i < length; i++) {
                    	newList.add(i, getConvertedValue(beanWrapper, searchProperty, entityProperty, list.get(i)));
                    }
                    newValue = newList;
                }
            } else {
                newValue = getConvertedValue(beanWrapper, searchProperty, entityProperty, value);
            }
		}

        searchFilter.setValue(newValue);
    }

	private static Object getConvertedValue(final BeanWrapperImpl beanWrapper, final String searchProperty, final String entityProperty, final Object value) {
		beanWrapper.setPropertyValue(entityProperty, getDatatimeValue(beanWrapper, entityProperty, value));
		Object newValue = beanWrapper.getPropertyValue(entityProperty);
		return newValue;
	}
    
    private static Object getDatatimeValue(final BeanWrapperImpl beanWrapper, final String entityProperty, final Object value) {
     	if (value != null) {
    		Class<?> entityPropertyClass = beanWrapper.getPropertyType(entityProperty);
    		if (entityPropertyClass != null && java.util.Date.class.isAssignableFrom(entityPropertyClass)) {
    			java.util.Date date = null;
    			if (java.util.Date.class.isAssignableFrom(value.getClass())) {
    				date = (java.util.Date) value;
    			}
    			
    			if (value instanceof java.lang.String) {
             		try {
        				date = timestampFormatThreadLocal.get().parse((String) value);
            		} catch (Exception e1) {
            			try {
                			date = datetimeFormatThreadLocal.get().parse((String) value);
            			} catch (Exception e2) {
                			try {
                    			date = dateFormatThreadLocal.get().parse((String) value);
                			} catch (Exception e3) {
                				try {
                					date = timeFormatThreadLocal.get().parse((String) value);
                				} catch (Exception e4) {
                				}
                			}
            			}
            		}
    			}
         		
         		if (date != null) {
         			if (java.sql.Timestamp.class.isAssignableFrom(entityPropertyClass)) {
         				return new java.sql.Timestamp(date.getTime());
         			} else if (java.sql.Time.class.isAssignableFrom(entityPropertyClass)) {
        				return new java.sql.Time(date.getTime());
         			} else if (java.sql.Date.class.isAssignableFrom(entityPropertyClass)) {
        				return new java.sql.Date(date.getTime());
         			}
        			return date;
         		}
    		}
    	}
    	
    	return value;
    }
}