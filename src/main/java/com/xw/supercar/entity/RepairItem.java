package com.xw.supercar.entity;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *维修服务项目
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:35:59
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class RepairItem extends BaseDateEntity {
		
	   	/** 项目类型，数据字典外键 */
    	private String typeLK;
		
	   	/** 项目代码 */
    	private String code;
		
	   	/** 项目名称 */
    	private String name;
		
	   	/** 工时数 */
    	private Double workHour;
		
	   	/** 工种，数据字典外键 */
    	private String workTypeLK;
		
	   	/** 备注 */
    	private String description;
		
	   	/** 金额 */
    	private BigDecimal sum;
		
	public static enum DP {
		id, typeLK, code, name, workHour, workTypeLK, description, sum;	
	}
}