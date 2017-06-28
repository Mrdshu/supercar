package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体类父类
 * @author wangsz 2017-06-04
 */
@Getter
@Setter
public abstract class BaseEntity {
	/**主键*/
	private String id;
}
