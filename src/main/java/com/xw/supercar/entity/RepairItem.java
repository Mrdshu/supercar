package com.xw.supercar.entity;



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
 * @date 2017-07-06 17:45:03
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class RepairItem extends BaseDateEntity {
		
	   	/** 项目类型，数据字典外键 */
    	private String type;
		
	   	/** 项目代码 */
    	private String code;
		
	   	/** 项目名称 */
    	private String name;
		
	   	/** 工时数 */
    	private Double workingHour;
		
	   	/** 工种，数据字典外键 */
    	private String workType;
		
	   	/** 备注 */
    	private String desc;
		
	   	/** 金额 */
    	private Double sum;
		
	public static enum DP {
		id, type, code, name, workingHour, workType, desc, sum;	
	}
}