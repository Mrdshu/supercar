package com.xw.supercar.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *客户优惠券
 * </p>
 * 
 * @author wangsz
 * @date 2017-09-13 16:12:12
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class ClientCoupon extends BaseDateEntity {
		   	/** 客户id */
    	private String clientId;
		   	/** 优惠券数据字典id */
    	private String couponId;
		   	/** 客户优惠券数目 */
    	private Integer num;
		
	public static enum DP {
		id, clientId, couponId, num;	
	}
}