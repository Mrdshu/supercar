package com.xw.supercar.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息实体类
 * @author wsz 2017-06-09
 */
@Setter @Getter @ToString
public class User extends BaseEntity{
	/**用户名*/
	private String username;
	/**全名*/
	private String fullname;
	/**密码*/
	private String password;
	/**邮箱*/
	private String email;
	/**手机*/
	private String mobile;
	/**角色*/
	private String role;
	/**公司*/
	private String company;
	/**备注*/
	private String description;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志，必须用Boolean*/
	private Boolean isDeleted;
	/**禁用标志*/
	private Boolean isDisable;
	
	public static enum DP{
		username,fullname,password,email,mobile,role,company,description,createTime,updateTime,isDeleted;
	}

}
