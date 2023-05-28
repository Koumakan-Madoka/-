`user`# Host: localhost  (Version: 5.5.53)
# Date: 2022-02-28 21:43:03
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "category"
#

CREATE TABLE `category` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(100) DEFAULT NULL COMMENT '类型',
  `name` VARCHAR(100) DEFAULT NULL COMMENT '名称',
  `sort` INT(11) DEFAULT NULL COMMENT '排序值(从小到大)',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "category"
#

INSERT INTO `category` VALUES (1,'PRODUCT','自然科学',1),(2,'PRODUCT','哲学',2),(3,'PRODUCT','马列毛经典著作',3),(4,'DEPART','销售部',1),(5,'DEPART','采购部',2);

#
# Structure for table "code"
#

CREATE TABLE `code` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `codetype` VARCHAR(40) NOT NULL DEFAULT '' COMMENT '编码组',
  `codedesc` VARCHAR(100) NOT NULL COMMENT '编码组用途',
  `code` VARCHAR(10) NOT NULL COMMENT '编码',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MYISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

#
# Data for table "code"
#

INSERT INTO `code` VALUES (1,'USER_TYPE','用户类型','10','管理员'),(2,'USER_TYPE','用户类型','20','员工'),(3,'USER_STATUS','账号状态','20','冻结'),(4,'USER_STATUS','账号状态','10','正常'),(5,'PRODUCT_STATUS','服务状态','10','售卖中'),(6,'PRODUCT_STATUS','服务状态','20','停售'),(16,'STOCK_TYPE','库存变动类型','10','入库'),(17,'STOCK_TYPE','库存变动类型','20','出库'),(19,'ORD_STATUS','订单状态','10','正常'),(20,'ORD_STATUS','订单状态','90','已取消');

#
# Structure for table "ord"
#

CREATE TABLE `ord` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `no` VARCHAR(255) DEFAULT NULL COMMENT '订单编号',
  `prdno` VARCHAR(255) DEFAULT NULL COMMENT '服务编号',
  `price` FLOAT(8,2) DEFAULT NULL COMMENT '价格',
  `num` INT(11) DEFAULT '0' COMMENT '订购数量',
  `total` DECIMAL(10,2) DEFAULT NULL COMMENT '总金额',
  `status` VARCHAR(10) DEFAULT '10' COMMENT '状态',
  `cstnm` VARCHAR(200) DEFAULT NULL COMMENT '客户姓名',
  `tel` VARCHAR(100) DEFAULT NULL COMMENT '联系电话',
  `addr` VARCHAR(255) DEFAULT '' COMMENT '地址',
  `descr` VARCHAR(1000) DEFAULT NULL COMMENT '备注',
  `iuid` INT(11) DEFAULT NULL COMMENT '添加人',
  `itime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Data for table "ord"
#

INSERT INTO `ord` VALUES (1,'20220225325377','P10001',6.00,4,24.00,'10','张三','1381111111','北京市阿斯兰的看法','老顾客',1,'2022-02-25 20:32:22'),(2,'20220225358416','P10002',1999.00,5,9995.00,'10','李四','139111111111','北京市北京','回头客',1,'2022-02-25 20:35:26'),(3,'20220225351549','P10001',6.00,3,18.00,'90','李四','139111111111','北京市北京','回头客',1,'2022-02-25 20:35:26'),(4,'20220225365539','P10002',1999.00,2,3998.00,'90','李四','139111111111','北京市北京','回头客',1,'2022-02-25 20:36:23'),(6,'20220225583751','P10002',1999.00,1,1999.00,'10','ksf','1381111111','asdf','asdf',2,'2022-02-25 21:58:21'),(7,'20220225586578','P10001',6.00,1,6.00,'10','ksf','1381111111','asdf','asdf',2,'2022-02-25 21:58:21'),(8,'20220225002087','P10002',1999.00,1,1999.00,'10','456','1381111','111','11',2,'2022-02-25 22:00:35'),(9,'20220225022309','P10002',1999.00,1,1999.00,'10','alak','19321341234','撒地方','大师傅都是',2,'2022-02-25 22:02:47'),(10,'20220225053959','P10003',99.99,10,999.90,'90','测试','13911111111','北京市宣武区按劳动法','熟人常客',2,'2022-02-25 22:05:49'),(11,'20220225058951','P10002',1999.00,1,1999.00,'10','测试','13911111111','北京市宣武区按劳动法','熟人常客',2,'2022-02-25 22:05:49'),(12,'20220225055087','P10001',6.00,1,6.00,'10','测试','13911111111','北京市宣武区按劳动法','熟人常客',2,'2022-02-25 22:05:49'),(13,'20220228324118','P10008',99.90,6,599.40,'90','会员1','13811111111','俺的沙发','',53,'2022-02-28 21:32:40'),(14,'20220228323314','P10002',1999.00,1,1999.00,'10','会员1','13811111111','俺的沙发','',53,'2022-02-28 21:32:40');

#
# Structure for table "product"
#

CREATE TABLE `product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `no` VARCHAR(255) DEFAULT NULL COMMENT '编号',
  `name` VARCHAR(255) DEFAULT NULL COMMENT '名称',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT '图片',
  `cid` INT(11) DEFAULT NULL COMMENT '所属分类',
  `price` FLOAT(8,2) DEFAULT NULL COMMENT '价格',
  `content` TEXT COMMENT '说明',
  `num` INT(11) DEFAULT '0' COMMENT '库存数量',
  `status` VARCHAR(10) DEFAULT '10' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "product"
