package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 配件实体类
 * @author wsz 2017-06-20
 */
@Getter
@Setter
public class Part extends BaseEntity {
	/** 配件编号 */
	private String code;

	/** 配件名称 */
	private String name;

	/** 单位 ，数据字典外键*/
	private String unitLookup;

	/** 销售价 */
	private Double sale;

	/** 批发价 */
	private Double wholeSale;

	/** 产地 */
	private String produceArea;

	/** 规格，数据字典外键 */
	private String specificationLookup;

	/** 适用车型 */
	private String carModel;

	/** 分类 ，数据字典*/
	private String pCategoryLookup;

	/** 创建日期 */
	private Date createTime;

	/** 更新日期 */
	private Date updateTime;

	/** 软删除标志 */
	private Byte isDeleted;

	/** 禁用标志 */
	private Byte isDisable;


	public static enum DP {
		id, code, name, unitLookup, sale, wholeSale, produceArea, specificationLookup, carModel, pCategoryLookup, createTime, updateTime, isDisable, isDeleted;
	}

}