/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1:3388
Source Server Version : 50155
Source Host           : localhost:3388
Source Database       : wts

Target Server Type    : MYSQL
Target Server Version : 50155
File Encoding         : 65001

Date: 2020-02-11 16:03:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `alone_app_version`
-- ----------------------------
DROP TABLE IF EXISTS `alone_app_version`;
CREATE TABLE `alone_app_version` (
  `version` varchar(32) NOT NULL,
  `update_time` varchar(32) DEFAULT NULL,
  `update_user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_app_version
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_applog`
-- ----------------------------
DROP TABLE IF EXISTS `alone_applog`;
CREATE TABLE `alone_applog` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `DESCRIBES` varchar(1024) NOT NULL,
  `APPUSER` varchar(32) NOT NULL,
  `LEVELS` varchar(32) DEFAULT NULL,
  `METHOD` varchar(128) DEFAULT NULL,
  `CLASSNAME` varchar(128) DEFAULT NULL,
  `IP` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_9` (`APPUSER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_applog
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_action`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_action`;
CREATE TABLE `alone_auth_action` (
  `ID` varchar(32) NOT NULL,
  `AUTHKEY` varchar(128) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `STATE` char(1) NOT NULL,
  `CHECKIS` char(1) NOT NULL,
  `LOGINIS` char(1) NOT NULL COMMENT '默认所有ACTION都要登录',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB alone_action';

-- ----------------------------
-- Records of alone_auth_action
-- ----------------------------
INSERT INTO `alone_auth_action` VALUES ('40288b854a38408e014a384de88a0005', 'action/list', '权限管理_权限定义', null, '20141211154357', '20141211154357', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38b3a6700017', 'actiontree/list', '权限管理_权限构造', null, '20141211173505', '20141211173505', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38b93d0a0022', 'log/list', '系统配置_系统日志', '', '20141211174111', '20150831184926', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38ba24f90024', 'parameter/list', '系统配置_参数定义', '', '20141211174210', '20150831185312', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38bafb830026', 'parameter/editlist', '系统配置_系统参数', '', '20141211174305', '20151016154936', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38bb431b0028', 'parameter/userelist', '系统配置_个性化参数', '', '20141211174324', '20151016154959', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38bd26a0002a', 'dictionary/list', '系统配置_数据字典', '', '20141211174527', '20150831185552', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38beae79002d', 'user/list', '组织用户管理_用户管理', '', '20141211174708', '20150831185712', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a38974f014a38bf10fc002f', 'organization/list', '组织用户管理_组织机构管理', '', '20141211174733', '20150831185753', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a3daac8014a3dfe03990005', 'FarmPortalComponentConsole', '门户管理_组件库', null, '20141212181424', '20141212181424', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a3daac8014a3dfefcce0007', 'FarmPortalPortaltreeConsole', '门户管理_门户配置', null, '20141212181528', '20141212181528', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a3e037e014a3e1d76810003', 'FarmFormsFormcategoryConsole', '动态表单_表单分类', null, '20141212184845', '20141212184845', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a3e037e014a3e1e46150005', 'FarmFormsQueryConsole', '动态表单_查询表单配置', null, '20141212184939', '20141212184939', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a50d918014a50dbdc460002', 'HhYWConsole', '系统配置_数据库管理', null, '20141216100953', '20141216100953', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a50d918014a50dcfa580005', 'qzTrigger/list', '任务调度_触发器定义', '', '20141216101106', '20171211230511', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a50d918014a50ddb7400007', 'qzTask/list', '任务调度_任务定义', '', '20141216101155', '20171211230536', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854a50d918014a50def0aa0009', 'qzScheduler/list', '任务调度_调度实例', null, '20141216101315', '20141216101315', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a1790810003', 'docfile/list', '文档管理_附件管理', '', '20141230152723', '20150831185928', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a182dbb0005', 'FarmDocgroupUser_ACTION_CONSOLE', '文档管理_工作小组管理', null, '20141230152803', '20141230152803', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a1905200007', 'docgroup/list', '文档管理_工作小组管理', '', '20141230152858', '20150831190059', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a1a32940009', 'FarmDocmessage_ACTION_CONSOLE', '文档管理_文档留言', null, '20141230153015', '20141230153015', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a1a8dd3000b', 'doctype/list', '文档管理_文档分类', '', '20141230153039', '20150831190127', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4a9a155d014a9a1afe60000d', 'doc/list', '文档管理_文档管理', '', '20141230153108', '20150901182811', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854ab4231a014ab45e15850003', 'FarmCodeProject_ACTION_CONSOLE', '代码工程_工程定义', null, '20150104175432', '20150104175432', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('40288b854ab4231a014ab45ee9fb0005', 'FarmCodeGuide_ACTION_CONSOLE', '代码工程_生成向导', null, '20150104175526', '20150104175526', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402888a84f970dad014f971444550009', 'farmtop/list', '文档管理_置顶文档', '', '20150904143851', '20150904144025', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4add11f6014add19da9d000a', 'FarmPlanTaskConsole', '当前任务_计划任务', null, '20150112154426', '20150112154426', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402894ca4ae0e797014ae1ab12120031', 'lucene/list', '文档管理_全文索引', '', '20150113130132', '20150831190209', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402888a850472ac50150472d85070009', 'weburl/list', '文档管理_推荐服务', '', '20151008191936', '20151008191936', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402888a8509d16e201509d191db90009', 'farmReleaseRanking/list', '文档管理_用户文档排名', '', '20151025114440', '20151025114440', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402888a850bcceeb0150bcd7f9780009', 'usermessage/list', '用户消息', '', '20151031154122', '20151031154122', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b355de72390155de787d000014', 'resumebase/list', '简历管理_简历查询', null, '20160712173806', '20160712173806', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b3563f530701563f5821e40000', 'expert/list', '简历管理_专家管理', null, '20160731130555', '20160731130555', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b3573c10d0015741b2273c0018', 'user/online', '系统配置_在线用户', null, '20160919170617', '20160919170617', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b3577bd11201578a0a22ef0035', 'webHotCase/list', '文档管理_检索热词', null, '20161003181502', '20161003181502', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b35ac74f63015ae477e873005d', 'question/list', '问答管理_问答管理', null, '20170319104907', '20170319104907', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a2831b35ac74f63015ae47881d3005f', 'fqaIndex/list', '检索管理_问答索引', null, '20170319104947', '20170319104947', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5b2782a6015b281052a50060', 'typedemo/list', '知识管理_模板管理', null, '20170401135010', '20170401135010', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5bfb0c93015bfb42f2ca0000', 'docspecial/list', '知识管理_知识专题', null, '20170512140520', '20170512140520', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8af5c60d5c215c54015c216201ce0002', 'outuser/list', '用户管理_外部账户', null, '20170519234450', '20170519234450', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5da5d50f015da5d736060000', 'toolweb/sysbackup', '系统配置_系统备份向导', null, '20170803100531', '20170803100531', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402880f65ddb5c65015ddb5f8a5d0001', 'useropaudit/list', '基本设置_操作申请', null, '20170813193417', '20170813193417', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5f94d2ef015f94f2502c0007', 'dingding/loadOrg', '用户管理_同步钉钉组织机构', null, '20171107132709', '20171107132709', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5fbd8ef3015fbd954fea0001', 'usermessage/msConsole', '用户管理_用户消息发送', '', '20171115105000', '20171115105142', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5fc2b9a6015fc2c34e9a0001', 'fileindex/list', '检索管理_附件索引', null, '20171116105820', '20171116105820', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402889ac5fc36ab9015fc3794fea0003', 'helper/readme', '系统配置_系统接口API', '', '20171116141708', '20181108141210', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('8a8580496053a94e016053bbba700001', 'wdaconvertlog/list', '运维管理_预览文件转换日志', null, '20171214143500', '20171214143500', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402880ef61096f4401610975b6a50004', 'pop/list', '系统配置_POP权限检索', null, '20180118212924', '20180118212924', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b88166f209370166f20cbd1a0000', 'messagemodel/list', '用户管理_用户消息模板', null, '20181108144013', '20181108144013', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b88166f21e650166f2bfead1002f', 'room/list', '考试管理_考试管理', null, '20181108175555', '20181108175555', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b88166f21e650166f2c0422d0031', 'examtype/list', '考试管理_考试分类', null, '20181108175618', '20181108175618', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b881672aedf401672af38f840001', 'subjecttype/list', '考题管理_考题分类', null, '20181119155104', '20181119155104', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b881672aedf401672af448150003', 'subject/list', '考题管理_考题管理', null, '20181119155151', '20181119155151', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('4028b881672b623e01672b6a16df0000', 'paper/list', '考试管理_考卷管理', '', '20181119180032', '20181119180158', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402881ec701325100170132647530000', 'material/list', '答题管理_引用材料', null, '20200205102040', '20200205102040', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');

-- ----------------------------
-- Table structure for `alone_auth_actiontree`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_actiontree`;
CREATE TABLE `alone_auth_actiontree` (
  `ID` varchar(32) NOT NULL,
  `SORT` int(11) NOT NULL,
  `PARENTID` varchar(32) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `TREECODE` varchar(256) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `TYPE` char(1) NOT NULL COMMENT '分类、菜单、权限',
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `UUSER` varchar(32) NOT NULL,
  `STATE` char(1) NOT NULL,
  `ACTIONID` varchar(32) DEFAULT NULL,
  `DOMAIN` varchar(64) NOT NULL,
  `ICON` varchar(64) DEFAULT NULL,
  `IMGID` varchar(32) DEFAULT NULL,
  `PARAMS` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_7` (`ACTIONID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB; (`action`) REFER `alone/alone_action`';

-- ----------------------------
-- Records of alone_auth_actiontree
-- ----------------------------
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38b93d0a0023', '10', '8a2831b35ac74f63015ae4710d6a005b', '系统日志', '8a2831b35ac74f63015ae4710d6a005b40288b854a38974f014a38b93d0a0023', '', '2', '20141211174111', '20141211174111', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38b93d0a0022', 'alone', 'icon-tip', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38b3a6700018', '2', '40288b854a38408e014a384c4f3c0002', '权限构造', '40288b854a38408e014a384c4f3c000240288b854a38974f014a38b3a6700018', '', '2', '20141211173505', '20141211173505', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38b3a6700017', 'alone', 'icon-category', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38408e014a384c4f3c0002', '4', 'NONE', '系统配置', '40288b854a38408e014a384c4f3c0002', '', '1', '20141211154212', '20181206213713', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-sprocket_dark', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38bafb830027', '3', '40288b854a38408e014a384c4f3c0002', '参数设置', '40288b854a38408e014a384c4f3c000240288b854a38974f014a38bafb830027', '', '2', '20141211174305', '20151016155834', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38bafb830026', 'alone', 'icon-sprocket_dark', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38bb431b0029', '4', '40288b854a38408e014a384c4f3c0002', '个性化参数', '40288b854a38408e014a384c4f3c000240288b854a38974f014a38bb431b0029', '', '2', '20141211174324', '20141211174435', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38bb431b0028', 'alone', 'icon-client_account_template', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38bd26a0002b', '5', '40288b854a38408e014a384c4f3c0002', '数据字典', '40288b854a38408e014a384c4f3c000240288b854a38974f014a38bd26a0002b', '', '2', '20141211174527', '20141211174527', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38bd26a0002a', 'alone', 'icon-address-book', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38be1805002c', '3', 'NONE', '用户管理', '40288b854a38974f014a38be1805002c', '', '1', '20141211174629', '20181206203902', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-group_green_edit', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38beae79002e', '1', '40288b854a38974f014a38be1805002c', '用户管理', '40288b854a38974f014a38be1805002c40288b854a38974f014a38beae79002e', '', '2', '20141211174708', '20141211174708', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38beae79002d', 'alone', 'icon-hire-me', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a38974f014a38bf10fc0030', '2', '40288b854a38974f014a38be1805002c', '组织管理', '40288b854a38974f014a38be1805002c40288b854a38974f014a38bf10fc0030', '', '2', '20141211174733', '20170319103800', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38bf10fc002f', 'alone', 'icon-customers', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402888ac506eb41b01506fa9b0a30027', '1', '40288b854a38408e014a384c4f3c0002', '注册参数', '40288b854a38408e014a384c4f3c0002402888ac506eb41b01506fa9b0a30027', '', '2', '20151016160003', '20151016160003', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38974f014a38ba24f90024', 'alone', 'icon-edit', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b88166f209370166f20cbd230001', '93', '40288b854a38974f014a38be1805002c', '用户消息模板', '40288b854a38974f014a38be1805002c4028b88166f209370166f20cbd230001', '', '2', '20181108144013', '20181108144040', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b88166f209370166f20cbd1a0000', 'alone', 'icon-books-brown', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a50d918014a50dc82980004', '11', '40288b854a38408e014a384c4f3c0002', '任务调度', '40288b854a38408e014a384c4f3c000240288b854a50d918014a50dc82980004', '', '1', '20141216101036', '20141216101036', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', null, 'alone', 'icon-application_osx_terminal', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('40288b854a50d918014a50def0aa000a', '3', '40288b854a50d918014a50dc82980004', '调度实例', '40288b854a38408e014a384c4f3c000240288b854a50d918014a50dc8298000440288b854a50d918014a50def0aa000a', '', '2', '20141216101315', '20141216101315', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a50d918014a50def0aa0009', 'alone', 'icon-future-projects', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402894ca4a9a155d014a9a1790810004', '1', '40288b854a38408e014a384c4f3c0002', '附件管理', '40288b854a38408e014a384c4f3c0002402894ca4a9a155d014a9a1790810004', '', '2', '20141230152723', '20141230152723', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402894ca4a9a155d014a9a1790810003', 'alone', 'icon-save', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402888a84f835c36014f835f72cb000e', '1', '40288b854a38408e014a384c4f3c0002', '权限定义', '40288b854a38408e014a384c4f3c0002402888a84f835c36014f835f72cb000e', '', '2', '20150831184834', '20150831184834', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a38408e014a384de88a0005', 'alone', 'icon-communication', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402888a850bcceeb0150bcd86c2e000a', '94', '40288b854a38974f014a38be1805002c', '用户消息查看', '40288b854a38974f014a38be1805002c402888a850bcceeb0150bcd86c2e000a', '', '2', '20151031154151', '20171115104859', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402888a850bcceeb0150bcd7f9780009', 'alone', 'icon-email', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('8a2831b3573c10d0015741b2273c0019', '6', '8a2831b35ac74f63015ae4710d6a005b', '在线用户', '8a2831b35ac74f63015ae4710d6a005b8a2831b3573c10d0015741b2273c0019', '', '2', '20160919170617', '20160919170617', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '8a2831b3573c10d0015741b2273c0018', 'alone', 'icon-group_green_new', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('8a2831b35ac74f63015ae4710d6a005b', '7', 'NONE', '运维管理', '8a2831b35ac74f63015ae4710d6a005b', '', '1', '20170319104138', '20171214141623', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-showreel', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b88166f21e650166f2c0422d0032', '2', '4028b881672aedf401672af2e97f0000', '业务分类及权限', '4028b881672aedf401672af2e97f00004028b88166f21e650166f2c0422d0032', '', '2', '20181108175618', '20190116111242', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b88166f21e650166f2c0422d0031', 'alone', 'icon-category', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('8af5c60d5c215c54015c216201ce0003', '3', '40288b854a38974f014a38be1805002c', '外部账户', '40288b854a38974f014a38be1805002c8af5c60d5c215c54015c216201ce0003', '', '2', '20170519234450', '20170519234450', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '8af5c60d5c215c54015c216201ce0002', 'alone', 'icon-group_green_new', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402889ac5da5d50f015da5d736110001', '15', '8a2831b35ac74f63015ae4710d6a005b', '系统备份向导', '8a2831b35ac74f63015ae4710d6a005b402889ac5da5d50f015da5d736110001', '', '2', '20170803100531', '20170803100623', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402889ac5da5d50f015da5d736060000', 'alone', 'icon-save', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b88166f1e1f90166f1f4b2c90000', '1', '8a2831b35ac74f63015ae4710d6a005b', '附件管理', '8a2831b35ac74f63015ae4710d6a005b4028b88166f1e1f90166f1f4b2c90000', '', '2', '20181108141357', '20181108141357', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402894ca4a9a155d014a9a1790810003', 'alone', 'icon-save', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402889ac5fbd8ef3015fbd954fea0002', '95', '40288b854a38974f014a38be1805002c', '用户消息发送', '40288b854a38974f014a38be1805002c402889ac5fbd8ef3015fbd954fea0002', '', '2', '20171115105000', '20171115105000', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402889ac5fbd8ef3015fbd954fea0001', 'alone', 'icon-contact', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402889ac5fc36ab9015fc3794feb0004', '99', '8a2831b35ac74f63015ae4710d6a005b', '系统接口API', '8a2831b35ac74f63015ae4710d6a005b402889ac5fc36ab9015fc3794feb0004', '', '2', '20171116141708', '20171116141708', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402889ac5fc36ab9015fc3794fea0003', 'alone', 'icon-help', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402880ef6046149d0160461a366b0001', '1', '40288b854a50d918014a50dc82980004', '任务定义', '40288b854a38408e014a384c4f3c000240288b854a50d918014a50dc82980004402880ef6046149d0160461a366b0001', '', '2', '20171211230331', '20171211230331', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a50d918014a50ddb7400007', 'alone', 'icon-business-contact', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402880ef6046149d0160461aac480002', '2', '40288b854a50d918014a50dc82980004', '触发器定义', '40288b854a38408e014a384c4f3c000240288b854a50d918014a50dc82980004402880ef6046149d0160461aac480002', '', '2', '20171211230401', '20171211230401', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '40288b854a50d918014a50dcfa580005', 'alone', 'icon-full-time', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b88166f21e650166f2bf833f002e', '1', 'NONE', '答题管理', '4028b88166f21e650166f2bf833f002e', '', '1', '20181108175529', '20181206203851', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-networking', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b88166f21e650166f2bfead10030', '4', '4028b88166f21e650166f2bf833f002e', '答题室管理', '4028b88166f21e650166f2bf833f002e4028b88166f21e650166f2bfead10030', '', '2', '20181108175555', '20200205102100', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b88166f21e650166f2bfead1002f', 'alone', 'icon-home', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402880ef61096f4401610975b6a50005', '6', '40288b854a38408e014a384c4f3c0002', 'POP权限检索', '40288b854a38408e014a384c4f3c0002402880ef61096f4401610975b6a50005', '', '2', '20180118212924', '20180118213043', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402880ef61096f4401610975b6a50004', 'alone', 'icon-exclamation-shield-frame', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b881672aedf401672af2e97f0000', '2', 'NONE', '分类管理', '4028b881672aedf401672af2e97f0000', '', '1', '20181119155021', '20181206213654', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-blogs', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b881672aedf401672af38f840002', '1', '4028b881672aedf401672af2e97f0000', '题库分类及权限', '4028b881672aedf401672af2e97f00004028b881672aedf401672af38f840002', '', '2', '20181119155104', '20190116111251', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b881672aedf401672af38f840001', 'alone', 'icon-category', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b881672aedf401672af448150004', '2', '4028b88166f21e650166f2bf833f002e', '题库管理', '4028b88166f21e650166f2bf833f002e4028b881672aedf401672af448150004', '', '2', '20181119155151', '20200205102048', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b881672aedf401672af448150003', 'alone', 'icon-cv', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('4028b881672b623e01672b6a16ec0001', '3', '4028b88166f21e650166f2bf833f002e', '答卷管理', '4028b88166f21e650166f2bf833f002e4028b881672b623e01672b6a16ec0001', '', '2', '20181119180032', '20200205102054', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4028b881672b623e01672b6a16df0000', 'alone', 'icon-archives', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec7013251001701326475a0001', '1', '4028b88166f21e650166f2bf833f002e', '引用材料', '4028b88166f21e650166f2bf833f002e402881ec7013251001701326475a0001', '', '2', '20200205102040', '20200205102040', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec701325100170132647530000', 'alone', 'icon-address-book-open', null, '');

-- ----------------------------
-- Table structure for `alone_auth_organization`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_organization`;
CREATE TABLE `alone_auth_organization` (
  `ID` varchar(32) NOT NULL,
  `TREECODE` varchar(256) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `STATE` char(1) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `TYPE` char(1) NOT NULL COMMENT '组织类型：1科室、2班组、3队组、0其他',
  `APPID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='组织类型：科室、班组、队组、其他';

-- ----------------------------
-- Records of alone_auth_organization
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_outuser`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_outuser`;
CREATE TABLE `alone_auth_outuser` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `USERID` varchar(32) DEFAULT NULL,
  `ACCOUNTID` varchar(64) NOT NULL,
  `ACCOUNTNAME` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_54` (`USERID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_auth_outuser
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_pop`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_pop`;
CREATE TABLE `alone_auth_pop` (
  `ID` varchar(32) NOT NULL,
  `POPTYPE` varchar(1) NOT NULL COMMENT '1人、2组织机构、3岗位',
  `OID` varchar(32) NOT NULL COMMENT '人ID、组织机构ID、岗位ID',
  `ONAME` varchar(128) NOT NULL COMMENT '人NAME、组织机构NAME、岗位NAME',
  `TARGETTYPE` varchar(64) NOT NULL COMMENT '权限业务类型',
  `TARGETID` varchar(32) NOT NULL COMMENT '权限业务ID',
  `TARGETNAME` varchar(128) DEFAULT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_auth_pop
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_post`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_post`;
CREATE TABLE `alone_auth_post` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `ORGANIZATIONID` varchar(32) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `EXTENDIS` varchar(2) NOT NULL COMMENT '0:否1:是（默认否）',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_51` (`ORGANIZATIONID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_auth_post
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_postaction`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_postaction`;
CREATE TABLE `alone_auth_postaction` (
  `ID` varchar(32) NOT NULL,
  `MENUID` varchar(32) NOT NULL,
  `POSTID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_8` (`MENUID`),
  KEY `FK_Reference_9` (`POSTID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB; (`actionid`) REFER `alone/alone_actio';

-- ----------------------------
-- Records of alone_auth_postaction
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_user`;
CREATE TABLE `alone_auth_user` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `PASSWORD` varchar(32) NOT NULL COMMENT 'MD5(password+loginname)',
  `COMMENTS` varchar(128) DEFAULT NULL,
  `TYPE` char(1) DEFAULT NULL COMMENT '1:系统用户:2其他3超级用户',
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `STATE` char(1) NOT NULL,
  `LOGINNAME` varchar(64) NOT NULL,
  `LOGINTIME` varchar(14) DEFAULT NULL,
  `IMGID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB alone_user';

-- ----------------------------
-- Records of alone_auth_user
-- ----------------------------
INSERT INTO `alone_auth_user` VALUES ('40288b854a329988014a329a12f30002', '系统管理员', '45A6964B87BEC90B5B6C6414FAF397A7', '', '3', '20141210130925', '20200211155443', 'userId', '40288b854a329988014a329a12f30002', '1', 'sysadmin', '20200211115754', '2c902a8d67085e660167086948800011');

-- ----------------------------
-- Table structure for `alone_auth_userorg`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_userorg`;
CREATE TABLE `alone_auth_userorg` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `ORGANIZATIONID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB; (`organizationid`) REFER `alone/alone';

-- ----------------------------
-- Records of alone_auth_userorg
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_auth_userpost`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_userpost`;
CREATE TABLE `alone_auth_userpost` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `POSTID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_auth_userpost
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_dictionary_entity`
-- ----------------------------
DROP TABLE IF EXISTS `alone_dictionary_entity`;
CREATE TABLE `alone_dictionary_entity` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(12) NOT NULL,
  `UTIME` varchar(12) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `STATE` char(1) NOT NULL DEFAULT '',
  `NAME` varchar(128) NOT NULL,
  `ENTITYINDEX` varchar(128) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `TYPE` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB';

-- ----------------------------
-- Records of alone_dictionary_entity
-- ----------------------------
INSERT INTO `alone_dictionary_entity` VALUES ('402888a855ce21490155ce3ebb7f0000', '201607091401', '201607091401', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-期望工作性质', 'RESUME_WORKNATURE', '{全职:1, 兼职:2, 实习:3}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888a855ce21490155ce402ec90001', '201607091402', '201607091402', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-期望从事职业', 'RESUME_WORKOCCUPATION', '{开发:1, 测试:2, 项目经理:4}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888a855ce21490155ce4062ef0002', '201607091402', '201607091402', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-期望从事行业', 'RESUME_WORKINDUSTRY', '{金融业:1, IT|通信|电子|互联网:2, 政府|非盈利机构:3, 交通|运输|物流|仓储:4, 其它:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888a855ce21490155ce409db00003', '201607091403', '201607091403', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-工作状态', 'RESUME_WORKSTAT', '{在职:1, 离职:2}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c54f310155c557c10f0000', '201607072031', '201607072031', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-性别', 'RESUME_SEX', '{男:1, 女:2}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c9abb00155c9b11a730000', '201607081647', '201607081647', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-婚姻状况', 'RESUME_MARRIAGESTA', '{未婚:1, 已婚:2, 离异:3}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c9abb00155c9b1a65e0001', '201607081648', '201607081648', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-海外工作/学习经历', 'RESUME_STUDYABROAD', '{有:1, 无:2}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c9abb00155c9b1d83e0002', '201607081648', '201607081648', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-政治面貌', 'RESUME_ZZMM', '{中共党员(含预备党员):1, 团员:2, 群众:3, 民主党派:4, 无党派人士:5}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c9abb00155c9ba602a000d', '201607081658', '201607081658', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-国籍', 'RESUME_NATIONALITY', '{中国:1, 其它:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55c9abb00155c9bec52b0011', '201607081702', '201607081702', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-证件类型', 'RESUME_OTHERTYPE', '{驾照:1, 其它:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55cd984d0155cdad81b30000', '201607091122', '201607091122', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-学历', 'RESUME_DEGREE', '{初中:4, 高中:8, 大专:12, 本科:16, 研究生:5, 博士生:20}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55cd984d0155cdadd46f0001', '201607091122', '201607091122', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-是否统招', 'RESUME_UNITARYIS', '{是:1, 否:2}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55ce66e90155ce7702aa0000', '201607091502', '201607091502', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-行业类别', 'RESUME_TYPE', '{IT:1, 房地产/建筑/建材/工程:2, 耐用消费品（服饰/纺织/皮革/家具/家电）:3, 医药/生物工程:4, 广告/会展/公关:5, 其它:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55ce66e90155ce7736ed0001', '201607091502', '201607091502', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-企业性质', 'RESUME_EPROPERTY', '{国企|央企:1, 私企:2, 外企:3, 其它:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55ce66e90155ce7761670002', '201607091502', '201607091502', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-企业规模', 'RESUME_ESCALE', '{20人以下:1, 20~100:2, 100~500:3, 500~1000:4, 1000~5000:5, 5000~10000:6, 10000人以上:7}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55ce66e90155ce77871a0003', '201607091503', '201607091503', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-职位月薪(税前)', 'RESUME_SALARY', '{1000以下:1, 2000~3000:2, 3000~4000:3, 4000~5000:4, 5000~7000:5, 7000~9000:7, 10000~15000:10, 15000~以上...', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402888ac55d3b5ef0155d3baf3ac0000', '201607101534', '201607101534', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '个人简历-与本人关系', 'RESUME_RELATIONTYPE', '{父母:1, 兄弟姐妹:2, 子女:3, 其他:99}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402894ca49600f600149602141820000', '201410301617', '201410301617', '12312323123123', '12312323123123', '1', '系统菜单域', 'ALONE_MENU_DOMAIN', '{前台:HOME}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('402894ca4add11f6014add1ad9cd000e', '201501121545', '201501121545', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '计划任务标签', 'PLAN_TAGS', '{日程:日程, 错误修复:错误修复}', '1');
INSERT INTO `alone_dictionary_entity` VALUES ('da96d32ae8964b03ab57b3c663537e5f', '201805211217', '201805211217', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '0', '4i1P0O1uZvSuR0i/ACoZMmiCbESk9573', 'j2x3ItA3cmo=', '{none}', '1');

-- ----------------------------
-- Table structure for `alone_dictionary_type`
-- ----------------------------
DROP TABLE IF EXISTS `alone_dictionary_type`;
CREATE TABLE `alone_dictionary_type` (
  `CTIME` varchar(12) NOT NULL,
  `UTIME` varchar(12) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `STATE` char(1) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `ENTITYTYPE` varchar(128) NOT NULL,
  `ENTITY` varchar(32) NOT NULL,
  `ID` varchar(32) NOT NULL DEFAULT '',
  `SORT` int(11) NOT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `TREECODE` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_8` (`ENTITY`) USING BTREE,
  CONSTRAINT `alone_dictionary_type_ibfk_1` FOREIGN KEY (`ENTITY`) REFERENCES `alone_dictionary_entity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB alone_dictionary_type';

-- ----------------------------
-- Records of alone_dictionary_type
-- ----------------------------
INSERT INTO `alone_dictionary_type` VALUES ('201607091404', '201607091404', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '在职', '', '1', '402888a855ce21490155ce409db00003', '402888a855ce21490155ce41a7670004', '1', 'NONE', '402888a855ce21490155ce41a7670004');
INSERT INTO `alone_dictionary_type` VALUES ('201607091404', '201607091404', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '离职', '', '2', '402888a855ce21490155ce409db00003', '402888a855ce21490155ce41c5150005', '2', 'NONE', '402888a855ce21490155ce41c5150005');
INSERT INTO `alone_dictionary_type` VALUES ('201607091404', '201607091408', '40288b854a329988014a329a12f30002', '系统管理员', '1', '全职', '', '1', '402888a855ce21490155ce3ebb7f0000', '402888a855ce21490155ce423ae50006', '1', 'NONE', '402888a855ce21490155ce423ae50006');
INSERT INTO `alone_dictionary_type` VALUES ('201607091405', '201607091408', '40288b854a329988014a329a12f30002', '系统管理员', '1', '兼职', '', '2', '402888a855ce21490155ce3ebb7f0000', '402888a855ce21490155ce425f100007', '2', 'NONE', '402888a855ce21490155ce425f100007');
INSERT INTO `alone_dictionary_type` VALUES ('201607091405', '201607091408', '40288b854a329988014a329a12f30002', '系统管理员', '1', '实习', '', '3', '402888a855ce21490155ce3ebb7f0000', '402888a855ce21490155ce4289370008', '3', 'NONE', '402888a855ce21490155ce4289370008');
INSERT INTO `alone_dictionary_type` VALUES ('201607091406', '201607091406', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '金融业', '', '1', '402888a855ce21490155ce4062ef0002', '402888a855ce21490155ce4386860009', '1', 'NONE', '402888a855ce21490155ce4386860009');
INSERT INTO `alone_dictionary_type` VALUES ('201607091406', '201607091407', '40288b854a329988014a329a12f30002', '系统管理员', '1', 'IT|通信|电子|互联网', '', '2', '402888a855ce21490155ce4062ef0002', '402888a855ce21490155ce43be05000a', '2', 'NONE', '402888a855ce21490155ce43be05000a');
INSERT INTO `alone_dictionary_type` VALUES ('201607091406', '201607091406', '40288b854a329988014a329a12f30002', '系统管理员', '1', '政府|非盈利机构', '', '3', '402888a855ce21490155ce4062ef0002', '402888a855ce21490155ce43f6e6000b', '3', 'NONE', '402888a855ce21490155ce43f6e6000b');
INSERT INTO `alone_dictionary_type` VALUES ('201607091407', '201607091407', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '交通|运输|物流|仓储', '', '4', '402888a855ce21490155ce4062ef0002', '402888a855ce21490155ce447d79000c', '4', 'NONE', '402888a855ce21490155ce447d79000c');
INSERT INTO `alone_dictionary_type` VALUES ('201607091407', '201607091407', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '其它', '', '99', '402888a855ce21490155ce4062ef0002', '402888a855ce21490155ce44faac000d', '99', 'NONE', '402888a855ce21490155ce44faac000d');
INSERT INTO `alone_dictionary_type` VALUES ('201607091409', '201607091409', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '开发', '', '1', '402888a855ce21490155ce402ec90001', '402888a855ce21490155ce4655fa000e', '1', 'NONE', '402888a855ce21490155ce4655fa000e');
INSERT INTO `alone_dictionary_type` VALUES ('201607091409', '201607091409', '40288b854a329988014a329a12f30002', '系统管理员', '1', '测试', '', '2', '402888a855ce21490155ce402ec90001', '402888a855ce21490155ce466939000f', '2', 'NONE', '402888a855ce21490155ce466939000f');
INSERT INTO `alone_dictionary_type` VALUES ('201607091409', '201607091409', '40288b854a329988014a329a12f30002', '系统管理员', '1', '项目经理', '', '4', '402888a855ce21490155ce402ec90001', '402888a855ce21490155ce4685650010', '4', 'NONE', '402888a855ce21490155ce4685650010');
INSERT INTO `alone_dictionary_type` VALUES ('201607072032', '201607072032', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '男', '', '1', '402888ac55c54f310155c557c10f0000', '402888ac55c54f310155c5581bc10001', '1', 'NONE', '402888ac55c54f310155c5581bc10001');
INSERT INTO `alone_dictionary_type` VALUES ('201607072032', '201607072032', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '女', '', '2', '402888ac55c54f310155c557c10f0000', '402888ac55c54f310155c55840860002', '2', 'NONE', '402888ac55c54f310155c55840860002');
INSERT INTO `alone_dictionary_type` VALUES ('201607081649', '201607081649', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '有', '', '1', '402888ac55c9abb00155c9b1a65e0001', '402888ac55c9abb00155c9b224aa0003', '1', 'NONE', '402888ac55c9abb00155c9b224aa0003');
INSERT INTO `alone_dictionary_type` VALUES ('201607081649', '201607081649', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '无', '', '2', '402888ac55c9abb00155c9b1a65e0001', '402888ac55c9abb00155c9b24e9b0004', '2', 'NONE', '402888ac55c9abb00155c9b24e9b0004');
INSERT INTO `alone_dictionary_type` VALUES ('201607081649', '201607081649', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '中共党员(含预备党员)', '', '1', '402888ac55c9abb00155c9b1d83e0002', '402888ac55c9abb00155c9b2a65c0005', '1', 'NONE', '402888ac55c9abb00155c9b2a65c0005');
INSERT INTO `alone_dictionary_type` VALUES ('201607081649', '201607081649', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '团员', '', '2', '402888ac55c9abb00155c9b1d83e0002', '402888ac55c9abb00155c9b2cb7a0006', '2', 'NONE', '402888ac55c9abb00155c9b2cb7a0006');
INSERT INTO `alone_dictionary_type` VALUES ('201607081649', '201607081649', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '群众', '', '3', '402888ac55c9abb00155c9b1d83e0002', '402888ac55c9abb00155c9b2ea760007', '3', 'NONE', '402888ac55c9abb00155c9b2ea760007');
INSERT INTO `alone_dictionary_type` VALUES ('201607081650', '201607081650', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '民主党派', '', '4', '402888ac55c9abb00155c9b1d83e0002', '402888ac55c9abb00155c9b308cb0008', '4', 'NONE', '402888ac55c9abb00155c9b308cb0008');
INSERT INTO `alone_dictionary_type` VALUES ('201607081650', '201607081650', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '无党派人士', '', '5', '402888ac55c9abb00155c9b1d83e0002', '402888ac55c9abb00155c9b31ee50009', '5', 'NONE', '402888ac55c9abb00155c9b31ee50009');
INSERT INTO `alone_dictionary_type` VALUES ('201607081650', '201607081650', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '未婚', '', '1', '402888ac55c9abb00155c9b11a730000', '402888ac55c9abb00155c9b3727c000a', '1', 'NONE', '402888ac55c9abb00155c9b3727c000a');
INSERT INTO `alone_dictionary_type` VALUES ('201607081650', '201607081650', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '已婚', '', '2', '402888ac55c9abb00155c9b11a730000', '402888ac55c9abb00155c9b38991000b', '2', 'NONE', '402888ac55c9abb00155c9b38991000b');
INSERT INTO `alone_dictionary_type` VALUES ('201607081650', '201607081650', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '离异', '', '3', '402888ac55c9abb00155c9b11a730000', '402888ac55c9abb00155c9b39d1e000c', '3', 'NONE', '402888ac55c9abb00155c9b39d1e000c');
INSERT INTO `alone_dictionary_type` VALUES ('201607081658', '201607081658', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '中国', '', '1', '402888ac55c9abb00155c9ba602a000d', '402888ac55c9abb00155c9bb2246000e', '1', 'NONE', '402888ac55c9abb00155c9bb2246000e');
INSERT INTO `alone_dictionary_type` VALUES ('201607081659', '201607081659', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '其它', '', '99', '402888ac55c9abb00155c9ba602a000d', '402888ac55c9abb00155c9bb5a380010', '99', 'NONE', '402888ac55c9abb00155c9bb5a380010');
INSERT INTO `alone_dictionary_type` VALUES ('201607081703', '201607081703', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '驾照', '', '1', '402888ac55c9abb00155c9bec52b0011', '402888ac55c9abb00155c9bf2fe00012', '1', 'NONE', '402888ac55c9abb00155c9bf2fe00012');
INSERT INTO `alone_dictionary_type` VALUES ('201607081703', '201607081703', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '其它', '', '99', '402888ac55c9abb00155c9bec52b0011', '402888ac55c9abb00155c9bf4c4d0013', '99', 'NONE', '402888ac55c9abb00155c9bf4c4d0013');
INSERT INTO `alone_dictionary_type` VALUES ('201607091123', '201607111614', '40288b854a329988014a329a12f30002', '系统管理员', '1', '初中', '', '4', '402888ac55cd984d0155cdad81b30000', '402888ac55cd984d0155cdae2b170002', '4', 'NONE', '402888ac55cd984d0155cdae2b170002');
INSERT INTO `alone_dictionary_type` VALUES ('201607091123', '201607111615', '40288b854a329988014a329a12f30002', '系统管理员', '1', '高中', '', '8', '402888ac55cd984d0155cdad81b30000', '402888ac55cd984d0155cdae47870003', '8', 'NONE', '402888ac55cd984d0155cdae47870003');
INSERT INTO `alone_dictionary_type` VALUES ('201607091123', '201607111615', '40288b854a329988014a329a12f30002', '系统管理员', '1', '大专', '', '12', '402888ac55cd984d0155cdad81b30000', '402888ac55cd984d0155cdaece680004', '12', 'NONE', '402888ac55cd984d0155cdaece680004');
INSERT INTO `alone_dictionary_type` VALUES ('201607091124', '201607111615', '40288b854a329988014a329a12f30002', '系统管理员', '1', '本科', '', '16', '402888ac55cd984d0155cdad81b30000', '402888ac55cd984d0155cdaee91f0005', '16', 'NONE', '402888ac55cd984d0155cdaee91f0005');
INSERT INTO `alone_dictionary_type` VALUES ('201607091124', '201607091124', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '是', '', '1', '402888ac55cd984d0155cdadd46f0001', '402888ac55cd984d0155cdaf36510006', '1', 'NONE', '402888ac55cd984d0155cdaf36510006');
INSERT INTO `alone_dictionary_type` VALUES ('201607091124', '201607091124', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '否', '', '2', '402888ac55cd984d0155cdadd46f0001', '402888ac55cd984d0155cdaf50df0007', '2', 'NONE', '402888ac55cd984d0155cdaf50df0007');
INSERT INTO `alone_dictionary_type` VALUES ('201607091503', '201607091503', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1000以下', '', '1', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce782f610004', '1', 'NONE', '402888ac55ce66e90155ce782f610004');
INSERT INTO `alone_dictionary_type` VALUES ('201607091504', '201607091504', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '2000~3000', '', '2', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce785c810005', '2', 'NONE', '402888ac55ce66e90155ce785c810005');
INSERT INTO `alone_dictionary_type` VALUES ('201607091504', '201607091504', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '3000~4000', '', '3', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce787e660006', '3', 'NONE', '402888ac55ce66e90155ce787e660006');
INSERT INTO `alone_dictionary_type` VALUES ('201607091504', '201607091504', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '4000~5000', '', '4', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce78b94b0007', '4', 'NONE', '402888ac55ce66e90155ce78b94b0007');
INSERT INTO `alone_dictionary_type` VALUES ('201607091504', '201607091504', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '5000~7000', '', '5', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce7901780008', '5', 'NONE', '402888ac55ce66e90155ce7901780008');
INSERT INTO `alone_dictionary_type` VALUES ('201607091505', '201607091505', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '7000~9000', '', '7', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce799aaa0009', '7', 'NONE', '402888ac55ce66e90155ce799aaa0009');
INSERT INTO `alone_dictionary_type` VALUES ('201607091505', '201607091505', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '10000~15000', '', '10', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce79cfb1000a', '10', 'NONE', '402888ac55ce66e90155ce79cfb1000a');
INSERT INTO `alone_dictionary_type` VALUES ('201607091505', '201607091505', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '15000~以上', '', '15', '402888ac55ce66e90155ce77871a0003', '402888ac55ce66e90155ce7a22be000b', '15', 'NONE', '402888ac55ce66e90155ce7a22be000b');
INSERT INTO `alone_dictionary_type` VALUES ('201607091506', '201607091506', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', 'IT', '', '1', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7ae4ba000c', '1', 'NONE', '402888ac55ce66e90155ce7ae4ba000c');
INSERT INTO `alone_dictionary_type` VALUES ('201607091507', '201607091507', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '房地产/建筑/建材/工程', '', '2', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7b2db1000d', '2', 'NONE', '402888ac55ce66e90155ce7b2db1000d');
INSERT INTO `alone_dictionary_type` VALUES ('201607091507', '201607091507', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '耐用消费品（服饰/纺织/皮革/家具/家电）', '', '3', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7ba0c2000e', '3', 'NONE', '402888ac55ce66e90155ce7ba0c2000e');
INSERT INTO `alone_dictionary_type` VALUES ('201607091507', '201607091507', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '医药/生物工程', '', '4', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7bcbb7000f', '4', 'NONE', '402888ac55ce66e90155ce7bcbb7000f');
INSERT INTO `alone_dictionary_type` VALUES ('201607091508', '201607091508', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '广告/会展/公关', '', '5', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7c03ac0010', '5', 'NONE', '402888ac55ce66e90155ce7c03ac0010');
INSERT INTO `alone_dictionary_type` VALUES ('201607091508', '201607091508', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '其它', '', '99', '402888ac55ce66e90155ce7702aa0000', '402888ac55ce66e90155ce7c2b450011', '99', 'NONE', '402888ac55ce66e90155ce7c2b450011');
INSERT INTO `alone_dictionary_type` VALUES ('201607091508', '201607091509', '40288b854a329988014a329a12f30002', '系统管理员', '1', '国企|央企', '', '1', '402888ac55ce66e90155ce7736ed0001', '402888ac55ce66e90155ce7cd0df0012', '1', 'NONE', '402888ac55ce66e90155ce7cd0df0012');
INSERT INTO `alone_dictionary_type` VALUES ('201607091509', '201607091509', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '私企', '', '2', '402888ac55ce66e90155ce7736ed0001', '402888ac55ce66e90155ce7cedec0013', '2', 'NONE', '402888ac55ce66e90155ce7cedec0013');
INSERT INTO `alone_dictionary_type` VALUES ('201607091509', '201607091509', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '外企', '', '3', '402888ac55ce66e90155ce7736ed0001', '402888ac55ce66e90155ce7d0cb70014', '3', 'NONE', '402888ac55ce66e90155ce7d0cb70014');
INSERT INTO `alone_dictionary_type` VALUES ('201607091509', '201607091509', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '其它', '', '99', '402888ac55ce66e90155ce7736ed0001', '402888ac55ce66e90155ce7d29d30015', '99', 'NONE', '402888ac55ce66e90155ce7d29d30015');
INSERT INTO `alone_dictionary_type` VALUES ('201607091510', '201607091510', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '20人以下', '', '1', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7e0d600016', '1', 'NONE', '402888ac55ce66e90155ce7e0d600016');
INSERT INTO `alone_dictionary_type` VALUES ('201607091510', '201607091510', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '20~100', '', '2', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7e86560017', '2', 'NONE', '402888ac55ce66e90155ce7e86560017');
INSERT INTO `alone_dictionary_type` VALUES ('201607091510', '201607091510', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '100~500', '', '3', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7eab010018', '3', 'NONE', '402888ac55ce66e90155ce7eab010018');
INSERT INTO `alone_dictionary_type` VALUES ('201607091511', '201607091511', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '500~1000', '', '4', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7ed1820019', '4', 'NONE', '402888ac55ce66e90155ce7ed1820019');
INSERT INTO `alone_dictionary_type` VALUES ('201607091511', '201607091511', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1000~5000', '', '5', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7f12b3001a', '5', 'NONE', '402888ac55ce66e90155ce7f12b3001a');
INSERT INTO `alone_dictionary_type` VALUES ('201607091511', '201607091511', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '5000~10000', '', '6', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7f3891001b', '6', 'NONE', '402888ac55ce66e90155ce7f3891001b');
INSERT INTO `alone_dictionary_type` VALUES ('201607091511', '201607091511', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '10000人以上', '', '7', '402888ac55ce66e90155ce7761670002', '402888ac55ce66e90155ce7f6e2d001c', '7', 'NONE', '402888ac55ce66e90155ce7f6e2d001c');
INSERT INTO `alone_dictionary_type` VALUES ('201607101535', '201607101535', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '父母', '', '1', '402888ac55d3b5ef0155d3baf3ac0000', '402888ac55d3b5ef0155d3bb31b20001', '1', 'NONE', '402888ac55d3b5ef0155d3bb31b20001');
INSERT INTO `alone_dictionary_type` VALUES ('201607101535', '201607101535', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '兄弟姐妹', '', '2', '402888ac55d3b5ef0155d3baf3ac0000', '402888ac55d3b5ef0155d3bb6ad90002', '2', 'NONE', '402888ac55d3b5ef0155d3bb6ad90002');
INSERT INTO `alone_dictionary_type` VALUES ('201607101535', '201607101535', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '子女', '', '3', '402888ac55d3b5ef0155d3baf3ac0000', '402888ac55d3b5ef0155d3bb9db50003', '3', 'NONE', '402888ac55d3b5ef0155d3bb9db50003');
INSERT INTO `alone_dictionary_type` VALUES ('201607101535', '201607101535', '40288b854a329988014a329a12f30002', '系统管理员', '1', '其他', '', '99', '402888ac55d3b5ef0155d3baf3ac0000', '402888ac55d3b5ef0155d3bbb90d0004', '99', 'NONE', '402888ac55d3b5ef0155d3bbb90d0004');
INSERT INTO `alone_dictionary_type` VALUES ('201607111614', '201607111614', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '研究生', '', '5', '402888ac55cd984d0155cdad81b30000', '402888ac55d8bcbe0155d90576510002', '5', 'NONE', '402888ac55d8bcbe0155d90576510002');
INSERT INTO `alone_dictionary_type` VALUES ('201607111614', '201607111614', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '博士生', '', '20', '402888ac55cd984d0155cdad81b30000', '402888ac55d8bcbe0155d905cfb50003', '20', 'NONE', '402888ac55d8bcbe0155d905cfb50003');
INSERT INTO `alone_dictionary_type` VALUES ('201410301617', '201410301617', '12312323123123', '12312323123123', '1', '前台', '', 'HOME', '402894ca49600f600149602141820000', '402894ca49600f6001496021a7020001', '1', 'NONE', '402894ca49600f6001496021a7020001');
INSERT INTO `alone_dictionary_type` VALUES ('201501121545', '201501121545', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '日程', '', '日程', '402894ca4add11f6014add1ad9cd000e', '402894ca4add11f6014add1b1c25000f', '1', 'NONE', '402894ca4add11f6014add1b1c25000f');
INSERT INTO `alone_dictionary_type` VALUES ('201501121545', '201501121545', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '错误修复', '', '错误修复', '402894ca4add11f6014add1ad9cd000e', '402894ca4add11f6014add1b42c80010', '2', 'NONE', '402894ca4add11f6014add1b42c80010');

-- ----------------------------
-- Table structure for `alone_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `alone_parameter`;
CREATE TABLE `alone_parameter` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(12) NOT NULL,
  `UTIME` varchar(12) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  `STATE` char(1) NOT NULL,
  `PKEY` varchar(64) NOT NULL,
  `PVALUE` varchar(2048) NOT NULL,
  `RULES` varchar(256) DEFAULT NULL,
  `DOMAIN` varchar(64) DEFAULT NULL,
  `COMMENTS` varchar(256) DEFAULT NULL,
  `VTYPE` char(1) NOT NULL COMMENT ' 文本：1 枚举：2',
  `USERABLE` varchar(1) NOT NULL COMMENT '0否，1是',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB; InnoDB free: 12288 kB alone_parameter';

-- ----------------------------
-- Records of alone_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for `alone_parameter_local`
-- ----------------------------
DROP TABLE IF EXISTS `alone_parameter_local`;
CREATE TABLE `alone_parameter_local` (
  `ID` varchar(32) NOT NULL,
  `PARAMETERID` varchar(32) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PVALUE` varchar(2048) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_50` (`PARAMETERID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alone_parameter_local
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_docfile`
-- ----------------------------
DROP TABLE IF EXISTS `farm_docfile`;
CREATE TABLE `farm_docfile` (
  `DIR` varchar(256) NOT NULL,
  `SERVERID` varchar(32) NOT NULL,
  `TYPE` varchar(2) NOT NULL COMMENT '1图片',
  `NAME` varchar(512) DEFAULT NULL,
  `EXNAME` varchar(16) NOT NULL,
  `LEN` float NOT NULL,
  `FILENAME` varchar(64) NOT NULL,
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL COMMENT '1正常、0临时',
  `DOWNUM` int(11) NOT NULL,
  `APPID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_docfile
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_docfile_text`
-- ----------------------------
DROP TABLE IF EXISTS `farm_docfile_text`;
CREATE TABLE `farm_docfile_text` (
  `ID` varchar(32) NOT NULL,
  `DESCRIBES` text NOT NULL,
  `FILEID` varchar(32) NOT NULL,
  `DOCID` varchar(32) NOT NULL,
  `DESCRIBESMIN` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_49` (`FILEID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_docfile_text
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_message_model`
-- ----------------------------
DROP TABLE IF EXISTS `farm_message_model`;
CREATE TABLE `farm_message_model` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TITLE` varchar(512) NOT NULL,
  `TYPEKEY` varchar(128) NOT NULL,
  `OVERER` varchar(128) NOT NULL,
  `TITLEMODEL` varchar(512) NOT NULL,
  `CONTENTMODEL` varchar(512) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_message_model
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_message_sender`
-- ----------------------------
DROP TABLE IF EXISTS `farm_message_sender`;
CREATE TABLE `farm_message_sender` (
  `ID` varchar(32) NOT NULL,
  `MODELID` varchar(32) NOT NULL,
  `APPID` varchar(32) NOT NULL,
  `TYPE` varchar(1) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FARM_MES_REFERENCE_FARM_MES` (`MODELID`),
  CONSTRAINT `FK_FARM_MES_REFERENCE_FARM_MES` FOREIGN KEY (`MODELID`) REFERENCES `farm_message_model` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_message_sender
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_qz_scheduler`
-- ----------------------------
DROP TABLE IF EXISTS `farm_qz_scheduler`;
CREATE TABLE `farm_qz_scheduler` (
  `ID` varchar(32) NOT NULL,
  `AUTOIS` varchar(2) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TASKID` varchar(32) NOT NULL,
  `TRIGGERID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_18` (`TASKID`),
  KEY `FK_Reference_19` (`TRIGGERID`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`TASKID`) REFERENCES `farm_qz_task` (`ID`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`TRIGGERID`) REFERENCES `farm_qz_trigger` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_qz_scheduler
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_qz_task`
-- ----------------------------
DROP TABLE IF EXISTS `farm_qz_task`;
CREATE TABLE `farm_qz_task` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `JOBCLASS` varchar(128) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  `JOBPARAS` varchar(1024) DEFAULT NULL,
  `JOBKEY` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_qz_task
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_qz_trigger`
-- ----------------------------
DROP TABLE IF EXISTS `farm_qz_trigger`;
CREATE TABLE `farm_qz_trigger` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `DESCRIPT` varchar(128) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_qz_trigger
-- ----------------------------

-- ----------------------------
-- Table structure for `farm_usermessage`
-- ----------------------------
DROP TABLE IF EXISTS `farm_usermessage`;
CREATE TABLE `farm_usermessage` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSER` varchar(32) DEFAULT NULL,
  `CUSERNAME` varchar(64) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `READUSERID` varchar(32) NOT NULL,
  `CONTENT` text NOT NULL,
  `TITLE` varchar(64) NOT NULL,
  `READSTATE` varchar(2) NOT NULL COMMENT '0未读、1已读',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of farm_usermessage
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_card`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card`;
CREATE TABLE `wts_card` (
  `ID` varchar(32) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `POINT` float NOT NULL,
  `ADJUDGEUSERNAME` varchar(64) DEFAULT NULL,
  `ADJUDGEUSER` varchar(32) DEFAULT NULL,
  `ADJUDGETIME` varchar(16) DEFAULT NULL,
  `STARTTIME` varchar(16) DEFAULT NULL,
  `ENDTIME` varchar(16) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(256) DEFAULT NULL,
  `COMPLETENUM` int(11) DEFAULT NULL,
  `ALLNUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_card_answer`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card_answer`;
CREATE TABLE `wts_card_answer` (
  `ID` varchar(32) NOT NULL,
  `CARDID` varchar(32) NOT NULL,
  `ANSWERID` varchar(32) DEFAULT NULL,
  `VERSIONID` varchar(32) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `VALSTR` text NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `PCONTENT` varchar(256) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_answer
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_card_point`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card_point`;
CREATE TABLE `wts_card_point` (
  `ID` char(32) NOT NULL,
  `POINT` int(11) DEFAULT NULL,
  `CARDID` char(32) NOT NULL,
  `VERSIONID` char(32) NOT NULL,
  `COMPLETE` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_point
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_exam_pop`
-- ----------------------------
DROP TABLE IF EXISTS `wts_exam_pop`;
CREATE TABLE `wts_exam_pop` (
  `ID` varchar(32) NOT NULL,
  `FUNTYPE` varchar(1) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `USERNAME` varchar(64) NOT NULL,
  `TYPEID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_EXAM_REFERENCE_WTS_EXAM` (`TYPEID`),
  CONSTRAINT `FK_WTS_EXAM_REFERENCE_WTS_EXAM` FOREIGN KEY (`TYPEID`) REFERENCES `wts_exam_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_exam_pop
-- ----------------------------
INSERT INTO `wts_exam_pop` VALUES ('402881ec703338ab0170334027880006', '1', '40288b854a329988014a329a12f30002', '系统管理员', '402881ec703338ab017033400c3d0005');
INSERT INTO `wts_exam_pop` VALUES ('402881ec703338ab0170334042be0007', '2', '40288b854a329988014a329a12f30002', '系统管理员', '402881ec703338ab017033400c3d0005');

-- ----------------------------
-- Table structure for `wts_exam_type`
-- ----------------------------
DROP TABLE IF EXISTS `wts_exam_type`;
CREATE TABLE `wts_exam_type` (
  `ID` varchar(32) NOT NULL,
  `TREECODE` varchar(256) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `STATE` char(1) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `MNGPOP` char(1) NOT NULL,
  `ADJUDGEPOP` char(1) NOT NULL,
  `QUERYPOP` char(1) NOT NULL,
  `SUPERPOP` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_exam_type
-- ----------------------------
INSERT INTO `wts_exam_type` VALUES ('402881ec703338ab017033400c3d0005', '402881ec703338ab017033400c3d0005', '', '模拟测试', '20200211155640', '20200211155654', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '1', '2', '2', '3', '3');

-- ----------------------------
-- Table structure for `wts_material`
-- ----------------------------
DROP TABLE IF EXISTS `wts_material`;
CREATE TABLE `wts_material` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TEXT` text NOT NULL,
  `TITLE` varchar(512) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_material
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_paper`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper`;
CREATE TABLE `wts_paper` (
  `ID` varchar(32) NOT NULL,
  `EXAMTYPEID` varchar(32) DEFAULT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `NAME` varchar(128) NOT NULL,
  `SUBJECTNUM` int(11) NOT NULL,
  `POINTNUM` int(11) NOT NULL,
  `COMPLETETNUM` int(11) NOT NULL,
  `AVGPOINT` int(11) NOT NULL,
  `TOPPOINT` int(11) NOT NULL,
  `LOWPOINT` int(11) NOT NULL,
  `ADVICETIME` int(11) NOT NULL,
  `PAPERNOTE` text,
  `MODELTYPE` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_PAPE_REFERENCE_WTS_EXAM` (`EXAMTYPEID`),
  CONSTRAINT `FK_WTS_PAPE_REFERENCE_WTS_EXAM` FOREIGN KEY (`EXAMTYPEID`) REFERENCES `wts_exam_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper
-- ----------------------------
INSERT INTO `wts_paper` VALUES ('402881ec703338ab01703341ea5d001f', '402881ec703338ab017033400c3d0005', '20200211155842', '20200211160109', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '2', '', '考卷示例', '5', '0', '0', '0', '0', '0', '60', '', '1');
INSERT INTO `wts_paper` VALUES ('402881ec703338ab0170334393e40028', '402881ec703338ab017033400c3d0005', '20200211160031', '20200211160110', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '2', '', '练习示例', '5', '0', '0', '0', '0', '0', '60', '', '3');

-- ----------------------------
-- Table structure for `wts_paper_answer`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_answer`;
CREATE TABLE `wts_paper_answer` (
  `ID` varchar(32) NOT NULL,
  `CARDID` varchar(32) NOT NULL,
  `ANSWERID` varchar(32) DEFAULT NULL,
  `VERSIONID` varchar(32) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `VALSTR` text NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `PCONTENT` varchar(256) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_answer
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_paper_card`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_card`;
CREATE TABLE `wts_paper_card` (
  `ID` varchar(32) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `POINT` float NOT NULL,
  `ADJUDGEUSERNAME` varchar(64) DEFAULT NULL,
  `ADJUDGEUSER` varchar(32) DEFAULT NULL,
  `ADJUDGETIME` varchar(16) DEFAULT NULL,
  `STARTTIME` varchar(16) DEFAULT NULL,
  `ENDTIME` varchar(16) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_PAPE_REFERENCE_WTS_ROOM` (`ROOMID`),
  CONSTRAINT `FK_WTS_PAPE_REFERENCE_WTS_ROOM` FOREIGN KEY (`ROOMID`) REFERENCES `wts_room` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_card
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_paper_cardsub`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_cardsub`;
CREATE TABLE `wts_paper_cardsub` (
  `ID` char(32) NOT NULL,
  `POINT` int(11) DEFAULT NULL,
  `CARDID` char(32) NOT NULL,
  `VERSIONID` char(32) NOT NULL,
  `COMPLETE` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_cardsub
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_paper_chapter`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_chapter`;
CREATE TABLE `wts_paper_chapter` (
  `ID` varchar(32) NOT NULL,
  `STYPE` varchar(2) NOT NULL,
  `PTYPE` varchar(2) NOT NULL,
  `INITPOINT` int(11) NOT NULL,
  `SUBJECTTYPEID` varchar(32) DEFAULT NULL,
  `SUBJECTNUM` int(11) DEFAULT NULL,
  `SUBJECTPOINT` int(11) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  `TEXTNOTE` text,
  `PARENTID` varchar(32) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `SORT` int(11) NOT NULL,
  `TREECODE` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_PAPE_REFERENCE_WTS_PAPE` (`PAPERID`),
  CONSTRAINT `FK_WTS_PAPE_REFERENCE_WTS_PAPE` FOREIGN KEY (`PAPERID`) REFERENCES `wts_paper` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_chapter
-- ----------------------------
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab017033420d0b0020', '1', '2', '0', null, null, null, '选择题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '1', '402881ec703338ab017033420d0b0020');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab0170334227c60021', '1', '2', '0', null, null, null, '填空题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '2', '402881ec703338ab0170334227c60021');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab0170334271480022', '1', '2', '0', null, null, null, '其它题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '3', '402881ec703338ab0170334271480022');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab01703343cd7a0029', '1', '2', '0', null, null, null, '全部题', '', 'NONE', '402881ec703338ab0170334393e40028', '1', '402881ec703338ab01703343cd7a0029');

-- ----------------------------
-- Table structure for `wts_paper_subject`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_subject`;
CREATE TABLE `wts_paper_subject` (
  `ID` varchar(32) NOT NULL,
  `VERSIONID` varchar(32) NOT NULL,
  `SUBJECTID` varchar(32) NOT NULL,
  `CHAPTERID` varchar(32) NOT NULL,
  `SORT` int(11) NOT NULL,
  `POINT` int(11) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_PAPE_REFERENCE_WTS_SUBJ` (`VERSIONID`),
  CONSTRAINT `FK_WTS_PAPE_REFERENCE_WTS_SUBJ` FOREIGN KEY (`VERSIONID`) REFERENCES `wts_subject_version` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_subject
-- ----------------------------
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab0170334294b10023', '402881ec703338ab017033411b740013', '402881ec703338ab017033411b740012', '402881ec703338ab017033420d0b0020', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab0170334295610024', '402881ec703338ab017033411b52000d', '402881ec703338ab017033411b51000c', '402881ec703338ab017033420d0b0020', '2', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342a8ba0025', '402881ec703338ab017033411a890009', '402881ec703338ab017033411a880008', '402881ec703338ab0170334227c60021', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342d0930026', '402881ec703338ab017033411bcd001d', '402881ec703338ab017033411bcd001c', '402881ec703338ab0170334271480022', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342d1510027', '402881ec703338ab017033411b990019', '402881ec703338ab017033411b990018', '402881ec703338ab0170334271480022', '2', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703343e254002a', '402881ec703338ab017033411a890009', '402881ec703338ab017033411a880008', '402881ec703338ab01703343cd7a0029', '1', '1', '402881ec703338ab0170334393e40028');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703343e2c2002b', '402881ec703338ab017033411bcd001d', '402881ec703338ab017033411bcd001c', '402881ec703338ab01703343cd7a0029', '2', '1', '402881ec703338ab0170334393e40028');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703343e317002c', '402881ec703338ab017033411b990019', '402881ec703338ab017033411b990018', '402881ec703338ab01703343cd7a0029', '3', '1', '402881ec703338ab0170334393e40028');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703343e376002d', '402881ec703338ab017033411b740013', '402881ec703338ab017033411b740012', '402881ec703338ab01703343cd7a0029', '4', '1', '402881ec703338ab0170334393e40028');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703343e3e3002e', '402881ec703338ab017033411b52000d', '402881ec703338ab017033411b51000c', '402881ec703338ab01703343cd7a0029', '5', '1', '402881ec703338ab0170334393e40028');

-- ----------------------------
-- Table structure for `wts_room`
-- ----------------------------
DROP TABLE IF EXISTS `wts_room`;
CREATE TABLE `wts_room` (
  `ID` varchar(32) NOT NULL,
  `DUSERNAME` varchar(32) DEFAULT NULL,
  `DUSER` varchar(32) DEFAULT NULL,
  `DTIME` varchar(14) DEFAULT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `EXAMTYPEID` varchar(32) DEFAULT NULL,
  `TIMETYPE` varchar(2) NOT NULL,
  `STARTTIME` varchar(16) DEFAULT NULL,
  `ENDTIME` varchar(16) DEFAULT NULL,
  `WRITETYPE` varchar(2) NOT NULL,
  `ROOMNOTE` text,
  `TIMELEN` int(11) NOT NULL,
  `COUNTTYPE` varchar(2) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `RESTARTTYPE` varchar(2) NOT NULL,
  `IMGID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_ROOM_REFERENCE_WTS_EXAM` (`EXAMTYPEID`),
  CONSTRAINT `FK_WTS_ROOM_REFERENCE_WTS_EXAM` FOREIGN KEY (`EXAMTYPEID`) REFERENCES `wts_exam_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_room
-- ----------------------------
INSERT INTO `wts_room` VALUES ('402881ec703338ab01703344a1cd002f', null, null, null, '', '2', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '20200211160140', '20200211160140', '402881ec703338ab017033400c3d0005', '1', '', '', '0', '', '60', '2', '模拟考场', '2', '');
INSERT INTO `wts_room` VALUES ('402881ec703338ab01703345479e0031', null, null, null, '', '2', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '20200211160223', '20200211160223', '402881ec703338ab017033400c3d0005', '1', '', '', '0', '', '20', '2', '答题练习室', '2', '');

-- ----------------------------
-- Table structure for `wts_room_paper`
-- ----------------------------
DROP TABLE IF EXISTS `wts_room_paper`;
CREATE TABLE `wts_room_paper` (
  `ID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_ROOM_REFERENCE_WTS_PAPE` (`PAPERID`),
  CONSTRAINT `FK_WTS_ROOM_REFERENCE_WTS_PAPE` FOREIGN KEY (`PAPERID`) REFERENCES `wts_paper` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_room_paper
-- ----------------------------
INSERT INTO `wts_room_paper` VALUES ('402881ec703338ab01703344ba3b0030', '402881ec703338ab01703344a1cd002f', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_room_paper` VALUES ('402881ec703338ab017033456eb60032', '402881ec703338ab01703345479e0031', '402881ec703338ab0170334393e40028');

-- ----------------------------
-- Table structure for `wts_room_user`
-- ----------------------------
DROP TABLE IF EXISTS `wts_room_user`;
CREATE TABLE `wts_room_user` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_ROOM_REFERENCE_WTS_ROOM` (`ROOMID`),
  CONSTRAINT `FK_WTS_ROOM_REFERENCE_WTS_ROOM` FOREIGN KEY (`ROOMID`) REFERENCES `wts_room` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_room_user
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_subject`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject`;
CREATE TABLE `wts_subject` (
  `ID` varchar(32) NOT NULL,
  `TYPEID` varchar(32) DEFAULT NULL,
  `VERSIONID` varchar(32) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `MATERIALID` varchar(32) DEFAULT NULL,
  `PRAISENUM` int(11) NOT NULL,
  `COMMENTNUM` int(11) NOT NULL,
  `ANALYSISNUM` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_SUBJ_REFERENCE_WTS_SUBJ` (`TYPEID`),
  KEY `FK_WTS_SUBJ_REFERENCE_WTS_MATE` (`MATERIALID`),
  CONSTRAINT `FK_WTS_SUBJ_REFERENCE_WTS_MATE` FOREIGN KEY (`MATERIALID`) REFERENCES `wts_material` (`ID`),
  CONSTRAINT `FK_WTS_SUBJ_REFERENCE_WTS_SUBJ` FOREIGN KEY (`TYPEID`) REFERENCES `wts_subject_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject
-- ----------------------------
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411a880008', '402881ec703338ab0170333f2a660000', '402881ec703338ab017033411a890009', '1', null, '0', '0', '0');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b51000c', '402881ec703338ab0170333f50420001', '402881ec703338ab017033411b52000d', '1', null, '0', '0', '0');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b740012', '402881ec703338ab0170333f68c10002', '402881ec703338ab017033411b740013', '1', null, '0', '0', '0');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b990018', '402881ec703338ab0170333f91fa0003', '402881ec703338ab017033411b990019', '1', null, '0', '0', '0');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411bcd001c', '402881ec703338ab0170333fb8650004', '402881ec703338ab017033411bcd001d', '1', null, '0', '0', '0');

-- ----------------------------
-- Table structure for `wts_subject_analysis`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_analysis`;
CREATE TABLE `wts_subject_analysis` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TEXT` text NOT NULL,
  `SUBJECTID` varchar(32) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_SUBJ_RE_WTS_SUBJ1007` (`SUBJECTID`),
  CONSTRAINT `FK_WTS_SUBJ_RE_WTS_SUBJ1007` FOREIGN KEY (`SUBJECTID`) REFERENCES `wts_subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_analysis
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_subject_answer`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_answer`;
CREATE TABLE `wts_subject_answer` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `VERSIONID` varchar(32) NOT NULL,
  `ANSWER` varchar(512) NOT NULL,
  `ANSWERNOTE` text,
  `SORT` int(11) NOT NULL,
  `RIGHTANSWER` varchar(2) NOT NULL,
  `POINTWEIGHT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_answer
-- ----------------------------
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411a8e000a', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411a890009', '依', null, '1', '2', '0');
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411a8f000b', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411a890009', '入', null, '2', '2', '0');
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b52000e', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b52000d', '一', null, '1', '0', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b52000f', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b52000d', '二', null, '2', '1', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b520010', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b52000d', '三', null, '3', '0', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b530011', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b52000d', '四', null, '4', '0', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b750014', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b740013', '原料', null, '1', '1', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b750015', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b740013', '原料、辅料', null, '2', '1', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b750016', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b740013', '食品添加剂', null, '3', '1', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b760017', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b740013', '原料、辅料、食品添加剂', null, '4', '0', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b9a001a', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b990019', '对', null, '1', '1', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411b9a001b', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411b990019', '错', null, '2', '0', null);
INSERT INTO `wts_subject_answer` VALUES ('402881ec703338ab017033411bce001e', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '402881ec703338ab017033411bcd001d', '安全制度', null, '1', '2', '10');

-- ----------------------------
-- Table structure for `wts_subject_comment`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_comment`;
CREATE TABLE `wts_subject_comment` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TEXT` text NOT NULL,
  `SUBJECTID` varchar(32) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_SUBJ_RE_WTS_SUBJ1006` (`SUBJECTID`),
  CONSTRAINT `FK_WTS_SUBJ_RE_WTS_SUBJ1006` FOREIGN KEY (`SUBJECTID`) REFERENCES `wts_subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_subject_pop`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_pop`;
CREATE TABLE `wts_subject_pop` (
  `ID` varchar(32) NOT NULL,
  `FUNTYPE` varchar(1) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `USERNAME` varchar(64) NOT NULL,
  `TYPEID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_pop
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_subject_type`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_type`;
CREATE TABLE `wts_subject_type` (
  `ID` varchar(32) NOT NULL,
  `TREECODE` varchar(256) NOT NULL,
  `COMMENTS` varchar(128) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  `CTIME` varchar(14) NOT NULL,
  `UTIME` varchar(14) NOT NULL,
  `STATE` char(1) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `MUSER` varchar(32) NOT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `READPOP` char(1) NOT NULL,
  `WRITEPOP` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_type
-- ----------------------------
INSERT INTO `wts_subject_type` VALUES ('402881ec703338ab0170333f2a660000', '402881ec703338ab0170333f2a660000', '', '填空题', '20200211155542', '20200211155542', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '1', '1', '1');
INSERT INTO `wts_subject_type` VALUES ('402881ec703338ab0170333f50420001', '402881ec703338ab0170333f50420001', '', '单选题', '20200211155552', '20200211155552', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '2', '1', '1');
INSERT INTO `wts_subject_type` VALUES ('402881ec703338ab0170333f68c10002', '402881ec703338ab0170333f68c10002', '', '多选题', '20200211155558', '20200211155558', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '3', '1', '1');
INSERT INTO `wts_subject_type` VALUES ('402881ec703338ab0170333f91fa0003', '402881ec703338ab0170333f91fa0003', '', '判断题', '20200211155609', '20200211155609', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '4', '1', '1');
INSERT INTO `wts_subject_type` VALUES ('402881ec703338ab0170333fb8650004', '402881ec703338ab0170333fb8650004', '', '问答题', '20200211155618', '20200211155618', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '5', '1', '1');

-- ----------------------------
-- Table structure for `wts_subject_userown`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_userown`;
CREATE TABLE `wts_subject_userown` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `MODELTYPE` varchar(2) NOT NULL,
  `SUBJECTID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_SUBJ_RE_WTS_SUBJ0935` (`SUBJECTID`),
  CONSTRAINT `FK_WTS_SUBJ_RE_WTS_SUBJ0935` FOREIGN KEY (`SUBJECTID`) REFERENCES `wts_subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_userown
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_subject_version`
-- ----------------------------
DROP TABLE IF EXISTS `wts_subject_version`;
CREATE TABLE `wts_subject_version` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `TIPSTR` varchar(256) NOT NULL,
  `TIPNOTE` text,
  `TIPTYPE` varchar(2) NOT NULL,
  `SUBJECTID` varchar(32) NOT NULL,
  `ANSWERED` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject_version
-- ----------------------------
INSERT INTO `wts_subject_version` VALUES ('402881ec703338ab017033411a890009', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '完成下列诗句:白日____山尽,黄河____海流.', null, '1', '402881ec703338ab017033411a880008', '1');
INSERT INTO `wts_subject_version` VALUES ('402881ec703338ab017033411b52000d', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '一加一等于()\r', null, '2', '402881ec703338ab017033411b51000c', '1');
INSERT INTO `wts_subject_version` VALUES ('402881ec703338ab017033411b740013', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '食品标签中的配料包括()\r', null, '3', '402881ec703338ab017033411b740012', '1');
INSERT INTO `wts_subject_version` VALUES ('402881ec703338ab017033411b990019', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '保质期是指预包装食品在标签指明的贮存条件下保持品质的期限.()', null, '4', '402881ec703338ab017033411b990018', '1');
INSERT INTO `wts_subject_version` VALUES ('402881ec703338ab017033411bcd001d', '20200211155749', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '食品质量安全主体责任规定企业应建立哪些制度？', null, '5', '402881ec703338ab017033411bcd001c', '1');
