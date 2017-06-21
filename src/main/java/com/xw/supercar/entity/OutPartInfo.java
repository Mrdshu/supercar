package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutPartInfo extends BaseEntity {
    /** 出库单号 */
    private String workOrderNo;

    /** 库存配件id，外键 */
    private String inventoryId;

    /** 配件出库数目 */
    private Integer count;

    /**项目代码*/
    private String itemCode;
    
    /** 软删除标志 */
    private Boolean isDeleted;
    
    public static enum DP {
		id, workOrderNo, inventoryId, count, itemCode, isDeleted;
	}
}