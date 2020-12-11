/*
SQLyog v10.2 
MySQL - 8.0.22 : Database - web-mastar
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`web-mastar` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `web-mastar`;

/*Table structure for table `web_ip` */

DROP TABLE IF EXISTS `web_ip`;

CREATE TABLE `web_ip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `add_time` varchar(50) DEFAULT NULL COMMENT '添加时间',
  `up_time` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `status` int DEFAULT '1' COMMENT '状态1允许2拒绝',
  `note` varchar(50) DEFAULT NULL COMMENT '说明',
  `type` int DEFAULT '1' COMMENT '类型1白名单2黑名单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='ip信息';

/*Data for the table `web_ip` */

insert  into `web_ip`(`id`,`ip`,`add_time`,`up_time`,`status`,`note`,`type`) values (5,'127.0.0.1','2020-10-12 15:54:06','2020-10-12 17:06:37',1,'测试',1),(9,'192.168.13.100','2020-10-12 16:04:07','2020-10-13 18:52:09',1,'个',1),(10,'220.248.73.29','2020-10-13 18:52:23','2020-10-13 18:52:34',2,'sdf',2);

/*Table structure for table `web_menu` */

DROP TABLE IF EXISTS `web_menu`;

CREATE TABLE `web_menu` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `parents` varchar(255) DEFAULT NULL COMMENT '父类菜单标识',
  `css` varchar(255) DEFAULT NULL COMMENT '菜单图标ioc',
  `href` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `lever` varchar(255) DEFAULT NULL COMMENT '菜单级别',
  `note` varchar(255) DEFAULT NULL COMMENT '说明',
  `type` varchar(255) DEFAULT NULL COMMENT '类型0目录，1菜单，2按钮',
  `superior` varchar(255) DEFAULT NULL COMMENT '上级',
  `lowerlevel` varchar(255) DEFAULT NULL COMMENT '下级',
  `belogin` int DEFAULT '1' COMMENT '所属平台1后台2前台3App',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `web_menu` */

insert  into `web_menu`(`Id`,`menu_name`,`parents`,`css`,`href`,`lever`,`note`,`type`,`superior`,`lowerlevel`,`belogin`) values (1,'系统设置','0','layui-icon-snowflake','#','1','系统设置','0','01000000','01010000',1),(2,'菜单列表','1','layui-icon-layer','WebMenu/list','2','','1','01000000','01020000',1),(3,'添加目录','2','','WebMenu/addMenu','3','','2','','',1),(4,'修改目录','2','','WebMenu/edit','3','','2','','',1),(5,'删除目录','2','','WebMenu/del','3','','2','','',1),(6,'全部展开','2','','WebMenu/openmenu','3','','2','','',1),(7,'全部折叠','2','','WebMenu/closeMenu','3','','2','','',1),(8,'角色管理','1','layui-icon-friends','WebRole/list','2','','1','01000000','01030000',1),(9,'添加角色','8','','WebRole/add','3','','2','','',1),(10,'编辑角色','8','','WebRole/edit','3','','2','','',1),(11,'设置权限','8','','WebRole/power','3','','2','','',1),(12,'删除角色','8','','WebRole/del','3','','2','','',1),(13,'行为记录','1','layui-icon-search','WebOperationLog/list','2','','1','01000000','01040000',1),(14,'系统用户','0','layui-icon-username','#','1','','0','02000000','02010000',1),(15,'用户管理','14','layui-icon-user','WebUser/list','2','','1','02000000','02020000',1),(16,'添加用户','15','','WebUser/add','3','','2','','',1),(17,'编辑用户','15','','WebUser/edit','3','','2','','',1),(18,'删除用户','15','','WebUser/del','3','','2','','',1),(19,'修改用户状态','15','','WebUser/stauts','3','','2','','',1),(20,'数据监控','1','layui-icon-chart','/druid/index.html','2','数据监控','1','01000000','01050000',1),(32,'IP设置','1','layui-icon-share','WebIp/list','2','查看允许访的IP地址','1','01000000','01060000',1),(33,'设置IP','32','','WebIp/add','3','','2','','',1),(34,'修改IP','32','','WebIp/edit','3','','2','','',1),(35,'删除IP','32','','WebIp/del','3','','2','','',1),(36,'在线用户','1','layui-icon-speaker','OnleUser/list','2','查看在线用户','1','01000000','01070000',1);

/*Table structure for table `web_operation_log` */

DROP TABLE IF EXISTS `web_operation_log`;

CREATE TABLE `web_operation_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `device` varchar(200) DEFAULT NULL COMMENT '硬件设备',
  `device_sys` varchar(200) DEFAULT NULL COMMENT '设备系统',
  `device_v` varchar(200) DEFAULT NULL COMMENT '设备版本',
  `oper_module` varchar(200) DEFAULT NULL COMMENT '操作模块',
  `oper_type` varchar(200) DEFAULT NULL COMMENT '操作类型',
  `oper_msg` varchar(500) DEFAULT NULL COMMENT '操作描述',
  `req_uri` varchar(100) DEFAULT NULL COMMENT '请求URI',
  `req_ip` varchar(100) DEFAULT NULL COMMENT '请求ip',
  `oper_method` varchar(200) DEFAULT NULL COMMENT '操作方法',
  `oper_times` varchar(200) DEFAULT NULL COMMENT '操作时间',
  `req_parameter` longtext COMMENT '请求参数',
  `resp_parameter` longtext COMMENT '返回结果',
  `resp_times` varchar(200) DEFAULT NULL COMMENT '结束时间',
  `take_up_time` varchar(200) DEFAULT NULL COMMENT '耗时',
  `log_type` int DEFAULT '2' COMMENT '日志类型1登入,2操作',
  `log_status` int DEFAULT '1' COMMENT '日志状态1正常2异常',
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  KEY `device` (`device`),
  KEY `oper_module` (`oper_module`),
  KEY `oper_type` (`oper_type`),
  KEY `oper_msg` (`oper_msg`(255)),
  KEY `req_uri` (`req_uri`),
  KEY `oper_method` (`oper_method`),
  KEY `oper_msg_2` (`oper_msg`(255)),
  KEY `req_parameter` (`req_parameter`(255)),
  KEY `resp_parameter` (`resp_parameter`(255)),
  KEY `log_type` (`log_type`),
  KEY `log_status` (`log_status`)
) ENGINE=InnoDB AUTO_INCREMENT=3618 DEFAULT CHARSET=utf8 COMMENT='系统日志记录';

