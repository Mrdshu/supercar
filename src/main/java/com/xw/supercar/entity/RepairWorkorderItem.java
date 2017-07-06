package com.xw.supercar.entity;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *维修工单-服务项目
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 22:35:59
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class RepairWorkorderItem extends BaseDateEntity {
		
	   	/** 维修工单id，外键 */
    	private String workorderId;
		
	   	/** 维修项目id，外键 */
    	private String itemId;
		
	   	/** 维修工，外键 */
    	private String mechanic;
		
	   	/** 开始时间 */
    	private Date startTime;
		
	   	/** 结束时间 */
    	private Date endTime;
		
	public static enum DP {
		id, workorderId, itemId, mechanic, startTime, endTime;	
	}
}