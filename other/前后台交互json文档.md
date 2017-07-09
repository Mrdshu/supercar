
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
	* [新增测试成功json](#新增测试成功json-4)
	* [分页查询返回json](#分页查询返回json-3)
	* [详细查询结果（即查询出库工单配件）](#详细查询结果即查询出库工单配件)
	* [删除出库工单](#删除出库工单)
	* [批量删除出库工单](#批量删除出库工单)
* [RepairItem 维修服务项目](#repairitem-维修服务项目)
	* [字段定义](#字段定义-7)
	* [新增测试成功json](#新增测试成功json-5)
	* [分页查询返回json](#分页查询返回json-4)
* [RepairWorkorder 维修工单](#repairworkorder-维修工单)
	* [字段定义](#字段定义-8)
	* [新增测试成功json](#新增测试成功json-6)
	* [修改测试成功json](#修改测试成功json)
	* [分页查询返回json](#分页查询返回json-5)
	* [查询维修工单详细信息](#查询维修工单详细信息)

<!-- tocstop -->

# Client 客户
考虑到开发的方便性，以及客户主要针对的是车这一主体。该模块已将原先的`Client`和`Car`合并。
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
为区分普通数据字典定义和树状数据结构的数据字典定义，新增了`type`这一字段，具体信息在字段定义中
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
当新增的数据字典为树状类型时，需要额外填写“父节点”这一属性信息。父节点即为已经存在的该类型的数据字典
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
说明：传一个组合对象，<font color="red">注意：此操作通过数据库触发器修改库存表对应配件的数目</font>。格式如下：

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
		"cost": 1.1,
        "supplierLK": "1",
        "repositoryCodeLK": "1",
        "isDeleted": false
    },
    {
        "workOrderNo": "1",
        "partId": "475980DBF3FC4EC48B63C7C04156B5FC",
        "count": 1,
		"cost": 2.2,
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
说明：`GET`方式，传入库工单id。此操作将级联软删除工单配件，<font color="red">同时通过数据库触发器修改库存表对应配件的数目</font>

## 批量删除入库工单
URL：`http://localhost:8090/supercar/inPart/removeInPart?ids=1,2`
说明：`GET`方式，传入库工单ids。将级联软删除工单配件，<font color="red">同时通过数据库触发器修改库存表对应配件的数目</font>

# outPart 出库
## 字段定义
```
		/** 出库单号 */
		private String workOrderNo;

		/** 出库类型，0-维修领料，1-配件销售，2-配件内耗 */
		private String type;

		/** 客户名称 */
		private String clientName;

		/** 领料人，用户外键 */
		private String receiver;

		/** 出库时间 */
		private Date outTime;

		/** 合计金额 */
		private BigDecimal sum;

		/** 维修工单号。出库类型：维修领料时使用 */
		private String repairWorkorderNo;

		/** 车牌号。出库类型：配件销售时使用 */
		private String carNo;

		/** 部门，数据字典外键。出库类型：配件内耗时使用 */
		private String departmentLK;

		/** 所属门店，公司外键 */
		private String company;

		/** 软删除标志 */
		private Boolean isDeleted;

	//=============出库配件OutPartInfo==================
		/** 出库单号 */
		private String workOrderNo;

		/** 库存配件id，外键 */
		private String inventoryId;

		/** 配件销售价 */
		private BigDecimal sale;

		/** 配件出库数目 */
		private Integer count;

		/** 软删除标志 */
		private Boolean isDeleted;
```

## 新增测试成功json
URL：`baseurl/outPart/newOutPart`
说明：传一个组合对象`OutPartComposite`，<font color="red">注意：此操作通过数据库触发器修改库存表对应配件的数目</font>。格式如下：

```
{
	"outPart": {
		"workOrderNo": "workOrderNo",
		"type": "1",
		"clientName": "clientName",
		"receiver": "1",
		"repairWorkorderNo": "repairWorkorderNo",
		"carNo": "carNo",
		"departmentLK": "1",
		"company": "1",
		"isDeleted": false,
		"date": {}
	},
	"outPartInfos": [{
			"workOrderNo": "workOrderNo",
			"inventoryId": "87c4ce9461e511e7a848704d7bbc2105",
			"count": 2,
			"isDeleted": false,
			"sale":11.1,
			"date": {}
		}, {
			"workOrderNo": "workOrderNo",
			"inventoryId": "936c6b7a621d11e7b44d0c5b8f279a64",
			"count": 3,
			"sale":22.2,
			"isDeleted": false,
			"date": {}
		}
	]
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
					"workOrderNo": "2",
					"type": "0",
					"clientName": "clientName",
					"receiver": "1",
					"outTime": null,
					"sum": null,
					"repairWorkorderNo": "repairWorkorderNo",
					"carNo": "carNo",
					"departmentLK": "1",
					"company": "1",
					"isDeleted": false,
					"id": "213AB1D129944073A8160C33766FD0F7",
					"date": {
						"receiver": {
							"username": "wsz",
							"fullname": "王树政",
							"password": "e10adc3949ba59abbe56e057f20f883e",
							"email": null,
							"mobile": null,
							"role": "A072D82A6AE14146B47A70E4C58AA28D",
							"company": "1",
							"description": null,
							"createTime": "2017-06-18 16:20:52",
							"updateTime": "2017-06-28 21:00:38",
							"isDeleted": false,
							"isDisable": false,
							"id": "1",
							"date": {}
						},
						"company": {
							"name": "深圳门店",
							"code": "shenzhen",
							"brand": "辉门冠军",
							"type": "E2221CCC83404FAEB89A882D5E112E15",
							"mobile": "111",
							"carNo": "深A",
							"email": null,
							"address": null,
							"description": null,
							"createTime": "2017-06-18 14:37:28",
							"updateTime": "2017-06-18 14:37:28",
							"isDeleted": false,
							"id": "1",
							"date": {}
						},
						"departmentLK": {
							"definitionId": "1",
							"code": "BYD",
							"value": "比亚迪",
							"description": null,
							"additional": null,
							"parentId": null,
							"zzLevel": null,
							"zzIsLeaf": null,
							"zzLevel1Id": null,
							"zzLevel2Id": null,
							"zzLevel3Id": null,
							"zzLevel4Id": null,
							"zzLevel5Id": null,
							"zzLevel6Id": null,
							"id": "1"
						}
					}
				}, {
					"workOrderNo": "4",
					"type": "1",
					"clientName": "clientName",
					"receiver": "1",
					"outTime": null,
					"sum": null,
					"repairWorkorderNo": "repairWorkorderNo",
					"carNo": "carNo",
					"departmentLK": "1",
					"company": "1",
					"isDeleted": false,
					"id": "4E3AC9AA1B8C4C92A61B6994F256C78C",
					"date": {
						"receiver": {
							"username": "wsz",
							"fullname": "王树政",
							"password": "e10adc3949ba59abbe56e057f20f883e",
							"email": null,
							"mobile": null,
							"role": "A072D82A6AE14146B47A70E4C58AA28D",
							"company": "1",
							"description": null,
							"createTime": "2017-06-18 16:20:52",
							"updateTime": "2017-06-28 21:00:38",
							"isDeleted": false,
							"isDisable": false,
							"id": "1",
							"date": {}
						},
						"company": {
							"name": "深圳门店",
							"code": "shenzhen",
							"brand": "辉门冠军",
							"type": "E2221CCC83404FAEB89A882D5E112E15",
							"mobile": "111",
							"carNo": "深A",
							"email": null,
							"address": null,
							"description": null,
							"createTime": "2017-06-18 14:37:28",
							"updateTime": "2017-06-18 14:37:28",
							"isDeleted": false,
							"id": "1",
							"date": {}
						},
						"departmentLK": {
							"definitionId": "1",
							"code": "BYD",
							"value": "比亚迪",
							"description": null,
							"additional": null,
							"parentId": null,
							"zzLevel": null,
							"zzIsLeaf": null,
							"zzLevel1Id": null,
							"zzLevel2Id": null,
							"zzLevel3Id": null,
							"zzLevel4Id": null,
							"zzLevel5Id": null,
							"zzLevel6Id": null,
							"id": "1"
						}
					}
				}, {
					"workOrderNo": "3",
					"type": "1",
					"clientName": "clientName",
					"receiver": "1",
					"outTime": null,
					"sum": null,
					"repairWorkorderNo": "repairWorkorderNo",
					"carNo": "carNo",
					"departmentLK": "1",
					"company": "1",
					"isDeleted": false,
					"id": "A50C7514B3E54182BF0139DF0181B9B4",
					"date": {
						"receiver": {
							"username": "wsz",
							"fullname": "王树政",
							"password": "e10adc3949ba59abbe56e057f20f883e",
							"email": null,
							"mobile": null,
							"role": "A072D82A6AE14146B47A70E4C58AA28D",
							"company": "1",
							"description": null,
							"createTime": "2017-06-18 16:20:52",
							"updateTime": "2017-06-28 21:00:38",
							"isDeleted": false,
							"isDisable": false,
							"id": "1",
							"date": {}
						},
						"company": {
							"name": "深圳门店",
							"code": "shenzhen",
							"brand": "辉门冠军",
							"type": "E2221CCC83404FAEB89A882D5E112E15",
							"mobile": "111",
							"carNo": "深A",
							"email": null,
							"address": null,
							"description": null,
							"createTime": "2017-06-18 14:37:28",
							"updateTime": "2017-06-18 14:37:28",
							"isDeleted": false,
							"id": "1",
							"date": {}
						},
						"departmentLK": {
							"definitionId": "1",
							"code": "BYD",
							"value": "比亚迪",
							"description": null,
							"additional": null,
							"parentId": null,
							"zzLevel": null,
							"zzIsLeaf": null,
							"zzLevel1Id": null,
							"zzLevel2Id": null,
							"zzLevel3Id": null,
							"zzLevel4Id": null,
							"zzLevel5Id": null,
							"zzLevel6Id": null,
							"id": "1"
						}
					}
				}
			],
			"number": 0,
			"size": 10,
			"sort": {},
			"numberOfElements": 3,
			"totalPages": 1,
			"totalElements": 3,
			"firstPage": true,
			"lastPage": true
		}
	}
}
```

## 详细查询结果（即查询出库工单配件）
URL：`http://localhost:8090/supercar/outPart/getOutPartInfos?outWorkOrderNo=3`
说明：`GET`方式，传出库工单号，查看该工单的出库配件列表信息
```
{
	"success": true,
	"errorNo": "",
	"errorMsg": "",
	"data": {
		"page": {
			"content": [{
					"workOrderNo": "3",
					"inventoryId": "87c4ce9461e511e7a848704d7bbc2105",
					"sale": 11,
					"count": 2,
					"isDeleted": false,
					"id": "33840FE28DFE4E2792F115BF2A9C5DB3",
					"date": {
						"inventoryId": {
							"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
							"count": 6,
							"cost": null,
							"supplierLK": "1",
							"company": "1",
							"repCodeLK": "1",
							"isDeleted": false,
							"id": "87c4ce9461e511e7a848704d7bbc2105",
							"date": {}
						}
					}
				}, {
					"workOrderNo": "3",
					"inventoryId": "936c6b7a621d11e7b44d0c5b8f279a64",
					"sale": 22,
					"count": 3,
					"isDeleted": false,
					"id": "7784D0D7F3604BB9BDF53F0B46B31FAD",
					"date": {
						"inventoryId": {
							"partId": "6EE27FCCC34C4C86ABB2B6FAD3FA9BC9",
							"count": 5,
							"cost": null,
							"supplierLK": "1",
							"company": "1",
							"repCodeLK": "1",
							"isDeleted": false,
							"id": "936c6b7a621d11e7b44d0c5b8f279a64",
							"date": {}
						}
					}
				}
			],
			"number": 0,
			"size": 10,
			"sort": {},
			"numberOfElements": 2,
			"totalPages": 1,
			"totalElements": 2,
			"firstPage": true,
			"lastPage": true
		}
	}
}
```

## 删除出库工单
URL：`http://localhost:8090/supercar/inPart/removeInPart?id=1`
说明：`GET`方式，传入库工单id。此操作将级联软删除工单配件，<font color="red">同时通过数据库触发器修改库存表对应配件的数目</font>

## 批量删除出库工单
URL：`http://localhost:8090/supercar/inPart/removeInPart?ids=1,2`
说明：`GET`方式，传入库工单ids。将级联软删除工单配件，<font color="red">同时通过数据库触发器修改库存表对应配件的数目</font>

# RepairItem 维修服务项目
## 字段定义
```
			/** 项目类型，数据字典外键 */
    	private String typeLK;

	   	/** 项目代码 */
    	private String code;

	   	/** 项目名称 */
    	private String name;

	   	/** 工时数 */
    	private Double workHour;

	   	/** 工种，数据字典外键 */
    	private String workTypeLK;

	   	/** 备注 */
    	private String description;

	   	/** 金额 */
    	private BigDecimal sum;
```

## 新增测试成功json
  ```
{
	"typeLK": "1",
	"code": "code",
	"name": "name",
	"workTypeLK": "1",
	"description": "description",
	"sum": 11,
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
				"typeLK": "1",
				"code": "code",
				"name": "name",
				"workHour": null,
				"workTypeLK": "1",
				"description": "description",
				"sum": 11,
				"id": "331EC8A236D34F7AA5B8FDBB516937A0",
				"date": {
					"typeLK": {
						"definitionId": "1",
						"code": "BYD",
						"value": "比亚迪",
						"description": null,
						"additional": null,
						"parentId": null,
						"zzLevel": null,
						"zzIsLeaf": null,
						"zzLevel1Id": null,
						"zzLevel2Id": null,
						"zzLevel3Id": null,
						"zzLevel4Id": null,
						"zzLevel5Id": null,
						"zzLevel6Id": null,
						"id": "1"
					},
					"workTypeLK": {
						"definitionId": "1",
						"code": "BYD",
						"value": "比亚迪",
						"description": null,
						"additional": null,
						"parentId": null,
						"zzLevel": null,
						"zzIsLeaf": null,
						"zzLevel1Id": null,
						"zzLevel2Id": null,
						"zzLevel3Id": null,
						"zzLevel4Id": null,
						"zzLevel5Id": null,
						"zzLevel6Id": null,
						"id": "1"
					}
				}
			}],
			"number": 0,
			"size": 10,
			"sort": {

			},
			"numberOfElements": 1,
			"lastPage": true,
			"totalPages": 1,
			"totalElements": 1,
			"firstPage": true
		}
	}
}
  ```

# RepairWorkorder 维修工单
## 字段定义
```
			/** 维修工单号 */
    	private String workorderNo;

	   	/** 工单状态 */
    	private String workorderState;

	   	/** 修理性质，数据字典外键 */
    	private String repairTypeLK;

	   	/** 结算金额 */
    	private BigDecimal sum;

	   	/** 服务顾问 */
    	private String clerk;

	   	/** 客户id，外键 */
    	private String clientId;

	   	/** 车进店里程 */
    	private Integer carMileage;

	   	/** 车进店油表 */
    	private Integer carOilmeter;

	   	/** 客户提醒 */
    	private String clentRemind;

	   	/** 送修时间 */
    	private Date sendTime;

	   	/** 交车时间 */
    	private Date endTime;
```

## 新增测试成功json
URL:`http://localhost:8090/supercar/repairWorkorder/newRepairWorkorder`
说明：POST方式，需要传维修工单信息，维修工单绑定的维修项目，以及领料信息（即出库信息）
  ```
	{
	"repairWorkorder": {
		"workorderNo": "workorderNo",
		"workorderState": "0",
		"repairTypeLK": "1",
		"sum": 11,
		"clerk": "1",
		"clientId": "1",
		"carMileage": 1,
		"carOilmeter": 1,
		"clentRemind": "维修提醒"
	},
	"repairWorkorderItems": [{
			"workorderId": "",
			"itemId": "331EC8A236D34F7AA5B8FDBB516937A0",
			"mechanic": "1"
		}, {
			"workorderId": "",
			"itemId": "1",
			"mechanic": "DA67698177BB4118BBB23079A6CA9BFA"
		}
	],
	"outPartComposite": {
		"outPart": {
			"workOrderNo": "workOrderNo",
			"type": "1",
			"clientName": "clientName",
			"receiver": "1",
			"sum": 11,
			"repairWorkorderNo": "repairWorkorderNo",
			"carNo": "carNo",
			"departmentLK": "1",
			"company": "1",
			"isDeleted": false
		},
		"outPartInfos": [{
				"workOrderNo": "workOrderNo",
				"inventoryId": "87c4ce9461e511e7a848704d7bbc2105",
				"sale": 11,
				"count": 1,
				"isDeleted": false
			}, {
				"workOrderNo": "workOrderNo",
				"inventoryId": "936c6b7a621d11e7b44d0c5b8f279a64",
				"sale": 12.2,
				"count": 3,
				"isDeleted": false
			}
		]
	}
}
```

## 修改测试成功json
URL:`http://localhost:8090/supercar/repairWorkorder/newRepairWorkorder`
说明：POST方式，需要传维修工单信息，维修工单绑定的维修项目。领料信息（出库信息）不用传，此部分应设计成不能修改
```
{
	"repairWorkorder": {
		"id":"115712086D94407F96A11AE92382BB5E",
		"workorderNo": "6",
		"workorderState": "1",
		"repairTypeLK": "1",
		"sum": 11,
		"clerk": "1",
		"clientId": "1",
		"carMileage": 1,
		"carOilmeter": 1,
		"clentRemind": "维修提醒修改2"
	},
	"repairWorkorderItems": [{
			"workorderId": "115712086D94407F96A11AE92382BB5E",
			"itemId": "331EC8A236D34F7AA5B8FDBB516937A0",
			"mechanic": "DA67698177BB4118BBB23079A6CA9BFA"
		}, {
			"workorderId": "",
			"itemId": "1",
			"mechanic": "DA67698177BB4118BBB23079A6CA9BFA"
		}
	]
}
```

## 分页查询返回json

说明：维修工单列表信息
```
	{
		"success": true,
		"errorNo": "",
		"errorMsg": "",
		"data": {
			"page": {
				"content": [{
						"workorderNo": "3",
						"workorderState": "0",
						"repairTypeLK": "1",
						"sum": 11,
						"clerk": "1",
						"clientId": "1",
						"carMileage": 1,
						"carOilmeter": 1,
						"clentRemind": "你好呀\r\n",
						"sendTime": "2017-07-07 11:05:31",
						"endTime": null,
						"id": "F289A81A27B041A88040682644F0FA10",
						"date": {
							"clerk": {
								"username": "wsz",
								"fullname": "王树政",
								"password": "e10adc3949ba59abbe56e057f20f883e",
								"email": null,
								"mobile": null,
								"role": "A072D82A6AE14146B47A70E4C58AA28D",
								"company": "1",
								"description": null,
								"createTime": "2017-06-18 16:20:52",
								"updateTime": "2017-06-28 21:00:38",
								"isDeleted": false,
								"isDisable": false,
								"id": "1",
								"date": {}
							},
							"clientId": {
								"carNo": "甘P82585",
								"carBrand": "1",
								"carModel": "99x",
								"carVIN": "1111",
								"carColor": "红",
								"engineNo": "12312",
								"insurer": "天天保险",
								"insuranceEndtime": "2017-06-23 17:49:59",
								"registrationDate": "2017-06-29 17:50:03",
								"company": "1",
								"name": "wsz",
								"sex": true,
								"idcard": "42102366262266",
								"type": "5",
								"level": "5713DCD00601409187CF0F975E92213C",
								"email": "842803829@qq.com",
								"mobile": "18782252525",
								"address": "广发银行总部99楼",
								"description": "此用户是大客户",
								"createTime": "2017-06-17 17:51:41",
								"updateTime": "2017-06-30 08:12:40",
								"isDeleted": false,
								"id": "1",
								"date": {}
							},
							"repairTypeLK": {
								"definitionId": "1",
								"code": "BYD",
								"value": "比亚迪",
								"description": null,
								"additional": null,
								"parentId": null,
								"zzLevel": null,
								"zzIsLeaf": null,
								"zzLevel1Id": null,
								"zzLevel2Id": null,
								"zzLevel3Id": null,
								"zzLevel4Id": null,
								"zzLevel5Id": null,
								"zzLevel6Id": null,
								"id": "1"
							}
						}
					}, {
						"workorderNo": "2",
						"workorderState": "0",
						"repairTypeLK": "1",
						"sum": 11,
						"clerk": "1",
						"clientId": "1",
						"carMileage": 1,
						"carOilmeter": 1,
						"clentRemind": "你好呀\r\n",
						"sendTime": null,
						"endTime": null,
						"id": "FDA77040146847069EFD70CB5E0E4400",
						"date": {
							"clerk": {
								"username": "wsz",
								"fullname": "王树政",
								"password": "e10adc3949ba59abbe56e057f20f883e",
								"email": null,
								"mobile": null,
								"role": "A072D82A6AE14146B47A70E4C58AA28D",
								"company": "1",
								"description": null,
								"createTime": "2017-06-18 16:20:52",
								"updateTime": "2017-06-28 21:00:38",
								"isDeleted": false,
								"isDisable": false,
								"id": "1",
								"date": {}
							},
							"clientId": {
								"carNo": "甘P82585",
								"carBrand": "1",
								"carModel": "99x",
								"carVIN": "1111",
								"carColor": "红",
								"engineNo": "12312",
								"insurer": "天天保险",
								"insuranceEndtime": "2017-06-23 17:49:59",
								"registrationDate": "2017-06-29 17:50:03",
								"company": "1",
								"name": "wsz",
								"sex": true,
								"idcard": "42102366262266",
								"type": "5",
								"level": "5713DCD00601409187CF0F975E92213C",
								"email": "842803829@qq.com",
								"mobile": "18782252525",
								"address": "广发银行总部99楼",
								"description": "此用户是大客户",
								"createTime": "2017-06-17 17:51:41",
								"updateTime": "2017-06-30 08:12:40",
								"isDeleted": false,
								"id": "1",
								"date": {}
							},
							"repairTypeLK": {
								"definitionId": "1",
								"code": "BYD",
								"value": "比亚迪",
								"description": null,
								"additional": null,
								"parentId": null,
								"zzLevel": null,
								"zzIsLeaf": null,
								"zzLevel1Id": null,
								"zzLevel2Id": null,
								"zzLevel3Id": null,
								"zzLevel4Id": null,
								"zzLevel5Id": null,
								"zzLevel6Id": null,
								"id": "1"
							}
						}
					}
				],
				"number": 0,
				"size": 10,
				"sort": {},
				"numberOfElements": 2,
				"totalPages": 1,
				"totalElements": 2,
				"firstPage": true,
				"lastPage": true
			}
		}
	}
```

## 查询维修工单详细信息
URL:`http://localhost:8090/supercar/repairWorkorder/getItemsAndParts?repairWorkOrderNo=6`
说明：分页返回维修工单绑定的维修项目和领料信息

```
{
	"success": true,
	"errorNo": "",
	"errorMsg": "",
	"data": {
		"outPartComposite": {
			"outPart": {
				"workOrderNo": "9",
				"type": "1",
				"clientName": "clientName",
				"receiver": "1",
				"outTime": "2017-07-07 16:54:37",
				"sum": 11,
				"repairWorkorderNo": "6",
				"carNo": "carNo",
				"departmentLK": "1",
				"company": "1",
				"isDeleted": false,
				"id": "1EE48C82837B4AE484839F3C10C394C2",
				"date": {}
			},
			"outPartInfos": [{
					"workOrderNo": "9",
					"inventoryId": "87c4ce9461e511e7a848704d7bbc2105",
					"sale": 11,
					"count": 1,
					"isDeleted": false,
					"id": "3D432077FF9543B4BD2BF8203E0B03FD",
					"date": {
						"inventoryId": {
							"partId": "3A9A0BE24BD14C5999C3F74533D8C769",
							"count": 3,
							"cost": null,
							"supplierLK": "1",
							"company": "1",
							"repCodeLK": "1",
							"isDeleted": false,
							"id": "87c4ce9461e511e7a848704d7bbc2105",
							"date": {}
						}
					}
				}, {
					"workOrderNo": "9",
					"inventoryId": "936c6b7a621d11e7b44d0c5b8f279a64",
					"sale": 12,
					"count": 3,
					"isDeleted": false,
					"id": "AA3D28F937054C2B9BF2FCCBAFCEA85C",
					"date": {
						"inventoryId": {
							"partId": "6EE27FCCC34C4C86ABB2B6FAD3FA9BC9",
							"count": -3,
							"cost": null,
							"supplierLK": "1",
							"company": "1",
							"repCodeLK": "1",
							"isDeleted": false,
							"id": "936c6b7a621d11e7b44d0c5b8f279a64",
							"date": {}
						}
					}
				}
			]
		},
		"items": [{
				"workorderId": "115712086D94407F96A11AE92382BB5E",
				"itemId": "331EC8A236D34F7AA5B8FDBB516937A0",
				"mechanic": "1",
				"startTime": null,
				"endTime": null,
				"id": "064FEB0D3D1E44E4AF2D948BDBF63075",
				"date": {
					"itemId": {
						"typeLK": "1",
						"code": "code",
						"name": "name",
						"workHour": null,
						"workTypeLK": "1",
						"description": "description",
						"sum": 11,
						"id": "331EC8A236D34F7AA5B8FDBB516937A0",
						"date": {}
					}
				}
			}, {
				"workorderId": "115712086D94407F96A11AE92382BB5E",
				"itemId": "1",
				"mechanic": "DA67698177BB4118BBB23079A6CA9BFA",
				"startTime": null,
				"endTime": null,
				"id": "8C94DF2E4EAA4393B663945E5A0B34EC",
				"date": {
					"itemId": null
				}
			}
		]
	}
}
```
