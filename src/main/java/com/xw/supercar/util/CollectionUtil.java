package com.xw.supercar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 集合工具类
 * @author wangsz 2017-07-06
 */
public class CollectionUtil  {
	private static final Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成Map
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map<?, ?> extractToMap(Collection<?> collection, String keyPropertyName, String valuePropertyName) {
		Map<?, ?> map = new HashMap<>();
		try {
			for (Object obj : collection) {
				map.put(ReflectUtil.getPropertyValue(obj, keyPropertyName),
						ReflectUtil.getPropertyValue(obj, valuePropertyName));
			}
		} catch (Exception e) {
			logger.error("提取集合中的对象的属性(通过Getter函数), 组合成Map-extractToMap() exception...", e);
		}
		return map;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * 
	 * @return List
	 */
	public static <T> List<T> extractToList(Collection<?> collection, String propertyName) {
		return extractToList(collection, propertyName, false);
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param ignoreEmptyValue
	 *            是否过滤null值和""值
	 * 
	 * @return List
	 */
	public static <T> List<T> extractToList(Collection<?> collection, String propertyName, boolean ignoreEmptyValue) {
		if (collection == null) {
			return null;
		}
		List<T> list = new ArrayList<>();

		try {
			for (Object obj : collection) {
				@SuppressWarnings("unchecked")
				T value = (T) ReflectUtil.getPropertyValue(obj, propertyName);
				if (ignoreEmptyValue && value == null || value.toString().equals("")) {
					continue;
				}
				list.add(ReflectUtil.getPropertyValue(obj, propertyName));
			}
		} catch (Exception e) {
			logger.error("提取集合中的对象的属性(通过Getter函数), 组合成List-extractToList() exception...", e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(Collection<?> collection, String propertyName, String separator) {
		List<?> list = extractToList(collection, propertyName);
		
		StringBuilder rs = new StringBuilder();
		for (Object object : list) {
			rs.append(object+",");
		}
		
		if(rs.length() > 0 )
			return rs.substring(0, rs.length()-1);
		return rs.toString();
	}
	
	/**
	 * 比较两个集合，获取增加的元素集合以及删除的元素集合
	 * @param oldElements 被比较的集合
	 * @param newElements 比较的集合
	 * @param addElements  比较后增加的元素集合
	 * @param deleteElements  比较后删除的元素集合
	 *
	 * @author wangsz  Mar 29, 2017 4:23:32 PM
	 */
	public void compareList(List<String> oldElements,List<String> newElements,List<String> addElements,List<String> deleteElements) {
		if(oldElements == null || newElements == null || addElements == null || deleteElements == null)
			return ;
		
		Map<String, String> olElementsMap = new HashMap<String, String>();
		List<String> oldCopyElements = new ArrayList<String>(oldElements);
		//将oldElements转换为Map
		for (String oldElement : oldElements) {
			olElementsMap.put(oldElement, "");
		}
		//迭代newElements，若oldElements没有的元素则放入增量集合
		for (String newElement : newElements) {
			if(olElementsMap.get(newElement) == null)
				addElements.add(newElement);
			else 
				oldCopyElements.remove(newElement);
		}
		
		deleteElements.addAll(oldCopyElements);
	}

}
