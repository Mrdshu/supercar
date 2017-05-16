package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class User extends BaseEntity{
	/**用户名*/
	private String username;
	/**密码*/
	private String password;
	/**此处必须为包装类型Boolean，若为boolean，则自动生成的get方法为isDeleted()*/
	private Boolean isDeleted;
	
	public static enum DP{
		username,password,isDeleted;
	}
	
	public User(){}

}
