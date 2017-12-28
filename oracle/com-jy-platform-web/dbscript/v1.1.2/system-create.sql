--prompt Creating SYS_ACL...
create table SYS_ACL
(
  ID             NUMBER(18) not null,
  ROLE_ID        NUMBER(18) not null,
  RESOURE_ID     NUMBER(18) not null,
  ACCESSIBILITY  NUMBER(1) not null,
  APP_ID         NUMBER(18) not null,
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_ACL
  is '操作权限控制表';
comment on column SYS_ACL.ID
  is '主键';
comment on column SYS_ACL.ROLE_ID
  is '角色ID';
comment on column SYS_ACL.RESOURE_ID
  is '资源ID';
comment on column SYS_ACL.ACCESSIBILITY
  is '1可访问，0拒绝访问';
comment on column SYS_ACL.APP_ID
  is '应用ID，备用';
comment on column SYS_ACL.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ACL.VERSION
  is '乐观锁';
alter table SYS_ACL
  add primary key (ID);

--prompt Creating SYS_CONFIG...
create table SYS_CONFIG
(
  ID             NUMBER(18) not null,
  CONFIG_NAME    VARCHAR2(100) not null,
  CONFIG_CODE    VARCHAR2(100) not null,
  CONFIG_VALUE   VARCHAR2(100) not null,
  CONFIG_TYPE    VARCHAR2(100) not null,
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_CONFIG
  is '系统配置表';
comment on column SYS_CONFIG.ID
  is '主键';
comment on column SYS_CONFIG.CONFIG_NAME
  is '配置名称';
comment on column SYS_CONFIG.CONFIG_CODE
  is '配置CODE';
comment on column SYS_CONFIG.CONFIG_VALUE
  is '配置值';
comment on column SYS_CONFIG.CONFIG_TYPE
  is '类型，0系统级（不可删除），1项目级';
comment on column SYS_CONFIG.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_CONFIG.VERSION
  is '乐观锁';
alter table SYS_CONFIG
  add primary key (ID);

--prompt Creating SYS_DICT...
create table SYS_DICT
(
  ID             NUMBER(18) not null,
  DICT_CODE      VARCHAR2(50) not null,
  DICT_NAME      VARCHAR2(50) not null,
  DICT_TYPE      VARCHAR2(50) not null,
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_DICT
  is '数据字典';
comment on column SYS_DICT.ID
  is '主键';
comment on column SYS_DICT.DICT_CODE
  is '数据字典code';
comment on column SYS_DICT.DICT_NAME
  is '数据字典名称';
comment on column SYS_DICT.DICT_TYPE
  is '数据字典类型，0系统级（不可删除），1项目级';
comment on column SYS_DICT.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_DICT.VERSION
  is '乐观锁';
alter table SYS_DICT
  add primary key (ID);

--prompt Creating SYS_DICT_DETAIL...
create table SYS_DICT_DETAIL
(
  ID                NUMBER(18) not null,
  DICT_ID           NUMBER(18) not null,
  DICT_DETAIL_NAME  VARCHAR2(100) not null,
  DICT_DETAIL_VALUE VARCHAR2(100) not null,
  ORDER_BY          VARCHAR2(50),
  VALIDATE_STATE    VARCHAR2(2) not null,
  VERSION           NUMBER(16)
)
;
comment on table SYS_DICT_DETAIL
  is '数据字典明细表';
comment on column SYS_DICT_DETAIL.ID
  is '主键';
comment on column SYS_DICT_DETAIL.DICT_ID
  is '主键字典主键';
comment on column SYS_DICT_DETAIL.DICT_DETAIL_NAME
  is '数据名称';
comment on column SYS_DICT_DETAIL.DICT_DETAIL_VALUE
  is '数据值';
comment on column SYS_DICT_DETAIL.ORDER_BY
  is '排序';
comment on column SYS_DICT_DETAIL.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_DICT_DETAIL.VERSION
  is '乐观锁';
alter table SYS_DICT_DETAIL
  add primary key (ID);

--prompt Creating SYS_MENU...
create table SYS_MENU
(
  ID             NUMBER(18) not null,
  MENU_CODE      VARCHAR2(50),
  MENU_NAME      VARCHAR2(50) not null,
  MENU_ICON      VARCHAR2(100),
  MENU_URL       VARCHAR2(100),
  PARENT_ID      VARCHAR2(50) not null,
  ORDER_BY       VARCHAR2(50),
  VALIDATE_STATE VARCHAR2(2) not null,
  APP_ID         NUMBER(18),
  VERSION        NUMBER(16)
)
;
comment on table SYS_MENU
  is '菜单管理表';
comment on column SYS_MENU.ID
  is '主键';
comment on column SYS_MENU.MENU_CODE
  is '菜单编码';
comment on column SYS_MENU.MENU_NAME
  is '菜单名称';
comment on column SYS_MENU.MENU_ICON
  is '菜单图标';
comment on column SYS_MENU.MENU_URL
  is '菜单URL';
comment on column SYS_MENU.PARENT_ID
  is '父菜单ID';
comment on column SYS_MENU.ORDER_BY
  is '排序';
comment on column SYS_MENU.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_MENU.APP_ID
  is '系统ID，备用';
comment on column SYS_MENU.VERSION
  is '乐观锁';
alter table SYS_MENU
  add primary key (ID);

alter table SYS_MENU add RESOURCE_ID  NUMBER(22);
comment on column  SYS_MENU.RESOURCE_ID is '资源id';

--prompt Creating SYS_ORG...
create table SYS_ORG
(
  ID             NUMBER(18) not null,
  ORG_CODE       VARCHAR2(100),
  ORG_NAME       VARCHAR2(100) not null,
  ORG_TYPE       VARCHAR2(10) not null,
  PARENT_ID      VARCHAR2(100) not null,
  PARENT_IDS     VARCHAR2(1000),
  ORDER_BY       VARCHAR2(50),
  VALIDATE_STATE VARCHAR2(2) not null,
  IS_VIRTUAL     VARCHAR2(1) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_ORG
  is '机构部门表';
comment on column SYS_ORG.ID
  is '机构ID';
comment on column SYS_ORG.ORG_CODE
  is '机构编码';
comment on column SYS_ORG.ORG_NAME
  is '机构名称';
comment on column SYS_ORG.ORG_TYPE
  is '机构类型：org组织，dept部门';
comment on column SYS_ORG.PARENT_ID
  is '父机构ID';
comment on column SYS_ORG.PARENT_IDS
  is '父机构ID串，以/分割';
comment on column SYS_ORG.ORDER_BY
  is '排序';
comment on column SYS_ORG.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG.IS_VIRTUAL
  is '是否是虚拟组织，1是，0否，默认0';
comment on column SYS_ORG.VERSION
  is '乐观锁';
alter table SYS_ORG
  add primary key (ID);
  
alter table SYS_ORG add APP_FLAG  varchar2(10);
comment on column  SYS_ORG.APP_FLAG is '应用标志';

alter table SYS_ORG add IS_LEEF varchar2(1);
comment on column SYS_ORG.IS_LEEF is '是否叶子节点';

alter table SYS_ORG add AREA_CODES VARCHAR2(50);
comment on column SYS_ORG.AREA_CODES is '区域编码';

alter table SYS_ORG add AREA_ADRESS VARCHAR2(50);
comment on column SYS_ORG.AREA_ADRESS is '区域名称';

--prompt Creating SYS_ORG_USER...
create table SYS_ORG_USER
(
  ID             NUMBER(18) not null,
  ORG_ID         NUMBER(18) not null,
  USER_ID        NUMBER(18) not null,
  POSITION_ID    NUMBER(18),
  IS_MAIN_ORG    VARCHAR2(1) not null,
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on column SYS_ORG_USER.ID
  is '主键';
comment on column SYS_ORG_USER.ORG_ID
  is '机构ID';
comment on column SYS_ORG_USER.USER_ID
  is '用户ID';
comment on column SYS_ORG_USER.POSITION_ID
  is '岗位ID';
comment on column SYS_ORG_USER.IS_MAIN_ORG
  is '是否主部门，1是（主部门），0否（兼职部门），默认1';
comment on column SYS_ORG_USER.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG_USER.VERSION
  is '乐观锁';
alter table SYS_ORG_USER
  add primary key (ID);

--prompt Creating SYS_POSITION...
create table SYS_POSITION
(
  ID             NUMBER(18) not null,
  POSITION_NAME  VARCHAR2(50) not null,
  POSITION_CODE  VARCHAR2(50),
  ORDER_BY       VARCHAR2(50),
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_POSITION
  is '岗位定义表';
comment on column SYS_POSITION.ID
  is '主键';
comment on column SYS_POSITION.POSITION_NAME
  is '岗位名称';
comment on column SYS_POSITION.POSITION_CODE
  is '岗位编码';
comment on column SYS_POSITION.ORDER_BY
  is '排序';
comment on column SYS_POSITION.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_POSITION.VERSION
  is '乐观锁';
alter table SYS_POSITION
  add primary key (ID);

--prompt Creating SYS_RESOURCE...
create table SYS_RESOURCE
(
  ID             NUMBER(18) not null,
  RESOURE_NAME   VARCHAR2(200) not null,
  RESOURE_TYPE   VARCHAR2(50) not null,
  RESOURE_URL    VARCHAR2(200),
  PERMISSION     VARCHAR2(200),
  PARENT_ID      VARCHAR2(50) not null,
  PARENT_IDS     VARCHAR2(500),
  APP_ID         NUMBER(18),
  VALIDATE_STATE VARCHAR2(2),
  VERSION        NUMBER(16) not null
)
;
comment on table SYS_RESOURCE
  is '资源管理表';
comment on column SYS_RESOURCE.ID
  is '主键';
comment on column SYS_RESOURCE.RESOURE_NAME
  is '名称';
comment on column SYS_RESOURCE.RESOURE_TYPE
  is '类型，url或button';
comment on column SYS_RESOURCE.RESOURE_URL
  is 'URL地址';
comment on column SYS_RESOURCE.PERMISSION
  is '权限标识';
comment on column SYS_RESOURCE.PARENT_ID
  is '父ID';
comment on column SYS_RESOURCE.PARENT_IDS
  is '父ID串，以/分割';
comment on column SYS_RESOURCE.APP_ID
  is '应用ID，备用字段';
comment on column SYS_RESOURCE.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_RESOURCE.VERSION
  is '乐观锁';
alter table SYS_RESOURCE
  add primary key (ID);

--prompt Creating SYS_ROLE...
create table SYS_ROLE
(
  ID             NUMBER(18) not null,
  ROLE_NAME      VARCHAR2(50) not null,
  ROLE_CODE      VARCHAR2(50),
  ROLE_TYPE      VARCHAR2(1) not null,
  APP_ID         NUMBER(18),
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_ROLE
  is '角色表';
comment on column SYS_ROLE.ID
  is '主键';
comment on column SYS_ROLE.ROLE_NAME
  is '角色名称';
comment on column SYS_ROLE.ROLE_CODE
  is '角色编码';
comment on column SYS_ROLE.ROLE_TYPE
  is '角色类型：0系统角色（不能删除），1项目自定义角色';
comment on column SYS_ROLE.APP_ID
  is '系统ID，备用';
comment on column SYS_ROLE.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ROLE.VERSION
  is '乐观锁';
alter table SYS_ROLE
  add primary key (ID);

--prompt Creating SYS_ROLE_USER...
create table SYS_ROLE_USER
(
  ID             NUMBER(18) not null,
  ROLE_ID        NUMBER(18) not null,
  TARGET_ID      NUMBER(18),
  APP_ID         NUMBER(18),
  TARGET_TYPE    VARCHAR2(50) not null,
  VALIDATE_STATE VARCHAR2(2) not null,
  VERSION        NUMBER(16)
)
;
comment on table SYS_ROLE_USER
  is '角色用户表';
comment on column SYS_ROLE_USER.ID
  is '主键';
comment on column SYS_ROLE_USER.ROLE_ID
  is '角色ID';
comment on column SYS_ROLE_USER.TARGET_ID
  is '用户ID或者机构ID';
comment on column SYS_ROLE_USER.APP_ID
  is '系统ID，备用';
comment on column SYS_ROLE_USER.TARGET_TYPE
  is 'user用户类型,org机构类型';
comment on column SYS_ROLE_USER.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ROLE_USER.VERSION
  is '乐观锁';
alter table SYS_ROLE_USER
  add primary key (ID);

--prompt Creating SYS_USER...
create table SYS_USER
(
  ID             NUMBER(18) not null,
  USER_NAME      VARCHAR2(50) not null,
  USER_NO        VARCHAR2(50),
  LOGIN_NAME     VARCHAR2(50),
  PASSWORD       VARCHAR2(50),
  SALT           VARCHAR2(50),
  MOBILE         VARCHAR2(50),
  EMAIL          VARCHAR2(50),
  USER_IMAGE     VARCHAR2(100),
  SEX            VARCHAR2(50),
  BIRTHDAY       VARCHAR2(50),
  NATIONALITY    VARCHAR2(50),
  EDUCATION      VARCHAR2(50),
  JOB            VARCHAR2(100),
  HOME_ADDRESS   VARCHAR2(100),
  HOME_ZIPCODE   VARCHAR2(50),
  HOME_TEL       VARCHAR2(50),
  OFFICE_TEL     VARCHAR2(50),
  OFFICE_ADDRESS VARCHAR2(100),
  ORDER_BY       VARCHAR2(50),
  VALIDATE_STATE VARCHAR2(2) not null,
  IS_LOCKED      VARCHAR2(2),
  VERSION        NUMBER(16)
)
;
comment on table SYS_USER
  is '系统用户表';
comment on column SYS_USER.ID
  is '主键';
comment on column SYS_USER.USER_NAME
  is '姓名';
comment on column SYS_USER.USER_NO
  is '编码';
comment on column SYS_USER.LOGIN_NAME
  is '登录名';
comment on column SYS_USER.PASSWORD
  is '登录密码';
comment on column SYS_USER.SALT
  is '盐值';
comment on column SYS_USER.MOBILE
  is '手机';
comment on column SYS_USER.EMAIL
  is '邮件';
comment on column SYS_USER.USER_IMAGE
  is '用户头像';
comment on column SYS_USER.SEX
  is '性别';
comment on column SYS_USER.BIRTHDAY
  is '出生日期';
comment on column SYS_USER.NATIONALITY
  is '民族';
comment on column SYS_USER.EDUCATION
  is '学历';
comment on column SYS_USER.JOB
  is '职务';
comment on column SYS_USER.HOME_ADDRESS
  is '家庭住址';
comment on column SYS_USER.HOME_ZIPCODE
  is '家庭邮编';
comment on column SYS_USER.HOME_TEL
  is '家庭电话';
comment on column SYS_USER.OFFICE_TEL
  is '办公电话';
comment on column SYS_USER.OFFICE_ADDRESS
  is '办公地址';
comment on column SYS_USER.ORDER_BY
  is '排序';
comment on column SYS_USER.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_USER.IS_LOCKED
  is '是否锁定，1锁定，0未锁，默认0';
comment on column SYS_USER.VERSION
  is '乐观锁';
alter table SYS_USER
  add primary key (ID);
create unique index IDX_SYS_USER_LOGIN_NAME on SYS_USER (LOGIN_NAME);

-- Create table
create table SYS_BIZ_LOG
(
  ID             NUMBER(18) not null,
  CLIENT_IP      VARCHAR2(50),
  USER_ID        NUMBER(18) not null,
  USER_NAME      VARCHAR2(50),
  LOG_CONTENT    VARCHAR2(4000) not null,
  LOG_TIME       DATE not null,
  LOG_TYPE       VARCHAR2(50),
  LOG_MODULE     VARCHAR2(50),
  LOG_OPERATE    VARCHAR2(50),
  VALIDATE_STATE VARCHAR2(2),
  IS_ARCHIVE     VARCHAR2(2)
);
-- Add comments to the table 
comment on table SYS_BIZ_LOG
  is '业务日志表';
-- Add comments to the columns 
comment on column SYS_BIZ_LOG.ID
  is '主键';
comment on column SYS_BIZ_LOG.CLIENT_IP
  is '客户端IP';
comment on column SYS_BIZ_LOG.USER_ID
  is '操作人ID';
comment on column SYS_BIZ_LOG.LOG_CONTENT
  is '日志内容';
comment on column SYS_BIZ_LOG.LOG_TIME
  is '插入时间';
comment on column SYS_BIZ_LOG.LOG_TYPE
  is '日志类型';
comment on column SYS_BIZ_LOG.LOG_MODULE
  is '所属模块';
comment on column SYS_BIZ_LOG.LOG_OPERATE
  is '操作类型';
comment on column SYS_BIZ_LOG.VALIDATE_STATE
  is '有效性，1有效，0无效，默认1';
comment on column SYS_BIZ_LOG.USER_NAME
  is '操作人姓名';
comment on column SYS_BIZ_LOG.IS_ARCHIVE
  is '是否归档，1已归档，0未归档';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_BIZ_LOG
  add constraint PK_SYS_BIZ_LOG primary key (ID);
  
-----------------------数据权限开始-----------------------------------------------
--生产环境分区表要创建独立表空间
--create tablespace SYS_PRV_BIZ_USER_space01 datafile '&1\SYS_PRV_BIZ_USER_space01.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space02 datafile '&1\SYS_PRV_BIZ_USER_space02.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space03 datafile '&1\SYS_PRV_BIZ_USER_space03.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space04 datafile '&1\SYS_PRV_BIZ_USER_space04.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space05 datafile '&1\SYS_PRV_BIZ_USER_space05.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space06 datafile '&1\SYS_PRV_BIZ_USER_space06.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space07 datafile '&1\SYS_PRV_BIZ_USER_space07.dbf ' size 100m autoextend on next 10m;
--create tablespace SYS_PRV_BIZ_USER_space08 datafile '&1\SYS_PRV_BIZ_USER_space08.dbf ' size 100m autoextend on next 10m;
-- Create table
create table SYS_PRV_BIZ_USER
(
  ID             NUMBER(18) not null,
  USER_ID        NUMBER(18) not null,
  ORG_ID         NUMBER(18) not null,
  BIZ_ID         NUMBER(18) not null,
  TABLE_NAME     VARCHAR2(50) not null,
  ACTION_STATE   VARCHAR2(2) not null,
  SYN_STATE      VARCHAR2(2) not null,
  VALIDATE_STATE VARCHAR2(2) not null
)
partition by hash(TABLE_NAME);  
--(  
--       partition part_01 tablespace SYS_PRV_BIZ_USER_space01,  
--       partition part_02 tablespace SYS_PRV_BIZ_USER_space02,  
--       partition part_03 tablespace SYS_PRV_BIZ_USER_space03,  
--       partition part_04 tablespace SYS_PRV_BIZ_USER_space04,
--       partition part_05 tablespace SYS_PRV_BIZ_USER_space05, 
--       partition part_06 tablespace SYS_PRV_BIZ_USER_space06, 
--       partition part_07 tablespace SYS_PRV_BIZ_USER_space07, 
--       partition part_08 tablespace SYS_PRV_BIZ_USER_space08,   
--);  
-- Add comments to the table 
comment on table SYS_PRV_BIZ_USER
  is '业务数据用户权限表';
-- Add comments to the columns 
comment on column SYS_PRV_BIZ_USER.ID
  is '主键';
comment on column SYS_PRV_BIZ_USER.USER_ID
  is '用户ID';
comment on column SYS_PRV_BIZ_USER.ORG_ID
  is '机构ID';
comment on column SYS_PRV_BIZ_USER.BIZ_ID
  is '业务ID';
comment on column SYS_PRV_BIZ_USER.TABLE_NAME
  is '业务表名';
comment on column SYS_PRV_BIZ_USER.ACTION_STATE
  is '操作状态，插入I，删除D';
comment on column SYS_PRV_BIZ_USER.SYN_STATE
  is '同步状态，未同步0，已同步1';
comment on column SYS_PRV_BIZ_USER.VALIDATE_STATE
  is '有效状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_BIZ_USER
  add constraint PK_SYS_PRV_BIZ_USER primary key (ID);


-- Create table
create table SYS_PRV_AUTH_RESULT
(
  ID             NUMBER(18) not null,
  USER_ID_FROM   NUMBER(18) not null,
  USER_ID_TO     NUMBER(18) not null,
  VALIDATE_STATE VARCHAR2(2)
);
-- Add comments to the table 
comment on table SYS_PRV_AUTH_RESULT
  is '数据权限授权结果表';
-- Add comments to the columns 
comment on column SYS_PRV_AUTH_RESULT.ID
  is '主键';
comment on column SYS_PRV_AUTH_RESULT.USER_ID_FROM
  is '授权用户ID';
comment on column SYS_PRV_AUTH_RESULT.USER_ID_TO
  is '被授权用户ID';
comment on column SYS_PRV_AUTH_RESULT.VALIDATE_STATE
  is '有效性';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_AUTH_RESULT
  add constraint PK_SYS_PRV_AUTH_RESULT primary key (ID);

alter table SYS_PRV_AUTH_RESULT add insert_from varchar2(18);
comment on column SYS_PRV_AUTH_RESULT.insert_from
  is '数据来源是哪个表。S是用户共享表，O是组织机构表，R是角色表。多个来源以'',''分开';
  
  -- Create table
create table SYS_PRV_ORG_AUTH
(
  ID             NUMBER(18) not null,
  USER_ID        NUMBER(18) not null,
  ORG_ID         NUMBER(18) not null,
  ACTION_STATE   VARCHAR2(2) not null,
  SYN_STATE      VARCHAR2(2) not null,
  VALIDATE_STATE VARCHAR2(2) not null
);
-- Add comments to the table 
comment on table SYS_PRV_ORG_AUTH
  is '组织授权表';
-- Add comments to the columns 
comment on column SYS_PRV_ORG_AUTH.ID
  is '主键';
comment on column SYS_PRV_ORG_AUTH.USER_ID
  is '用户ID';
comment on column SYS_PRV_ORG_AUTH.ORG_ID
  is '组织ID';
comment on column SYS_PRV_ORG_AUTH.ACTION_STATE
  is '操作状态，插入I，删除D';
comment on column SYS_PRV_ORG_AUTH.SYN_STATE
  is '同步状态，未同步0，已同步1';
comment on column SYS_PRV_ORG_AUTH.VALIDATE_STATE
  is '有效状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_ORG_AUTH
  add constraint PK_SYS_PRV_ORG_AUTH primary key (ID);
  
  
 -- Create table
create table SYS_PRV_ROLE
(
  ID             NUMBER(18) not null,
  ROLE_CODE      VARCHAR2(50) not null,
  ROLE_NAME      VARCHAR2(50) not null,
  VALIDATE_STATE VARCHAR2(2) not null
);
-- Add comments to the table 
comment on table SYS_PRV_ROLE
  is '数据权限角色定义';
-- Add comments to the columns 
comment on column SYS_PRV_ROLE.ID
  is '主键';
comment on column SYS_PRV_ROLE.ROLE_CODE
  is '角色CODE';
comment on column SYS_PRV_ROLE.ROLE_NAME
  is '角色名称';
comment on column SYS_PRV_ROLE.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_ROLE
  add constraint PK_SYS_PRV_ROLE primary key (ID);
 
-- Create table
create table SYS_PRV_ROLE_AUTH
(
  ID             NUMBER(18) not null,
  USER_ID        NUMBER(18) not null,
  ROLE_ID        NUMBER(18) not null,
  ACTION_STATE   VARCHAR2(2) not null,
  SYN_STATE      VARCHAR2(2) not null,
  VALIDATE_STATE VARCHAR2(2) not null
);
-- Add comments to the table 
comment on table SYS_PRV_ROLE_AUTH
  is '数据权限角色授权表';
-- Add comments to the columns 
comment on column SYS_PRV_ROLE_AUTH.ID
  is '主键';
comment on column SYS_PRV_ROLE_AUTH.USER_ID
  is '用户ID';
comment on column SYS_PRV_ROLE_AUTH.ROLE_ID
  is '角色ID';
comment on column SYS_PRV_ROLE_AUTH.ACTION_STATE
  is '操作状态，插入I，删除D';
comment on column SYS_PRV_ROLE_AUTH.SYN_STATE
  is '同步状态，未同步0，已同步1';
comment on column SYS_PRV_ROLE_AUTH.VALIDATE_STATE
  is '有效状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_ROLE_AUTH
  add constraint PK_SYS_PRV_ROLE_AUTH primary key (ID);


-- Create table
create table SYS_PRV_ROLE_RESOURCE
(
  ID             NUMBER(18) not null,
  ROLE_ID        NUMBER(18),
  RESOURCE_ID    NUMBER(18),
  RESOURCE_TYPE  VARCHAR2(10),
  ACTION_STATE   VARCHAR2(2),
  SYN_STATE      VARCHAR2(2),
  VALIDATE_STATE VARCHAR2(2)
);
-- Add comments to the table 
comment on table SYS_PRV_ROLE_RESOURCE
  is '数据角色资源表';
-- Add comments to the columns 
comment on column SYS_PRV_ROLE_RESOURCE.ID
  is '主键';
comment on column SYS_PRV_ROLE_RESOURCE.ROLE_ID
  is '角色Id';
comment on column SYS_PRV_ROLE_RESOURCE.RESOURCE_ID
  is '资源ID';
comment on column SYS_PRV_ROLE_RESOURCE.RESOURCE_TYPE
  is '资源类型，机构org，用户user';
comment on column SYS_PRV_ROLE_RESOURCE.ACTION_STATE
  is '操作状态，插入I，删除D';
comment on column SYS_PRV_ROLE_RESOURCE.SYN_STATE
  is '同步状态，未同步0，已同步1';
comment on column SYS_PRV_ROLE_RESOURCE.VALIDATE_STATE
  is '有效状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_ROLE_RESOURCE
  add constraint PK_SYS_PRV_ROLE_RESOURCE primary key (ID);


-- Create table
create table SYS_PRV_TABLE_DEF
(
  ID             NUMBER(18) not null,
  TABLE_NAME     VARCHAR2(50) not null,
  TABLE_DESC     VARCHAR2(500),
  VALIDATE_STATE VARCHAR2(2) not null
);
-- Add comments to the table 
comment on table SYS_PRV_TABLE_DEF
  is '数据权限表定义';
-- Add comments to the columns 
comment on column SYS_PRV_TABLE_DEF.ID
  is '主键';
comment on column SYS_PRV_TABLE_DEF.TABLE_NAME
  is '表名称';
comment on column SYS_PRV_TABLE_DEF.TABLE_DESC
  is '描述';
comment on column SYS_PRV_TABLE_DEF.VALIDATE_STATE
  is '有效性，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_TABLE_DEF
  add constraint PK_SYS_DATA_PRV_TABLE primary key (ID);
alter table SYS_PRV_TABLE_DEF
  add constraint UNI_SYS_DATA_PRV_TABLE unique (TABLE_NAME);


-- Create table
create table SYS_PRV_USER_SHARE
(
  ID             NUMBER(18) not null,
  FROM_USER_ID   NUMBER(18) not null,
  TO_USER_ID     NUMBER(18) not null,
  ACTION_STATE   VARCHAR2(2) not null,
  SYN_STATE      VARCHAR2(2) not null,
  VALIDATE_STATE VARCHAR2(2) not null
);
-- Add comments to the table 
comment on table SYS_PRV_USER_SHARE
  is '数据共享表';
-- Add comments to the columns 
comment on column SYS_PRV_USER_SHARE.ID
  is '主键';
comment on column SYS_PRV_USER_SHARE.FROM_USER_ID
  is '共享用户ID';
comment on column SYS_PRV_USER_SHARE.TO_USER_ID
  is '被共享用户ID';
comment on column SYS_PRV_USER_SHARE.ACTION_STATE
  is '操作状态，插入I，删除D';
comment on column SYS_PRV_USER_SHARE.SYN_STATE
  is '同步状态，未同步0，已同步1';
comment on column SYS_PRV_USER_SHARE.VALIDATE_STATE
  is '有效状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_PRV_USER_SHARE
  add constraint PK_SYS_PRV_USER_SHARE primary key (ID);
  
create view sys_data_prv as select  t1.table_name,t1.biz_id,t1.user_id as create_user_id,t2.user_id_from as auth_user_id from SYS_PRV_BIZ_USER t1, SYS_PRV_AUTH_RESULT  t2 where t2.user_id_to=t1.user_id;
--查询权限语句
--select * from sys_data_prv t where t.auth_user_id=1 and t.table_name='A'
------------------------数据权限结束-----------------------------------
create table SYS_AREA
(
  ID             NUMBER(18) not null,
  AREA_CODE      VARCHAR2(50) not null,
  AREA_NAME      VARCHAR2(100) not null,
  PARENT_ID      NUMBER(18) not null,
  VALIDATE_STATE VARCHAR2(1)
);
-- Add comments to the table 
comment on table SYS_AREA
  is '行政区域';
-- Add comments to the columns 
comment on column SYS_AREA.ID
  is '主键';
comment on column SYS_AREA.AREA_CODE
  is '区域编码';
comment on column SYS_AREA.AREA_NAME
  is '区域名称';
comment on column SYS_AREA.PARENT_ID
  is '父节点ID';
comment on column SYS_AREA.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_AREA
  add constraint PK_SYS_AREA primary key (ID);


--日志拦截数据库表
create table LOG_INFO  (
   ID                   NUMBER                          not null,
   USER_ID              VARCHAR2(50)                    not null,
   MODULE_NAME          VARCHAR2(100)                   not null,
   CONTROLLER_NAME      VARCHAR2(100),
   METHOD_NAME          VARCHAR2(100),
   PARAM_INFO           VARCHAR2(1000),
   CREATED              DATE,
   URI                  VARCHAR2(200),
   constraint PK_LOG_INFO primary key (ID)
);

comment on column LOG_INFO.ID is
'日志ID';

comment on column LOG_INFO.USER_ID is
'用户ID';

comment on column LOG_INFO.MODULE_NAME is
'模块名称';

comment on column LOG_INFO.CONTROLLER_NAME is
'Controller名称';

comment on column LOG_INFO.METHOD_NAME is
'方法名称';

comment on column LOG_INFO.PARAM_INFO is
'参数信息';

comment on column LOG_INFO.CREATED is
'生成日期';

comment on column LOG_INFO.URI is
'请求地址';
alter table LOG_INFO add log_type varchar2(1);
comment on column LOG_INFO.URI is
'日志类型0：访问日志，1：系统日志';


CREATE TABLE SYS_USER_MSG_RELATION_HIS (
	REL_ID 		NUMBER(20) NOT NULL ,
	REL_STATUS 	CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	USER_ID 	VARCHAR2(10 BYTE) NOT NULL ,
	READ_FLAG 	CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	MSG_ID 		NUMBER(20) NOT NULL 
);
COMMENT ON COLUMN SYS_USER_MSG_RELATION_HIS.REL_ID IS '主键';
COMMENT ON COLUMN SYS_USER_MSG_RELATION_HIS.REL_STATUS IS '信息状态：0：有效1：用户删除；default0';
COMMENT ON COLUMN SYS_USER_MSG_RELATION_HIS.USER_ID IS '归属者ID';
COMMENT ON COLUMN SYS_USER_MSG_RELATION_HIS.READ_FLAG IS '用户读取状态：0:未读；1：已读 default:0';
COMMENT ON COLUMN SYS_USER_MSG_RELATION_HIS.MSG_ID IS '消息ID';

ALTER TABLE SYS_USER_MSG_RELATION_HIS ADD CHECK (REL_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION_HIS ADD CHECK (REL_STATUS IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION_HIS ADD CHECK (USER_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION_HIS ADD CHECK (READ_FLAG IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION_HIS ADD CHECK (MSG_ID IS NOT NULL);


CREATE TABLE SYS_MESSAGE (
	MSG_ID 		NUMBER(20) NOT NULL ,
	TITLE 		VARCHAR2(100 BYTE) NOT NULL ,
	BODY 		VARCHAR2(1000 BYTE) NOT NULL ,
	URL 		VARCHAR2(300 BYTE) NULL ,
	CREATE_DATE DATE NOT NULL ,
	START_DATE 	DATE NULL ,
	END_DATE 	DATE NULL ,
	STATUS 		CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	PUBLISHER 	VARCHAR2(10 BYTE) NOT NULL ,
	TYPE 		CHAR(1 BYTE) NOT NULL ,
	CHARSET 	VARCHAR2(10 BYTE) DEFAULT 'UTF-8'  NULL ,
	URGENT_FLAG CHAR(1 BYTE) NULL ,
	SYS_FLAG 	VARCHAR2(6 BYTE) NULL 
);
COMMENT ON COLUMN SYS_MESSAGE.MSG_ID IS '唯一标识，主键';
COMMENT ON COLUMN SYS_MESSAGE.TITLE IS '消息标题';
COMMENT ON COLUMN SYS_MESSAGE.BODY IS '消息体';
COMMENT ON COLUMN SYS_MESSAGE.URL IS '消息链接，可为空';
COMMENT ON COLUMN SYS_MESSAGE.CREATE_DATE IS '消息创建日期';
COMMENT ON COLUMN SYS_MESSAGE.START_DATE IS '消息生效日期';
COMMENT ON COLUMN SYS_MESSAGE.END_DATE IS '消息失效日期';
COMMENT ON COLUMN SYS_MESSAGE.STATUS IS '信息状态 0：初始；1：生效；2：失效；3：删除；default：0';
COMMENT ON COLUMN SYS_MESSAGE.PUBLISHER IS '消息发布者ID';
COMMENT ON COLUMN SYS_MESSAGE.TYPE IS '信息的类型 0：全局消息；1：专有消息';
COMMENT ON COLUMN SYS_MESSAGE.CHARSET IS '信息体的编码集';
COMMENT ON COLUMN SYS_MESSAGE.URGENT_FLAG IS '紧急标识 0:普通；1：紧急；2：特急 default:0';
COMMENT ON COLUMN SYS_MESSAGE.SYS_FLAG IS '标识归属于哪个系统；可取值范围：7/1/2/4/5/6/3，取值规则：type为0时该字段为111；001标识贷前；010标识贷后；100标识财富；以此类推101标识贷前和财富';


ALTER TABLE SYS_MESSAGE ADD CHECK (MSG_ID IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (TITLE IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (BODY IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (CREATE_DATE IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (STATUS IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (PUBLISHER IS NOT NULL);
ALTER TABLE SYS_MESSAGE ADD CHECK (TYPE IS NOT NULL);

ALTER TABLE SYS_MESSAGE ADD PRIMARY KEY (MSG_ID);
  
  
  

CREATE TABLE SYS_MESSAGE_HIS (
	MSG_ID 		NUMBER(20) NOT NULL ,
	TITLE 		VARCHAR2(100 BYTE) NOT NULL ,
	BODY 		VARCHAR2(1000 BYTE) NOT NULL ,
	URL 		VARCHAR2(300 BYTE) NULL ,
	CREATE_DATE DATE NOT NULL ,
	START_DATE 	DATE NULL ,
	END_DATE 	DATE NULL ,
	STATUS 		CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	PUBLISHER 	VARCHAR2(10 BYTE) NOT NULL ,
	TYPE 		CHAR(1 BYTE) NOT NULL ,
	CHARSET 	VARCHAR2(10 BYTE) DEFAULT 'UTF-8'  NULL ,
	URGENT_FLAG CHAR(1 BYTE) NULL ,
	SYS_FLAG 	VARCHAR2(6 BYTE) NULL 
)
;
COMMENT ON COLUMN SYS_MESSAGE_HIS.MSG_ID IS '唯一标识，主键';
COMMENT ON COLUMN SYS_MESSAGE_HIS.TITLE IS '消息标题';
COMMENT ON COLUMN SYS_MESSAGE_HIS.BODY IS '消息体';
COMMENT ON COLUMN SYS_MESSAGE_HIS.URL IS '消息链接，可为空';
COMMENT ON COLUMN SYS_MESSAGE_HIS.CREATE_DATE IS '消息创建日期';
COMMENT ON COLUMN SYS_MESSAGE_HIS.START_DATE IS '消息生效日期';
COMMENT ON COLUMN SYS_MESSAGE_HIS.END_DATE IS '消息失效日期';
COMMENT ON COLUMN SYS_MESSAGE_HIS.STATUS IS '信息状态 0：初始；1：生效；2：失效；3：删除；default：0';
COMMENT ON COLUMN SYS_MESSAGE_HIS.PUBLISHER IS '消息发布者ID';
COMMENT ON COLUMN SYS_MESSAGE_HIS.TYPE IS '信息的类型 0：全局消息；1：专有消息';
COMMENT ON COLUMN SYS_MESSAGE_HIS.CHARSET IS '信息体的编码集';
COMMENT ON COLUMN SYS_MESSAGE_HIS.URGENT_FLAG IS '紧急标识 0:普通；1：紧急；2：特急 default:0';
COMMENT ON COLUMN SYS_MESSAGE_HIS.SYS_FLAG IS '标识归属于哪个系统；可取值范围：7/1/2/4/5/6/3，取值规则：type为0时该字段为111；001标识贷前；010标识贷后；100标识财富；以此类推101标识贷前和财富';

-- ----------------------------
-- Checks structure for table SYS_MESSAGE_HIS
-- ----------------------------
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (MSG_ID IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (TITLE IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (BODY IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (CREATE_DATE IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (STATUS IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (PUBLISHER IS NOT NULL);
ALTER TABLE SYS_MESSAGE_HIS ADD CHECK (TYPE IS NOT NULL);



CREATE TABLE SYS_USER_MSG_RELATION (
	REL_ID 		NUMBER(20) NOT NULL ,
	REL_STATUS 	CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	USER_ID 	VARCHAR2(10 BYTE) NOT NULL ,
	READ_FLAG 	CHAR(1 BYTE) DEFAULT 0  NOT NULL ,
	MSG_ID 		NUMBER(20) NOT NULL 
);
COMMENT ON COLUMN SYS_USER_MSG_RELATION.REL_ID IS '主键';
COMMENT ON COLUMN SYS_USER_MSG_RELATION.REL_STATUS IS '信息状态：0：有效1：用户删除；default0';
COMMENT ON COLUMN SYS_USER_MSG_RELATION.USER_ID IS '归属者ID';
COMMENT ON COLUMN SYS_USER_MSG_RELATION.READ_FLAG IS '用户读取状态：0:未读；1：已读 default:0';
COMMENT ON COLUMN SYS_USER_MSG_RELATION.MSG_ID IS '消息ID';

-- ----------------------------
-- Checks structure for table SYS_USER_MSG_RELATION
-- ----------------------------
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (REL_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (REL_STATUS IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (USER_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (READ_FLAG IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (MSG_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (REL_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (REL_STATUS IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (USER_ID IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (READ_FLAG IS NOT NULL);
ALTER TABLE SYS_USER_MSG_RELATION ADD CHECK (MSG_ID IS NOT NULL);




create table vmauth_register_info
(
  ID                NUMBER(18) not null primary key ,
  vmtree_code       VARCHAR2(50) not null,
  vmtree_name       VARCHAR2(100) not null,
  map_tab_name      varchar2(100) not null,
  data_tab_name     varchar2(100) not null,
  map_init_sql      varchar2(4000) not null,
  data_init_sql     varchar2(4000) not null,
  CREATE_TIME             TIMESTAMP(6),
  CREATE_BY               NUMBER(18)
);
-- Add comments to the table 
comment on table vmauth_register_info
  is '权限注册表';
-- Add comments to the columns 
comment on column vmauth_register_info.ID
  is '主键';
comment on column vmauth_register_info.vmtree_code
  is '虚拟树代码';
comment on column vmauth_register_info.vmtree_name
  is '虚拟树名称';
comment on column vmauth_register_info.map_tab_name
  is '映射表名';
comment on column vmauth_register_info.data_tab_name
  is '数据权限表名';
comment on column vmauth_register_info.map_init_sql
  is '映射表创建SQL'; 
comment on column vmauth_register_info.data_init_sql
  is '数据权限表创建SQL'; 
  comment on column vmauth_register_info.CREATE_TIME
  is '创建时间'; 
  comment on column vmauth_register_info.CREATE_BY
  is '创建人'; 
  
create sequence seq_vmauth_register_info start with 1 increment by 1;

create table vmtree_info
(
  ORG_ID                NUMBER(18) not null primary key ,
  Org_Name         VARCHAR2(200) not null,
  Org_Type         VARCHAR2(50) not null,
  Parent_ID        NUMBER(18) not null,
  Source_Type     varchar2(10),
  End_Flag        varchar2(2),
  CREATE_TIME             TIMESTAMP(6),
  CREATE_BY               NUMBER(18)
);
-- Add comments to the table 
comment on table vmtree_info
  is '虚拟树表';
-- Add comments to the columns 
comment on column vmtree_info.ORG_ID
  is '序列或HR的OrgID';
comment on column vmtree_info.Org_Name
  is '机构名称';
comment on column vmtree_info.Org_Type
  is '虚拟树代码';
comment on column vmtree_info.Parent_ID
  is '父节点ORG_ID';
comment on column vmtree_info.Source_Type
  is '数据来源（XN/HR）';
comment on column vmtree_info.End_Flag
  is '是否叶子节点（1：是，0：否）'; 
  comment on column vmtree_info.CREATE_TIME
  is '创建时间'; 
  comment on column vmtree_info.CREATE_BY
  is '创建人'; 
  
create sequence seq_vmtree_info start with 9999999998 increment by 1;

-- Create table
create table vmuser_vmorg_map
(
  ID                NUMBER(18) not null primary key ,
  User_Id         NUMBER(18) not null,
  Org_Id        NUMBER(18) not null,
  Org_Type      VARCHAR2(50) 
);
-- Add comments to the table 
comment on table vmuser_vmorg_map
  is '员工虚拟组织关系表';
-- Add comments to the columns 
comment on column vmuser_vmorg_map.ID
  is '主键ID';
comment on column vmuser_vmorg_map.User_Id
  is '用户ID';
comment on column vmuser_vmorg_map.Org_Id
  is '虚拟树的Org_ID';
comment on column vmuser_vmorg_map.Org_Type
  is '虚拟树代码';
  
  create sequence seq_vmuser_vmorg_map start with 1 increment by 1;

------------------------------------------  
create table SYS_INDUSTRY
(
  id             NUMBER(18) not null,
  industry_name  VARCHAR2(500) not null,
  industry_type  VARCHAR2(10),
  parent_code    VARCHAR2(50),
  validate_state VARCHAR2(1),
  industry_code  VARCHAR2(50)
)
;
comment on column SYS_INDUSTRY.industry_name
  is '行业名称/职位名称';
comment on column SYS_INDUSTRY.industry_type
  is 'industry:行业;position:职位';
comment on column SYS_INDUSTRY.parent_code
  is '职位所属的行(只对应职位数据有效)';
comment on column SYS_INDUSTRY.industry_code
  is '行业、职位编码';
alter table SYS_INDUSTRY
  add primary key (ID)
  ;
  
create table SYS_TEMPLATE
(
  id               NUMBER(18) not null,
  app_id           VARCHAR2(50) not null,
  template_no      VARCHAR2(50) not null,
  template_name    VARCHAR2(100),
  template_type    VARCHAR2(2) not null,
  template_content BLOB,
  validate_state   VARCHAR2(2) not null,
  version          NUMBER(16)
)
;
comment on table SYS_TEMPLATE
  is '模板';
comment on column SYS_TEMPLATE.id
  is '机构ID';
comment on column SYS_TEMPLATE.app_id
  is '系统ID';
comment on column SYS_TEMPLATE.template_no
  is '模板编码';
comment on column SYS_TEMPLATE.template_name
  is '模板名称';
comment on column SYS_TEMPLATE.template_type
  is '模板类型 1  短信 2邮件 9 其他';
comment on column SYS_TEMPLATE.template_content
  is '模板内容';
comment on column SYS_TEMPLATE.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_TEMPLATE.version
  is '乐观锁';
alter table SYS_TEMPLATE
  add primary key (ID)
  ;

create table SYS_ROLE_GROUP
(
  id              NUMBER(18) not null,
  role_group_name VARCHAR2(50) not null,
  role_group_code VARCHAR2(50),
  role_group_type VARCHAR2(1) not null,
  app_id          NUMBER(18),
  validate_state  VARCHAR2(2) not null,
  version         NUMBER(16)
)
;
comment on table SYS_ROLE_GROUP
  is '角色组';
comment on column SYS_ROLE_GROUP.id
  is '主键';
comment on column SYS_ROLE_GROUP.role_group_name
  is '角色组名称';
comment on column SYS_ROLE_GROUP.role_group_code
  is '角色组编码';
comment on column SYS_ROLE_GROUP.role_group_type
  is '角色组类型：备用，默认1';
comment on column SYS_ROLE_GROUP.app_id
  is '系统ID，备用';
comment on column SYS_ROLE_GROUP.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ROLE_GROUP.version
  is '乐观锁';
alter table SYS_ROLE_GROUP
  add primary key (ID)
  ;

  create table SYS_ROLE_GROUP_ROLE
(
  id             NUMBER(18) not null,
  role_id        NUMBER(18) not null,
  role_group_id  NUMBER(18),
  app_id         NUMBER(18),
  validate_state VARCHAR2(2) not null,
  version        NUMBER(16)
)
;
comment on table SYS_ROLE_GROUP_ROLE
  is '角色组角色中间表';
comment on column SYS_ROLE_GROUP_ROLE.id
  is '主键';
comment on column SYS_ROLE_GROUP_ROLE.role_id
  is '角色ID';
comment on column SYS_ROLE_GROUP_ROLE.role_group_id
  is '角色组ID';
comment on column SYS_ROLE_GROUP_ROLE.app_id
  is '系统ID，备用';
comment on column SYS_ROLE_GROUP_ROLE.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ROLE_GROUP_ROLE.version
  is '乐观锁';
alter table SYS_ROLE_GROUP_ROLE
  add primary key (ID)
  ;
alter table sys_config modify config_value varchar2(500); 


  create table SYS_VERSION
(
  ID               NUMBER(18) not null primary key ,
  VERSION_NUM      VARCHAR2(50) not null,
  VERSION_NAME     VARCHAR2(200) not null,
  VERSION_CONTENT  VARCHAR2(4000),
  VERSION_TIME     VARCHAR2(50) ,
  SYSTEM_STATE 		 VARCHAR2(50)
)
;
comment on table SYS_VERSION
  is '系统版本号表';
comment on column SYS_VERSION.ID
  is '主键';
comment on column SYS_VERSION.VERSION_NUM
  is '版本号';
comment on column SYS_VERSION.VERSION_NAME
  is '版本名称';
comment on column SYS_VERSION.VERSION_CONTENT
  is '版本内容';
comment on column SYS_VERSION.VERSION_TIME
  is '上线时间';
comment on column SYS_VERSION.SYSTEM_STATE
  is '系统标志位';

alter table SYS_ROLE_GROUP modify role_group_name varchar2(100);


-- Create table
create table SYS_LEAVE_INFO
(
  ID             NUMBER(18) not null,
  LEAVE_USER_ID  VARCHAR2(20),
  USER_LEVEL     VARCHAR2(200),
  USER_NAME      VARCHAR2(50),
  ORG_NAME       VARCHAR2(200),
  REASON         VARCHAR2(500),
  EMAIL          VARCHAR2(100),
  STATUS         VARCHAR2(4),
  START_TIME     DATE,
  END_TIME       DATE,
  LEAVE_TYPE     VARCHAR2(2),
  VALIDATE_STATE VARCHAR2(2),
  OWNER_ID       NUMBER(16),
  CREATE_TIME    TIMESTAMP(6),
  MODIFY_TIME    TIMESTAMP(6),
  CREATE_BY      NUMBER(16),
  REMARK         VARCHAR2(500),
  MODIFY_BY      NUMBER(16),
  ORG_ID         NUMBER(18)
);
-- Add comments to the table 
comment on table SYS_LEAVE_INFO
  is '员工请假表';
-- Add comments to the columns 
comment on column SYS_LEAVE_INFO.ID
  is '主键ID';
comment on column SYS_LEAVE_INFO.LEAVE_USER_ID
  is '请假人ID';
comment on column SYS_LEAVE_INFO.USER_LEVEL
  is '员工职级';
comment on column SYS_LEAVE_INFO.USER_NAME
  is '员工姓名';
comment on column SYS_LEAVE_INFO.ORG_NAME
  is '业务所属机构';
comment on column SYS_LEAVE_INFO.REASON
  is '事由';
comment on column SYS_LEAVE_INFO.EMAIL
  is '电子邮箱';
comment on column SYS_LEAVE_INFO.STATUS
  is '状态（1：正常   2：请假中）';
comment on column SYS_LEAVE_INFO.START_TIME
  is '请假开始时间';
comment on column SYS_LEAVE_INFO.END_TIME
  is '请假结束时间';
comment on column SYS_LEAVE_INFO.LEAVE_TYPE
  is '请假类型（1：代理请假，2：本人请假）';
comment on column SYS_LEAVE_INFO.VALIDATE_STATE
  is '数据有效性（1：有效、0：无效）';
comment on column SYS_LEAVE_INFO.OWNER_ID
  is '业务归属人';
comment on column SYS_LEAVE_INFO.CREATE_TIME
  is '创建时间';
comment on column SYS_LEAVE_INFO.MODIFY_TIME
  is '修改时间';
comment on column SYS_LEAVE_INFO.CREATE_BY
  is '创建人';
comment on column SYS_LEAVE_INFO.REMARK
  is '备注';
comment on column SYS_LEAVE_INFO.MODIFY_BY
  is '修改人';
comment on column SYS_LEAVE_INFO.ORG_ID
  is '业务所属机构';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_LEAVE_INFO
  add constraint PK_SYS_LEAVE_INFO primary key (ID);