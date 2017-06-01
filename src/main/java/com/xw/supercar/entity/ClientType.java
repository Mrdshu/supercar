package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户类别实体类。不同类别折扣不一样
 * @author wangsz 2017-06-01
 */
@Setter @Getter @ToString
public class ClientType extends BaseEntity{
	/**类别code*/
	private String code;
	/**类别名称*/
	private String name;
	/**类别描述*/
	private String description;
	/**配件折扣*/
	private Double partsDiscount;
	/**服务项目工时折扣*/
	private Double itemsDiscount;
	
}
