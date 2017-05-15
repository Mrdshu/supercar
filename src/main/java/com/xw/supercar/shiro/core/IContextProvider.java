package com.xw.supercar.shiro.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface IContextProvider {

	public HttpServletRequest getRequest();

	/**
	 * @Title: getApplicationRealPath 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param path
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getApplicationRealPath(String path);
	/**
	 * @Title: getApplicationRoot 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getApplicationRoot();
	public void setResponse(HttpServletResponse response);
	public void setRequest(HttpServletRequest request);

	/**
	 * @Title: getApplicationSuffix 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getApplicationSuffix();

	/**
	 * @Title: getApplicationContextPath 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getApplicationContextPath();

	/**
	 * @Title: getServerName 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getServerName();

	/**
	 * @Title: getServerPort 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return int  返回类型 
	 * @throws
	 */
	public int getServerPort();

	/**
	 * @Title: getDomain 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getDomain();

	public String getDomain(HttpServletRequest request);

	/**
	 * @Title: logout 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param   参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void logout();

	/**
	 * @Title: getResponse 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return HttpServletResponse  返回类型 
	 * @throws
	 */
	public HttpServletResponse getResponse();

	/**
	 * @Title: getRequestAttribute 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @return  参数说明 
	 * @return Object  返回类型 
	 * @throws
	 */
	public Object getRequestAttribute(String key);

	/**
	 * @Title: setRequestAttribute 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @param value  参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void setRequestAttribute(String key, Object value);

	/**
	 * @Title: getSessionAttribute 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @return  参数说明 
	 * @return Object  返回类型 
	 * @throws
	 */
	public Object getSessionAttribute(String key);

	/**
	 * @Title: setSessionAttribute 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @param value  参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void setSessionAttribute(String key, Object value);

	/**
	 * @Title: removeAttribute 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key  参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void removeAttribute(String key);

	public Object getApplicationAttribute(String key);

	public void setApplicationAttribute(String key, Object value);

	/**
	 * @Title: getSessionId 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param isCreate
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getSessionId(boolean isCreate);

	/**
	 * @Title: getRemoteIp 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRemoteIp();

	/**
	 * @Title: getRemotePort 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return int  返回类型 
	 * @throws
	 */
	public int getRemotePort();

	/**
	 * @Title: getRequestURL 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRequestURL();

	/**
	 * @Title: getRequestURI 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRequestURI();

	/**
	 * @Title: getRequestBrowser 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRequestBrowser();

	/**
	 * @Title: getRequestOs 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRequestOs();

	/**
	 * @Title: getRequestUserAgent 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getRequestUserAgent();

	/**
	 * @Title: addCookie 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param cookie  参数说明 
	 * @return void  返回类型 
	 * @throws
	 */
	public void addCookie(Cookie cookie);

	/**
	 * @Title: getCookie 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param name
	 * @param @return  参数说明 
	 * @return Cookie  返回类型 
	 * @throws
	 */
	public Cookie getCookie(String name);

	/**
	 * @Title: isMethodPost 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return boolean  返回类型 
	 * @throws
	 */
	public boolean isMethodPost();
	
	/**
	 * @Title: getBasePath 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return  参数说明 
	 * @return String  返回类型 
	 * @throws
	 */
	public String getBasePath();
}
