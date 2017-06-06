package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典实体类
 * @author wangsz 2017-06-06
 */
@Setter @Getter @ToString
public class Lookup extends BaseEntity{
	/**最大层级*/
	public static int maxLevel = 7;
	
	/**数据字典code*/
	private String code;
	/**数据字典value*/
	private String value;
	/**数据字典描述*/
	private String description;
	/**附加内容*/
	private String addtionContent;
	/**父节点id*/
	private String parentId;
	/**数据字典定义id*/
	private String definitionId;
}
