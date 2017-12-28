--prompt Dropping SYS_ACL...
drop table SYS_ACL cascade constraints;
--prompt Dropping SYS_CONFIG...
drop table SYS_CONFIG cascade constraints;
--prompt Dropping SYS_DICT...
drop table SYS_DICT cascade constraints;
--prompt Dropping SYS_DICT_DETAIL...
drop table SYS_DICT_DETAIL cascade constraints;
--prompt Dropping SYS_MENU...
drop table SYS_MENU cascade constraints;
--prompt Dropping SYS_ORG...
drop table SYS_ORG cascade constraints;
--prompt Dropping SYS_ORG_USER...
drop table SYS_ORG_USER cascade constraints;
--prompt Dropping SYS_POSITION...
drop table SYS_POSITION cascade constraints;
--prompt Dropping SYS_RESOURCE...
drop table SYS_RESOURCE cascade constraints;
--prompt Dropping SYS_ROLE...
drop table SYS_ROLE cascade constraints;
--prompt Dropping SYS_ROLE_USER...
drop table SYS_ROLE_USER cascade constraints;
--prompt Dropping SYS_USER...
drop table SYS_USER cascade constraints;
--prompt Dropping SYS_BIZ_LOG...
drop table SYS_BIZ_LOG cascade constraints;
------------------------数据权限开始-----------------------------------
drop table SYS_PRV_BIZ_USER cascade constraints;
drop table SYS_PRV_AUTH_RESULT cascade constraints;
drop table SYS_PRV_ORG_AUTH cascade constraints;
drop table SYS_PRV_ROLE cascade constraints;
drop table SYS_PRV_ROLE_AUTH cascade constraints;
drop table SYS_PRV_ROLE_RESOURCE cascade constraints;
drop table SYS_PRV_TABLE_DEF cascade constraints;
drop table SYS_PRV_USER_SHARE cascade constraints;
drop view sys_data_prv;
------------------------数据权限结束-----------------------------------
drop table SYS_AREA cascade constraints;

drop table SYS_VERSION cascade constraints;


drop table SYS_APP_FTP_INFO cascade constraints;
drop table SYS_APP_ERROR_INFO cascade constraints;
----------------------请假-------------------------------------------
drop table SYS_LEAVE_INFO cascade constraints;
