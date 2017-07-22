package com.xw.supercar.entity.composite;

import java.util.List;

import com.xw.supercar.entity.Client;
import com.xw.supercar.entity.RepairWorkorder;
import com.xw.supercar.entity.RepairWorkorderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 维修工单组合类
 * @author wangsz 2017-07-07
 */
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class RepairWorkOrderComposite {
	/**客户信息*/
	private Client client;
	/**维修工单*/
	private RepairWorkorder repairWorkorder;
	/**维修服务项目*/
	private List<RepairWorkorderItem> repairWorkorderItems;
	/**维修出库工单*/
	private OutPartComposite outPartComposite;
}
