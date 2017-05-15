package com.xw.supercar.shiro;

import java.util.List;
import java.util.Map;

import com.xw.supercar.entity.User;


public interface IShiroSecurityService {
	
	void testMethod();
	
	/**
	 * 获取登出url
	 * @Title: getLogoutUrl 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getLogoutUrl();
	
	/**
	 * 获取登录url
	 * @Title: getLoginUrl 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getLoginUrl();
	
	/**
	 * 获取登录成功后的url
	 * @Title: getSuccessUrl 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getSuccessUrl();
	
	/**
	 * 获取未授权的跳转路径
	 * @Title: getUnauthorizedUrl 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getUnauthorizedUrl();
	
	/**
	 * 从ShiroSession获取授权的权限信息
	 * @Title: getPrivilegeInfoFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param check
	 * @param @return    参数 
	 * @return PrivilegeInfo    返回类型 
	 * @throws
	 */
	PrivilegeInfo getPrivilegeInfoFromShiroSession(boolean check);
	
	/**
	 * 从ShiroSession中获取用户id
	 * @Title: getUserIdFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param check
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getUserIdFromShiroSession(boolean check);
	
	/**
	 * 从ShiroSession中获取用户名
	 * @Title: getUsernameFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param check
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getUsernameFromShiroSession(boolean check);
	
	/**
	 * 从ShiroSession中获取用户名主认证信息
	 * @Title: getPrincipalFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	String getPrincipalFromShiroSession();
	
	/**
	 * 用户登录
	 * @Title: login 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param username
	 * @param @param password
	 * @param @return    参数 
	 * @return User    返回类型 
	 * @throws
	 */
	User login(String username, String password);
	
	/**
	 * 从ShiroSession中获取用户的信息
	 * @Title: getUserFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param check
	 * @param @return    参数 
	 * @return User    返回类型 
	 * @throws
	 */
	User getUserFromShiroSession(boolean check);
	
	/**
	 * 判断用户是否为管理员
	 * @Title: userIsAdmin 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param user
	 * @param @return    参数 
	 * @return boolean    返回类型 
	 * @throws
	 */
	boolean userIsAdmin(User user);
	
	/**
	 * 从ShiroSession中获取菜单权限信息
	 * @Title: getRestrictionsFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> getRestrictionsFromShiroSession();
	
	/**
	 * 从ShiroSession中获取菜单信息
	 * @Title: getMenusFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param language
	 * @param @return    参数 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> getMenusFromShiroSession(String language);
	
	/**
	 * 从ShiroSession中获取角色信息
	 * @Title: getRolesFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> getRolesFromShiroSession();
	
	/**
	 * 从ShiroSession中获取组信息
	 * @Title: getGroupsFromShiroSession 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    参数 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> getGroupsFromShiroSession();
	
}
