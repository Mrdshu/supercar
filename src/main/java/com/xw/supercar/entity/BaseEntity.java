package com.xw.supercar.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体类父类
 * @author wangsz 2017-06-04
 */
@Getter
@Setter
public abstract class BaseEntity {
	private String id;
	private Map<String, Object> date = new HashMap<>();
}
