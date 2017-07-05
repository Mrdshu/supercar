
<!-- toc orderedList:0 depthFrom:1 depthTo:6 -->

* [Client 客户](#client-客户)
	* [字段定义：](#字段定义)
	* [新增测试成功json](#新增测试成功json)
* [LookupDefinition 数据字典定义](#lookupdefinition-数据字典定义)
	* [字段定义：](#字段定义-1)
* [Lookup 数据字典](#lookup-数据字典)
	* [字段定义：](#字段定义-2)
	* [获取树状结构json](#获取树状结构json)
* [Part 配件](#part-配件)
	* [字段定义：](#字段定义-3)
	* [新增测试成功json](#新增测试成功json-1)
	* [分页查询返回json](#分页查询返回json)
* [Inventory 库存](#inventory-库存)
	* [字段定义：](#字段定义-4)
	* [新增测试成功json](#新增测试成功json-2)
	* [分页查询返回json](#分页查询返回json-1)
* [inpart 入库](#inpart-入库)
	* [字段定义：](#字段定义-5)
	* [新增测试成功json](#新增测试成功json-3)
	* [分页查询返回json](#分页查询返回json-2)
	* [详细查询结果（即查询入库工单配件）](#详细查询结果即查询入库工单配件)
	* [删除入库工单](#删除入库工单)
	* [批量删除入库工单](#批量删除入库工单)
* [outPart 出库](#outpart-出库)
	* [字段定义](#字段定义-6)
* [RepairItem 维修服务项目](#repairitem-维修服务项目)
	* [字段定义](#字段定义-7)
* [RepairWorkorder 维修工单](#repairworkorder-维修工单)
	* [字段定义](#字段定义-8)
* [RepairWorkorderItem 维修工单-服务项目](#repairworkorderitem-维修工单-服务项目)
	* [字段定义](#字段定义-9)

<!-- tocstop -->

# Client 客户
<font color = "red">考虑到开发的方便性，以及客户主要针对的是车这一主体。该模块已将原先的`Client`和`Car`合并。**前台请抓紧修改**</font>
## 字段定义：
```
	/**车牌号*/
	private String carNo;
	/**车品牌，数据字典*/
	private String carBrand;
	/**车型*/
	private String carModel;
	/**底盘号*/
	private String carVIN;
	/**车身颜色*/
	private String carColor;
	/**发动机号*/
	private String engineNo;
	/**保险公司*/
	private String insurer;
	/**保险到期时间*/
	private Date insuranceEndtime;
	/**上牌日期*/
	private Date registrationDate;
	/**所属门店*/
	private String company;

	/**客户姓名*/
	private String name;
	/**客户性别（true为男，false为女）*/
	private Boolean sex;
	/**身份证*/
	private String idcard;
	/**客户类别，数据字典*/
	private String type;
	/**客户级别，数据字典*/
	private String level;
	/**邮箱*/
	private String email;
	/**手机*/
	private String mobile;
	/**地址*/
	private String address;
	/**备注*/
	private String description;
	/**创建时间*/
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //若已设置全局json解析date格式配置，则不需要该注解
	private Date createTime;
	/**更新时间*/
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
```

## 新增测试成功json
```
{
	"carNo": "carNo",
	"carBrand": "1",
	"carModel": "carModel",
	"carVIN": "carVIN",
	"carColor": "carColor",
	"engineNo": "engineNo",
	"insurer": "insurer",
	"company": "1",
	"name": "name",
	"sex": false,
	"idcard": "idcard",
	"type": "1",
	"level": "1",
	"email": "email",
	"mobile": "mobile",
	"address": "address",
	"description": "description",
	"isDeleted": 0,
}
```

# LookupDefinition 数据字典定义
<font color = "red">为区分普通数据字典定义和树状数据结构的数据字典定义，新增了`type`这一字段，具体信息在字段定义中</font>
## 字段定义：
```
	/**数据字典定义code*/
	private String code;
	/**数据字典定义名称*/
	private String name;
	/**数据字典定义类型，0为普通，1为树结构*/
	private String type;
	/**数据字典定义描述*/
	private String description;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**软删除标志*/
	private Boolean isDeleted;
```

# Lookup 数据字典
<font color = "red">当新增的数据字典为树状类型时，需要额外填写“父节点”这一属性信息。父节点即为已经存在的该类型的数据字典</font>
## 字段定义：
```
	/**最大层级*/
	public static int maxLevel = 7;
	/**数据字典定义id*/
	private String definitionId;
	/**数据字典code*/
	private String code;
	/**数据字典value*/
	private String value;
	/**描述*/
	private String description;
	/**附加内容*/
	private String additional;
	/**父节点id*/
	private String parentId;
	/**节点层级*/
	private Integer zzLevel;
	/**是否叶子节点*/
	private Boolean zzIsLeaf;
	/**一级父节点*/
	private String zzLevel1Id;
	/**二级父节点*/
	private String zzLevel2Id;
	/**三级父节点*/
	private String zzLevel3Id;
	/**四级父节点*/
	private String zzLevel4Id;
	/**五级父节点*/
	private String zzLevel5Id;
	/**六级父节点*/
	private String zzLevel6Id;
```

## 获取树状结构json
URL:`http://localhost:8090/supercar/lookup/getTree?lookupDefineCode=part_type`
说明：GET方式，传数据字典定义code

返回Json：
```
"id": "192E16F8EA434E029E7911BF9770A504",
	"label": "level1-level1",
	"children": [{
		"id": "E008329ECEE041D3A69464C969E9286D",
		"label": "level2-level2",
		"children": [{
			"id": "12662744B75A48B8B58258E6F7DC8E78",
			"label": "level3-level3",
			"children": []
		},
		{
			"id": "9C37D098242D49268F305D2133DD7D65",
			"label": "level3_2-level3_2",
			"children": []
		}]
```


# Part 配件
## 字段定义：
```
	/** 配件编号 */
	private String code;

	/** 配件名称 */
	private String name;

	/** 单位 ，数据字典外键*/
	private String unitLookup;

	/** 销售价 */
	private Double sale;

	/** 批发价 */
	private Double wholeSale;

	/** 产地 */
	private String produceArea;

	/** 规格，数据字典外键 */
	private String specificationLookup;

	/** 适用车型 */
	private String carModel;

	/** 分类 ，数据字典*/
	private String pCategoryLookup;

	/** 创建日期 */
	private Date createTime;

	/** 更新日期 */
	private Date updateTime;

	/** 软删除标志 */
	private Byte isDeleted;

	/** 禁用标志 */
	private Byte isDisable;
  ```

  ## 新增测试成功json
  ```
  {
    "code": "code",
    "name": "name",
    "unitLookup": "1",
    "produceArea": "produceArea",
    "specificationLookup": "1",
    "carModel": "carModel",
    "pCategoryLookup": "1"
  }

  ```
  ## 分页查询返回json
  ```
  {
    "success": true,
    "errorNo": "",
    "errorMsg": "",
    "data": {
      "page": {
        "content": [{
          "id": "3A9A0BE24BD14C5999C3F74533D8C769",
          "code": "code",
          "name": "name",
          "unitLookup": "1",
          "sale": null,
          "wholeSale": null,
          "produceArea": "produceArea",
          "specificationLookup": "1",
          "carModel": "carModel\r\n\r\n\r\n",
          "createTime": null,
          "updateTime": null,
          "isDeleted": 0,
          "isDisable": 0,
          "pcategoryLookup": "1"
        }],
        "number": 0,
        "size": 10,
        "sort": {},
        "lastPage": true,
        "totalElements": 1,
        "firstPage": true,
        "totalPages": 1,
        "numberOfElements": 1
      }
    }
  }
  ```


# Inventory 库存
## 字段定义：
```
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
```

  ## 新增测试成功json
  ```
{
  "partId": "3A9A0BE24BD14C5999C3F74533D8C769",
  "count": 1,
  "supplierLookup": "1",
  "company": "1",
  "repCodeLookup": "1",
  "isDeleted": false
}
  ```
  ## 分页查询返回json
  ```
{
  "success": true,
  "errorNo": "",
  "errorMsg": "",
  "data": {
    "page": {
      "content": [{
        "id": "274E85D5ECF843BEA5DA00DAC95B149B",
        "partId": "3A9A0BE24BD14C5999C3F74533D8C769",
        "count": 1,
        "cost": null,
        "supplierLookup": "1",
        "company": "1",
        "repCodeLookup": "1",
        "isDeleted": false
      }],
      "number": 0,
      "size": 10,
      "sort": {},
      "lastPage": true,
      "totalElements": 1,
      "firstPage": true,
      "totalPages": 1,
      "numberOfElements": 1
    }
  }
}
  ```

  # inpart 入库
## 字段定义：

```
  //=============入库工单===================
	/** 入库单号 */
	private String workOrderNo;

	/** 入库时间 */
	private Date inTime;

	/** 结算方式，数据字典 */
	private String payMethhodLK;

	/** 供应商，数据字典外键 */
	private String supplierLK;

	/** 合计金额 */
	private Double sum;

	/** 所属门店 */
	private String company;

	/** 软删除标志 */
	private Boolean isDeleted;

	//=============入库工单配件===================
	 /** 入库单号 */
    private String workOrderNo;

    /** 配件id,外键 */
    private String partId;

    /** 配件入库数目 */
    private Integer count;

    /** 进货价 */
    private Long cost;

    /** 供应商，数据字典外键 */
    private String supplierLK;

    /** 库位号code，数据字典外键 */
    private String repositoryCodeLK;

    /** 软删除标志 */
    private Boolean isDeleted;
```

  ## 新增测试成功json
URL：`baseurl/inPart/newInPart`
说明：传一个组合对象，格式如下：

```
{
	"inPart": {
		"workOrderNo": "1",
		"payMethhodLK": "1",
		"supplierLK": "1",
		"company": "1",
		"isDeleted": false
	},
	"inpartInfos": [{
		"workOrderNo": "1",
		"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
		"count": 1,
		"supplierLK": "1",
		"repositoryCodeLK": "1",
		"isDeleted": false
	},
	{
		"workOrderNo": "1",
		"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
		"count": 1,
		"supplierLK": "1",
		"repositoryCodeLK": "1",
		"isDeleted": false
	}]
}
```


## 分页查询返回json
```
{
  "success": true,
  "errorNo": "",
  "errorMsg": "",
  "data": {
    "page": {
      "content": [{
        "workOrderNo": "1",
        "inTime": null,
        "payMethhodLK": "1",
        "supplierLK": "1",
        "sum": null,
        "company": "1",
        "isDeleted": false,
        "id": "C8078E98CB7A496FB81644C562D94F95"
      }],
      "number": 0,
      "size": 10,
      "sort": {},
      "numberOfElements": 1,
      "totalElements": 1,
      "firstPage": true,
      "totalPages": 1,
      "lastPage": true
    }
  }
}
```

## 详细查询结果（即查询入库工单配件）
URL：`http://localhost:8090/supercar/inPart/getInPartInfos?inWorkOrderNo=1`
说明：`GET`方式，传入库工单号，查看该工单的入库配件列表信息
```
{
	"success": true,
	"errorNo": "",
	"errorMsg": "",
	"data": {
		"page": {
			"content": [{
				"workOrderNo": "1",
				"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
				"count": 1,
				"cost": null,
				"supplierLK": "1",
				"repositoryCodeLK": "1",
				"isDeleted": false,
				"id": "CAB55CE3EFD44BF0BE528C15A95D296C",
				"date": {
					"partId": {
						"wholeSale": 10.0,
						"sale": 120.0,
						"code": "code",
						"unitLK": "349DBB62003E4CB7A29F7A0D19790682",
						"produceArea": "produceArea",
						"name": "name",
						"pCategoryLK": "7FA179BA0BAF4CA4874DA57DD6393861",
						"specificationLK": "73C970D8567A4833B554D6EECE5BBFF5",
						"carModel": "carmodel"
					},
					"unitLK": {
						"code": "barrel",
						"value": "桶"
					},
					"repositoryCodeLK": {
						"code": "BYD",
						"value": "比亚迪"
					},
					"supplierLK": {
						"code": "BYD",
						"value": "比亚迪"
					}
				}
			},
			{
				"workOrderNo": "1",
				"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
				"count": 1,
				"cost": null,
				"supplierLK": "1",
				"repositoryCodeLK": "1",
				"isDeleted": false,
				"id": "F9B02519D7444A599B1B20D303818CB8",
				"date": {
					"partId": {
						"wholeSale": 10.0,
						"sale": 120.0,
						"code": "code",
						"unitLK": "349DBB62003E4CB7A29F7A0D19790682",
						"produceArea": "produceArea",
						"name": "name",
						"pCategoryLK": "7FA179BA0BAF4CA4874DA57DD6393861",
						"specificationLK": "73C970D8567A4833B554D6EECE5BBFF5",
						"carModel": "carmodel"
					},
					"unitLK": {
						"code": "barrel",
						"value": "桶"
					},
					"repositoryCodeLK": {
						"code": "BYD",
						"value": "比亚迪"
					},
					"supplierLK": {
						"code": "BYD",
						"value": "比亚迪"
					}
				}
			}],
			"number": 0,
			"size": 10,
			"sort": {

			},
			"numberOfElements": 2,
			"totalPages": 1,
			"totalElements": 2,
			"lastPage": true,
			"firstPage": true
		}
	}
}
```

## 删除入库工单
URL：`http://localhost:8090/supercar/inPart/removeInPart?id=1`
说明：`GET`方式，传入库工单id,将级联软删除工单配件

## 批量删除入库工单
URL：`http://localhost:8090/supercar/inPart/removeInPart?ids=1,2`
说明：`GET`方式，传入库工单ids,将级联软删除工单配件

# outPart 出库
## 字段定义
```
	/** 出库单号 */
	private String workOrderNo;

	/** 出库时间 */
	private Date outTime;

	/** 出库类型，数据字典外键 */
	private String type;

	/** 车主名称 */
	private String clientName;

	/** 领料人，外键 */
	private String receiver;

	/** 合计金额 */
	private Long sum;

	/** 所属门店，数据字典外键 */
	private String company;

	/** 软删除标志 */
	private Byte isDeleted;

	//=============出库配件OutPartInfo==================
	/** 出库单号 */
	private String workOrderNo;

	/** 出库时间 */
	private Date outTime;

	/** 出库类型，数据字典外键 */
	private String type;

	/** 车主名称 */
	private String clientName;

	/** 领料人，外键 */
	private String receiver;

	/** 合计金额 */
	private Long sum;

	/** 所属门店，数据字典外键 */
	private String company;

	/** 软删除标志 */
	private Byte isDeleted;
```


# RepairItem 维修服务项目
## 字段定义
```
			/** 项目名称 */
    	private String name;

	   	/** 备注 */
    	private String desc;

	   	/** 项目类型，数据字典外键 */
    	private String type;

	   	/** 项目代码 */
    	private String code;

	   	/** 工时数 */
    	private Double workingHour;

	   	/** 工种，数据字典外键 */
    	private String workType;

	   	/** 金额 */
    	private Double sum;
```

# RepairWorkorder 维修工单
## 字段定义
```
			/** 修理性质 */
    	private String repairType;

	   	/** 送修时间 */
    	private Date sendTime;

	   	/** 车进店油表 */
    	private Integer carOilmeter;

	   	/** 交车时间 */
    	private Date endTime;

	   	/** 结算金额 */
    	private BigDecimal sum;

	   	/** 维修工单号 */
    	private String workorderNo;

	   	/** 服务顾问 */
    	private String clerk;

	   	/** 工单状态 */
    	private String workorderState;

	   	/** 客户id，外键 */
    	private String clientId;

	   	/** 客户提醒 */
    	private String clentRemind;

	   	/** 车进店里程 */
    	private Integer carMileage;
```

# RepairWorkorderItem 维修工单-服务项目
## 字段定义
```
			/** 维修工单id，外键 */
    	private String workorderId;


	   	/** 维修项目id，外键 */
    	private String itemId;

	   	/** 维修工，外键 */
    	private String mechanic;

    	/** 开始时间 */
    	private Date startTime;
	   	/** 结束时间 */
    	private Date endTime;
```
