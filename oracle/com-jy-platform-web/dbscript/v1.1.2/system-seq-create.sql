--prompt
--prompt Creating sequence SEQ_SYS_ACL
--prompt =============================
--prompt
create sequence SEQ_SYS_ACL
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_CONFIG
--prompt ================================
--prompt
create sequence SEQ_SYS_CONFIG
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_DICT
--prompt ==============================
--prompt
create sequence SEQ_SYS_DICT
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_DICT_DETAIL
--prompt =====================================
--prompt
create sequence SEQ_SYS_DICT_DETAIL
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_MENU
--prompt ==============================
--prompt
create sequence SEQ_SYS_MENU
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_ORG
--prompt =============================
--prompt
create sequence SEQ_SYS_ORG
minvalue 2000000000     
maxvalue 999999999999999
start with 2000000000
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_ORG_USER
--prompt ==================================
--prompt
create sequence SEQ_SYS_ORG_USER
minvalue 20000
maxvalue 99999999
start with 20000
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_POSITION
--prompt ==================================
--prompt
create sequence SEQ_SYS_POSITION
minvalue 1000
maxvalue 99999999
start with 1000
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_RESOURCE
--prompt ==================================
--prompt
create sequence SEQ_SYS_RESOURCE
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_ROLE
--prompt ==============================
--prompt
create sequence SEQ_SYS_ROLE
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_ROLE_USER
--prompt ===================================
--prompt
create sequence SEQ_SYS_ROLE_USER
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_USER
--prompt ==============================
--prompt
create sequence SEQ_SYS_USER
minvalue 40000000
maxvalue 99999999
start with 40000000
increment by 1
cache 20;

--prompt
--prompt Creating sequence SEQ_SYS_USER
--prompt ==============================
--prompt
create sequence SEQ_SYS_BIZ_LOG
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;


------------------------数据权限开始-----------------------------------
create sequence SEQ_SYS_PRV_BIZ_USER
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_AUTH_RESULT
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_ORG_AUTH
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_ROLE
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_ROLE_AUTH
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_ROLE_RESOURCE
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_TABLE_DEF
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_PRV_USER_SHARE
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;
------------------------数据权限结束-----------------------------------
create sequence SEQ_SYS_AREA
minvalue 4000
maxvalue 99999999
start with 4000
increment by 1
cache 20;
---------------------日志拦截-------------------------------
create sequence SEQ_LOG_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
-------------------模版----------------------------------
create sequence SEQ_SYS_TEMPLATE
minvalue 1
maxvalue 99999999
start with 181
increment by 1
cache 20;
----------------行业名称/职位名称--------------------
create sequence SEQ_SYS_INDUSTRY
minvalue 1
maxvalue 999999999999999999999999999
start with 942
increment by 1
cache 20;

create sequence SEQ_SYS_ROLE_GROUP
minvalue 1
maxvalue 99999999
start with 102
increment by 1
cache 20;

--
-- Creating sequence SEQ_SYS_ROLE_GROUP_ROLE
-- =========================================
--
create sequence SEQ_SYS_ROLE_GROUP_ROLE
minvalue 1
maxvalue 99999999
start with 61
increment by 1
cache 20;

create sequence SEQ_SYS_VERSION 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;


create sequence SEQ_SYS_MESSAGE 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_USER_MSG_RELATION 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_SYS_LEAVE_INFO 
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;