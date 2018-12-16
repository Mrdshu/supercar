package com.xw.supercar.spring.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xw.supercar.entity.ResponseResult;
import com.xw.supercar.util.CommonUtil;

/**
 * 控制层异常拦截类
 * @author wsz 2017-06-17
 */
@Aspect
@Component
public class ControllerExceptionAop{
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 当controller层方法出现异常时，组装失败报文返回给页面
	 *
	 * @author wsz 2017-06-17
	 */
	@Around("execution(* com.xw.supercar.controller..*.*(..))")
	public Object aroundOfController(ProceedingJoinPoint pjp) throws Throwable {
		Object rs = null;
		try {
			rs = pjp.proceed();
		} catch (Throwable e) {
			log.error("======method【"+getMethodPath(pjp)+"】出现异常======", e);
			rs = ResponseResult.generateErrorResponse("1111", "系统错误，请联系管理员");
		}
		
		return rs;
	}
	
	/**
	 * 获取拦截方法路径（即类名+方法名）
	 * @author wangsz
	 */
	public  String getMethodPath(ProceedingJoinPoint pjp){
		String tclassName = pjp.getTarget().getClass().getName(); // 切面方法所在类名
		String tMethodName = ((MethodSignature) pjp.getSignature()).getName();	 // 切面方法名
		String methodPath=tclassName + "." + tMethodName;
		
		return methodPath;
	}
}
