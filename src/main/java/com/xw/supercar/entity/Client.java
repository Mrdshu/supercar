package com.xw.supercar.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户信息实体类
 * @author wangsz 2017-06-01
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class Client extends BaseDateEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**车牌号*/
	private String carNo;
	/**车品牌，数据字典*/
	private String carBrand;
	/**车型*/
	private String carModel;
	/**车架号*/
	private String carVIN;
	/**车身颜色*/
	private String carColor;
	/**发动机号*/
	private String engineNo;
	/**保险公司*/
	private String insurer;
	/**保险到期时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date insuranceEndtime;
	/**上牌日期*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date registrationDate;
	/**所属门店*/
	private String company;
	
	/**客户姓名*/
	private String name;
	/**客户性别（true为男，false为女）*/
	private Boolean sex;
	/**身份证*/
	private String idcard;
	/**客户类别，个人用户还是其余用户，数据字典*/
	private String type;
	/**客户级别，铜银金级别，折扣不一样，数据字典*/
	private String level;
	/**邮箱*/
	private String email;
	/**手机*/
	private String mobile;
	/**地址*/
	private String address;
	/**备注*/
	private String description;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
	
	public static enum DP{
		id,carNo,carBrand,carModel,carVIN,carColor,engineNo,insurer,insuranceEndtime,registrationDate,
		name,sex,idcard,type,level,email,company,mobile,address,description,createTime,updateTime,isDeleted
	}
}
