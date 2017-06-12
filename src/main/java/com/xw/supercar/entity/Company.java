package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 公司信息实体类
 * @author wsz 2017-06-09
 */
@Setter @Getter @ToString
public class Company extends BaseEntity{
	/**公司名称*/
	private String name;
	/**公司code*/
	private String code;
	/**公司品牌*/
	private String brand;
	/**公司类别*/
	private String type;
	/**售后热线*/
	private String mobile;
	/**默认车牌*/
	private String carNo;
	/**公司邮箱*/
	private String email;
	/**公司地址*/
	private String address;
	/**备注*/
	private String description;
	
	public static enum DP{
		name,code,brand,type,mobile,carNo,email,address,description
	}
}
