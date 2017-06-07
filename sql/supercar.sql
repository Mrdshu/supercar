/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : supercar

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-06-07 12:00:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_lookup
-- ----------------------------
DROP TABLE IF EXISTS `tb_lookup`;
CREATE TABLE `tb_lookup` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `LKD_ID` varchar(10) NOT NULL COMMENT '数据字典定义id，外键',
  `LK_CODE` varchar(10) NOT NULL COMMENT '数据字典code',
  `LK_VALUE` varchar(20) NOT NULL COMMENT '数据字典value',
  `LK_ADDITIONAL` varchar(10) DEFAULT NULL COMMENT '附加内容',
  `LK_DESCRIPTION` varchar(20) DEFAULT NULL COMMENT '数据字典描述',
  `LK_LEVEL` varchar(10) DEFAULT NULL COMMENT '节点层级',
  `LK_PARENT_ID` varchar(10) DEFAULT NULL COMMENT '父节点id',
  `LK_IS_LEAF` varchar(10) DEFAULT NULL COMMENT '是否叶子节点',
  `LK_LEVEL1_ID` varchar(10) DEFAULT NULL COMMENT '一级父节点',
  `LK_LEVEL2_ID` varchar(10) DEFAULT NULL COMMENT '二级父节点',
  `LK_LEVEL3_ID` varchar(10) DEFAULT NULL COMMENT '三级父节点',
  `LK_LEVEL4_ID` varchar(10) DEFAULT NULL COMMENT '四级父节点',
  `LK_LEVEL5_ID` varchar(10) DEFAULT NULL COMMENT '五级父节点',
  `LK_LEVEL6_ID` varchar(10) DEFAULT NULL COMMENT '六级父节点',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`LK_CODE`),
  KEY `lookup_definition_fk` (`LKD_ID`),
  CONSTRAINT `lookup_definition_fk` FOREIGN KEY (`LKD_ID`) REFERENCES `tb_lookup_df` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lookup
-- ----------------------------
INSERT INTO `tb_lookup` VALUES ('1', '1', 'byd', '比亚迪', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('2', '1', 'bc', '奔驰', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('3', '2', 'normal', '普通用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('4', '2', 'silver', '银牌用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('5', '2', 'gold', '金牌用户', null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_lookup_df
-- ----------------------------
DROP TABLE IF EXISTS `tb_lookup_df`;
CREATE TABLE `tb_lookup_df` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `LKD_CODE` varchar(10) NOT NULL COMMENT '数据字典定义code',
  `LKD_NAME` varchar(20) NOT NULL COMMENT '数据字典定义名称',
  `LKD_DESCRIPTION` varchar(20) DEFAULT NULL COMMENT '数据字典定义描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`LKD_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lookup_df
-- ----------------------------
INSERT INTO `tb_lookup_df` VALUES ('1', 'car_model', '车型', '车辆型号', null, null);
INSERT INTO `tb_lookup_df` VALUES ('2', 'client_typ', '客户类别', '不同类别客户享受的优惠不一样', null, null);

-- ----------------------------
-- Table structure for u_car
-- ----------------------------
DROP TABLE IF EXISTS `u_car`;
CREATE TABLE `u_car` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `car_no` varchar(32) NOT NULL COMMENT '车牌号',
  `car_model` varchar(32) NOT NULL COMMENT '车型，外键',
  `car_chassis` varchar(32) DEFAULT NULL COMMENT '底盘号',
  `car_color` varchar(32) DEFAULT NULL COMMENT '车身颜色',
  `car_engine` varchar(32) DEFAULT NULL COMMENT '发动机号',
  `car_insurer` varchar(32) DEFAULT NULL COMMENT '保险公司',
  `car_insurance_endtime` datetime DEFAULT NULL COMMENT '保险到期时间',
  `car_registration_time` datetime DEFAULT NULL COMMENT '上牌日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `isdeleted` tinyint(4) DEFAULT '1',
  `extend1` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `car_no` (`car_no`),
  KEY `car_model_fk` (`car_model`),
  CONSTRAINT `car_model_fk` FOREIGN KEY (`car_model`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_car
-- ----------------------------

-- ----------------------------
-- Table structure for u_client
-- ----------------------------
DROP TABLE IF EXISTS `u_client`;
CREATE TABLE `u_client` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `c_name` varchar(20) NOT NULL COMMENT '客户姓名',
  `c_sex` tinyint(4) DEFAULT NULL COMMENT '客户性别',
  `c_idcard` varchar(32) DEFAULT NULL COMMENT '客户身份证',
  `c_type` varchar(32) NOT NULL COMMENT '客户类别，外键',
  `c_email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `c_mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `c_address` varchar(20) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `isdeleted` tinyint(4) DEFAULT '0',
  `extend1` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend2` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `c_idcard` (`c_idcard`),
  KEY `c_type` (`c_type`),
  CONSTRAINT `c_type_fk` FOREIGN KEY (`c_type`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_client
-- ----------------------------

-- ----------------------------
-- Table structure for u_client_car
-- ----------------------------
DROP TABLE IF EXISTS `u_client_car`;
CREATE TABLE `u_client_car` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `client_id` varchar(32) NOT NULL COMMENT '客户id，外键',
  `c_car_id` varchar(32) NOT NULL COMMENT '车id，外键',
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  KEY `c_car_id` (`c_car_id`),
  CONSTRAINT `u_client_car_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `u_client` (`id`),
  CONSTRAINT `u_client_car_ibfk_2` FOREIGN KEY (`c_car_id`) REFERENCES `u_car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_client_car
-- ----------------------------
