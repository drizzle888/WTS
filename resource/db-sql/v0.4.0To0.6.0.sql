INSERT INTO `alone_app_version` VALUES ('v0.6.0',  now(), 'USERNAME');
alter table WTS_ROOM add SSORTTYPE varchar(2) not null;
alter table WTS_ROOM add OSORTTYPE varchar(2) not null;
alter table WTS_ROOM add PSHOWTYPE varchar(2) not null;
update WTS_ROOM set SSORTTYPE='1';
update WTS_ROOM set OSORTTYPE='1';
update WTS_ROOM set PSHOWTYPE='1';

create table WTS_CARD_HIS 
(
   ID                   varchar(32)                    not null,
   PAPERNAME            varchar(128)                   not null,
   PAPERID              varchar(32)                    not null,
   ROOMNAME             varchar(64)                    not null,
   ROOMID               varchar(32)                    not null,
   USERID               varchar(32)                    not null,
   POINT                float                          not null,
   ADJUDGEUSER          varchar(32),
   ADJUDGEUSERNAME      varchar(64),
   ADJUDGETIME          varchar(16),
   STARTTIME            varchar(16),
   ENDTIME              varchar(16),
   PSTATE               varchar(2)                     not null,
   PCONTENT             varchar(256),
   COMPLETENUM          int,
   ALLNUM               int,
   constraint PK_WTS_CARD_HIS primary key clustered (ID)
);
create table WTS_CARD_ANSWER_HIS 
(
   ID                   varchar(32)                    not null,
   CARDID               varchar(32)                    not null,
   ANSWERID             varchar(32),
   VERSIONID            varchar(32)                    not null,
   CUSER                varchar(32)                    not null,
   VALSTR               text                           not null,
   CTIME                varchar(16)                    not null,
   PCONTENT             varchar(256),
   PSTATE               varchar(2)                     not null,
   constraint PK_WTS_CARD_ANSWER_HIS primary key clustered (ID)
);
create table WTS_CARD_POINT_HIS 
(
   ID                   character(32)                  not null,
   POINT                int,
   CARDID               character(32)                  not null,
   VERSIONID            character(32)                  not null,
   COMPLETE             character(1)                   not null,
   constraint PK_WTS_CARD_POINT_HIS primary key clustered (ID)
);

alter table WTS_CARD_HIS add USERNAME varchar(64) not null;

INSERT INTO `alone_auth_action` VALUES ('402881ec70c6e4c10170c6ebbc830002', 'cardquery/hislist', '统计查询_归档成绩查询', '', '20200311080822', '20200311085424', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402881ec70c6e4c10170c712b9960004', 'cardquery/livelist', '统计查询_未归档成绩查询', '', '20200311085058', '20200311085414', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');
INSERT INTO `alone_auth_action` VALUES ('402881ec70c8c66b0170c8ca3e140000', 'randomitem/list', '答题管理_随机卷配置', null, '20200311165102', '20200311165102', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '1', '1');

INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c6e97c510001', '3', 'NONE', '统计查询', '402881ec70c6e4c10170c6e97c510001', '', '1', '20200311080555', '20200311080555', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', null, 'alone', 'icon-statistics', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c6ebbc840003', '1', '402881ec70c6e4c10170c6e97c510001', '已归档成绩查询', '402881ec70c6e4c10170c6e97c510001402881ec70c6e4c10170c6ebbc840003', '', '2', '20200311080822', '20200311085136', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c6e4c10170c6ebbc830002', 'alone', 'icon-tip', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c6e4c10170c712b9960005', '2', '402881ec70c6e4c10170c6e97c510001', '未归档成绩查询', '402881ec70c6e4c10170c6e97c510001402881ec70c6e4c10170c712b9960005', '', '2', '20200311085058', '20200311085058', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c6e4c10170c712b9960004', 'alone', 'icon-tip', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c8c66b0170c8ca3e220001', '5', '402881ec70c8c66b0170c8ce0b250002', '随机卷规则配置', '402881ec70c8c66b0170c8ce0b250002402881ec70c8c66b0170c8ca3e220001', '', '2', '20200311165102', '20200311165443', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '402881ec70c8c66b0170c8ca3e140000', 'alone', 'icon-application_osx_terminal', null, '');
INSERT INTO `alone_auth_actiontree` VALUES ('402881ec70c8c66b0170c8ce0b250002', '4', 'NONE', '规则管理', '402881ec70c8c66b0170c8ce0b250002', '', '1', '20200311165511', '20200311165532', '40288b854a329988014a329a12f30002', '40288b854a329988014a329a12f30002', '1', '', 'alone', 'icon-application_osx_terminal', null, '');

create table WTS_RANDOM_ITEM 
(
   ID                   varchar(32)                    not null,
   CTIME                varchar(16)                    not null,
   CUSER                varchar(32)                    not null,
   PSTATE               varchar(2)                     not null,
   PCONTENT             varchar(128),
   NAME                 varchar(64)                    not null,
   constraint PK_WTS_RANDOM_ITEM primary key clustered (ID)
);
create table WTS_RANDOM_STEP 
(
   ID                   varchar(32)                    not null,
   TYPEID               varchar(32)                    not null,
   TIPTYPE              varchar(2)                     not null,
   SUBNUM               int                            not null,
   SUBPOINT             int                            not null,
   SORT                 int                            not null,
   NAME                 varchar(64)                    not null,
   PCONTENT             varchar(128),
   ITEMID               varchar(32)                    not null,
   constraint PK_WTS_RANDOM_STEP primary key clustered (ID)
);

alter table WTS_RANDOM_STEP
   add constraint FK_WTS_RAND_REFERENCE_WTS_RAND foreign key (ITEMID)
      references WTS_RANDOM_ITEM (ID)
      on update restrict
      on delete restrict;