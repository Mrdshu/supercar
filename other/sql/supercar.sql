/*
Navicat MySQL Data Transfer

Source Server         : localhost_conn
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : supercar

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-12-16 18:42:53
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
  PRIMARY KEY (`seq_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('in_workorder_no', '31', '1');
INSERT INTO `sequence` VALUES ('out_workorder_no', '18', '1');
INSERT INTO `sequence` VALUES ('repair_workorder', '12', '1');

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `c_idcard` (`c_idcard`) USING BTREE,
  KEY `c_type` (`c_type`) USING BTREE,
  KEY `c_level` (`c_level`) USING BTREE,
  KEY `car_model_fk` (`car_brand`) USING BTREE,
  KEY `cp_company_fk` (`c_company`) USING BTREE,
  CONSTRAINT `c_level_fk` FOREIGN KEY (`c_level`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `c_type_fk` FOREIGN KEY (`c_type`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `car_model_fk` FOREIGN KEY (`car_brand`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `cp_company_fk` FOREIGN KEY (`c_company`) REFERENCES `tb_company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_client
-- ----------------------------
INSERT INTO `tb_client` VALUES ('1', 'wsz', '1', '42102366262266', '153B10CE80B14D7EBB7B8B48A9E22376', '5713DCD00601409187CF0F975E92213C', '842803829@qq.com', '18782252525', '广发银行总部99楼', '1', '此用户是大客户', '2017-06-17 17:51:41', '2017-07-12 09:20:14', '0', '甘P82585', '1', '99x', '1111', null, null, '红', '12312', '天天保险', '2017-06-23 17:49:59', '2017-06-29 17:50:03');
INSERT INTO `tb_client` VALUES ('287E2C6DB859423F901B3762102A9C37', 'kim', '1', '1234567890098745678', 'B2D3F5DEDD204994BAD1425D2EFB1392', '5713DCD00601409187CF0F975E92213C', null, '18378311282', '广东深圳', '1', '新增客户', '2017-07-12 09:25:15', '2018-12-16 14:04:30', '0', '粤BNB5201', '049C1B34F160472EB0712A6427292F14', 'A4L', '1241244324', null, null, '白色', '3243242433234', '平安保险', null, null);
INSERT INTO `tb_client` VALUES ('77A24F759FF24B179AA16F12920D3FDF', 'king', null, '124423435351232132143', '3F1F1EFC9B2B46F38EB3E92C6259364C', '5713DCD00601409187CF0F975E92213C', '2133123@qq.com', '13213134325', '广东深圳南山前海', '1', '32424', '2017-07-12 09:29:31', '2018-12-16 14:04:41', '0', '粤B213234', '049C1B34F160472EB0712A6427292F14', 'A6', '1232436456', null, null, '黑色', '233464532221', '太平洋保险', null, null);
INSERT INTO `tb_client` VALUES ('F7022766E9F74D1F93E473E8B42D7079', 'string', '1', 'string', '1', '1', 'string', '1', 'string', '1', 'string', '2018-12-16 18:41:48', '2018-12-16 18:41:48', '0', 'string', '1', 'string', 'string', null, null, 'string', 'string', 'string', '2018-12-16 00:00:00', '2018-12-16 00:00:00');

-- ----------------------------
-- Table structure for tb_client_coupon
-- ----------------------------
DROP TABLE IF EXISTS `tb_client_coupon`;
CREATE TABLE `tb_client_coupon` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `cc_client_id` varchar(32) DEFAULT NULL COMMENT '客户id',
  `cc_coupon_id` varchar(32) DEFAULT NULL COMMENT '优惠券数据字典id',
  `cc_num` int(11) DEFAULT NULL COMMENT '客户优惠券数目',
  PRIMARY KEY (`ID`),
  KEY `cc_clientId_fk` (`cc_client_id`),
  KEY `cc_couponId_fk` (`cc_coupon_id`),
  CONSTRAINT `cc_clientId_fk` FOREIGN KEY (`cc_client_id`) REFERENCES `tb_client` (`id`),
  CONSTRAINT `cc_couponId_fk` FOREIGN KEY (`cc_coupon_id`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户优惠券';

-- ----------------------------
-- Records of tb_client_coupon
-- ----------------------------
INSERT INTO `tb_client_coupon` VALUES ('84245874BAD24010B04ABD55B1EC3189', '1', '1', '1');
INSERT INTO `tb_client_coupon` VALUES ('95EAA6C6B3B8462A9103711C39622760', '1', '2', '2');

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
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `cp_type_fk2` (`CP_TYPE`) USING BTREE,
  KEY `CP_EMAIL_fk2` (`CP_EMAIL`) USING BTREE,
  CONSTRAINT `cp_type_fk` FOREIGN KEY (`CP_TYPE`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  `p_company` varchar(32) DEFAULT NULL COMMENT '所属门店',
  `r_code` varchar(32) DEFAULT NULL COMMENT '库位号code，数据字典外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `p_id_fk` (`p_id`) USING BTREE,
  KEY `r_code_fk` (`r_code`) USING BTREE,
  KEY `inventory_p_company_fk` (`p_company`) USING BTREE,
  CONSTRAINT `inventory_p_company_fk` FOREIGN KEY (`p_company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `p_id_fk` FOREIGN KEY (`p_id`) REFERENCES `tb_part` (`id`),
  CONSTRAINT `r_code_fk` FOREIGN KEY (`r_code`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='库存';

-- ----------------------------
-- Records of tb_inventory
-- ----------------------------
INSERT INTO `tb_inventory` VALUES ('345f0e1279e411e7a457704d7bbc2105', '8E65E1B022C84C15B902FA6F8997D414', '9', '1', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_inventory` VALUES ('5764e79f75e011e7b2a0704d7bbc2105', '49EA5A80F78A4711982DD710D919098A', '5', '1', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_inventory` VALUES ('5764ee7d75e011e7b2a0704d7bbc2105', '6EE27FCCC34C4C86ABB2B6FAD3FA9BC9', '0', '1', '53D57671F37F464F8D30734A53F2DEEC', '1', null, null, null);
INSERT INTO `tb_inventory` VALUES ('5766820e75e011e7b2a0704d7bbc2105', '2D953EE846DB4B2EA00B6A324BEB8450', '0', '1', '53D57671F37F464F8D30734A53F2DEEC', '1', null, null, null);
INSERT INTO `tb_inventory` VALUES ('71d9273f002f11e9988700163e0e46cc', '2D953EE846DB4B2EA00B6A324BEB8450', '1', '1', '287A959F16BC423BA27D1AC3D99BA752', '0', null, null, null);

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `in_workorder_no` (`in_workorder_no`) USING BTREE,
  KEY `pin_pay_method_fk` (`in_pay_method`) USING BTREE,
  KEY `in_p_supplier_fk` (`p_supplier`) USING BTREE,
  KEY `p_company_fk` (`company`) USING BTREE,
  CONSTRAINT `in_p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `p_company_fk` FOREIGN KEY (`company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `pin_pay_method_fk` FOREIGN KEY (`in_pay_method`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_in_part
-- ----------------------------
INSERT INTO `tb_in_part` VALUES ('007DB9DA4CE4432A822288A3C51E9024', '25', '2017-07-31 19:02:17', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('0E09BFD9CA6540C39E8B434CCDDEFBE4', '16', '2017-07-31 18:16:36', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('1D06F12B081D470087DC41B686C000A2', '30', '2018-12-15 14:04:41', '9FFEB1FF40144F4CAC515EDD52AD8DCD', '10', '6A88275899B24969841940BCDBCD330F', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('1FEC0CDC818646C791FC1C19E87015B1', '24', '2017-07-31 19:01:37', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('2F36DC005DA544B1B88CC1C6A3529542', '31', '2018-12-15 14:05:38', '9FFEB1FF40144F4CAC515EDD52AD8DCD', '10', '6A88275899B24969841940BCDBCD330F', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('34B082885A434C55AFE14A8CF3221523', '22', '2017-07-31 18:59:57', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('45BEDCFF6B894FF59C009793EFB84DEB', '17', '2017-07-31 18:17:11', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('7761293E4E8A416A9C2F8CA6BF99EA95', '28', '2017-08-05 21:44:28', '9FFEB1FF40144F4CAC515EDD52AD8DCD', '170', '6A88275899B24969841940BCDBCD330F', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('80EC40CE649248CE9DFD6B776E9D07AA', '14', '2017-07-31 18:14:30', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('8E442E8407944004B0688C2DB9B520C3', '18', '2017-07-31 18:21:55', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('9A29C1D476D241B79EB1568E0F7EBDF3', '29', '2018-12-15 14:03:20', 'F2CA260B7AF34CA995F2E01A9701D8ED', '40', '6A88275899B24969841940BCDBCD330F', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('CB8E67A70145416F80D13C912C746CA5', '19', '2017-07-31 18:50:45', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('CD6B2EE25E0C4AFBB677D7299BC953D2', '27', '2017-07-31 19:14:22', '9FFEB1FF40144F4CAC515EDD52AD8DCD', '8', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('D4E77213B7934685A3A1F4DAAA8CC557', '20', '2017-07-31 18:56:50', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('DCE232F3FA5544519BEA0776EE2BC4C3', '23', '2017-07-31 19:00:22', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('F0F1074C7E444976980D04FF38870B65', '15', '2017-07-31 18:14:34', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('F9F453D140F041ECA0D5000AF767D84F', '21', '2017-07-31 18:58:06', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);
INSERT INTO `tb_in_part` VALUES ('FCDACFE64F81485FBE01384927DA107C', '26', '2017-07-31 19:06:42', 'F2CA260B7AF34CA995F2E01A9701D8ED', '12', '8A0FD4172EDA46A89FFE627852DE2516', '1', '0', null, null, null);

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
  `p_supplier` varchar(32) DEFAULT NULL COMMENT '供应商，数据字典外键',
  `r_code` varchar(32) DEFAULT NULL COMMENT '库位号code，数据字典外键',
  `isdeleted` tinyint(4) DEFAULT '0' COMMENT '软删除标志',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  `extend3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `info_p_id_fk` (`p_id`) USING BTREE,
  KEY `info_p_supplier_fk` (`p_supplier`) USING BTREE,
  KEY `info_r_code_fk` (`r_code`) USING BTREE,
  CONSTRAINT `info_p_id_fk` FOREIGN KEY (`p_id`) REFERENCES `tb_part` (`id`),
  CONSTRAINT `info_p_supplier_fk` FOREIGN KEY (`p_supplier`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `info_r_code_fk` FOREIGN KEY (`r_code`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_in_part_info
-- ----------------------------
INSERT INTO `tb_in_part_info` VALUES ('0876FD3A7BF2435D88DBD04A3A293605', '27', '49EA5A80F78A4711982DD710D919098A', '2', '1.00', '8A0FD4172EDA46A89FFE627852DE2516', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('0B4B5CD37ED044B0AF9021ECB7F08794', '26', '2D953EE846DB4B2EA00B6A324BEB8450', '3', '3.00', '8A0FD4172EDA46A89FFE627852DE2516', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('47263077A96C4F3D84517AD608AC35EA', '30', '8E65E1B022C84C15B902FA6F8997D414', '1', '10.00', '6A88275899B24969841940BCDBCD330F', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('6C6371E55FA3496AA2BB7506A012EF34', '26', '49EA5A80F78A4711982DD710D919098A', '1', '1.00', '8A0FD4172EDA46A89FFE627852DE2516', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('A99DD35CF8C64F0EA146FC72B3A20E44', '28', '49EA5A80F78A4711982DD710D919098A', '5', '10.00', '6A88275899B24969841940BCDBCD330F', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('BA278D3FD48844E7AB68DA271859E08C', '31', '2D953EE846DB4B2EA00B6A324BEB8450', '1', '10.00', '6A88275899B24969841940BCDBCD330F', '287A959F16BC423BA27D1AC3D99BA752', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('C96045085E97411EB347AB77E378FC96', '28', '8E65E1B022C84C15B902FA6F8997D414', '6', '20.00', '6A88275899B24969841940BCDBCD330F', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('DA48B65D46A34C5A868597994C8F4217', '27', '6EE27FCCC34C4C86ABB2B6FAD3FA9BC9', '2', '3.00', '8A0FD4172EDA46A89FFE627852DE2516', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('DE63F88071A348DDB5DD2B11A35981D4', '29', '8E65E1B022C84C15B902FA6F8997D414', '4', '10.00', '6A88275899B24969841940BCDBCD330F', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);
INSERT INTO `tb_in_part_info` VALUES ('E5F65B88C62646EA99FFB54D0134FC3B', '26', '6EE27FCCC34C4C86ABB2B6FAD3FA9BC9', '1', '2.00', '8A0FD4172EDA46A89FFE627852DE2516', '53D57671F37F464F8D30734A53F2DEEC', '0', null, null, null);

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`LK_CODE`) USING BTREE,
  KEY `lookup_definition_fk` (`LKD_ID`) USING BTREE,
  CONSTRAINT `lookup_definition_fk` FOREIGN KEY (`LKD_ID`) REFERENCES `tb_lookup_df` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_lookup
-- ----------------------------
INSERT INTO `tb_lookup` VALUES ('049C1B34F160472EB0712A6427292F14', '1', 'AUTO', '奥迪', null, null, '1', null, '1', '049C1B34F160472EB0712A6427292F14', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('051212273B584A7DAC7D551D6D5CDA23', '16169ADEE9C54A998A20F06A8F5A0C3D', 'A01', '轮胎保养', null, null, '2', 'EDE9470B34474A5B883271F92368276F', '1', 'EDE9470B34474A5B883271F92368276F', '051212273B584A7DAC7D551D6D5CDA23', null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('0A764AB31DA746A7A89815F5958C20D0', '88B2BCF91A124C119D9AFD47EC872E87', '2000', '电器相关', null, null, '1', null, '0', '0A764AB31DA746A7A89815F5958C20D0', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('1', '1', 'BYD', '比亚迪', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('134E48AE281C4376A70863421802769C', 'B918AA5914F14DB39777A0B5CF970FF9', 'examineQuality', '质检', null, null, '1', null, '1', '134E48AE281C4376A70863421802769C', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('153B10CE80B14D7EBB7B8B48A9E22376', 'FEDA8FE256C24391BFE56141737656DD', 'gold', '金牌客户', '7', null, '1', null, '1', '153B10CE80B14D7EBB7B8B48A9E22376', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('192E16F8EA434E029E7911BF9770A504', '88B2BCF91A124C119D9AFD47EC872E87', '1000', '发动机相关', null, null, '1', null, '0', '192E16F8EA434E029E7911BF9770A504', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('1D7B120296A44602B0360A7922DA1659', 'B918AA5914F14DB39777A0B5CF970FF9', 'receivedCar', '接车', null, null, '1', null, '1', '1D7B120296A44602B0360A7922DA1659', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('2', '1', 'Ben-Z', '奔驰', null, 'AAA', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('200A24BA0A0C4EF5B19E0244931BA954', '88B2BCF91A124C119D9AFD47EC872E87', '30001', '前悬架', null, null, '2', 'E232A1884DCD4E668E29860C202F088A', '1', 'E232A1884DCD4E668E29860C202F088A', '200A24BA0A0C4EF5B19E0244931BA954', null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('287A959F16BC423BA27D1AC3D99BA752', '0040694F322542C9B98A09712DC346D6', 'code002', '002', null, null, '1', null, '1', '287A959F16BC423BA27D1AC3D99BA752', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('2F57AF168B564DB39A65D9D309BABD77', 'C892A0C11C364C22A87E334864C89FC9', 'half', '1/2', null, null, '1', null, '1', '2F57AF168B564DB39A65D9D309BABD77', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('3', '2', 'normal', '普通用户', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('349DBB62003E4CB7A29F7A0D19790682', '35D65110D3164208B1F303842DAF661D', 'barrel', '桶', null, null, '1', null, '1', '349DBB62003E4CB7A29F7A0D19790682', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('37BACFF568274226B66DC455664EE847', '0E3E848D87B04106BCDD8E67E1726D26', '0', '维修领料', null, null, '1', null, '1', '37BACFF568274226B66DC455664EE847', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('389FA81D83D849EBAFB21AC4C6E932EF', '88B2BCF91A124C119D9AFD47EC872E87', '00001', '发动机润滑油', null, '测试子节点1', '2', '7FA179BA0BAF4CA4874DA57DD6393861', '1', '7FA179BA0BAF4CA4874DA57DD6393861', '389FA81D83D849EBAFB21AC4C6E932EF', null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('3F1F1EFC9B2B46F38EB3E92C6259364C', 'FEDA8FE256C24391BFE56141737656DD', 'copper ', '铜牌客户', '9', null, '1', null, '1', '3F1F1EFC9B2B46F38EB3E92C6259364C', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('53D57671F37F464F8D30734A53F2DEEC', '0040694F322542C9B98A09712DC346D6', 'code001', '001', null, null, '1', null, '1', '53D57671F37F464F8D30734A53F2DEEC', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('54E424D41AC14E6283FCAE735639D418', '0E3E848D87B04106BCDD8E67E1726D26', '2', '配件内耗', null, null, '1', null, '1', '54E424D41AC14E6283FCAE735639D418', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('5713DCD00601409187CF0F975E92213C', 'D2D734CFEDFF4CEBA87AE954E8DE9AE3', 'persion', '个人', null, null, '1', null, '1', '5713DCD00601409187CF0F975E92213C', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('6', '1', 'lbjl', '兰博基尼', null, null, '0', null, '0', null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('63518C625471443AA8DDB03BE0447BB5', '35D65110D3164208B1F303842DAF661D', 'individual', '个', null, null, '1', null, '1', '63518C625471443AA8DDB03BE0447BB5', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('64D72058ABC74813A517E3CE70005EBC', 'B918AA5914F14DB39777A0B5CF970FF9', 'wrot', '刨光', null, null, '1', null, '1', '64D72058ABC74813A517E3CE70005EBC', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('6A88275899B24969841940BCDBCD330F', '1FF3AF7C9D164290A4EAE8E11BA3ABC7', 'HUATIANLUNTAI', '华天轮胎', null, null, '1', null, '1', '6A88275899B24969841940BCDBCD330F', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('700886ED4ECE4DAC8BBB908DF7553894', 'D2D734CFEDFF4CEBA87AE954E8DE9AE3', 'unit', '单位', null, null, '1', null, '1', '700886ED4ECE4DAC8BBB908DF7553894', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('73251BB95A2444AE9A65A758314B6E08', '1', 'BMW', '宝马', null, null, '1', null, '1', '73251BB95A2444AE9A65A758314B6E08', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('73C970D8567A4833B554D6EECE5BBFF5', '33B849B72D644304B7CC2B6CAEF6D0DE', 'buckets', '一整桶', null, null, '1', null, '1', '73C970D8567A4833B554D6EECE5BBFF5', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('7E66A5AEF4BC46AA91D9174EE861DA12', '88B2BCF91A124C119D9AFD47EC872E87', '10001', '发动机及附件', null, '测试子节点2', '2', '192E16F8EA434E029E7911BF9770A504', '1', '192E16F8EA434E029E7911BF9770A504', '7E66A5AEF4BC46AA91D9174EE861DA12', null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('7FA179BA0BAF4CA4874DA57DD6393861', '88B2BCF91A124C119D9AFD47EC872E87', '0000', '保养', null, null, '1', null, '0', '7FA179BA0BAF4CA4874DA57DD6393861', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('82B1E580FEDA4C8586DDD0C2830599D8', 'B918AA5914F14DB39777A0B5CF970FF9', 'repairGold', '钣金', null, null, '1', null, '1', '82B1E580FEDA4C8586DDD0C2830599D8', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('8A0FD4172EDA46A89FFE627852DE2516', '1FF3AF7C9D164290A4EAE8E11BA3ABC7', 'HKS', '广东好快省汽车服务有限公司', null, null, '1', null, '1', '8A0FD4172EDA46A89FFE627852DE2516', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('91E5B64F767448AA8D8737D458BB0B42', '16169ADEE9C54A998A20F06A8F5A0C3D', 'A10', '养护', null, null, null, 'undefined', null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('956F990484854F5DAA279A93FCA46F06', 'DFC8A9660CC24FA8AD344197BD416BA6', '8', '8折', null, null, '1', null, '1', '956F990484854F5DAA279A93FCA46F06', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('95A61B5B08EF41059742D30E8E71BE3E', '16169ADEE9C54A998A20F06A8F5A0C3D', 'B10', '底盘', null, null, null, 'undefined', null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('98F5FEDA96CD4C6C859559555D7E8027', 'FEDA8FE256C24391BFE56141737656DD', 'silver', '银牌客户', '8', null, '1', null, '1', '98F5FEDA96CD4C6C859559555D7E8027', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('9FFEB1FF40144F4CAC515EDD52AD8DCD', 'CD14DF1BD1CD4C76AA2467B41297580B', 'alipay', '支付宝支付', null, null, '1', null, '1', '9FFEB1FF40144F4CAC515EDD52AD8DCD', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('A072D82A6AE14146B47A70E4C58AA28D', 'E3F9A726B2434B219EDEE2E23734EA4B', 'super_admin', '超级管理员', null, '最高权限的角色，可查看所有门店账号、客户、维修工单', null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('A6625ADC0E8B47AE8E253FBB9533DD0D', 'B918AA5914F14DB39777A0B5CF970FF9', 'electromechanical', '机电', null, null, '1', null, '1', 'A6625ADC0E8B47AE8E253FBB9533DD0D', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('ACA48F72F47A4F698699167FFA09FD5E', '35D65110D3164208B1F303842DAF661D', 'peace', '只', null, null, '1', null, '1', 'ACA48F72F47A4F698699167FFA09FD5E', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('AF36EC2145004374BD147BE0561CF3C7', 'CD14DF1BD1CD4C76AA2467B41297580B', 'wechatpay', '微信支付', null, null, '1', null, '1', 'AF36EC2145004374BD147BE0561CF3C7', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('B2D3F5DEDD204994BAD1425D2EFB1392', 'FEDA8FE256C24391BFE56141737656DD', 'diamond', '钻石客户', '6', null, '1', null, '1', 'B2D3F5DEDD204994BAD1425D2EFB1392', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('BDCDC960C4A44D409ADFF763B78B383C', '16169ADEE9C54A998A20F06A8F5A0C3D', 'A15', '加装', null, null, null, 'undefined', null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('C025FEFDF89D4AB89E41B3027A0BB2F2', '0E3E848D87B04106BCDD8E67E1726D26', '1', '配件销售', null, null, '1', null, '1', 'C025FEFDF89D4AB89E41B3027A0BB2F2', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('C1145C53367340D4969A0B682FD53AC8', '88B2BCF91A124C119D9AFD47EC872E87', '20001', '空调及暖风', null, null, '2', '0A764AB31DA746A7A89815F5958C20D0', '1', '0A764AB31DA746A7A89815F5958C20D0', 'C1145C53367340D4969A0B682FD53AC8', null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('E2221CCC83404FAEB89A882D5E112E15', '1253114623E54642A38112E4142E0D5A', 'repair_shop', '快修门店', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('E232A1884DCD4E668E29860C202F088A', '88B2BCF91A124C119D9AFD47EC872E87', '3000', '底盘相关', null, null, '1', null, '0', 'E232A1884DCD4E668E29860C202F088A', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('EDE9470B34474A5B883271F92368276F', '16169ADEE9C54A998A20F06A8F5A0C3D', 'A00', '保养', null, null, '1', null, '0', 'EDE9470B34474A5B883271F92368276F', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('F2CA260B7AF34CA995F2E01A9701D8ED', 'CD14DF1BD1CD4C76AA2467B41297580B', 'cash', '现金支付', null, null, '1', null, '1', 'F2CA260B7AF34CA995F2E01A9701D8ED', null, null, null, null, null);
INSERT INTO `tb_lookup` VALUES ('F62D35E20B144B9F94A09A7BF7BC5A7D', 'E3F9A726B2434B219EDEE2E23734EA4B', 'putong', '普通管理员', null, '普通管理员', '1', null, '1', 'F62D35E20B144B9F94A09A7BF7BC5A7D', null, null, null, null, null);

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`LKD_CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_lookup_df
-- ----------------------------
INSERT INTO `tb_lookup_df` VALUES ('0040694F322542C9B98A09712DC346D6', 'repCode', '库位号', '0', '测试库位号', '2017-06-29 09:43:03', '2017-06-29 09:43:03', '0');
INSERT INTO `tb_lookup_df` VALUES ('0E3E848D87B04106BCDD8E67E1726D26', 'outpart_type', '出库类型', '0', null, '2017-07-11 20:53:31', '2017-07-11 20:53:31', '0');
INSERT INTO `tb_lookup_df` VALUES ('1', 'car_brand', '车辆品牌', '0', null, '2017-06-28 21:25:51', '2017-06-28 21:28:28', '0');
INSERT INTO `tb_lookup_df` VALUES ('1253114623E54642A38112E4142E0D5A', 'company_type', '公司类型', '0', null, '2017-06-18 14:24:56', '2017-06-28 21:28:30', '0');
INSERT INTO `tb_lookup_df` VALUES ('16169ADEE9C54A998A20F06A8F5A0C3D', 'repair_type', '维修分类', '1', null, '2017-07-13 21:59:23', '2017-07-13 21:59:23', '0');
INSERT INTO `tb_lookup_df` VALUES ('1FF3AF7C9D164290A4EAE8E11BA3ABC7', 'supplier', '供应商', '0', null, '2017-07-09 23:26:41', '2017-07-09 23:26:41', '0');
INSERT INTO `tb_lookup_df` VALUES ('2', 'client', '客户级别', '0', '不同级别客户享受的优惠不一样', null, '2017-07-12 09:12:35', '1');
INSERT INTO `tb_lookup_df` VALUES ('33B849B72D644304B7CC2B6CAEF6D0DE', 'part_specification', '商品规格', '0', null, '2017-06-29 10:16:18', '2017-06-29 10:16:18', '0');
INSERT INTO `tb_lookup_df` VALUES ('35D65110D3164208B1F303842DAF661D', 'unit', '单位', '0', '测试数据', '2017-06-28 21:11:13', '2017-06-28 21:11:13', '0');
INSERT INTO `tb_lookup_df` VALUES ('88B2BCF91A124C119D9AFD47EC872E87', 'part_type', '配件分类', '1', '树结构', '2017-06-27 21:24:18', '2017-06-28 08:21:16', '0');
INSERT INTO `tb_lookup_df` VALUES ('B918AA5914F14DB39777A0B5CF970FF9', 'work_type', '维修工种', '0', null, '2017-07-13 22:07:41', '2017-07-13 22:07:41', '0');
INSERT INTO `tb_lookup_df` VALUES ('C892A0C11C364C22A87E334864C89FC9', 'carOilmeter', '进店油表', '0', null, '2018-12-15 18:36:48', '2018-12-15 18:36:48', '0');
INSERT INTO `tb_lookup_df` VALUES ('CD14DF1BD1CD4C76AA2467B41297580B', 'pay_type', '支付方式', '0', null, '2017-07-09 19:51:32', '2017-07-09 19:51:32', '0');
INSERT INTO `tb_lookup_df` VALUES ('D2D734CFEDFF4CEBA87AE954E8DE9AE3', 'client_type', '客户类型', '0', null, '2017-06-25 09:38:38', '2017-06-28 21:28:38', '0');
INSERT INTO `tb_lookup_df` VALUES ('DFC8A9660CC24FA8AD344197BD416BA6', 'clientCoupon', '客户优惠券', '0', null, '2018-12-15 18:52:26', '2018-12-15 18:52:26', '0');
INSERT INTO `tb_lookup_df` VALUES ('E3F9A726B2434B219EDEE2E23734EA4B', 'user_role', '账号角色', '0', '账号角色，不同角色权限不一', '2017-06-18 16:18:32', '2017-06-28 21:28:41', '0');
INSERT INTO `tb_lookup_df` VALUES ('FEDA8FE256C24391BFE56141737656DD', 'client_level', '客户级别', '0', null, '2017-07-11 23:14:01', '2017-07-12 09:12:44', '0');

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `out_workorder_no` (`out_workorder_no`) USING BTREE,
  KEY `out_type_fk` (`out_type`) USING BTREE,
  KEY `out_p_company_fk` (`company`) USING BTREE,
  KEY `out_receiver_fk` (`out_receiver`) USING BTREE,
  CONSTRAINT `out_p_company_fk` FOREIGN KEY (`company`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `out_receiver_fk` FOREIGN KEY (`out_receiver`) REFERENCES `tb_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='出库工单';

-- ----------------------------
-- Records of tb_out_part
-- ----------------------------
INSERT INTO `tb_out_part` VALUES ('50C7FD603076464AB3DFBA5205C3802F', '18', '37BACFF568274226B66DC455664EE847', null, null, '2017-08-05 21:47:35', '24.00', '10', null, null, '1', '0', null, null, null);
INSERT INTO `tb_out_part` VALUES ('5319660F1A54464680BF34BF6F93E2BE', '14', '37BACFF568274226B66DC455664EE847', 'wsz', 'DA67698177BB4118BBB23079A6CA9BFA', '2017-08-05 16:27:58', '132.00', null, null, null, '1', '0', null, null, null);
INSERT INTO `tb_out_part` VALUES ('C58184F9332647FC95591E2EFE1125FF', '16', '37BACFF568274226B66DC455664EE847', null, null, '2017-08-05 18:22:02', '24.00', '8', null, null, '1', '0', null, null, null);
INSERT INTO `tb_out_part` VALUES ('E927A0488ACE4F8C98A355B74D79EBD6', '17', '37BACFF568274226B66DC455664EE847', null, null, '2017-08-05 18:25:17', '200.00', '9', null, null, '1', '0', null, null, null);
INSERT INTO `tb_out_part` VALUES ('FC33A65935F34B4D95EDBCF194B8745F', '15', '37BACFF568274226B66DC455664EE847', 'wsz', 'DA67698177BB4118BBB23079A6CA9BFA', '2017-08-05 17:58:15', '40.00', null, null, null, '1', '0', null, null, null);

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
  PRIMARY KEY (`id`) USING BTREE,
  KEY `inventory_id_fk` (`inventory_id`) USING BTREE,
  CONSTRAINT `inventory_id_fk` FOREIGN KEY (`inventory_id`) REFERENCES `tb_inventory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='出库工单配件信息';

-- ----------------------------
-- Records of tb_out_part_info
-- ----------------------------
INSERT INTO `tb_out_part_info` VALUES ('2661D910591147BBBC6350291C324A40', '14', '5764e79f75e011e7b2a0704d7bbc2105', '20.00', '1', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('27F7E275BC7D436C96D170C60E759FC0', '18', '345f0e1279e411e7a457704d7bbc2105', '12.00', '2', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('39E21884C1CC4EE9997201AD9A9EC0FB', '15', '5764e79f75e011e7b2a0704d7bbc2105', '20.00', '2', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('654865C3166B48389E902EE5F8C4B3B8', '17', '5766820e75e011e7b2a0704d7bbc2105', '100.00', '2', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('88DFAEE1870C4116ADE5886F7C895AC0', '16', '5764ee7d75e011e7b2a0704d7bbc2105', '12.00', '2', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('8E7EBCF254804C3683A40B25524C675E', '14', '5766820e75e011e7b2a0704d7bbc2105', '100.00', '1', '0', null, null, null);
INSERT INTO `tb_out_part_info` VALUES ('EA492493CF4949378FA126353480234D', '14', '5764ee7d75e011e7b2a0704d7bbc2105', '12.00', '1', '0', null, null, null);

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `p_code` (`p_code`) USING BTREE,
  KEY `p_unit_fk` (`p_unit`) USING BTREE,
  KEY `p_category_fk` (`p_category`) USING BTREE,
  CONSTRAINT `p_category_fk` FOREIGN KEY (`p_category`) REFERENCES `tb_lookup` (`id`),
  CONSTRAINT `p_unit_fk` FOREIGN KEY (`p_unit`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='配件';

-- ----------------------------
-- Records of tb_part
-- ----------------------------
INSERT INTO `tb_part` VALUES ('2D953EE846DB4B2EA00B6A324BEB8450', 'NO000001', '机油', '349DBB62003E4CB7A29F7A0D19790682', '100.00', '100.00', '美国', '73C970D8567A4833B554D6EECE5BBFF5', '奥迪、宝马、奔驰', '7E66A5AEF4BC46AA91D9174EE861DA12', '2017-06-29 16:00:01', '2017-07-12 09:34:02', '0', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('3A9A0BE24BD14C5999C3F74533D8C769', 'code', 'name', '349DBB62003E4CB7A29F7A0D19790682', '120.00', '10.00', 'produceArea', '73C970D8567A4833B554D6EECE5BBFF5', 'carmodel', '7FA179BA0BAF4CA4874DA57DD6393861', null, '2017-07-27 08:56:15', '1', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('475980DBF3FC4EC48B63C7C04156B5FC', 'NO000005', '润滑油1', '349DBB62003E4CB7A29F7A0D19790682', '10.00', '1.00', '佛山', '73C970D8567A4833B554D6EECE5BBFF5', '奔驰', '389FA81D83D849EBAFB21AC4C6E932EF', '2017-06-29 12:50:53', '2018-12-15 15:26:26', '0', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('49EA5A80F78A4711982DD710D919098A', 'No000009', '车轮胎', '63518C625471443AA8DDB03BE0447BB5', '20.00', '10.00', '浙江', '73C970D8567A4833B554D6EECE5BBFF5', '兰博基尼', '7FA179BA0BAF4CA4874DA57DD6393861', '2017-07-27 08:56:00', '2018-12-15 17:53:42', '0', '1', null, null, null);
INSERT INTO `tb_part` VALUES ('6EE27FCCC34C4C86ABB2B6FAD3FA9BC9', 'NO000007', '润滑油', '349DBB62003E4CB7A29F7A0D19790682', '12.00', '1.00', '澳大利亚', '73C970D8567A4833B554D6EECE5BBFF5', '比亚迪', '389FA81D83D849EBAFB21AC4C6E932EF', '2017-06-29 16:07:30', '2017-07-13 22:24:23', '0', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('8E65E1B022C84C15B902FA6F8997D414', 'NO000003', '润滑油', '349DBB62003E4CB7A29F7A0D19790682', '120.00', '120.00', '美国', '73C970D8567A4833B554D6EECE5BBFF5', '宝马X5', 'E232A1884DCD4E668E29860C202F088A', '2017-06-29 11:30:01', '2017-06-29 20:26:34', '0', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('A42125248E2141CCB4CACAF26479DB92', 'NO000006', '机油2', '349DBB62003E4CB7A29F7A0D19790682', '123.00', '12.00', '广东广州', '73C970D8567A4833B554D6EECE5BBFF5', '宝马、奔驰', '7FA179BA0BAF4CA4874DA57DD6393861', '2017-06-29 16:04:46', '2017-06-29 17:40:37', '1', '1', null, null, null);
INSERT INTO `tb_part` VALUES ('B68DCC061C654B688591EED2CD00FC97', 'NO000004', '润滑油2', '349DBB62003E4CB7A29F7A0D19790682', '100.00', '10.00', '广东', '73C970D8567A4833B554D6EECE5BBFF5', '奔驰', '389FA81D83D849EBAFB21AC4C6E932EF', '2017-06-29 12:48:43', '2017-06-29 14:50:21', '1', '0', null, null, null);
INSERT INTO `tb_part` VALUES ('C4BCFA08FCD442DFB5FE4ECF12660146', 'NO000002', '美孚机油', '349DBB62003E4CB7A29F7A0D19790682', '100.00', '10.00', '广东', '73C970D8567A4833B554D6EECE5BBFF5', '奥迪A4', '7FA179BA0BAF4CA4874DA57DD6393861', null, '2017-08-04 19:26:34', '0', '0', null, null, null);

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
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='维修服务项目';

-- ----------------------------
-- Records of tb_repair_item
-- ----------------------------
INSERT INTO `tb_repair_item` VALUES ('5E22F21B629F4765BDA4C2753625EFEB', '91E5B64F767448AA8D8737D458BB0B42', 'code2', '项目测试2', '2', 'A6625ADC0E8B47AE8E253FBB9533DD0D', '23424', '200', null, null, null);
INSERT INTO `tb_repair_item` VALUES ('62850B0963E4417D8C9077B2C65A22E8', '051212273B584A7DAC7D551D6D5CDA23', 'code1', '汽车轮胎保养', '1', '134E48AE281C4376A70863421802769C', '测试', '100', null, null, null);

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
  `rw_car_oilmeter` varchar(50) DEFAULT NULL COMMENT '车进店油表',
  `rw_clent_remind` varchar(255) DEFAULT NULL COMMENT '客户提醒',
  `rw_send_man` varchar(20) DEFAULT NULL COMMENT '送修人名称',
  `rw_send_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '送修时间',
  `rw_end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '交车时间',
  `rw_company` varchar(50) DEFAULT NULL COMMENT '预留拓展字段',
  `extend3` varchar(20) DEFAULT NULL COMMENT '预留拓展字段',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `rw_client_fk` (`rw_client_id`) USING BTREE,
  CONSTRAINT `rw_client_fk` FOREIGN KEY (`rw_client_id`) REFERENCES `tb_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='维修工单';

-- ----------------------------
-- Records of tb_repair_workorder
-- ----------------------------
INSERT INTO `tb_repair_workorder` VALUES ('string', '20181216184148uA', 'string', 'string', null, 'string', 'F7022766E9F74D1F93E473E8B42D7079', null, 'string', 'string', 'string', '2018-12-16 18:41:52', '2018-12-16 00:00:00', 'string', null);

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
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='维修工单-服务项目';

-- ----------------------------
-- Records of tb_repair_workorder_item
-- ----------------------------
INSERT INTO `tb_repair_workorder_item` VALUES ('B7EC07892DF84CB89AE88E1E2EF5F045', '3041CBD00D2845318E14EE382FDFCDA0', '5E22F21B629F4765BDA4C2753625EFEB', 'DA67698177BB4118BBB23079A6CA9BFA', null, null);
INSERT INTO `tb_repair_workorder_item` VALUES ('string', 'string', 'string', 'string', '2018-12-16 18:19:55', '2018-12-16 18:19:55');

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
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `u_role_fk` (`U_ROLE`) USING BTREE,
  KEY `u_company_fk` (`U_COMPANY`) USING BTREE,
  CONSTRAINT `u_company_fk` FOREIGN KEY (`U_COMPANY`) REFERENCES `tb_company` (`ID`),
  CONSTRAINT `u_role_fk` FOREIGN KEY (`U_ROLE`) REFERENCES `tb_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'wsz', '王树政', '5f4dcc3b5aa765d61d8327deb882cf99', null, null, 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2017-06-18 16:20:52', '2017-07-26 13:01:27', '0', '0');
INSERT INTO `tb_user` VALUES ('53A82107FD90457E9445DBD27E79C19B', 'test', 'test', 'password', null, '11111', 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2017-07-30 15:55:28', '2017-07-30 15:58:35', '0', '1');
INSERT INTO `tb_user` VALUES ('6287A12E62E44177952A4692D8607A28', 'test', null, 'password', null, null, 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2017-07-30 15:59:53', '2017-07-31 15:36:07', '0', '1');
INSERT INTO `tb_user` VALUES ('77C22C2FC8A4418BBA2ECFF54F0BD0CE', 'test', 'test', '123456', null, null, 'A072D82A6AE14146B47A70E4C58AA28D', '2', null, '2017-07-31 15:36:26', '2017-07-31 15:36:26', '0', '0');
INSERT INTO `tb_user` VALUES ('D6BACA18FAE8419792357BBE4739093B', 'test', 'test', 'password', null, '11', 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2017-07-30 15:58:48', '2017-07-30 15:59:24', '0', '1');
INSERT INTO `tb_user` VALUES ('DA67698177BB4118BBB23079A6CA9BFA', 'kim', '谢顶金', 'e10adc3949ba59abbe56e057f20f883e', '1134771121@qq.com', '18378311282', 'A072D82A6AE14146B47A70E4C58AA28D', '1', '12341234324', '2017-06-28 21:01:52', '2018-12-09 20:03:34', '0', '0');
INSERT INTO `tb_user` VALUES ('DC7A00CD45B0438AA38DED7223166FE1', 'username', 'fullname', 'password', 'email', 'mobile', '1', '1', 'description', '2017-06-25 21:25:08', '2017-07-31 15:36:13', '0', '1');
INSERT INTO `tb_user` VALUES ('DC7A00CD45B0438AA38DED7223166FE2', 'admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', null, null, 'A072D82A6AE14146B47A70E4C58AA28D', '1', null, '2018-12-10 21:58:45', '2018-12-10 22:01:52', '0', '0');

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
		 set @count = (select count(*) from tb_inventory where p_id = new.p_id and r_code= new.r_code and isdeleted=0);
		 set @company = (select company from tb_in_part where in_workorder_no = new.in_workorder_no);
		 if @count > 0 then
				update tb_inventory set p_count = p_count + new.in_count where tb_inventory.p_id = new.p_id and tb_inventory.r_code = new.r_code;
		 elseif @count <= 0 then
				insert into tb_inventory(id,p_id,p_count,p_company,r_code,isdeleted)
				VALUES (  replace(uuid(),"-","") ,new.p_id,new.in_count ,@company,new.r_code,default);
		 end if; 
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deleteInPartInfo`;
DELIMITER ;;
CREATE TRIGGER `deleteInPartInfo` AFTER UPDATE ON `tb_in_part_info` FOR EACH ROW BEGIN
		 if (new.isdeleted = 1 && old.isdeleted = 0) then
		 update tb_inventory set p_count = p_count- old.in_count where tb_inventory.p_id = old.p_id and tb_inventory.r_code = old.r_code;
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
