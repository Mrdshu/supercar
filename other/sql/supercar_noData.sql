/*
Navicat MySQL Data Transfer

Source Server         : localhost_conn
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : supercar

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-07-22 17:28:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `seq_name` varchar(50) NOT NULL,
  `current_val` int(11) NOT NULL,
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_client
-- ----------------------------
DROP TABLE IF EXISTS `tb_client`;
CREATE TABLE `tb_client` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `c_name` varchar(32) NOT NULL COMMENT '客户姓名',
  `c_sex` tinyint(4) DEFAULT NULL COMMENT '客户性别（true为男，false为女）',
  `c_idcard` varchar(32) DEFAULT NULL COMMENT '客户身份证',
  `c_level` varchar(32) NOT NULL COMMENT '客户级别，数据字典外键',
  `c_type` varchar(32) NOT NULL COMMENT '客户类别，数据字典外键',
  `c_email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `c_mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `c_address` varchar(20) DEFAULT NULL COMMENT '地址',
  `c_company` varchar(32) DEFAULT NULL COMMENT '所属门店',
  `description` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isdeleted` tinyint(4) DEFAULT '0',
  `car_no` varchar(32) NOT NULL COMMENT '车牌号',
  `car_brand` varchar(32) NOT NULL COMMENT '车品牌，外键',
  `car_model` varchar(32) DEFAULT NULL,
  `car_chassis` varchar(32) DEFAULT NULL COMMENT '底盘号',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `car_color` varchar(32) DEFAULT NULL COMMENT '车身颜色',
  `car_engine` varchar(32) DEFAULT NULL COMMENT '发动机号',
  `car_insurer` varchar(32) DEFAULT NULL COMMENT '保险公司',
  `car_insurance_endtime` datetime DEFAULT NULL COMMENT '保险到期时间',
  `car_registration_time` datetime DEFAULT NULL COMMENT '上牌日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `c_idcard` (`c_idcard`),
  KEY `c_type` (`c_type`),
  KEY `c_level` (`c_level`),
  KEY `car_model_fk` (`car_brand`),
  KEY `cp_company_fk` (`c_company`),
  CONSTRAINT `c_level_fk` FOREIGN KEY (`c_level`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `c_type_fk` FOREIGN KEY (`c_type`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `car_model_fk` FOREIGN KEY (`car_brand`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `cp_company_fk` FOREIGN KEY (`c_company`) REFERENCES `tb_company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_company
-- ----------------------------
DROP TABLE IF EXISTS `tb_company`;
CREATE TABLE `tb_company` (
  `ID` varchar(32) NOT NULL,
  `CP_NAME` varchar(50) NOT NULL COMMENT '公司名称',
  `CP_CODE` varchar(50) DEFAULT NULL COMMENT '公司code',
  `CP_BRAND` varchar(50) DEFAULT NULL COMMENT '公司品牌',
  `CP_TYPE` varchar(32) NOT NULL COMMENT '公司类别',
  `CP_MOBILE` varchar(50) DEFAULT NULL COMMENT '售后热线',
  `CP_CARNO` varchar(50) DEFAULT NULL COMMENT '默认车牌',
  `CP_EMAIL` varchar(50) DEFAULT NULL COMMENT '公司邮箱',
  `CP_ADDRESS` varchar(50) DEFAULT NULL COMMENT '公司地址',
  `CP_DESCRIPTION` varchar(50) DEFAULT NULL COMMENT '备注',
  `ISDELETED` smallint(6) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `cp_type_fk2` (`CP_TYPE`),
  KEY `CP_EMAIL_fk2` (`CP_EMAIL`),
  CONSTRAINT `cp_type_fk` FOREIGN KEY (`CP_TYPE`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_inventory
-- ----------------------------
DROP TABLE IF EXISTS `tb_inventory`;
CREATE TABLE `tb_inventory` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `p_id` varchar(32) NOT NULL COMMENT '配件id',
  `p_count` int(11) DEFAULT '0' COMMENT '配件库存数目',
  `p_cost` decimal(10,2) DEFAULT NULL COMMENT '进货价',
  `p_supplier` varchar(32) NOT NULL COMMENT '供应商，数据字典外键',
  `p_company` varchar(32) DEFAULT NULL COMMENT '所属门店',
  `r_code` varchar(32) DEFAULT NULL COMMENT '库位号code，数据字典外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `p_id_fk` (`p_id`),
  KEY `p_supplier_fk` (`p_supplier`),
  KEY `r_code_fk` (`r_code`),
  KEY `inventory_p_company_fk` (`p_company`),
  CONSTRAINT `inventory_p_company_fk` FOREIGN KEY (`p_company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `p_id_fk` FOREIGN KEY (`p_id`) REFERENCES `tb_part` (`id`),
  CONSTRAINT `p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `r_code_fk` FOREIGN KEY (`r_code`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存';

-- ----------------------------
-- Table structure for tb_in_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_in_part`;
CREATE TABLE `tb_in_part` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `in_workorder_no` varchar(32) DEFAULT NULL COMMENT '入库单号',
  `in_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `in_pay_method` varchar(32) DEFAULT NULL COMMENT '结算方式，数据字典',
  `in_sum` double DEFAULT NULL COMMENT '合计金额',
  `p_supplier` varchar(32) NOT NULL COMMENT '供应商，数据字典外键',
  `company` varchar(32) DEFAULT NULL COMMENT '所属门店，门店外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `in_workorder_no` (`in_workorder_no`),
  KEY `pin_pay_method_fk` (`in_pay_method`),
  KEY `in_p_supplier_fk` (`p_supplier`),
  KEY `p_company_fk` (`company`),
  CONSTRAINT `in_p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `p_company_fk` FOREIGN KEY (`company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `pin_pay_method_fk` FOREIGN KEY (`in_pay_method`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_in_part_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_in_part_info`;
CREATE TABLE `tb_in_part_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `in_workorder_no` varchar(32) DEFAULT NULL COMMENT '入库单号',
  `p_id` varchar(32) DEFAULT NULL COMMENT '配件id',
  `in_count` int(11) DEFAULT NULL COMMENT '配件入库数目',
  `p_cost` decimal(10,2) DEFAULT NULL COMMENT '进货价',
  `p_supplier` varchar(32) NOT NULL COMMENT '供应商，数据字典外键',
  `r_code` varchar(32) DEFAULT NULL COMMENT '库位号code，数据字典外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `info_p_id_fk` (`p_id`),
  KEY `info_p_supplier_fk` (`p_supplier`),
  KEY `info_r_code_fk` (`r_code`),
  CONSTRAINT `info_p_id_fk` FOREIGN KEY (`p_id`) REFERENCES `tb_part` (`id`),
  CONSTRAINT `info_p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `info_r_code_fk` FOREIGN KEY (`r_code`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_lookup
-- ----------------------------
DROP TABLE IF EXISTS `tb_lookup`;
CREATE TABLE `tb_lookup` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `LKD_ID` varchar(32) NOT NULL COMMENT '数据字典定义id，外键',
  `LK_CODE` varchar(32) NOT NULL COMMENT '数据字典code',
  `LK_VALUE` varchar(32) NOT NULL COMMENT '数据字典value',
  `LK_ADDITIONAL` varchar(100) DEFAULT NULL COMMENT '附加内容',
  `LK_DESCRIPTION` varchar(100) DEFAULT NULL COMMENT '数据字典描述',
  `LK_LEVEL` int(10) DEFAULT '1' COMMENT '节点层级',
  `LK_PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `LK_IS_LEAF` smallint(10) DEFAULT '1' COMMENT '是否叶子节点',
  `LK_LEVEL1_ID` varchar(32) DEFAULT NULL COMMENT '一级父节点',
  `LK_LEVEL2_ID` varchar(32) DEFAULT NULL COMMENT '二级父节点',
  `LK_LEVEL3_ID` varchar(32) DEFAULT NULL COMMENT '三级父节点',
  `LK_LEVEL4_ID` varchar(32) DEFAULT NULL COMMENT '四级父节点',
  `LK_LEVEL5_ID` varchar(32) DEFAULT NULL COMMENT '五级父节点',
  `LK_LEVEL6_ID` varchar(32) DEFAULT NULL COMMENT '六级父节点',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`LK_CODE`),
  KEY `lookup_definition_fk` (`LKD_ID`),
  CONSTRAINT `lookup_definition_fk` FOREIGN KEY (`LKD_ID`) REFERENCES `tb_lookup_df` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_lookup_df
-- ----------------------------
DROP TABLE IF EXISTS `tb_lookup_df`;
CREATE TABLE `tb_lookup_df` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `LKD_CODE` varchar(32) NOT NULL COMMENT '数据字典定义code',
  `LKD_NAME` varchar(32) NOT NULL COMMENT '数据字典定义名称',
  `LKD_TYPE` varchar(32) DEFAULT NULL COMMENT '数据字典定义类型，0为普通，1为树结构',
  `LKD_DESCRIPTION` varchar(100) DEFAULT NULL COMMENT '数据字典定义描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`LKD_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_out_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_out_part`;
CREATE TABLE `tb_out_part` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `out_workorder_no` varchar(32) DEFAULT NULL COMMENT '出库单号',
  `out_type` varchar(32) DEFAULT NULL COMMENT '出库类型，0-维修领料，1-配件销售，2-配件内耗',
  `out_client_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `out_receiver` varchar(32) DEFAULT NULL COMMENT '领料人，用户外键',
  `out_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `out_sum` decimal(10,2) DEFAULT NULL COMMENT '合计金额',
  `repair_workorder_no` varchar(50) DEFAULT NULL COMMENT '维修工单号。出库类型：维修领料时使用',
  `car_no` varchar(32) DEFAULT NULL COMMENT '车牌号。出库类型：配件销售时使用',
  `department` varchar(32) DEFAULT NULL COMMENT '部门，数据字典外键。出库类型：配件内耗时使用',
  `company` varchar(32) DEFAULT NULL COMMENT '所属门店，公司外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `out_workorder_no` (`out_workorder_no`),
  KEY `out_type_fk` (`out_type`),
  KEY `out_p_company_fk` (`company`),
  KEY `out_receiver_fk` (`out_receiver`),
  CONSTRAINT `out_p_company_fk` FOREIGN KEY (`company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `out_receiver_fk` FOREIGN KEY (`out_receiver`) REFERENCES `tb_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库工单';

-- ----------------------------
-- Table structure for tb_out_part_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_out_part_info`;
CREATE TABLE `tb_out_part_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `out_workorder_no` varchar(50) DEFAULT NULL COMMENT '出库单号',
  `inventory_id` varchar(32) DEFAULT NULL COMMENT '库存配件id，外键',
  `p_sale` decimal(10,2) DEFAULT NULL COMMENT '配件销售价',
  `out_count` int(11) DEFAULT NULL COMMENT '配件出库数目',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `inventory_id_fk` (`inventory_id`),
  CONSTRAINT `inventory_id_fk` FOREIGN KEY (`inventory_id`) REFERENCES `tb_inventory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库工单配件信息';

-- ----------------------------
-- Table structure for tb_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_part`;
CREATE TABLE `tb_part` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `p_code` varchar(32) NOT NULL COMMENT '配件编号',
  `p_name` varchar(50) NOT NULL COMMENT '配件名称',
  `p_unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `p_sale` decimal(10,2) DEFAULT NULL COMMENT '销售价',
  `p_wholesale` decimal(10,2) DEFAULT NULL COMMENT '批发价',
  `p_produce_area` varchar(32) DEFAULT '' COMMENT '产地',
  `p_specification` varchar(32) DEFAULT NULL COMMENT '规格',
  `p_car_model` varchar(32) DEFAULT NULL COMMENT '适用车型',
  `p_category` varchar(32) DEFAULT NULL COMMENT '分类',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `isdisable` tinyint(4) DEFAULT '0' COMMENT '禁用标志',
  `extend1` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `p_code` (`p_code`),
  KEY `p_unit_fk` (`p_unit`),
  KEY `p_category_fk` (`p_category`),
  CONSTRAINT `p_category_fk` FOREIGN KEY (`p_category`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `p_unit_fk` FOREIGN KEY (`p_unit`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配件';

-- ----------------------------
-- Table structure for tb_repair_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_repair_item`;
CREATE TABLE `tb_repair_item` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `ri_type` varchar(32) NOT NULL COMMENT '项目类型，数据字典外键',
  `ri_code` varchar(255) NOT NULL COMMENT '项目代码',
  `ri_name` varchar(255) NOT NULL COMMENT '项目名称',
  `ri_working_hour` double DEFAULT NULL COMMENT '工时数',
  `ri_work_type` varchar(32) DEFAULT NULL COMMENT '工种，数据字典外键',
  `ri_desc` varchar(255) DEFAULT NULL COMMENT '备注',
  `ri_sum` decimal(10,0) NOT NULL COMMENT '金额',
  `extend1` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修服务项目';

-- ----------------------------
-- Table structure for tb_repair_workorder
-- ----------------------------
DROP TABLE IF EXISTS `tb_repair_workorder`;
CREATE TABLE `tb_repair_workorder` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `rw_workorder_no` varchar(32) DEFAULT NULL COMMENT '维修工单号',
  `rw_workorder_state` varchar(32) DEFAULT NULL COMMENT '工单状态。0-待派工，1-已派工',
  `rw_repair_type` varchar(32) DEFAULT NULL COMMENT '修理性质，数据字典外键',
  `rw_sum` decimal(10,0) DEFAULT NULL COMMENT '结算金额',
  `rw_clerk` varchar(32) DEFAULT NULL COMMENT '服务顾问，用户表外键',
  `rw_client_id` varchar(32) DEFAULT NULL COMMENT '客户id，外键',
  `rw_car_mileage` int(11) DEFAULT NULL COMMENT '车进店里程',
  `rw_car_oilmeter` int(11) DEFAULT NULL COMMENT '车进店油表',
  `rw_clent_remind` varchar(255) DEFAULT NULL COMMENT '客户提醒',
  `rw_send_man` varchar(20) DEFAULT NULL COMMENT '送修人名称',
  `rw_send_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '送修时间',
  `rw_end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '交车时间',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修工单';

-- ----------------------------
-- Table structure for tb_repair_workorder_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_repair_workorder_item`;
CREATE TABLE `tb_repair_workorder_item` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `rwi_workorder_id` varchar(32) DEFAULT NULL COMMENT '维修工单id，外键',
  `rwi_item_id` varchar(32) DEFAULT NULL COMMENT '维修项目id，外键',
  `rwi_mechanic` varchar(32) DEFAULT NULL COMMENT '维修工，外键',
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修工单-服务项目';

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `U_USERNAME` varchar(50) NOT NULL COMMENT '用户名',
  `U_FULLNAME` varchar(50) DEFAULT NULL COMMENT '全名',
  `U_PASSWORD` varchar(200) NOT NULL COMMENT '密码',
  `U_EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `U_MOBILE` varchar(50) DEFAULT NULL COMMENT '手机',
  `U_ROLE` varchar(32) DEFAULT NULL COMMENT '角色，数据字典外键',
  `U_COMPANY` varchar(32) DEFAULT NULL COMMENT '公司，外键',
  `U_DESCRIPTION` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `ISDISABLE` tinyint(4) DEFAULT '0' COMMENT '禁用标志',
  `ISDELETED` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  PRIMARY KEY (`ID`),
  KEY `u_role_fk` (`U_ROLE`),
  KEY `u_company_fk` (`U_COMPANY`),
  CONSTRAINT `u_company_fk` FOREIGN KEY (`U_COMPANY`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `u_role_fk` FOREIGN KEY (`U_ROLE`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Function structure for currval
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin     
    declare value integer;       
    set value = 0;       
    select current_val into value  from sequence where seq_name = v_seq_name; 
   return value; 
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin
    update sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;
    return currval(v_seq_name);
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `workorderNo`;
DELIMITER ;;
CREATE TRIGGER `workorderNo` BEFORE INSERT ON `tb_in_part` FOR EACH ROW BEGIN
set NEW.in_workorder_no = nextval('in_workorder_no');
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insertInPartInfo`;
DELIMITER ;;
CREATE TRIGGER `insertInPartInfo` AFTER INSERT ON `tb_in_part_info` FOR EACH ROW BEGIN
		 set @count = (select count(*) from tb_inventory where p_id = new.p_id and p_supplier = new.p_supplier);
		 set @company = (select company from tb_in_part where in_workorder_no = new.in_workorder_no);
		 if @count > 0 then
				update tb_inventory set p_count = p_count + new.in_count where tb_inventory.p_id = new.p_id and tb_inventory.p_supplier = new.p_supplier;
		 elseif @count <= 0 then
				insert into tb_inventory(id,p_id,p_count,p_cost,p_supplier,p_company,r_code,isdeleted)
				VALUES (  replace(uuid(),"-","") ,new.p_id,new.in_count ,new.p_cost,new.p_supplier,@company,new.r_code,default);
		 end if; 
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteInPartInfo`;
DELIMITER ;;
CREATE TRIGGER `deleteInPartInfo` AFTER UPDATE ON `tb_in_part_info` FOR EACH ROW BEGIN
		 if (new.isdeleted = 1 && old.isdeleted = 0) then
		 update tb_inventory set p_count = p_count- old.in_count where tb_inventory.p_id = old.p_id and tb_inventory.p_supplier = old.p_supplier;
		 end IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `out_workorder_no`;
DELIMITER ;;
CREATE TRIGGER `out_workorder_no` BEFORE INSERT ON `tb_out_part` FOR EACH ROW BEGIN
set NEW.out_workorder_no = nextval('out_workorder_no');
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insertOutPartInfo`;
DELIMITER ;;
CREATE TRIGGER `insertOutPartInfo` AFTER INSERT ON `tb_out_part_info` FOR EACH ROW BEGIN
		 update tb_inventory set p_count = p_count- new.out_count where tb_inventory.id = new.inventory_id;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteOutPartInfo`;
DELIMITER ;;
CREATE TRIGGER `deleteOutPartInfo` AFTER UPDATE ON `tb_out_part_info` FOR EACH ROW BEGIN
if (new.isdeleted = 1 && old.isdeleted = 0) then
update tb_inventory set p_count = p_count + old.out_count where tb_inventory.id = old.inventory_id;
end IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insertRepairWorkorder`;
DELIMITER ;;
CREATE TRIGGER `insertRepairWorkorder` BEFORE INSERT ON `tb_repair_workorder` FOR EACH ROW BEGIN
		 set NEW.rw_workorder_no = nextval('repair_workorder');
END
;;
DELIMITER ;
