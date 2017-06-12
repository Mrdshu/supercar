package com.xw.supercar.spring.resolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

public abstract class BaseMethodArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     * 获取指定前缀的参数：包括uri varaibles 和 parameters
     *
     * @param namePrefix
     * @param request
     * @subPrefix 是否截取掉namePrefix的前缀
     * @return
     */
    protected Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix) {
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();

        Map<String, String> variables = getUriTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
        	if (variables.get(name) != null && !variables.get(name).trim().isEmpty()) {
                if (name.startsWith(namePrefix)) {
                    //page.pn  则截取 pn
                    if(subPrefix) {
                        char ch = name.charAt(namePrefix.length());
                        //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                        if(illegalChar(ch)) {
                            continue;
                        }
                        result.put(name.substring(namePrefixLength + 1), new String[] {variables.get(name)});
                    } else {
                        result.put(name, new String[] {variables.get(name)});
                    }
                }
        	}
        }

        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            if (name.startsWith(namePrefix)) {
                List<String> values = new ArrayList<String>();
                if (request.getParameterValues(name) != null && request.getParameterValues(name).length > 0) {
                    for (String value : request.getParameterValues(name)) {
                    	if (value != null && !value.trim().isEmpty()) {
                    		values.add(value.trim());
                    	}
                    }
                }
                
                if (values.size() > 0) {
                    //page.pn  则截取 pn
                    if(subPrefix) {
                        char ch = name.charAt(namePrefix.length());
                        //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                        if(illegalChar(ch)) {
                            continue;
                        }
                        result.put(name.substring(namePrefixLength + 1), request.getParameterValues(name));
                    } else {
                        result.put(name, values.toArray(new String[values.size()]));
                    }
                }
            }
        }

        return getResultByOrder(result);
    }

    private boolean illegalChar(char ch) {
        return ch != '.' && ch != '_' && !(ch >= '0' && ch <= '9');
    }


    @SuppressWarnings("unchecked")
    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (variables != null) ? variables : Collections.<String, String>emptyMap();
    }

    private Map<String, String[]> getResultByOrder(Map<String, String[]> result) {
		Map<String, String[]> rs = new LinkedHashMap<String, String[]>();
    	Set<String> keySets = result.keySet();
    	 String[] keys = keySets.toArray(new String[keySets.size()]);
    	for (int i=keys.length; i>=1; i--) {
    		rs.put(keys[i-1], result.get(keys[i-1]));
    	}
    	return rs;
    }
}
