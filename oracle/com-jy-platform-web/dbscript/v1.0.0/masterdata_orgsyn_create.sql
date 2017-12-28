-- Create table
create table SYS_SYN_CONFIG
(
  id          NUMBER(18) not null,
  app_flag    VARCHAR2(50),
  create_date TIMESTAMP(6),
  org_names   VARCHAR2(500),
  org_ids     VARCHAR2(200),
  syn_status  VARCHAR2(1)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table SYS_SYN_CONFIG
  is '机构部门表';
-- Add comments to the columns 
comment on column SYS_SYN_CONFIG.id
  is '机构ID';
comment on column SYS_SYN_CONFIG.app_flag
  is '系统标示';
comment on column SYS_SYN_CONFIG.create_date
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_SYN_CONFIG
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create sequence 
create sequence SEQ_SYS_SYN_CONFIG
minvalue 1
maxvalue 99999999
start with 81
increment by 1
cache 20;


-- Create table
create table SYS_SYN_INFO
(
  id          NUMBER(18) not null,
  app_flag    VARCHAR2(50),
  create_date TIMESTAMP(6),
  syn_type    VARCHAR2(20),
  syn_id      NUMBER(18)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table SYS_SYN_INFO
  is '机构部门表';
-- Add comments to the columns 
comment on column SYS_SYN_INFO.id
  is '机构ID';
comment on column SYS_SYN_INFO.app_flag
  is '系统标示';
comment on column SYS_SYN_INFO.create_date
  is '创建时间';
comment on column SYS_SYN_INFO.syn_type
  is '同步类型';
comment on column SYS_SYN_INFO.syn_id
  is 'id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_SYN_INFO
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
  
  -- Create sequence 
create sequence SEQ_SYS_SYN_INFO
minvalue 1
maxvalue 9999999999999999
start with 21
increment by 1
cache 20;


create table SYS_LDAP_SYN
(
  id              NUMBER(18) not null,
  user_login_name VARCHAR2(100),
  user_password   VARCHAR2(100),
  user_name       VARCHAR2(500),
  org_code        VARCHAR2(100),
  org_old_code    VARCHAR2(100),
  org_parent_code VARCHAR2(100),
  org_name        VARCHAR2(100),
  ldap_syn        CHAR(1),
  ldap_type       CHAR(1),
  createdate      TIMESTAMP(6)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_LDAP_SYN
  is 'ldap同步表';
comment on column SYS_LDAP_SYN.id
  is '主键';
comment on column SYS_LDAP_SYN.user_login_name
  is '用户登录名称';
comment on column SYS_LDAP_SYN.user_password
  is '用户密码';
comment on column SYS_LDAP_SYN.user_name
  is '用户名称';
comment on column SYS_LDAP_SYN.org_code
  is '机构编码';
comment on column SYS_LDAP_SYN.org_old_code
  is '机构旧编码';
comment on column SYS_LDAP_SYN.org_parent_code
  is '机构父编码';
comment on column SYS_LDAP_SYN.org_name
  is '机构名称';
comment on column SYS_LDAP_SYN.ldap_syn
  is '同步ldap是否成功1成功 0 失败';
comment on column SYS_LDAP_SYN.ldap_type
  is '同步ldap类型 1机构 2用户';
comment on column SYS_LDAP_SYN.createdate
  is '创建时间';
alter table SYS_LDAP_SYN
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_ORG_HIS
(
  id             NUMBER(18) not null,
  org_code       VARCHAR2(100),
  org_name       VARCHAR2(100) not null,
  org_type       VARCHAR2(10) not null,
  parent_id      VARCHAR2(100) not null,
  parent_ids     VARCHAR2(1000),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  is_virtual     VARCHAR2(1) not null,
  version        NUMBER(16),
  app_flag       VARCHAR2(10),
  create_date    TIMESTAMP(6),
  org_id         NUMBER(18),
  is_leef        VARCHAR2(1),
  area_codes     VARCHAR2(50),
  area_adress    VARCHAR2(50)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_ORG_HIS
  is '机构部门表';
comment on column SYS_ORG_HIS.id
  is '机构ID';
comment on column SYS_ORG_HIS.org_code
  is '机构编码';
comment on column SYS_ORG_HIS.org_name
  is '机构名称';
comment on column SYS_ORG_HIS.org_type
  is '机构类型：org组织，dept部门';
comment on column SYS_ORG_HIS.parent_id
  is '父机构ID';
comment on column SYS_ORG_HIS.parent_ids
  is '父机构ID串，以/分割';
comment on column SYS_ORG_HIS.order_by
  is '排序';
comment on column SYS_ORG_HIS.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG_HIS.is_virtual
  is '是否是虚拟组织，1是，0否，默认0';
comment on column SYS_ORG_HIS.version
  is '乐观锁';
comment on column SYS_ORG_HIS.app_flag
  is '系统标示 1 平台 2贷前 3贷后 4 理财';
comment on column SYS_ORG_HIS.create_date
  is '创建时间';
comment on column SYS_ORG_HIS.org_id
  is '机构id';
comment on column SYS_ORG_HIS.is_leef
  is '是否叶子节点';
comment on column SYS_ORG_HIS.area_codes
  is '区域编码';
comment on column SYS_ORG_HIS.area_adress
  is '区域名称';
alter table SYS_ORG_HIS
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_ORG_SYN
(
  id             NUMBER(18) not null,
  org_code       VARCHAR2(100),
  org_name       VARCHAR2(100) not null,
  org_type       VARCHAR2(10) not null,
  parent_id      VARCHAR2(100) not null,
  parent_ids     VARCHAR2(1000),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  is_virtual     VARCHAR2(1) not null,
  version        NUMBER(16),
  app_flag       VARCHAR2(10),
  create_date    TIMESTAMP(6),
  org_id         NUMBER(18),
  is_leef        VARCHAR2(1),
  area_codes     VARCHAR2(50),
  area_adress    VARCHAR2(50)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_ORG_SYN
  is '机构部门表';
comment on column SYS_ORG_SYN.id
  is '机构ID';
comment on column SYS_ORG_SYN.org_code
  is '机构编码';
comment on column SYS_ORG_SYN.org_name
  is '机构名称';
comment on column SYS_ORG_SYN.org_type
  is '机构类型：org组织，dept部门';
comment on column SYS_ORG_SYN.parent_id
  is '父机构ID';
comment on column SYS_ORG_SYN.parent_ids
  is '父机构ID串，以/分割';
comment on column SYS_ORG_SYN.order_by
  is '排序';
comment on column SYS_ORG_SYN.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG_SYN.is_virtual
  is '是否是虚拟组织，1是，0否，默认0';
comment on column SYS_ORG_SYN.version
  is '乐观锁';
comment on column SYS_ORG_SYN.app_flag
  is '系统标示';
comment on column SYS_ORG_SYN.create_date
  is '创建时间';
comment on column SYS_ORG_SYN.org_id
  is '机构id';
comment on column SYS_ORG_SYN.is_leef
  is '是否叶子节点';
comment on column SYS_ORG_SYN.area_codes
  is '区域编码';
comment on column SYS_ORG_SYN.area_adress
  is '区域名称';
alter table SYS_ORG_SYN
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_ORG_USER_HIS
(
  id             NUMBER(18) not null,
  org_id         NUMBER(18) not null,
  user_id        NUMBER(18) not null,
  position_id    NUMBER(18),
  is_main_org    VARCHAR2(1) not null,
  validate_state VARCHAR2(2) not null,
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  org_user_id    NUMBER(18),
  org_name       VARCHAR2(100),
  user_name      VARCHAR2(100),
  position_name  VARCHAR2(100)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column SYS_ORG_USER_HIS.id
  is '主键';
comment on column SYS_ORG_USER_HIS.org_id
  is '机构ID';
comment on column SYS_ORG_USER_HIS.user_id
  is '用户ID';
comment on column SYS_ORG_USER_HIS.position_id
  is '岗位ID';
comment on column SYS_ORG_USER_HIS.is_main_org
  is '是否主部门，1是（主部门），0否（兼职部门），默认1';
comment on column SYS_ORG_USER_HIS.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG_USER_HIS.version
  is '乐观锁';
comment on column SYS_ORG_USER_HIS.create_date
  is '创建时间';
comment on column SYS_ORG_USER_HIS.org_user_id
  is 'orguserid';
comment on column SYS_ORG_USER_HIS.org_name
  is '机构名称';
comment on column SYS_ORG_USER_HIS.user_name
  is '用户名称';
comment on column SYS_ORG_USER_HIS.position_name
  is '岗位名称';
alter table SYS_ORG_USER_HIS
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_ORG_USER_SYN
(
  id             NUMBER(18) not null,
  org_id         NUMBER(18) not null,
  user_id        NUMBER(18) not null,
  position_id    NUMBER(18),
  is_main_org    VARCHAR2(1) not null,
  validate_state VARCHAR2(2) not null,
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  org_user_id    NUMBER(18),
  org_name       VARCHAR2(100),
  user_name      VARCHAR2(100),
  position_name  VARCHAR2(100)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column SYS_ORG_USER_SYN.id
  is '主键';
comment on column SYS_ORG_USER_SYN.org_id
  is '机构ID';
comment on column SYS_ORG_USER_SYN.user_id
  is '用户ID';
comment on column SYS_ORG_USER_SYN.position_id
  is '岗位ID';
comment on column SYS_ORG_USER_SYN.is_main_org
  is '是否主部门，1是（主部门），0否（兼职部门），默认1';
comment on column SYS_ORG_USER_SYN.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_ORG_USER_SYN.version
  is '乐观锁';
comment on column SYS_ORG_USER_SYN.create_date
  is '创建时间';
comment on column SYS_ORG_USER_SYN.org_user_id
  is 'orguserid';
comment on column SYS_ORG_USER_SYN.org_name
  is '机构名称';
comment on column SYS_ORG_USER_SYN.user_name
  is '用户名称';
comment on column SYS_ORG_USER_SYN.position_name
  is '岗位名称';
alter table SYS_ORG_USER_SYN
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_POSITION_HIS
(
  id             NUMBER(18) not null,
  position_name  VARCHAR2(50) not null,
  position_code  VARCHAR2(50),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  position_id    NUMBER(18)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_POSITION_HIS
  is '岗位历史表';
comment on column SYS_POSITION_HIS.id
  is '主键';
comment on column SYS_POSITION_HIS.position_name
  is '岗位名称';
comment on column SYS_POSITION_HIS.position_code
  is '岗位编码';
comment on column SYS_POSITION_HIS.order_by
  is '排序';
comment on column SYS_POSITION_HIS.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_POSITION_HIS.version
  is '乐观锁';
comment on column SYS_POSITION_HIS.create_date
  is '创建时间';
comment on column SYS_POSITION_HIS.position_id
  is '岗位id';
alter table SYS_POSITION_HIS
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_POSITION_SYN
(
  id             NUMBER(18) not null,
  position_name  VARCHAR2(50) not null,
  position_code  VARCHAR2(50),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  position_id    NUMBER(18)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_POSITION_SYN
  is '岗位待同步表';
comment on column SYS_POSITION_SYN.id
  is '主键';
comment on column SYS_POSITION_SYN.position_name
  is '岗位名称';
comment on column SYS_POSITION_SYN.position_code
  is '岗位编码';
comment on column SYS_POSITION_SYN.order_by
  is '排序';
comment on column SYS_POSITION_SYN.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_POSITION_SYN.version
  is '乐观锁';
comment on column SYS_POSITION_SYN.create_date
  is '创建时间';
comment on column SYS_POSITION_SYN.position_id
  is '岗位Id';
alter table SYS_POSITION_SYN
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


create table SYS_USER_HIS
(
  id             NUMBER(18) not null,
  user_name      VARCHAR2(50) not null,
  user_no        VARCHAR2(50),
  login_name     VARCHAR2(50),
  password       VARCHAR2(50),
  salt           VARCHAR2(50),
  mobile         VARCHAR2(50),
  email          VARCHAR2(50),
  user_image     VARCHAR2(100),
  sex            VARCHAR2(50),
  birthday       VARCHAR2(50),
  nationality    VARCHAR2(50),
  education      VARCHAR2(50),
  job            VARCHAR2(100),
  home_address   VARCHAR2(100),
  home_zipcode   VARCHAR2(50),
  home_tel       VARCHAR2(50),
  office_tel     VARCHAR2(50),
  office_address VARCHAR2(100),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  is_locked      VARCHAR2(2),
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  user_id        NUMBER(18)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_HIS
  is '系统用户表';
comment on column SYS_USER_HIS.id
  is '主键';
comment on column SYS_USER_HIS.user_name
  is '姓名';
comment on column SYS_USER_HIS.user_no
  is '编码';
comment on column SYS_USER_HIS.login_name
  is '登录名';
comment on column SYS_USER_HIS.password
  is '登录密码';
comment on column SYS_USER_HIS.salt
  is '盐值';
comment on column SYS_USER_HIS.mobile
  is '手机';
comment on column SYS_USER_HIS.email
  is '邮件';
comment on column SYS_USER_HIS.user_image
  is '用户头像';
comment on column SYS_USER_HIS.sex
  is '性别';
comment on column SYS_USER_HIS.birthday
  is '出生日期';
comment on column SYS_USER_HIS.nationality
  is '民族';
comment on column SYS_USER_HIS.education
  is '学历';
comment on column SYS_USER_HIS.job
  is '职务';
comment on column SYS_USER_HIS.home_address
  is '家庭住址';
comment on column SYS_USER_HIS.home_zipcode
  is '家庭邮编';
comment on column SYS_USER_HIS.home_tel
  is '家庭电话';
comment on column SYS_USER_HIS.office_tel
  is '办公电话';
comment on column SYS_USER_HIS.office_address
  is '办公地址';
comment on column SYS_USER_HIS.order_by
  is '排序';
comment on column SYS_USER_HIS.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_USER_HIS.is_locked
  is '是否锁定，1锁定，0未锁，默认0';
comment on column SYS_USER_HIS.version
  is '乐观锁';
comment on column SYS_USER_HIS.create_date
  is '创建时间';
comment on column SYS_USER_HIS.user_id
  is '用户id';


create table SYS_USER_SYN
(
  id             NUMBER(18) not null,
  user_name      VARCHAR2(50) not null,
  user_no        VARCHAR2(50),
  login_name     VARCHAR2(50),
  password       VARCHAR2(50),
  salt           VARCHAR2(50),
  mobile         VARCHAR2(50),
  email          VARCHAR2(50),
  user_image     VARCHAR2(100),
  sex            VARCHAR2(50),
  birthday       VARCHAR2(50),
  nationality    VARCHAR2(50),
  education      VARCHAR2(50),
  job            VARCHAR2(100),
  home_address   VARCHAR2(100),
  home_zipcode   VARCHAR2(50),
  home_tel       VARCHAR2(50),
  office_tel     VARCHAR2(50),
  office_address VARCHAR2(100),
  order_by       VARCHAR2(50),
  validate_state VARCHAR2(2) not null,
  is_locked      VARCHAR2(2),
  version        NUMBER(16),
  create_date    TIMESTAMP(6),
  user_id        NUMBER(18)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_SYN
  is '系统用户表';
comment on column SYS_USER_SYN.id
  is '主键';
comment on column SYS_USER_SYN.user_name
  is '姓名';
comment on column SYS_USER_SYN.user_no
  is '编码';
comment on column SYS_USER_SYN.login_name
  is '登录名';
comment on column SYS_USER_SYN.password
  is '登录密码';
comment on column SYS_USER_SYN.salt
  is '盐值';
comment on column SYS_USER_SYN.mobile
  is '手机';
comment on column SYS_USER_SYN.email
  is '邮件';
comment on column SYS_USER_SYN.user_image
  is '用户头像';
comment on column SYS_USER_SYN.sex
  is '性别';
comment on column SYS_USER_SYN.birthday
  is '出生日期';
comment on column SYS_USER_SYN.nationality
  is '民族';
comment on column SYS_USER_SYN.education
  is '学历';
comment on column SYS_USER_SYN.job
  is '职务';
comment on column SYS_USER_SYN.home_address
  is '家庭住址';
comment on column SYS_USER_SYN.home_zipcode
  is '家庭邮编';
comment on column SYS_USER_SYN.home_tel
  is '家庭电话';
comment on column SYS_USER_SYN.office_tel
  is '办公电话';
comment on column SYS_USER_SYN.office_address
  is '办公地址';
comment on column SYS_USER_SYN.order_by
  is '排序';
comment on column SYS_USER_SYN.validate_state
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_USER_SYN.is_locked
  is '是否锁定，1锁定，0未锁，默认0';
comment on column SYS_USER_SYN.version
  is '乐观锁';
comment on column SYS_USER_SYN.create_date
  is '创建时间';
comment on column SYS_USER_SYN.user_id
  is '用户id';

  alter table sys_position_syn add SYN_STATUS varchar2(1); 
  alter table sys_user_syn add SYN_STATUS varchar2(1); 
  alter table sys_org_user_syn add SYN_STATUS varchar2(1); 
  alter table sys_org_syn add SYN_STATUS varchar2(1);


  CREATE SEQUENCE  "SEQ_SYS_USER_SYN"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_USER_HIS"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_POSITION_SYN"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_POSITION_HIS"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_ORG_USER_SYN"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_ORG_USER_HIS"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_ORG_SYN"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_ORG_HIS"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_LDAP_SYN"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
  CREATE SEQUENCE  "SEQ_SYS_SYN_CONFIG"  MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;


  
  