#

INSERT INTO `product` VALUES (1,'P10001','毛泽东思想(一)','../../upload/16140892560621.jpg',3,6.00,'%3Cp%3Eadasdf%3Cbr/%3E%3C/p%3E',40,'10'),(2,'P10002','哲学理论启蒙','../../upload/16141713157161.jpg',2,1999.00,'%3Cp%3E%u963F%u9053%u592B%3Cbr/%3E%3C/p%3E',88,'10'),(3,'P10003','马列主义哲学','../../upload/16142613915335.jpg',3,99.99,'%3Cp%3E%u963F%u65AF%u987F%u53D1%3Cimg%20title%3D%221614261403927075602.jpg%22%20alt%3D%225.jpg%22%20src%3D%22/upload/ueditor/image/20220225/1614261403927075602.jpg%22/%3E%3C/p%3E',100,'10'),(4,'P10008','P10008十六大看缴费啊鲁大师','../../upload/16145190946835.jpg',1,99.90,'%3Cp%3E%u963F%u65AF%u987F%u53D1%u65AF%u8482%u82AC%3Cimg%20src%3D%22/upload/ueditor/image/20220228/1614519102933025279.jpg%22%20title%3D%221614519102933025279.jpg%22%20alt%3D%224.jpg%22/%3E%3C/p%3E',100,'10');

#
# Structure for table "stock"
#

