package com.xw.supercar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory extends BaseEntity {
    /** 配件id */
    private String partId;

    /** 配件库存数目 */
    private Integer count;

    /** 进货价 */
    private Double cost;

    /** 供应商，数据字典外键 */
    private String supplier;

    /** 所属门店 */
    private String company;

    /** 库位号code，数据字典外键 */
    private String repositoryCode;

    /** 软删除标志 */
    private Byte isDelete;

    private String extend1;

    private String extend2;

    private String extend3;
    
    public static enum DP {
		id, partId, count, cost, supplier, company, repositoryCode, isDelete;
	}
}