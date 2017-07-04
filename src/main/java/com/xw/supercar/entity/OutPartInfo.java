package com.xw.supercar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class OutPartInfo extends BaseDateEntity {
    /** 出库单号 */
    private String workOrderNo;

    /** 出库配件id，外键 */
    private String partId;

    /** 配件出库数目 */
    private Integer count;

    /**维修工单号*/
    private String repairWorkOrderNo;
    
    /** 软删除标志 */
    private Boolean isDeleted;
    
    public static enum DP {
		id, workOrderNo, partId, count, repairWorkOrderNo, isDeleted;
	}
}