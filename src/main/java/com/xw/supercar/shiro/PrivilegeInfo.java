package com.xw.supercar.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class PrivilegeInfo implements Serializable{

	private static final long serialVersionUID = -60634854546084038L;

	public static final String SESSION_KEY = "SHIRO_PRIVILEGE_INFO";
	
	private List<String> groups = new ArrayList<String>();
	private List<String> roles = new ArrayList<String>();
	private List<String> restrictions = new ArrayList<String>();
	private List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		if (roles == null)
			roles = new ArrayList<String>();
		this.roles = Collections.unmodifiableList(new ArrayList<String>(new LinkedHashSet<String>(roles)));
	}

	public List<String> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<String> restrictions) {
		if (restrictions == null)
			restrictions = new ArrayList<String>();
		this.restrictions = Collections.unmodifiableList(new ArrayList<String>(new LinkedHashSet<String>(restrictions)));
	}

	public List<Map<String, Object>> getMenus() {
		return menus;
	}

	public void setMenus(List<Map<String, Object>> menus) {
		this.menus = Collections.unmodifiableList(menus);
	}
	
	@Override
	public String toString() {
		return String.format("groups:%s, roles:%s, restrictions:%s, menus:%s",
				groups, roles, restrictions, menus);
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
