package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InPart extends BaseEntity {
	/** 入库单号 */
	private String workOrderNo;

	/** 入库时间 */
	private Date inTime;

	/** 结算方式，数据字典 */
	private String payMethhod;

	/** 供应商，数据字典外键 */
	private String supplier;

	/** 合计金额 */
	private Double sum;

	/** 所属门店 */
	private String company;

	/** 软删除标志 */
	private Boolean isDeleted;
	
	public static enum DP {
		id, workOrderNo, inTime, payMethhod, supplier,sum,company,isDeleted;
	}
}