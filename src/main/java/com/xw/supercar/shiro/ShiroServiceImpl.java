package com.xw.supercar.shiro;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.xw.supercar.entity.User;


@Service("shiroService")
public class ShiroServiceImpl implements IShiroSecurityService{
	
	@RequiresRoles({"admin"})
	public void testMethod(){
		System.out.println("testMethod, time: " + new Date());
		
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		
		System.out.println("shiro session: " + val);
	}

	@Override
	public String getLogoutUrl() {
		return "/logout";
	}

	@Override
	public String getLoginUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSuccessUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnauthorizedUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivilegeInfo getPrivilegeInfoFromShiroSession(boolean check) {
		PrivilegeInfo privilegeInfo = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Session session = subject.getSession(true);
				PrivilegeInfo sessionPrivilegeInfo = (PrivilegeInfo) session.getAttribute(PrivilegeInfo.SESSION_KEY);
				if (sessionPrivilegeInfo != null) {
					privilegeInfo = sessionPrivilegeInfo;
				} else {
					privilegeInfo = this.getPrivilegeInfoFromShiroSession(check);
					Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
					User user = new User();
					//user.setUserId(loginUser.getUserId());
					//user.setUserName(loginUser.getUserName());
					privilegeInfo = this.formatPrivilegeInfo(user, privilegeInfo);
					session.setAttribute(PrivilegeInfo.SESSION_KEY, privilegeInfo);
				}
			} else {
				if (check) throw new IllegalStateException("unauthorized");
			}
		} catch (UnavailableSecurityManagerException e) {
			
		}
		
		return privilegeInfo == null ? new PrivilegeInfo() : privilegeInfo;
	}

	@Override
	public String getUserIdFromShiroSession(boolean check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsernameFromShiroSession(boolean check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrincipalFromShiroSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserFromShiroSession(boolean check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userIsAdmin(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getRestrictionsFromShiroSession() {
		Set<String> results = new LinkedHashSet<String>();
		PrivilegeInfo privilegeInfo = this.getPrivilegeInfoFromShiroSession(false);
		if (privilegeInfo != null) {
			List<String> restrictions = privilegeInfo.getRestrictions();
			if (restrictions != null && restrictions.size() > 0) {
				results.addAll(restrictions);
			}
		}
		return Collections.unmodifiableList(new ArrayList<String>(results));
	}

	@Override
	public List<Map<String, Object>> getMenusFromShiroSession(String language) {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		PrivilegeInfo privilegeInfo = this.getPrivilegeInfoFromShiroSession(false);
		if (privilegeInfo != null) {
			List<Map<String, Object>> menus = privilegeInfo.getMenus();
			if (menus != null && menus.size() > 0) {
				//SpringContextHolder.getBean(MenuRestrictionSource.class).i18nForMenuTreeList(menus, language);
				results.addAll(menus);
			}
		}
		return Collections.unmodifiableList(results);
	}

	@Override
	public List<String> getRolesFromShiroSession() {
		Set<String> results = new LinkedHashSet<String>();
		PrivilegeInfo privilegeInfo = this.getPrivilegeInfoFromShiroSession(false);
		if (privilegeInfo != null) {
			List<String> roles = privilegeInfo.getRoles();
			if (roles != null && roles.size() > 0) {
				results.addAll(roles);
			}
		}
		return Collections.unmodifiableList(new ArrayList<String>(results));
	}

	@Override
	public List<String> getGroupsFromShiroSession() {
		Set<String> results = new LinkedHashSet<String>();
		PrivilegeInfo privilegeInfo = this.getPrivilegeInfoFromShiroSession(false);
		if (privilegeInfo != null) {
			List<String> groups = privilegeInfo.getGroups();
			if (groups != null && groups.size() > 0) {
				results.addAll(groups);
			}
		}
		return Collections.unmodifiableList(new ArrayList<String>(results));
	}
	
	private PrivilegeInfo formatPrivilegeInfo(User user, PrivilegeInfo privilegeInfo) {
		
		/*List<String> roles = privilegeInfo.getRoles() != null ? privilegeInfo.getRoles() : new ArrayList<String>();
		List<String> localRestrictions = new ArrayList<String>();
		
		if (this.isAdminUsername(user.getUserName())) {
			Map<String, Map<String, Object>> restrictionMap = MenuRestrictionSource.getRestrictionMap(null, MenuRestrictionSource.TYPE.ADMIN, true, false, null, null, roles);
			localRestrictions.addAll(restrictionMap.keySet());
		} else if (user.getId() != null) {
			Map<String, Map<String, Object>> restrictionMap = MenuRestrictionSource.getRestrictionMap(null, MenuRestrictionSource.TYPE.USER, true, false, null, null, roles);
			localRestrictions.addAll(restrictionMap.keySet());
		}
		
		List<String> restrictions = new ArrayList<String>();
		
		if (localRestrictions != null && !localRestrictions.isEmpty()) {
			restrictions = privilegeInfo.getRestrictions();
			for (String localRestriction : localRestrictions) {
				if (restrictions.contains(localRestriction)) {//!
					restrictions.add(localRestriction);
				}
			}
			privilegeInfo.setRestrictions(restrictions);
		}
		
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		if (this.userIsAdmin(user)) {
			menus = SpringContextHolder.getBean(MenuRestrictionSource.class).getMenuTree(MenuRestrictionSource.TYPE.ADMIN, true, true, null, null);
		} else {
			//localRestrictions
			menus = SpringContextHolder.getBean(MenuRestrictionSource.class).getMenuTreeByRestrictionCodesAndRoles(restrictions, roles, MenuRestrictionSource.TYPE.USER, true, true, null, null);
		}
		
		privilegeInfo.setMenus(menus);*/
		
		return privilegeInfo;
	}
}
