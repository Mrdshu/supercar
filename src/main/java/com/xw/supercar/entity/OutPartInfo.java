package com.xw.supercar.entity;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *出库工单配件信息
 * </p>
 * 
 * @author wangsz
 * @date 2017-07-06 16:36:15
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class OutPartInfo extends BaseDateEntity {
		
	   	/** 出库单号 */
    	private String workOrderNo;
		
	   	/** 库存配件id，外键 */
    	private String inventoryId;
		
	   	/** 配件销售价 */
    	private BigDecimal sale;
		
	   	/** 配件出库数目 */
    	private Integer count;
		
	   	/** 软删除标志 */
    	private Boolean isDeleted;
		
	public static enum DP {
		id, workOrderNo, inventoryId, sale, count, isDeleted;	
	}
}