package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典定义实体类
 * @author wangsz 2017-06-06
 */
@Setter @Getter @ToString
public class LookupDefinition extends BaseEntity{
	/**数据字典定义code*/
	private String code;
	/**数据字典定义名称*/
	private String name;
	/**数据字典定义描述*/
	private String description;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
}
