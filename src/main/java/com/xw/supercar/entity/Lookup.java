package com.xw.supercar.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典实体类
 * @author wangsz 2017-06-06
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class Lookup extends BaseEntity{
	/**最大层级*/
	public static int maxLevel = 7;
	
	/**数据字典定义id*/
	private String definitionId;
	/**数据字典code*/
	private String code;
	/**数据字典value*/
	private String value;
	/**描述*/
	private String description;
	/**附加内容*/
	private String additional;
	/**父节点id*/
	private String parentId;
	/**节点层级*/
	private Integer zzLevel;
	/**是否叶子节点*/
	private Boolean zzIsLeaf;
	/**一级父节点*/
	private String zzLevel1Id;
	/**二级父节点*/
	private String zzLevel2Id;
	/**三级父节点*/
	private String zzLevel3Id;
	/**四级父节点*/
	private String zzLevel4Id;
	/**五级父节点*/
	private String zzLevel5Id;
	/**六级父节点*/
	private String zzLevel6Id;
	
	public static enum DP{
		id,definitionId,code,value,description,additional,parentId,zzLevel,zzIsLeaf,
		zzLevel1Id,zzLevel2Id,zzLevel3Id,zzLevel4Id,zzLevel5Id,zzLevel6Id
	}
}
