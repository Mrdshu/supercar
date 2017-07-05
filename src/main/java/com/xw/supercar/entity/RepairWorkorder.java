package com.xw.supercar.entity;

import java.util.Date;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *维修工单
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 08:32:37
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class RepairWorkorder extends BaseDateEntity {
		
	   	/** 修理性质 */
    	private String repairType;
		
	   	/** 送修时间 */
    	private Date sendTime;
		
	   	/** 车进店油表 */
    	private Integer carOilmeter;
		
	   	/** 交车时间 */
    	private Date endTime;
		
	   	/** 结算金额 */
    	private BigDecimal sum;
		
	   	/** 维修工单号 */
    	private String workorderNo;
		
	   	/** 服务顾问 */
    	private String clerk;
		
	   	/** 工单状态 */
    	private String workorderState;
		
	   	/** 客户id，外键 */
    	private String clientId;
		
	   	/** 客户提醒 */
    	private String clentRemind;
		
	   	/** 车进店里程 */
    	private Integer carMileage;
		
}