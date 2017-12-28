CREATE TABLE SYS_APP_FTP_INFO(
       ID    NUMBER(18) NOT NULL,
       IP    VARCHAR2(256) NOT NULL,
       PORT  VARCHAR2(12) NOT NULL,
       USERNAME          VARCHAR2(20) NOT NULL,
       PASSWORD          VARCHAR2(50) NOT NULL,
       APP_FLAG          VARCHAR2(10) NOT NULL,
       REMOTE_DIC        VARCHAR2(300) NOT NULL
);
comment on table SYS_APP_FTP_INFO 
	is '业务系统节点FTP配置表';
comment on column SYS_APP_FTP_INFO.ID 
	is '主键';
comment on column SYS_APP_FTP_INFO.IP
	is '节点IP';
comment on column SYS_APP_FTP_INFO.PORT
	is '节点FTP端口';
comment on column SYS_APP_FTP_INFO.USERNAME
	is 'FTP用户名';
comment on column SYS_APP_FTP_INFO.PASSWORD
	is 'FTP密码';
comment on column SYS_APP_FTP_INFO.APP_FLAG
	is '业务系统标识';
comment on column SYS_APP_FTP_INFO.REMOTE_DIC
	is '错误日志目录';

alter table SYS_APP_FTP_INFO
  add primary key (ID)
  ;
alter table SYS_APP_FTP_INFO add APPLOG_DIC VARCHAR2(300) NOT NULL;
comment on column SYS_APP_FTP_INFO.APPLOG_DIC
	is '系统应用日志目录';
  
  
 CREATE TABLE SYS_APP_ERROR_INFO(
       ID     			NUMBER(18) NOT NULL,
       NODE_NAME        VARCHAR2(128) NOT NULL,
       APP_FLAG         VARCHAR2(10)  NOT NULL,
       CREATE_TIME      VARCHAR2(25) NOT NULL,
       CONCENT          VARCHAR2(4000) 
);
comment on table SYS_APP_ERROR_INFO 
	is '业务系统节点错误日志';
comment on column SYS_APP_ERROR_INFO.ID
	is '主键';
comment on column SYS_APP_ERROR_INFO.NODE_NAME
	is '节点名称（IP）';
comment on column SYS_APP_ERROR_INFO.APP_FLAG
	is '业务系统标识';
comment on column SYS_APP_ERROR_INFO.CREATE_TIME
	is '日志生成时间';
comment on column SYS_APP_ERROR_INFO.CONCENT
	is '内容';
alter table SYS_APP_ERROR_INFO
  add primary key (ID);
alter table SYS_APP_ERROR_INFO add FILE_NAME varchar2(200);

alter table SYS_APP_ERROR_INFO add LEVEL_SETUP_ID NUMBER(18);

alter table SYS_APP_ERROR_INFO add LOG_LEVEL INT;


CREATE TABLE SYS_APP_LEVEL_SETUP(
	ID				NUMBER(18) NOT NULL,
	LOG_LEVEL		INT		 NOT NULL,
	KEY_WORD		VARCHAR2(200) NOT NULL,
	EMAIL_FLAG		INT DEFAULT 0
);
COMMENT ON TABLE SYS_APP_LEVEL_SETUP 
	IS '错误级别设定表';
COMMENT ON COLUMN SYS_APP_LEVEL_SETUP.ID 
	IS '主键';
COMMENT ON COLUMN SYS_APP_LEVEL_SETUP.LOG_LEVEL 
	IS '级别类型';
COMMENT ON COLUMN SYS_APP_LEVEL_SETUP.KEY_WORD 
	IS '关键字';
COMMENT ON COLUMN SYS_APP_LEVEL_SETUP.EMAIL_FLAG 
	IS '邮件提醒标识';
alter table SYS_APP_LEVEL_SETUP
  add primary key (ID);
  
alter table SYS_APP_LEVEL_SETUP add RATE INT;

alter table  SYS_APP_LEVEL_SETUP add RATE_UNIT VARCHAR2(2);
	
	
CREATE TABLE SYS_APP_MANAGE_CONTACT_WAY(
	ID				NUMBER(18)	NOT NULL,
	APP_FLAG		VARCHAR2(10)	NOT NULL,
	EMAIL			VARCHAR2(100)	NOT NULL,
	TEL				VARCHAR2(18),
	MANAGE_NAME		VARCHAR2(100)
);
COMMENT ON TABLE SYS_APP_MANAGE_CONTACT_WAY 
	IS '系统管理者联系方式';
COMMENT ON COLUMN SYS_APP_MANAGE_CONTACT_WAY.ID	
	IS '主键';
COMMENT ON COLUMN SYS_APP_MANAGE_CONTACT_WAY.APP_FLAG	
	IS '系统标识';
COMMENT ON COLUMN SYS_APP_MANAGE_CONTACT_WAY.EMAIL	
	IS 'email地址';
COMMENT ON COLUMN SYS_APP_MANAGE_CONTACT_WAY.TEL	
	IS '手机号';
COMMENT ON COLUMN SYS_APP_MANAGE_CONTACT_WAY.MANAGE_NAME	
	IS '管理者名称';
alter table SYS_APP_MANAGE_CONTACT_WAY
  add primary key (ID);
alter table  SYS_APP_MANAGE_CONTACT_WAY add log_level int;
  
 -------------业务系统节点FTP配置表--------------
create sequence SEQ_SYS_APP_FTP_INFO 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_APP_ERROR_INFO 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;



create sequence SEQ_SYS_APP_LEVEL_SETUP 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_APP_MANAGE_CONTACT_WAY 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;