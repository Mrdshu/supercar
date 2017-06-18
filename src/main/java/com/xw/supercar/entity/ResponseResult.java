package com.xw.supercar.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回前端的实体类
 * @author wangsz 2017-06-03
 */
@Getter @Setter
public class ResponseResult {
	/**处理结果*/
	private Boolean success;
	/**错误码*/
	private String errorNo;
	/**错误信息*/
	private String errorMsg;
	/**返回的数据*/
	private Map<String, Object> data = new HashMap<>();
	
	public static ResponseResult generateResponse(){
		ResponseResult responseEntity = new ResponseResult(true, "", "");
		return responseEntity;
	}
	
	/**
	 * 返回错误信息的报文
	 *
	 * @author wsz 2017-06-18
	 */
	public static ResponseResult generateErrorResponse(String errorNo,String errorMsg){
		ResponseResult responseEntity = new ResponseResult(false, errorNo, errorMsg);
		return responseEntity;
	}
	
	public ResponseResult(Boolean success,String errorNo,String errorMsg) {
		this.success = success;
		this.errorNo = errorNo;
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 增加返回数据
	 * @author  wangsz 2017-06-04
	 */
	public void addAttribute(String attributeName,Object attributeValue) {
		data.put(attributeName, attributeValue);
	}
	
}
