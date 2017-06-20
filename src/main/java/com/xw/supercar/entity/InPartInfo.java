package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InPartInfo extends BaseEntity {
    /** 入库单号 */
    private String workOrderNo;

    /** 配件id */
    private String partId;

    /** 配件入库数目 */
    private Integer count;

    /** 进货价 */
    private Long cost;

    /** 供应商，数据字典外键 */
    private String suppler;

    /** 库位号code，数据字典外键 */
    private String repositoryCode;

    /** 软删除标志 */
    private Byte isDelete;

    private String extend1;

    private String extend2;

    private String extend3;
    
    public static enum DP {
		id, workOrderNo, partId, count, cost,suppler,repositoryCode,isDelete;
	}
}