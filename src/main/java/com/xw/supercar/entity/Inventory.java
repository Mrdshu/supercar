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
    private String supplierLookup;

    /** 所属门店,外键 */
    private String company;

    /** 库位号code，数据字典外键 */
    private String repCodeLookup;

    /** 软删除标志 */
    private Boolean isDeleted;
    
    public static enum DP {
		id, partId, count, cost, supplierLookup, company, repCodeLookup, isDeleted;
	}
}