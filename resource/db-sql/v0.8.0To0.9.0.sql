INSERT INTO ALONE_APP_VERSION VALUES ('v0.9.0',  now(), 'USERNAME');
alter table WTS_MATERIAL add UUID varchar(32) not null;
alter table WTS_SUBJECT add UUID varchar(32) not null;
alter table WTS_PAPER add UUID varchar(32) not null;
alter table WTS_ROOM add UUID varchar(32) not null;
update WTS_MATERIAL set UUID=ID;
update WTS_SUBJECT set UUID=ID;
update WTS_PAPER set UUID=ID;
update WTS_ROOM set UUID=ID;

