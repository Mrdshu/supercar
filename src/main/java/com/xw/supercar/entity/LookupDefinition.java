package com.xw.supercar.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典定义实体类
 * @author wangsz 2017-06-06
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class LookupDefinition extends BaseEntity{
	/**数据字典定义code*/
	private String code;
	/**数据字典定义名称*/
	private String name;
	/**数据字典定义类型，0为普通，1为树结构*/
	private String type;
	/**数据字典定义描述*/
	private String description;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
	
	public static enum DP{
		id,code,name,type,description,createTime,updateTime,isDeleted
	}
}