CREATE TABLE `stock` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `descr` VARCHAR(255) DEFAULT NULL COMMENT '库存变化说明',
  `type` VARCHAR(100) DEFAULT NULL COMMENT '类型',
  `prd_no` VARCHAR(100) DEFAULT NULL COMMENT '服务编码',
  `num` INT(11) DEFAULT NULL COMMENT '数量',
  `iuid` INT(11) DEFAULT NULL COMMENT '操作人',
  `itime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Data for table "stock"
#

INSERT INTO `stock` VALUES (1,'一季度采购','10','P10001',50,1,'2022-02-24 20:11:36'),(2,'订单销售','20','P10001',-4,1,'2022-02-25 20:32:30'),(3,'采购入库','10','P10002',100,1,'2022-02-25 20:34:41'),(4,'订单销售','20','P10002',-5,1,'2022-02-25 20:35:26'),(5,'订单销售','20','P10001',-3,1,'2022-02-25 20:35:26'),(6,'订单[20220225365539]销售','20','P10002',-2,1,'2022-02-25 20:36:23'),(7,'订单[20220225363003]销售','20','P10001',-1,1,'2022-02-25 20:36:23'),(8,'[20220225365539]订单取消','10','P10002',2,1,'2022-02-25 20:59:06'),(9,'[20220225351549]订单取消','10','P10001',3,1,'2022-02-25 21:01:42'),(10,'订单[20220225583751]销售','20','P10002',-1,2,'2022-02-25 21:58:21'),(11,'订单[20220225586578]销售','20','P10001',-1,2,'2022-02-25 21:58:21'),(12,'订单[20220225002087]销售','20','P10002',-1,2,'2022-02-25 22:00:35'),(13,'订单[20220225022309]销售','20','P10002',-1,2,'2022-02-25 22:02:47'),(14,'采购入库 从熟人那里八折进','10','P10003',100,4,'2022-02-25 22:04:43'),(15,'订单[20220225053959]销售','20','P10003',-10,2,'2022-02-25 22:05:49'),(16,'订单[20220225058951]销售','20','P10002',-1,2,'2022-02-25 22:05:49'),(17,'订单[20220225055087]销售','20','P10001',-1,2,'2022-02-25 22:05:49'),(21,'[20220225053959]订单取消','10','P10003',10,2,'2022-02-25 22:12:04'),(22,'采购入库','10','P10008',100,53,'2022-02-28 21:32:09'),(23,'订单[20220228324118]销售','20','P10008',-6,53,'2022-02-28 21:32:40'),(24,'订单[20220228323314]销售','20','P10002',-1,53,'2022-02-28 21:32:40'),(25,'[20220228324118]订单取消','10','P10008',6,53,'2022-02-28 21:33:08');

#
# Structure for table "user"
#

CREATE TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(40) NOT NULL COMMENT '昵称',
  `login` VARCHAR(40) NOT NULL COMMENT '登录账号',
  `pwd` VARCHAR(40) NOT NULL COMMENT '登陆密码',
  `utype` VARCHAR(10) NOT NULL COMMENT '用户类型(C001)',
  `status` VARCHAR(10) DEFAULT '10' COMMENT '账号状态(10:正常 20:冻结)',
  `itime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_login` (`login`)
) ENGINE=MYISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账户表';

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'超级管理员','admin','111111','10','10','2022-10-14 12:20:36'),(2,'张三','V001','111111','20','10','2022-10-14 12:20:36'),(3,'员工2','V002','111111','20','10','2022-10-14 12:20:36'),(4,'员工3','V003','111111','20','10','2022-10-14 12:20:36'),(5,'李策','V004','111111','20','10','2022-10-14 12:20:36'),(53,'测试55','v555','111111','20','10','2022-10-14 21:29:26');

#
# Structure for table "userinfo20"
#

CREATE TABLE `userinfo20` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `no` VARCHAR(255) DEFAULT NULL COMMENT '工号',
  `name` VARCHAR(255) DEFAULT NULL COMMENT '姓名',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT '照片',
  `sex` VARCHAR(255) DEFAULT NULL COMMENT '性别',
  `age` VARCHAR(255) DEFAULT NULL COMMENT '年龄',
  `tel` VARCHAR(255) DEFAULT NULL COMMENT '电话',
  `idc` VARCHAR(255) DEFAULT NULL COMMENT '身份证号',
  `descr` VARCHAR(2000) DEFAULT NULL COMMENT '备注',
  `cid` VARCHAR(255) DEFAULT NULL COMMENT '所属分类',
  `ymd` DATE DEFAULT NULL COMMENT '入职日期',
  `itime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

#
# Data for table "userinfo20"
#

INSERT INTO `userinfo20` VALUES (1,'N000','管理员','../../resource/img/defaultHead.png','男','21','13811111111','130123523123123121','1231','5','2022-02-22','2022-02-14 12:11:59'),(2,'N001','张三','../../upload/3.jpg','女','21','13811111111','130123523123123121','1231','5','2022-02-22','2022-02-14 12:11:59'),(3,'N002','李四','../../upload/2.jpg','男','21','13812222222','130122222222222222','备注备注备注备注备注备注备注备注备注备注备注备注备注备注','4','2022-02-12','2022-02-14 12:16:31'),(4,'N003','王五','../../upload/1.jpg','女','21','13811111111','130122222222222222','阿斯顿发生的阿斯顿发生的','4','2022-02-12','2022-02-14 12:17:02'),(5,'N004','李策','../../upload/4.jpg','男','22','13811111111','130122222222222222','12312','5','2022-02-21','2022-02-14 12:17:37'),(53,'N00010','张三','../../upload/16145196443742.jpg','男','21','13811111011','130192390471982123','爱迪生发明了什么','4','2022-02-28','2022-02-28 21:41:27');
