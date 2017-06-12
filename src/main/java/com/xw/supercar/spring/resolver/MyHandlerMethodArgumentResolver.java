package com.xw.supercar.spring.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    //用来判断参数是否支持当前resolver
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> klass = parameter.getParameterType();
        if (klass == String.class) {//这里使用参数类型匹配，MethodParameter还包含了方法注解和参数注解信息，可以使用它们来进行识别
            return true;
        }
        return false;
    }
    //真正返回要注入的值
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
    	webRequest.getParameterMap();
    	webRequest.getAttributeNames(0);
        return "custom string";
    }
}