/*Data for the table `web_operation_log` */


/*Table structure for table `web_role` */

DROP TABLE IF EXISTS `web_role`;

CREATE TABLE `web_role` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `add_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `edit_time` varchar(255) DEFAULT NULL COMMENT '操作时间',
  `note` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*Data for the table `web_role` */

insert  into `web_role`(`Id`,`role`,`name`,`add_time`,`edit_time`,`note`) values (8,'sys','系统管理员','2020-02-10 15:18:21','2020-10-10 11:22:12',''),(9,'userAdmin','用户管理员','2020-02-10 16:51:30','2020-10-12 18:58:05','');

/*Table structure for table `web_role_permission` */

DROP TABLE IF EXISTS `web_role_permission`;

CREATE TABLE `web_role_permission` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `menu_id` int DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=629 DEFAULT CHARSET=utf8 COMMENT='web用户权限表';

/*Data for the table `web_role_permission` */

insert  into `web_role_permission`(`Id`,`role_id`,`menu_id`) values (1,6,1),(2,6,2),(3,6,3),(4,6,4),(5,6,5),(6,6,6),(7,6,7),(8,6,8),(9,6,9),(10,6,10),(11,6,11),(12,6,12),(13,6,13),(14,6,14),(15,6,15),(16,6,16),(17,6,17),(18,6,18),(19,6,19),(39,3,1),(40,3,2),(41,3,3),(42,3,4),(43,3,5),(44,3,6),(45,3,7),(46,3,8),(47,3,9),(48,3,10),(49,3,11),(50,3,12),(51,3,13),(52,3,14),(53,3,15),(54,3,16),(55,3,17),(56,3,18),(57,3,19),(99,11,1),(100,11,2),(101,11,3),(102,11,6),(590,9,1),(591,9,8),(592,9,9),(593,9,10),(594,9,11),(595,9,12),(596,9,32),(597,9,33),(598,9,14),(599,9,15),(600,9,16),(601,9,17),(602,9,18),(603,9,19),(604,8,1),(605,8,2),(606,8,3),(607,8,4),(608,8,5),(609,8,6),(610,8,7),(611,8,8),(612,8,9),(613,8,10),(614,8,11),(615,8,12),(616,8,13),(617,8,20),(618,8,32),(619,8,33),(620,8,34),(621,8,35),(622,8,36),(623,8,14),(624,8,15),(625,8,16),(626,8,17),(627,8,18),(628,8,19);

/*Table structure for table `web_user` */

DROP TABLE IF EXISTS `web_user`;

CREATE TABLE `web_user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `userpass` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `lever` varchar(255) DEFAULT NULL COMMENT '级别（1管理员、2普通用户）',
  `status` int DEFAULT '1' COMMENT '用户状态1启用2禁用',
  `salt` varchar(255) DEFAULT NULL,
  `creat_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `login_errors` int DEFAULT '0' COMMENT '登入错误次数',
  `lockStatus` int DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='系统用户';

/*Data for the table `web_user` */

insert  into `web_user`(`Id`,`username`,`userpass`,`nickname`,`phone`,`lever`,`status`,`salt`,`creat_time`,`login_errors`,`lockStatus`) values (1,'15701556037','01a1a22a6c17cafc32102357911edb48','赵旗','15701556037','8',1,'f902353066c4a8203a742a4978dc92f2','2020-01-10 16:49:54',0,0),(2,'zhaoqi','01a1a22a6c17cafc32102357911edb48','注释掉','15701556037','8',1,NULL,'2020-09-16 16:44:53',0,0),(3,'15701556038','01a1a22a6c17cafc32102357911edb48','张三','15701556037','8',1,NULL,'2020-02-10 16:49:54',0,0),(15,'test2','01a1a22a6c17cafc32102357911edb48','NCtest2','15701556032','8',1,NULL,'2020-10-19 20:05:31',0,0),(17,'test4','01a1a22a6c17cafc32102357911edb48','NCtest4','15701556034','8',1,NULL,'2020-10-19 20:05:31',0,0),(18,'test5','01a1a22a6c17cafc32102357911edb48','NCtest5','15701556035','8',1,NULL,'2020-10-19 20:05:31',0,0),(19,'test6','01a1a22a6c17cafc32102357911edb48','NCtest6','15701556036','8',1,NULL,'2020-10-19 20:05:31',0,0),(20,'test7','01a1a22a6c17cafc32102357911edb48','NCtest7','15701556037','8',1,NULL,'2020-10-19 20:05:31',0,0),(21,'test8','01a1a22a6c17cafc32102357911edb48','NCtest8','15701556038','8',1,NULL,'2020-10-19 20:05:31',0,0),(22,'test9','01a1a22a6c17cafc32102357911edb48','NCtest9','15701556039','8',1,NULL,'2020-10-19 20:05:31',0,0),(23,'test10','01a1a22a6c17cafc32102357911edb48','NCtest10','157015560310','8',1,NULL,'2020-10-19 20:05:31',0,0),(24,'test11','01a1a22a6c17cafc32102357911edb48','NCtest11','157015560311','8',1,NULL,'2020-10-19 20:05:31',0,0),(25,'test12','01a1a22a6c17cafc32102357911edb48','NCtest12','157015560312','8',1,NULL,'2020-10-19 20:05:31',0,0),(26,'test13','01a1a22a6c17cafc32102357911edb48','NCtest13','15701556031','8',1,NULL,'2020-10-19 20:05:31',0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;