package com.xw.supercar.entity.composite;

import java.util.List;

import com.xw.supercar.entity.InPart;
import com.xw.supercar.entity.InPartInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 入库工单组合类，用作方法形参
 * @author wsz 2017-06-26
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class InPartComposite {
	
	private InPart inPart;
	
	private List<InPartInfo> inpartInfos;
}
