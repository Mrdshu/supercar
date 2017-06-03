package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户车辆信息实体类
 * @author wangsz 2017-06-01
 */
@Setter @Getter @ToString
public class Car extends BaseEntity{
	/**车牌号*/
	private String carNo;
	/**车型*/
	private String carModel;
	/**底盘号*/
	private String carVIN;
	/**车身颜色*/
	private String carColor;
	/**发动机号*/
	private String engineNo;
	/**保险公司*/
	private String insurer;
	/**保险到期时间*/
	private String insuranceEndtime;
	/**上牌日期*/
	private String registrationDate;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
	
	public static enum DP{
		id,carNo,carModel,carVIN,carColor,engineNo,insurer,insuranceEndtime,registrationDate
		,createTime,updateTime,isDeleted
	}
}
