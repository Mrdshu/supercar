package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户-车辆中间表实体类
 * @author wangsz 2017-06-01
 */
@Setter @Getter @ToString
public class ClientCar extends BaseEntity{
	/**车牌号*/
	private String clientId;
	/**车型*/
	private String carId;
	
	public static enum DP{
		id,clientId,carId
	}
}
