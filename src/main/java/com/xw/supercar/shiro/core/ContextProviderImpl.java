package com.xw.supercar.shiro.core;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xw.supercar.util.StringUtils;

@Component("contextProvider")
public class ContextProviderImpl implements IContextProvider {

	private HttpServletResponse response;
	private HttpServletRequest request;
	
	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		//return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return this.request;
	}

	public HttpServletResponse getResponse() {
		//return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
		return this.response;
	}

	public ServletContext getServletContext() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getServletContext();
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}

	public String getSessionId(boolean isCreate) {
		HttpSession session = getSession(isCreate);
		if (session == null) {
			return null;
		} else {
			return session.getId();
		}
	}

	public String getApplicationRealPath(String path) {
		return StringUtils.conversionSpecialCharacters(getServletContext().getRealPath(path));
	}

	public String getApplicationRoot() {
		return getApplicationRealPath("/");
	}

	public String getApplicationContextPath() {
		return getRequest().getContextPath();
	}

	public String getApplicationSuffix() {
		if (getRequestURI().contains(".")) {
			return getRequestURI().substring(getRequestURI().lastIndexOf("."));
		} else {
			return null;
		}
	}

	public String getServerName() {
		return getRequest().getServerName();
	}

	public int getServerPort() {
		return getRequest().getServerPort();
	}

	public String getDomain() {
		String domain = getServerName();
		if (domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		return domain;
	}

	public String getDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		return domain;
	}

	public void logout() {
		HttpSession session = getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	public Object getRequestAttribute(String key) {
		return getRequest().getAttribute(key);
	}

	public void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public Object getSessionAttribute(String key) {
		HttpSession session = getSession(false);
		if (session == null) {
			return null;
		} else {
			return session.getAttribute(key);
		}
	}

	public void setSessionAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public void removeAttribute(String key) {
		getSession().removeAttribute(key);
	}

	public Object getApplicationAttribute(String key) {
		return getServletContext().getAttribute(key);
	}

	public void setApplicationAttribute(String key, Object value) {
		getServletContext().setAttribute(key, value);
	}

	public String getRemoteIp() {
		// return TmIpUtil.getIpAddr(getRequest());
		return getRequest().getRemoteAddr();
	}

	public int getRemotePort() {
		return getRequest().getRemotePort();
	}

	public String getRequestURL() {
		return getRequest().getRequestURL().toString();
	}

	public String getRequestURI() {
		return getRequest().getRequestURI();
	}

	public String getRequestBrowser() {
		String userAgent = getRequestUserAgent();
		String[] agents = userAgent.split(";");
		if (agents.length > 1) {
			return agents[1].trim();
		} else {
			return null;
		}
	}

	public String getRequestOs() {
		String userAgent = getRequestUserAgent();
		String[] agents = userAgent.split(";");
		if (agents.length > 2) {
			return agents[2].trim();
		} else {
			return null;
		}
	}

	public String getRequestUserAgent() {
		return getRequest().getHeader("user-agent");
	}

	public void addCookie(Cookie cookie) {
		getResponse().addCookie(cookie);
	}

	public Cookie getCookie(String name) {
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	public boolean isMethodPost() {
		String method = getRequest().getMethod();
		if ("post".equalsIgnoreCase(method)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getBasePath() {
		
		return null;
	}

	
}
