/*
Navicat MySQL Data Transfer

Source Server         : localhost_conn
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : supercar

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-06-21 23:05:13
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of tb_client
-- ----------------------------
INSERT INTO `tb_client` VALUES ('1', 'wsz', '0', '42102366262266', '', '5', '842803829@qq.com', '18782252525', '广发银行总部99楼', '1', '此用户是大客户', '2017-06-17 17:51:41', '2017-06-18 15:50:33', '0', '甘P82585', '1', '99x', '1111', null, null, '红', '12312', '天天保险', '2017-06-23 17:49:59', '2017-06-29 17:50:03');

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
-- Records of tb_company
-- ----------------------------
INSERT INTO `tb_company` VALUES ('1', '深圳门店', 'shenzhen', '辉门冠军', 'E2221CCC83404FAEB89A882D5E112E15', '111', '深A', null, null, null, '0', '2017-06-18 14:37:28', '2017-06-18 14:37:28');
INSERT INTO `tb_company` VALUES ('2', '兰州门店', 'lanzhou', '辉门冠军', 'E2221CCC83404FAEB89A882D5E112E15', '222', '甘A', null, null, null, '0', '2017-06-18 14:37:28', '2017-06-18 14:37:28');

-- ----------------------------
-- Table structure for tb_inventory
-- ----------------------------
DROP TABLE IF EXISTS `tb_inventory`;
CREATE TABLE `tb_inventory` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `p_id` varchar(32) NOT NULL COMMENT '配件id',
  `p_count` int(11) DEFAULT '0' COMMENT '配件库存数目',
  `p_cost` double DEFAULT NULL COMMENT '进货价',
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
-- Records of tb_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for tb_in_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_in_part`;
CREATE TABLE `tb_in_part` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `in_workorder_no` varchar(32) NOT NULL COMMENT '入库单号',
  `in_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '入库时间',
  `in_pay_method` varchar(32) DEFAULT NULL COMMENT '结算方式，数据字典',
  `in_sum` double DEFAULT NULL COMMENT '合计金额',
  `p_supplier` varchar(32) NOT NULL COMMENT '供应商，数据字典外键',
  `p_company` varchar(32) DEFAULT NULL COMMENT '所属门店',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pin_pay_method_fk` (`in_pay_method`),
  KEY `in_p_supplier_fk` (`p_supplier`),
  KEY `p_company_fk` (`p_company`),
  CONSTRAINT `in_p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `p_company_fk` FOREIGN KEY (`p_company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `pin_pay_method_fk` FOREIGN KEY (`in_pay_method`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_in_part
-- ----------------------------

-- ----------------------------
-- Table structure for tb_in_part_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_in_part_info`;
CREATE TABLE `tb_in_part_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `in_workorder_num` varchar(32) DEFAULT NULL COMMENT '入库单号',
  `p_id` varchar(32) DEFAULT NULL COMMENT '配件id',
  `in_count` int(11) DEFAULT NULL COMMENT '配件入库数目',
  `p_cost` decimal(10,0) DEFAULT NULL COMMENT '进货价',
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
-- Records of tb_in_part_info
-- ----------------------------

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
  `LK_LEVEL` int(10) DEFAULT '0' COMMENT '节点层级',
  `LK_PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `LK_IS_LEAF` smallint(10) DEFAULT '0' COMMENT '是否叶子节点',
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
-- Records of tb_lookup
-- ----------------------------
INSERT INTO `tb_lookup` VALUES ('1', '1', 'byd', '比亚迪', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('2', '1', 'bc', '奔驰', null, 'AAA', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('3', '2', 'normal', '普通用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('4', '2', 'silver', '银牌用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('5', '2', 'gold', '金牌用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('6', '1', 'lbjl', '兰博基尼', null, null, '0', null, '0', null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('A072D82A6AE14146B47A70E4C58AA28D', 'E3F9A726B2434B219EDEE2E23734EA4B', 'admin', '管理员', null, '最高权限的角色，可查看所有门店账号、客户、维修工单', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('E2221CCC83404FAEB89A882D5E112E15', '1253114623E54642A38112E4142E0D5A', 'repair_shop', '快修门店', null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_lookup_df
-- ----------------------------
DROP TABLE IF EXISTS `tb_lookup_df`;
CREATE TABLE `tb_lookup_df` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `LKD_CODE` varchar(32) NOT NULL COMMENT '数据字典定义code',
  `LKD_NAME` varchar(32) NOT NULL COMMENT '数据字典定义名称',
  `LKD_DESCRIPTION` varchar(100) DEFAULT NULL COMMENT '数据字典定义描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`LKD_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lookup_df
-- ----------------------------
INSERT INTO `tb_lookup_df` VALUES ('1', 'car_brand', '车辆品牌', null, '2017-06-28 21:25:51', '2017-06-17 17:44:57', '0');
INSERT INTO `tb_lookup_df` VALUES ('1253114623E54642A38112E4142E0D5A', 'company_type', '公司类型', null, '2017-06-18 14:24:56', '2017-06-18 14:26:06', '0');
INSERT INTO `tb_lookup_df` VALUES ('2', 'client_type', '客户类别', '不同类别客户享受的优惠不一样', null, '2017-06-17 10:19:12', '0');
INSERT INTO `tb_lookup_df` VALUES ('25CDFCA32AB14D2AB39A4764F5D93470', 'w', 'w', null, '2017-06-17 18:30:44', '2017-06-18 14:24:38', '1');
INSERT INTO `tb_lookup_df` VALUES ('91295F61D79A4CBC85558F65A946BFD1', 'test2', 'test2', null, '2017-06-17 10:19:33', '2017-06-17 10:23:07', '1');
INSERT INTO `tb_lookup_df` VALUES ('D12E86A4F4054E5787404E8A9E75EA8D', 't', 't', 're', '2017-06-17 10:23:29', '2017-06-18 14:24:38', '1');
INSERT INTO `tb_lookup_df` VALUES ('E2929F4F40FB45F1A3A8D59B4DEA4756', 'test', 'test', '1', '2017-06-17 10:13:34', '2017-06-17 10:23:07', '1');
INSERT INTO `tb_lookup_df` VALUES ('E3F9A726B2434B219EDEE2E23734EA4B', 'user_role', '账号角色', '账号角色，不同角色权限不一', '2017-06-18 16:18:32', '2017-06-18 16:18:32', '0');
INSERT INTO `tb_lookup_df` VALUES ('F6DF8162CE964F669C82BA9C6DA449F1', 'q', 'q', null, '2017-06-17 18:25:02', '2017-06-18 14:24:38', '1');

-- ----------------------------
-- Table structure for tb_out_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_out_part`;
CREATE TABLE `tb_out_part` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `out_workorder_no` varchar(32) DEFAULT NULL COMMENT '出库单号',
  `out_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '出库时间',
  `out_type` varchar(32) DEFAULT NULL COMMENT '出库类型，数据字典外键',
  `out_client_name` varchar(50) DEFAULT NULL COMMENT '车主名称',
  `out_receiver` varchar(32) DEFAULT NULL COMMENT '领料人，外键',
  `out_sum` decimal(10,0) DEFAULT NULL COMMENT '合计金额',
  `p_company` varchar(32) DEFAULT NULL COMMENT '所属门店，数据字典外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `out_type_fk` (`out_type`),
  KEY `out_p_company_fk` (`p_company`),
  KEY `out_receiver_fk` (`out_receiver`),
  CONSTRAINT `out_p_company_fk` FOREIGN KEY (`p_company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `out_receiver_fk` FOREIGN KEY (`out_receiver`) REFERENCES `tb_user` (`ID`),
  CONSTRAINT `out_type_fk` FOREIGN KEY (`out_type`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_out_part
-- ----------------------------

-- ----------------------------
-- Table structure for tb_out_part_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_out_part_info`;
CREATE TABLE `tb_out_part_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `out_workorder_no` varchar(50) DEFAULT NULL COMMENT '出库单号',
  `inventory_id` varchar(32) DEFAULT NULL COMMENT '库存配件id，外键',
  `out_count` int(11) DEFAULT NULL COMMENT '配件出库数目',
  `item_code` varchar(50) DEFAULT NULL,
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `inventory_id_fk` (`inventory_id`),
  CONSTRAINT `inventory_id_fk` FOREIGN KEY (`inventory_id`) REFERENCES `tb_inventory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_out_part_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_part
-- ----------------------------
DROP TABLE IF EXISTS `tb_part`;
CREATE TABLE `tb_part` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `p_code` varchar(32) NOT NULL COMMENT '配件编号',
  `p_name` varchar(50) NOT NULL COMMENT '配件名称',
  `p_unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `p_sale` double DEFAULT NULL COMMENT '销售价',
  `p_wholesale` double DEFAULT NULL COMMENT '批发价',
  `p_produce_area` varchar(32) DEFAULT '' COMMENT '产地',
  `p_specification` varchar(32) DEFAULT NULL COMMENT '规格',
  `p_car_model` varchar(32) DEFAULT NULL COMMENT '适用车型',
  `p_category` varchar(32) DEFAULT NULL COMMENT '分类',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
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
-- Records of tb_part
-- ----------------------------

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
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'wsz', '王树政', '1231231', null, null, 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2017-06-18 16:20:52', '2017-06-18 16:20:52', '0', '0');
