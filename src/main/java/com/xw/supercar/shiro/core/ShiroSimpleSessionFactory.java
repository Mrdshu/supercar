package com.xw.supercar.shiro.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import com.xw.supercar.shiro.ShiroSimpleSession;


public class ShiroSimpleSessionFactory implements SessionFactory {
	@Override
    public Session createSession(SessionContext initData) {
    	ShiroSimpleSession session = new ShiroSimpleSession();
        if (initData != null) {
            String host = initData.getHost();
        	session.setHost(host);
            
            if (initData instanceof WebSessionContext) {
                WebSessionContext sessionContext = (WebSessionContext) initData;
                HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            	session.setRemoteIp(ShiroSimpleSessionFactory.getIpAddr(request));
                session.setUserAgent(request.getHeader("User-Agent"));
            }
        }
        return session;
    }
    
    public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}