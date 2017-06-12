package com.xw.supercar.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>先从参数找，参数找不到从方法上找，否则使用默认的查询参数</p>
 * <pre>
 *     格式如下：
 *     value = {"baseInfo.age_lt=123", "name_like=abc", "id_in=1,2,3,4"}
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.PARAMETER}) //注解目标：方法和参数注解
@Retention(RetentionPolicy.RUNTIME) //保留到程序运行时
@Documented //该Annotation可以被javadoc工具提取成文档
public @interface SearchableDefaults {
	/**
     * 默认查询参数字符串
     * @return
     */
    String[] value() default {};

    /**
     * 是否需要分页
     * @return
     */
    boolean needPage() default true;

    /**
     * 是否需要排序
     * @return
     */
    boolean needSort() default true;
}
