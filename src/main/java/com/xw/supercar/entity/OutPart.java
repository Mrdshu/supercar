package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutPart extends BaseEntity {
	/** 出库单号 */
	private String workOrderNo;

	/** 出库时间 */
	private Date outTime;

	/** 出库类型，数据字典外键 */
	private String type;

	/** 车主名称 */
	private String clientName;

	/** 领料人，外键 */
	private String receiver;

	/** 合计金额 */
	private Long sum;

	/** 所属门店，数据字典外键 */
	private String company;

	/** 软删除标志 */
	private Byte isDeleted;
	
	public static enum DP {
		id,workOrderNo, outTime, type, clientName, receiver, sum, company, isDeleted;
	}

}