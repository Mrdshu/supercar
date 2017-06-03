package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户信息实体类
 * @author wangsz 2017-06-01
 */
@Setter @Getter @ToString
public class Client extends BaseEntity{
	/**客户姓名*/
	private String name;
	/**客户性别（true为男，false为女）*/
	private Boolean sex;
	/**身份证*/
	private String idcard;
	/**客户类别*/
	private String type;
	/**邮箱*/
	private String email;
	/**手机*/
	private String mobile;
	/**地址*/
	private String address;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
	
	public static enum DP{
		id,name,sex,idcard,type,email,mobile,address,createTime,updateTime,isDeleted
	}
}
