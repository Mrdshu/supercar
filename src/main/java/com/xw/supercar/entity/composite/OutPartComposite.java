package com.xw.supercar.entity.composite;

import java.util.List;

import com.xw.supercar.entity.OutPart;
import com.xw.supercar.entity.OutPartInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 出库工单组合类，用作controller层方法形参
 * @author wsz 2017-06-26
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class OutPartComposite {
	
	private OutPart outPart;
	
	private List<OutPartInfo> outPartInfos;
}
