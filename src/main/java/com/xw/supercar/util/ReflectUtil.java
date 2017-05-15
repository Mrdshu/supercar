package com.xw.supercar.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.xw.supercar.dao.BaseDao;
import com.xw.supercar.dao.UserDao;

/**
 * 反射工具类
 * @author wangsz 2017-05-10
 */
public class ReflectUtil {
	
	/**
	 * 获取指定class的泛型class(单个)
	 * @author  wangsz 2017-05-10
	 */
	public static Class<?> getSingleGenericClass(Class<?> clazz) {
        Type[] params = getGenericTypes(clazz);
        if(params.length > 0)
        	return (Class<?>) params[0];
        
        return null;
	}
	
	/**
	 * 获取指定class的泛型Type数组
	 * @author  wangsz 2017-05-10
	 */
	public static  Type[] getGenericTypes(Class<?> clazz) {
		Type genType = clazz.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return  params;
	}
	
	/**
	 * 通过反射执行指定class的指定方法
	 *
	 * @author wangsz  Dec 27, 2016 1:58:39 PM
	 */
	public static Object exeMethodOfClass(Class<?> objClass,String methodName,Class<?>[] parameterTypes,Object[] parameters) {
		Object rs = null;
		try {
			Method method = objClass.getMethod(methodName, parameterTypes);
			rs = method.invoke(objClass.newInstance(), parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 反射执行对象的方法（无形参）
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * 反射执行对象的方法
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		} catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}
	
	/**
	 * 通过反射，获取实体中指定属性的值
	 * @param object 实体对象
	 * @param propertyDescriptor 属性描述
	 * 
	 * @author  wangsz 2017-05-11
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> V getPropertyValue(T object, PropertyDescriptor propertyDescriptor) {
		Assert.notNull(object, "'object' must not be null.");

		Method method = propertyDescriptor.getReadMethod();
		return (V) invokeMethod(method, object);
	}
	
	/**
	 * 通过反射，设置实体中指定属性的值
	 * @param object 实体类
	 * @param propertyDescriptor 属性描述
	 * @param value 设置的属性值
	 * 
	 * @author  wangsz 2017-05-11
	 */
	public static <T, V> void setPropertyValue(T object, PropertyDescriptor propertyDescriptor, V value) {
		Assert.notNull(object, "'object' must not be null.");

		Method method = propertyDescriptor.getWriteMethod();
		invokeMethod(method, object, value);
	}
	
	/**
	 * 通过反射，设置实体中指定属性的值
	 * @param object  实体类
	 * @param fieldName 属性名称
	 * @param value  设置的属性值
	 * 
	 * @author  wangsz 2017-05-11
	 */
	public static <T, V> void setPropertyValue(T object, String fieldName, V value) {
		Assert.notNull(object, "'object' must not be null.");

		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, object.getClass());
			setPropertyValue(object, propertyDescriptor, value);
		} catch (Exception e) {
			handleReflectionException(e);
		}
	}
	
	/**
	 * 返回指定bean中的属性数组
	 * @param clazz 指定bean的class
	 * @param filterNoWriteMethod 过滤无set()方法的属性
	 * @param filterNoReadMethod 过滤无get()方法的属性
	 * @return
	 * @author  wangsz 2017-05-10
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz,boolean filterNoWriteMethod,boolean filterNoReadMethod) {
		List<PropertyDescriptor> newDescriptorList = new ArrayList<PropertyDescriptor>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : descriptors) {
				if ((!filterNoWriteMethod || descriptor.getWriteMethod() != null) 
						&& (!filterNoReadMethod || descriptor.getReadMethod() != null))
				newDescriptorList.add(descriptor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDescriptorList.toArray(new PropertyDescriptor[] {});
	}
	
	
	/**
	 * 处理反射异常
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			throw new UndeclaredThrowableException(ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}
	
	public static void main(String[] args) {}
}
