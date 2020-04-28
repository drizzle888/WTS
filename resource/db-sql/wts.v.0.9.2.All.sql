/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1:3388-wts
Source Server Version : 50155
Source Host           : localhost:3388
Source Database       : wts

Target Server Type    : MYSQL
Target Server Version : 50155
File Encoding         : 65001

Date: 2020-04-28 19:59:11
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
INSERT INTO `alone_app_version` VALUES ('v0.4.0', '2020-02-26 15:35:36', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.5.0', '2020-03-12 10:43:37', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.6.0', '2020-03-12 10:45:46', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.7.0', '2020-03-20 12:30:03', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.8.0', '2020-03-26 18:12:21', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.9.0', '2020-04-18 10:13:42', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.9.1', '2020-04-18 10:14:08', 'USERNAME');
INSERT INTO `alone_app_version` VALUES ('v0.9.2', '2020-04-28 19:58:42', 'USERNAME');

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
INSERT INTO `alone_applog` VALUES ('402880e8719067e00171906885650000', '20200419110821', '导出JSON.wtsp答卷402881ec703338ab0170334393e40028 / 操作用户:系统管理员[40288b854a329988014a329a12f30002]', '40288b854a329988014a329a12f30002', 'INFO', 'info', 'com.farm.web.log.WcpLog', '127.0.0.1');
INSERT INTO `alone_applog` VALUES ('402880e8719067e001719069a5890003', '20200419110934', '导出JSON.wtsp答卷402881ec703338ab0170334393e40028 / 操作用户:系统管理员[40288b854a329988014a329a12f30002]', '40288b854a329988014a329a12f30002', 'INFO', 'info', 'com.farm.web.log.WcpLog', '127.0.0.1');

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
INSERT INTO `alone_auth_action` VALUES ('402881ec70c6e4c10170c6ebbc830002', 'cardquery/hislist', '统计查询_归档成绩查询', '', '20200311080822', '20200311085424', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402881ec70c6e4c10170c712b9960004', 'cardquery/livelist', '统计查询_未归档成绩查询', '', '20200311085058', '20200311085414', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402881ec70c8c66b0170c8ca3e140000', 'randomitem/list', '答题管理_随机卷配置', null, '20200311165102', '20200311165102', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');

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
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c6e97c510001', '3', 'NONE', '统计查询', '402881ec70c6e4c10170c6e97c510001', '', '1', '20200311080555', '20200311080555', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', null, 'alone', 'icon-statistics', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c6ebbc840003', '1', '402881ec70c6e4c10170c6e97c510001', '已归档成绩查询', '402881ec70c6e4c10170c6e97c510001402881ec70c6e4c10170c6ebbc840003', '', '2', '20200311080822', '20200311085136', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c6e4c10170c6ebbc830002', 'alone', 'icon-tip', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c712b9960005', '2', '402881ec70c6e4c10170c6e97c510001', '未归档成绩查询', '402881ec70c6e4c10170c6e97c510001402881ec70c6e4c10170c712b9960005', '', '2', '20200311085058', '20200311085058', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c6e4c10170c712b9960004', 'alone', 'icon-tip', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c8c66b0170c8ca3e220001', '5', '402881ec70c8c66b0170c8ce0b250002', '随机卷规则配置', '402881ec70c8c66b0170c8ce0b250002402881ec70c8c66b0170c8ca3e220001', '', '2', '20200311165102', '20200311165443', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c8c66b0170c8ca3e140000', 'alone', 'icon-application_osx_terminal', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c8c66b0170c8ce0b250002', '4', 'NONE', '规则管理', '402881ec70c8c66b0170c8ce0b250002', '', '1', '20200311165511', '20200311165532', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-application_osx_terminal', null, '');

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
INSERT INTO `alone_auth_organization` VALUES ('402880e8718ff73301718ff846640000', '402880e8718ff73301718ff846640000', '', '机构1', '20200419090545', '20200419090545', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '1', '1', null);
INSERT INTO `alone_auth_organization` VALUES ('402880e8718ff73301718ff8698f0001', '402880e8718ff73301718ff8698f0001', '', '机构2', '20200419090554', '20200419090554', '1', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'NONE', '2', '1', null);

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
INSERT INTO `alone_auth_user` VALUES ('40288b854a329988014a329a12f30002', '系统管理员', '45A6964B87BEC90B5B6C6414FAF397A7', '', '3', '20141210130925', '20200211155443', 'userId', '40288b854a329988014a329a12f30002', '1', 'sysadmin', '20200419110809', '2c902a8d67085e660167086948800011');
INSERT INTO `alone_auth_user` VALUES ('402880e8718ff73301718ff89e2f0002', 'guest', 'D97D84A22A82C02F738BF9D9ED14FE4A', '', '1', '20200419090607', '20200419090607', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', 'guest', '20200419090616', null);

-- ----------------------------
-- Table structure for `alone_auth_userorg`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_userorg`;
CREATE TABLE `alone_auth_userorg` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `ORGANIZATIONID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `USERORG_USERID` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 12288 kB; (`organizationid`) REFER `alone/alone';

-- ----------------------------
-- Records of alone_auth_userorg
-- ----------------------------
INSERT INTO `alone_auth_userorg` VALUES ('402880e8718ff73301718ff89e2f0003', '402880e8718ff73301718ff89e2f0002', '402880e8718ff73301718ff846640000');

-- ----------------------------
-- Table structure for `alone_auth_userpost`
-- ----------------------------
DROP TABLE IF EXISTS `alone_auth_userpost`;
CREATE TABLE `alone_auth_userpost` (
  `ID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `POSTID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `USERPOST_USERID` (`USERID`)
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
INSERT INTO `alone_dictionary_entity` VALUES ('402881eb711570c7017115a662ce0008', '202003261502', '202003261502', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', 'LaTeX公式模板', 'LATEX_MODE', '{不同分母分式的加减:\\frac{{a}}{{b}} \\pm \\frac{{c}}{{d}}=\\frac{{ad \\pm bc}}{{bd}}, 乘积的方根:{\\sqrt[{n}]{{ab}}=\\sq...', '1');
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
INSERT INTO `alone_dictionary_type` VALUES ('202003261531', '202003261531', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '不同分母分式的加减', '', '\\frac{{a}}{{b}} \\pm \\frac{{c}}{{d}}=\\frac{{ad \\pm bc}}{{bd}}', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c0ce96001b', '1', 'NONE', '402881eb711570c7017115c0ce96001b');
INSERT INTO `alone_dictionary_type` VALUES ('202003261531', '202003261531', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '乘积的方根', '', '{\\sqrt[{n}]{{ab}}=\\sqrt[{n}]{{a}} \\cdot \\sqrt[{n}]{{b}},a \\ge 0,b \\ge 0}', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c11563001c', '1', 'NONE', '402881eb711570c7017115c11563001c');
INSERT INTO `alone_dictionary_type` VALUES ('202003261532', '202003261532', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '分式的乘方', '', '\\mathop{{ \\left( {\\frac{{a}}{{b}}} \\right) }}\\nolimits^{{n}}=\\frac{{\\mathop{{a}}\\nolimits^{{n}}}}{{\\mathop{{b}}\\nolimits^{{n}}}}', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c1d3d9001d', '3', 'NONE', '402881eb711570c7017115c1d3d9001d');
INSERT INTO `alone_dictionary_type` VALUES ('202003261532', '202003261532', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '分式的方根', '', '\n\\sqrt[{n}]{{\\frac{{a}}{{b}}}}=\\frac{{\\sqrt[{n}]{{a}}}}{{\\sqrt[{n}]{{b}}}},a\n\\ge 0,b \\ge 0', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c20970001e', '4', 'NONE', '402881eb711570c7017115c20970001e');
INSERT INTO `alone_dictionary_type` VALUES ('202003261533', '202003261533', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '分式的开方', '', '\n\\sqrt[{n}]{{\\frac{{a}}{{b}}}}=\\frac{{\\sqrt[{n}]{{a}}}}{{\\sqrt[{n}]{{b}}}}{\n\\left( {a &gt; 0,b &gt; 0} \\right) }', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c24c75001f', '5', 'NONE', '402881eb711570c7017115c24c75001f');
INSERT INTO `alone_dictionary_type` VALUES ('202003261533', '202003261533', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '分式的相乘', '', '\\frac{{a}}{{b}} \\cdot \\frac{{c}}{{d}}=\\frac{{ac}}{{bd}}', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c2863f0020', '6', 'NONE', '402881eb711570c7017115c2863f0020');
INSERT INTO `alone_dictionary_type` VALUES ('202003261533', '202003261533', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '根式的乘方', '', '\\sqrt[{n}]{{\\mathop{{a}}\\nolimits^{{m}}}}=\\mathop{{ \\left(\n{\\sqrt[{n}]{{a}}} \\right) }}\\nolimits^{{m}},a \\ge 0', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c2c5be0021', '7', 'NONE', '402881eb711570c7017115c2c5be0021');
INSERT INTO `alone_dictionary_type` VALUES ('202003261534', '202003261534', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '同分母相加减', '', '\\frac{{a}}{{b}} \\pm \\frac{{c}}{{b}}=\\frac{{a \\pm c}}{{b}}', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c3499e0022', '8', 'NONE', '402881eb711570c7017115c3499e0022');
INSERT INTO `alone_dictionary_type` VALUES ('202003261534', '202003261534', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '两直线平行', '', 'a \\parallel c,b \\parallel c \\Rightarrow a \\parallel b', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c3e8080023', '9', 'NONE', '402881eb711570c7017115c3e8080023');
INSERT INTO `alone_dictionary_type` VALUES ('202003261535', '202003261535', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '两直线垂直判定', '', 'l \\perp  \\beta ,l \\subset  \\alpha  \\Rightarrow  \\alpha  \\perp  \\beta', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c41c450024', '10', 'NONE', '402881eb711570c7017115c41c450024');
INSERT INTO `alone_dictionary_type` VALUES ('202003261535', '202003261535', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '线面垂直性质定理', '', '\\begin{array}{*{20}{l}} {a \\perp  \\alpha }\\\\ {b \\perp  \\alpha }\n\\end{array} \\left\\} a \\parallel b\\right.', '402881eb711570c7017115a662ce0008', '402881eb711570c7017115c465cd0025', '11', 'NONE', '402881eb711570c7017115c465cd0025');
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
INSERT INTO `alone_parameter` VALUES ('402881ec70ec28f30170ec29dd800000', '202003181342', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.ologinname', '1', 'config.sso.ologinname', 'NONE', '', '单点登陆', '遠程用户登陆名', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70ec28f30170ec29dddd0002', '202003181342', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.secret', '1', 'config.sso.secret', 'NONE', '', '单点登陆', '单点登系统的安全码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70ec28f30170ec29de150004', '202003181342', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.opassword', '1', 'config.sso.opassword', 'NONE', '', '单点登陆', '远程用户登陆密码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70ed9f210170ed9fbb540000', '202003182030', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.title', '1', 'config.sso.title', 'WCP知识库', '', '单点登陆', '服务端展示名称', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70edbbbc0170edbcd9be0000', '202003182102', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.home.url', '1', 'config.sso.home.url', 'http://127.0.0.1:8080/wcp/home/Pubindex.html', '', '单点登陆', '服务器首页', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b168e0000', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.download.url', '1', 'config.doc.download.url', 'actionImg/Publoadfile.do?id=', '', '文件', '', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16a10001', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.registed.audit', '1', 'config.registed.audit', 'false', '', '登录,注册', '用户前台注册后是否为待审核状态,true为需要审核,false直接为可用状态', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16a50002', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.restful.state', '1', 'config.restful.state', 'false', '', 'restful接口', '是否启用restful接口', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16b00003', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.upload.types', '1', 'config.doc.upload.types', 'png,jpg,jpeg,zip,doc,docx,xls,xlsx,pdf,ppt,pptx,web,rar,txt,flv,mp3,mp4,dcr', '', '文件', '上传文件允许的后缀名、文件类型', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16b40004', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.default.password', '1', 'config.default.password', '111111', '', '密码', '用户创建时的默认密码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16bb0005', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.restful.secret.key', '1', 'config.restful.secret.key', 'FB087A81D4DCA06C4910', '', 'restful接口', '系统通用的接口密码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16c20006', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.foot', '1', 'config.sys.foot', 'WTS在线答题系统', '', '文字标记/通用配置', '系统页面最下方显示', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16c90007', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.limit.cut.png.length', '1', 'config.limit.cut.png.length', '50', '', '文件', '大小（单位KB）超过该设置的PNG图片将被裁切(其他格式不做限制)否则不做处理，裁切后失去透明背景', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16d30008', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.noright.img.path', '1', 'config.doc.noright.img.path', 'text/img/noRight.png', '', '文件', '如果无操作权限，默认的图片', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16da0009', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.unreadmsg.show.num', '1', 'config.sys.unreadmsg.show.num', '5', '', '首页显示', '首页显示未读消息的数量', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16e6000a', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.none.photo.path', '1', 'config.doc.none.photo.path', 'text/img/photo.png', '', '文件', '如果头像文件不存在时，默认的图片', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16ee000b', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.regist.showOrg', '1', 'config.regist.showOrg', 'false', '', '登录,注册', '注册用户时是否要求选择组织机构', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16f4000c', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.firstlogin.message', '1', 'config.sys.firstlogin.message', '欢迎使用本系统，如果您的密码是系统自动生成的请及时重置密码!', '', '登录,注册', '用户首次正常登录时的提示信息', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b16fa000d', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.useredit.showName', '1', 'config.useredit.showName', 'false', '', '用户权限，用户信息修改 ', '前台编辑当前用户信息时，是否可以修改用户名', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1701000e', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.password.update.tip', '1', 'config.sys.password.update.tip', '密码不能包含换行符', '', '密码', '当前用户前台修改密码时,密码验证-正则表达式验证失败提示信息', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1708000f', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.dir', '1', 'config.doc.dir', 'D:\\wcp3server\\resource\\file', '', '文件', '附件文件夹地址， 1.使用相对路径 [WEBROOT]表示部署路径的根目录比如：(WEBROOT/files=E:\\server\\wcp-tomcat-6.0.16\\webapps\\wcp\\files) 2.或者使用绝对路径如：[D:\\wcp3server\\resource\\file]', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b170f0010', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.img.url', '1', 'config.doc.img.url', 'actionImg/Publoadimg.do?id=', '', '文件', '', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17140011', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.photo.url', '1', 'config.doc.photo.url', 'actionImg/Publoadphoto.do?id=', '', '文件', '', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17180012', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.password.update.regex', '1', 'config.sys.password.update.regex', '(.*)', '', '密码', '当前用户前台修改密码时,密码验证-正则表达式,如:密码规则为字母加数字至少 6位:^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$,如:密码不包含换行符:(.*)', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b171d0013', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.enforce.password.update', '1', 'config.sys.enforce.password.update', 'false', '', '密码', '是否强制当前用户修改默认密码(true强制修改),在当前用户密码和默认密码一致时（没有修改过默认密码）。', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17210014', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.media.upload.types', '1', 'config.doc.media.upload.types', 'mp4,mp3', '', '文件', '上传多媒体允许的后缀名、文件类型', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17240015', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.upload.length.max', '1', 'config.doc.upload.length.max', '2147483648', '', '文件', '上传文件允许文件大小,单位是字节', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17270016', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.verifycode.able', '1', 'config.sys.verifycode.able', 'true', '', '登录,注册', '是否启用用户登录验证码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b172b0017', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.state', '1', 'config.sso.state', 'false', '', '单点登陆', '是否启用单点登陆', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17320018', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.img.upload.length.max', '1', 'config.doc.img.upload.length.max', '10485760', '', '文件', '上传图片允许文件大小,单位是字节', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17350019', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.restful.debug', '1', 'config.restful.debug', 'false', '', 'restful接口', '是否启用接口调试模式', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b173c001a', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.none.img.path', '1', 'config.doc.none.img.path', 'text/img/none.png', '', '文件', '如果图片文件不存在时，默认的图片', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1741001b', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.useredit.showOrg', '1', 'config.useredit.showOrg', 'false', '', '用户权限，用户信息修改 ', '前台编辑当前用户信息时，是否可以选择组织机构', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1745001c', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.local.rmi.state', '1', 'config.local.rmi.state', 'false', '', '对外RMI接口', '是否启用rmi接口', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1748001d', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sso.url', '1', 'config.sso.url', 'http://127.0.0.1:8080/wcp', '', '单点登陆', '单点登系统的url，如：http://127.0.0.1:8080/wcp', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b174b001e', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.url.free.path.prefix', '1', 'config.url.free.path.prefix', 'Pub', '', '登录,注册', '是否允许用户不登录就访问知识页面，可选项：NONEPAGE(不允许访问)Pub(允许访问)', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b1750001f', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.verifycode.checknum', '1', 'config.sys.verifycode.checknum', '3', '', '登录,注册', '用户免验证码登录失败次数，超过该数量就启用验证码，为0时总是启用验证码，外部免登绑定账户时直接启用验证码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17530020', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.local.rmi.port', '1', 'config.local.rmi.port', '8701', '', '对外RMI接口', '对外RMI端口', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17570021', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.local.rmi.secret.key', '1', 'config.local.rmi.secret.key', '1D4DCA06C49105F7EDAFB087A', '', '对外RMI接口', '对外RMI接口密码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b175a0022', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.logic.remove.img.able', '1', 'config.logic.remove.img.able', 'false', '', '文件', '是否允许删除图片附件', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b175d0023', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.kindeditor.media.able', '1', 'config.kindeditor.media.able', 'false', '', '知识', '是否在文档知识超文本编辑器中启用mp4、mp3上传播放功能 (部分移动端浏览器和safari浏览器不支持)', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17600024', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.perfect.userinfo.able', '1', 'config.sys.perfect.userinfo.able', 'false', '', '登录,注册', '用户登录后是否必须先完善个人信息,true时，如果用户没有完善个人信息直接跳到个人信息编辑页面(isCompleteUserInfo(LoginUser currentUser)方法判断用户信息是否完善)', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17640025', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.show.local.regist.able', '1', 'config.show.local.regist.able', 'false', '', '登录,注册', '是否允许本地注册新用户', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17690026', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.firstBind.message', '1', 'config.sys.firstBind.message', '欢迎使用本系统!', '', '登录,注册', '用户首次通过外部账号绑定到系统登录后收到的提示信息', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b176c0027', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.img.upload.types', '1', 'config.doc.img.upload.types', 'png,jpg,jpeg,gif', '', '文件', '上传图片允许的后缀名、文件类型', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b176f0028', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.doc.downloadfile.safecode.valid', '1', 'config.doc.downloadfile.safecode.valid', 'false', '', '文件', '在下载附件时是否验证安全码(防止在url中只填写附件id下载附件)，true验证安全码，false不验证安全码', '1', '0');
INSERT INTO `alone_parameter` VALUES ('402881ec70f639df0170f63b17730029', '202003201237', '202003201238', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', 'config.sys.title', '1', 'config.sys.title', 'WTS在线答题系统', '', '文字标记/通用配置', '系统标题', '1', '0');

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
INSERT INTO `farm_docfile` VALUES ('\\2020\\04\\19\\11\\', 'd0f27565fcb9469394b49b1e73eb0025', '1', 'homelogo.png', 'png', '18100', '56c070ba7f5941528a6090739008d7fepng.file', '402880e8719067e0017190692e5e0001', '20200419110904', '20200419110908', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '答题室描述', '1', '0', '402881ec703338ab01703345479e0031');
INSERT INTO `farm_docfile` VALUES ('\\2020\\04\\19\\11\\', 'db3b2f411b7b401299299676c8a45139', '1', 'homelogo.png', 'png', '18100', '133c33ceecad434b9b71674da6fb2f5dpng.file', '402880e8719067e00171906981790002', '20200419110925', '20200419110945', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '试卷描述', '0', '0', '402881ec703338ab0170334393e40028');
INSERT INTO `farm_docfile` VALUES ('\\2020\\04\\19\\11\\', '393f5dab07bd47c6ba1d1727f882152f', '1', 'homelogo.png', 'png', '18100', 'f81a3d1044bc450596cd9fd6b6e88163png.file', '402880e8719067e00171906a2e980004', '20200419111010', '20200419111010', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', 'wtsp导入答卷', '1', '0', null);

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
INSERT INTO `farm_usermessage` VALUES ('402880e8718ff73301718ff8c1680004', '202004190906', 'SYS', '系统消息', '1', null, '402880e8718ff73301718ff89e2f0002', '欢迎使用本系统，如果您的密码是系统自动生成的请及时重置密码!', '欢迎您首次登录，请阅仔细读本消息', '0');

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
  `OVERTIME` varchar(2) NOT NULL,
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
-- Table structure for `wts_card_answer_his`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card_answer_his`;
CREATE TABLE `wts_card_answer_his` (
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_answer_his
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_card_his`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card_his`;
CREATE TABLE `wts_card_his` (
  `ID` varchar(32) NOT NULL,
  `PAPERNAME` varchar(128) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `ROOMNAME` varchar(64) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  `USERID` varchar(32) NOT NULL,
  `POINT` float NOT NULL,
  `ADJUDGEUSER` varchar(32) DEFAULT NULL,
  `ADJUDGEUSERNAME` varchar(64) DEFAULT NULL,
  `ADJUDGETIME` varchar(16) DEFAULT NULL,
  `STARTTIME` varchar(16) DEFAULT NULL,
  `ENDTIME` varchar(16) DEFAULT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(256) DEFAULT NULL,
  `COMPLETENUM` int(11) DEFAULT NULL,
  `ALLNUM` int(11) DEFAULT NULL,
  `USERNAME` varchar(64) NOT NULL,
  `OVERTIME` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_his
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
  `MPOINT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_point
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_card_point_his`
-- ----------------------------
DROP TABLE IF EXISTS `wts_card_point_his`;
CREATE TABLE `wts_card_point_his` (
  `ID` char(32) NOT NULL,
  `POINT` int(11) DEFAULT NULL,
  `CARDID` char(32) NOT NULL,
  `VERSIONID` char(32) NOT NULL,
  `COMPLETE` char(1) NOT NULL,
  `MPOINT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_card_point_his
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
-- Table structure for `wts_exam_stat`
-- ----------------------------
DROP TABLE IF EXISTS `wts_exam_stat`;
CREATE TABLE `wts_exam_stat` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `ETIME` varchar(16) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `EUSERNAME` varchar(64) NOT NULL,
  `EUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `SUBJECTNUM` int(11) NOT NULL,
  `ERRORSUBNUM` int(11) NOT NULL,
  `PAPERNUM` int(11) NOT NULL,
  `TESTNUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_exam_stat
-- ----------------------------
INSERT INTO `wts_exam_stat` VALUES ('402881ec70cc9ebd0170cca3834c0000', '20200312104712', '20200312104712', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '1', null, '0', '0', '0', '1');

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
  `UUID` varchar(32) NOT NULL,
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
  `BOOKNUM` int(11) NOT NULL,
  `UUID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_PAPE_REFERENCE_WTS_EXAM` (`EXAMTYPEID`),
  CONSTRAINT `FK_WTS_PAPE_REFERENCE_WTS_EXAM` FOREIGN KEY (`EXAMTYPEID`) REFERENCES `wts_exam_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper
-- ----------------------------
INSERT INTO `wts_paper` VALUES ('402880e8719067e00171906a2e9f0005', '402881ec703338ab017033400c3d0005', '20200211160031', '20200419110929', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '1', '', '练习示例', '5', '0', '0', '0', '0', '0', '60', '<img alt=\"\" src=\"actionImg/Publoadimg.do?id=402880e8719067e00171906a2e980004&amp;type=max\" />', '0', '402881ec703338ab0170334393e40028');
INSERT INTO `wts_paper` VALUES ('402881ec703338ab01703341ea5d001f', '402881ec703338ab017033400c3d0005', '20200211155842', '20200211160109', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '2', '', '考卷示例', '5', '0', '0', '0', '0', '0', '60', '', '0', '402881ec703338ab01703341ea5d001f');

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
INSERT INTO `wts_paper_chapter` VALUES ('402880e8719067e00171906a2e9f0006', '1', '2', '0', null, null, null, '全部题', '', 'NONE', '402880e8719067e00171906a2e9f0005', '1', '402880e8719067e00171906a2e9f0006');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab017033420d0b0020', '1', '2', '0', null, null, null, '选择题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '1', '402881ec703338ab017033420d0b0020');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab0170334227c60021', '1', '2', '0', null, null, null, '填空题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '2', '402881ec703338ab0170334227c60021');
INSERT INTO `wts_paper_chapter` VALUES ('402881ec703338ab0170334271480022', '1', '2', '0', null, null, null, '其它题', '', 'NONE', '402881ec703338ab01703341ea5d001f', '3', '402881ec703338ab0170334271480022');

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
INSERT INTO `wts_paper_subject` VALUES ('402880e8719067e00171906a2e9f0007', '402881ec703338ab017033411a890009', '402881ec703338ab017033411a880008', '402880e8719067e00171906a2e9f0006', '1', '1', '402880e8719067e00171906a2e9f0005');
INSERT INTO `wts_paper_subject` VALUES ('402880e8719067e00171906a2e9f0008', '402881ec703338ab017033411bcd001d', '402881ec703338ab017033411bcd001c', '402880e8719067e00171906a2e9f0006', '2', '1', '402880e8719067e00171906a2e9f0005');
INSERT INTO `wts_paper_subject` VALUES ('402880e8719067e00171906a2e9f0009', '402881ec703338ab017033411b990019', '402881ec703338ab017033411b990018', '402880e8719067e00171906a2e9f0006', '3', '1', '402880e8719067e00171906a2e9f0005');
INSERT INTO `wts_paper_subject` VALUES ('402880e8719067e00171906a2e9f000a', '402881ec703338ab017033411b740013', '402881ec703338ab017033411b740012', '402880e8719067e00171906a2e9f0006', '4', '1', '402880e8719067e00171906a2e9f0005');
INSERT INTO `wts_paper_subject` VALUES ('402880e8719067e00171906a2e9f000b', '402881ec703338ab017033411b52000d', '402881ec703338ab017033411b51000c', '402880e8719067e00171906a2e9f0006', '5', '1', '402880e8719067e00171906a2e9f0005');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab0170334294b10023', '402881ec703338ab017033411b740013', '402881ec703338ab017033411b740012', '402881ec703338ab017033420d0b0020', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab0170334295610024', '402881ec703338ab017033411b52000d', '402881ec703338ab017033411b51000c', '402881ec703338ab017033420d0b0020', '2', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342a8ba0025', '402881ec703338ab017033411a890009', '402881ec703338ab017033411a880008', '402881ec703338ab0170334227c60021', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342d0930026', '402881ec703338ab017033411bcd001d', '402881ec703338ab017033411bcd001c', '402881ec703338ab0170334271480022', '1', '20', '402881ec703338ab01703341ea5d001f');
INSERT INTO `wts_paper_subject` VALUES ('402881ec703338ab01703342d1510027', '402881ec703338ab017033411b990019', '402881ec703338ab017033411b990018', '402881ec703338ab0170334271480022', '2', '20', '402881ec703338ab01703341ea5d001f');

-- ----------------------------
-- Table structure for `wts_paper_userown`
-- ----------------------------
DROP TABLE IF EXISTS `wts_paper_userown`;
CREATE TABLE `wts_paper_userown` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `CUSERNAME` varchar(64) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `MODELTYPE` varchar(2) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) DEFAULT NULL,
  `PAPERNAME` varchar(128) NOT NULL,
  `ROOMNAME` varchar(128) DEFAULT NULL,
  `SCORE` float DEFAULT NULL,
  `RPCENT` int(11) DEFAULT NULL,
  `CARDID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_paper_userown
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_random_item`
-- ----------------------------
DROP TABLE IF EXISTS `wts_random_item`;
CREATE TABLE `wts_random_item` (
  `ID` varchar(32) NOT NULL,
  `CTIME` varchar(16) NOT NULL,
  `CUSER` varchar(32) NOT NULL,
  `PSTATE` varchar(2) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `NAME` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_random_item
-- ----------------------------

-- ----------------------------
-- Table structure for `wts_random_step`
-- ----------------------------
DROP TABLE IF EXISTS `wts_random_step`;
CREATE TABLE `wts_random_step` (
  `ID` varchar(32) NOT NULL,
  `TYPEID` varchar(32) NOT NULL,
  `TIPTYPE` varchar(2) NOT NULL,
  `SUBNUM` int(11) NOT NULL,
  `SUBPOINT` int(11) NOT NULL,
  `SORT` int(11) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `PCONTENT` varchar(128) DEFAULT NULL,
  `ITEMID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_RAND_REFERENCE_WTS_RAND` (`ITEMID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_random_step
-- ----------------------------

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
  `SSORTTYPE` varchar(2) NOT NULL,
  `OSORTTYPE` varchar(2) NOT NULL,
  `PSHOWTYPE` varchar(2) NOT NULL,
  `UUID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_ROOM_REFERENCE_WTS_EXAM` (`EXAMTYPEID`),
  CONSTRAINT `FK_WTS_ROOM_REFERENCE_WTS_EXAM` FOREIGN KEY (`EXAMTYPEID`) REFERENCES `wts_exam_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_room
-- ----------------------------
INSERT INTO `wts_room` VALUES ('402881ec703338ab01703344a1cd002f', null, null, null, '', '2', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '20200211160140', '20200211160140', '402881ec703338ab017033400c3d0005', '1', '', '', '0', '', '60', '2', '模拟考场', '2', '', '1', '1', '1', '402881ec703338ab01703344a1cd002f');
INSERT INTO `wts_room` VALUES ('402881ec703338ab01703345479e0031', null, null, null, '', '0', '40288b854a329988014a329a12f30002', '系统管理员', '40288b854a329988014a329a12f30002', '系统管理员', '20200419110908', '20200211160223', '402881ec703338ab017033400c3d0005', '1', '', '', '0', '<img alt=\"\" src=\"actionImg/Publoadimg.do?id=402880e8719067e0017190692e5e0001&amp;type=max\" />', '20', '2', '答题练习室', '2', '', '1', '1', '3', '402881ec703338ab01703345479e0031');

-- ----------------------------
-- Table structure for `wts_room_paper`
-- ----------------------------
DROP TABLE IF EXISTS `wts_room_paper`;
CREATE TABLE `wts_room_paper` (
  `ID` varchar(32) NOT NULL,
  `ROOMID` varchar(32) NOT NULL,
  `PAPERID` varchar(32) NOT NULL,
  `NAME` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_ROOM_REFERENCE_WTS_PAPE` (`PAPERID`),
  CONSTRAINT `FK_WTS_ROOM_REFERENCE_WTS_PAPE` FOREIGN KEY (`PAPERID`) REFERENCES `wts_paper` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_room_paper
-- ----------------------------
INSERT INTO `wts_room_paper` VALUES ('402881ec703338ab01703344ba3b0030', '402881ec703338ab01703344a1cd002f', '402881ec703338ab01703341ea5d001f', null);

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
  `DONUM` int(11) NOT NULL,
  `RIGHTNUM` int(11) NOT NULL,
  `UUID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WTS_SUBJ_REFERENCE_WTS_SUBJ` (`TYPEID`),
  KEY `FK_WTS_SUBJ_REFERENCE_WTS_MATE` (`MATERIALID`),
  CONSTRAINT `FK_WTS_SUBJ_REFERENCE_WTS_MATE` FOREIGN KEY (`MATERIALID`) REFERENCES `wts_material` (`ID`),
  CONSTRAINT `FK_WTS_SUBJ_REFERENCE_WTS_SUBJ` FOREIGN KEY (`TYPEID`) REFERENCES `wts_subject_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wts_subject
-- ----------------------------
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411a880008', '402881ec703338ab0170333f2a660000', '402881ec703338ab017033411a890009', '1', null, '0', '0', '0', '0', '0', '402881ec703338ab017033411a880008');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b51000c', '402881ec703338ab0170333f50420001', '402881ec703338ab017033411b52000d', '1', null, '0', '0', '0', '0', '0', '402881ec703338ab017033411b51000c');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b740012', '402881ec703338ab0170333f68c10002', '402881ec703338ab017033411b740013', '1', null, '0', '0', '0', '0', '0', '402881ec703338ab017033411b740012');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411b990018', '402881ec703338ab0170333f91fa0003', '402881ec703338ab017033411b990019', '1', null, '0', '0', '0', '0', '0', '402881ec703338ab017033411b990018');
INSERT INTO `wts_subject` VALUES ('402881ec703338ab017033411bcd001c', '402881ec703338ab0170333fb8650004', '402881ec703338ab017033411bcd001d', '1', null, '0', '0', '0', '0', '0', '402881ec703338ab017033411bcd001c');

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
  `CARDID` varchar(32) DEFAULT NULL,
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
