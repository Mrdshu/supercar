package com.xw.supercar.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class InPart extends BaseDateEntity {
	/** 入库单号 */
	private String workOrderNo;

	/** 入库时间 */
	private Date inTime;

	/** 结算方式，数据字典 */
	private String payMethhodLK;

	/** 供应商，数据字典外键 */
	private String supplierLK;

	/** 合计金额 */
	private Double sum;

	/** 所属门店，外键 */
	private String company;

	/** 软删除标志 */
	private Boolean isDeleted;
	
	public static enum DP {
		id, workOrderNo, inTime, payMethhodLK, supplierLK,sum,company,isDeleted;
	}
}