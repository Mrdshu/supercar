package com.xw.supercar.entity;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回前端的实体类
 * @author wangsz 2017-06-03
 */
@Getter @Setter
@ApiModel(value = "返回结果")
public class ResponseResult {
	/**处理结果*/
	@ApiModelProperty(value = "处理结果, true or false")
	private Boolean success;
	/**错误码*/
	@ApiModelProperty(value = "错误码")
	private String errorNo;
	/**错误信息*/
	@ApiModelProperty(value = "错误详细信息")
	private String errorMsg;
	/**返回的数据*/
	@ApiModelProperty(value = "返回数据")
	private Map<String, Object> data = new HashMap<>();
	/**数据中id字段对应的信息*/
	Map<String, Map<String, Object>> extendInfo = new HashMap<>();
	
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
