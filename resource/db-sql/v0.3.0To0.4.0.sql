INSERT INTO `alone_app_version` VALUES ('v0.4.0',  now(), 'USERNAME');
alter table WTS_SUBJECT_USEROWN add CARDID varchar(32);
create table WTS_PAPER_USEROWN 
(
   ID                   varchar(32)                    not null,
   CTIME                varchar(16)                    not null,
   CUSER                varchar(32)                    not null,
   CUSERNAME            varchar(64)                    not null,
   PSTATE               varchar(2)                     not null,
   PCONTENT             varchar(128),
   MODELTYPE            varchar(2)                     not null,
   PAPERID              varchar(32)                    not null,
   ROOMID               varchar(32),
   PAPERNAME            varchar(128)                   not null,
   ROOMNAME             varchar(128),
   constraint PK_WTS_PAPER_USEROWN primary key clustered (ID)
);
create table WTS_EXAM_STAT 
(
   ID                   varchar(32)                    not null,
   CTIME                varchar(16)                    not null,
   ETIME                varchar(16)                    not null,
   CUSERNAME            varchar(64)                    not null,
   CUSER                varchar(32)                    not null,
   EUSERNAME            varchar(64)                    not null,
   EUSER                varchar(32)                    not null,
   PSTATE               varchar(2)                     not null,
   PCONTENT             varchar(128),
   SUBJECTNUM           int                            not null,
   ERRORSUBNUM          int                            not null,
   PAPERNUM             int                            not null,
   constraint PK_WTS_EXAM_STAT primary key clustered (ID)
);

alter table WTS_EXAM_STAT add TESTNUM int;
alter table WTS_PAPER_USEROWN add SCORE float;
alter table WTS_PAPER_USEROWN add RPCENT int;
alter table WTS_PAPER_USEROWN add CARDID varchar(32);
alter table WTS_PAPER add BOOKNUM int not null;
update WTS_PAPER set BOOKNUM=0;
update WTS_PAPER_USEROWN set SCORE=-1000;
update WTS_PAPER_USEROWN set RPCENT=-1000;

alter table WTS_SUBJECT add DONUM int not null;
alter table WTS_SUBJECT add RIGHTNUM int not null;
update WTS_SUBJECT set DONUM=0;
update WTS_SUBJECT set RIGHTNUM=0;