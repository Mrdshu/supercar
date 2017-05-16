package com.xw.supercar.spring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 持有spring上下文的工具类，一个系统只能有一个SpringContextHolder。
 * <p>该工具类主要用于: 通过spring上下文获取bean</p>
 * 
 * @author wangsz
 */
public class SpringContextHolder implements ApplicationContextAware,DisposableBean{
	protected static final Log log = LogFactory.getLog(SpringContextHolder.class);
	
	private static ApplicationContext applicationContext;
	
	/**
	 * 将spring容器上下文：applicationContext注入
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if(applicationContext != null)  throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
		log.info("Injecting 'applicationContext' to " + SpringContextHolder.class.getSimpleName() + ", applicationContext=" + context);
		applicationContext = context;
	}
	
	private static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	/**
	 * 本类SpringContextHolder被销毁时，将spring上下文置空
	 */
	@Override
	public void destroy() throws Exception {
		applicationContext = null;
	}
	
	/**
	 * 根据class获取spring容器中的bean
	 * @author  wangsz
	 */
	public static <T> T getBean(Class<T> c){
		return applicationContext.getBean(c);
	}
	
	/**
	 * 根据class名称获取spring中的bean
	 * @author  wangsz
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		return (T)getApplicationContext().getBean(beanName);
	}

}
