INSERT INTO ALONE_APP_VERSION VALUES ('v0.9.2',  now(), 'USERNAME');
alter table WTS_CARD add OVERTIME varchar(2) not null;
update WTS_CARD set OVERTIME='1';
alter table WTS_CARD_HIS add OVERTIME varchar(2) not null;
update WTS_CARD_HIS set OVERTIME='1';


