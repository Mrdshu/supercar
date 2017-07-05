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
 *出库工单
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-05 12:04:03
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class OutPart extends BaseDateEntity {
		
	   	/** 出库单号 */
    	private String workOrderNo;
		
	   	/** 出库类型，数据字典外键 */
    	private String type;
		
	   	/** 车主名称 */
    	private String clientName;
		
	   	/** 领料人，外键 */
    	private String receiver;
		
	   	/** 出库时间 */
    	private Date outTime;
		
	   	/** 合计金额 */
    	private BigDecimal sum;
		
	   	/** 所属门店，公司外键 */
    	private String company;
		
	   	/** 部门，数据字典外键。出库类型：配件内耗时使用 */
    	private String department;
		
	   	/** 维修工单号。出库类型：维修领料时使用 */
    	private String repairWorkorderNo;
		
	   	/** 车牌号。出库类型：配件销售时使用 */
    	private String carNo;
		
	   	/** 软删除标志 */
    	private Boolean isDeleted;
		
	public static enum DP {
		id, workOrderNo, type, clientName, receiver, outTime, sum, company, department, repairWorkorderNo, carNo, isDeleted;	
	}
}