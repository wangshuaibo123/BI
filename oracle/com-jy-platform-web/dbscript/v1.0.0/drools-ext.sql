create table SYS_RULE_MODEL
(
  ID             VARCHAR2(50) not null,
  MODEL_TYPE     VARCHAR2(20),
  CH_NAME        VARCHAR2(50),
  EN_NAME        VARCHAR2(100),
  REMARK         VARCHAR2(200),
  VALIDATE_STATE VARCHAR2(2)
);
-- Add comments to the table 
comment on table SYS_RULE_MODEL
  is '业务模型';
-- Add comments to the columns 
comment on column SYS_RULE_MODEL.MODEL_TYPE
  is '类型：1 实体类 2自定义模型';
comment on column SYS_RULE_MODEL.CH_NAME
  is '中文名';
comment on column SYS_RULE_MODEL.EN_NAME
  is '英文名';
comment on column SYS_RULE_MODEL.REMARK
  is '备注';
comment on column SYS_RULE_MODEL.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_RULE_MODEL
  add constraint PK_SYS_RULE_MODEL primary key (ID);
  
  
-- Create table
create table SYS_RULE_MODEL_ATTR
(
  ID             VARCHAR2(50) not null,
  MODEL_EN_NAME  VARCHAR2(100),
  CH_NAME        VARCHAR2(50),
  EN_NAME        VARCHAR2(100),
  DATA_TYPE      VARCHAR2(20),
  DATA_LONG      VARCHAR2(20),
  DICT_CODE      VARCHAR2(20),
  REMARK         VARCHAR2(200),
  VALIDATE_STATE VARCHAR2(2),
  SEQUENCE       NUMBER,
  RELATION_MODEL VARCHAR2(50)
);
-- Add comments to the table 
comment on table SYS_RULE_MODEL_ATTR
  is '业务模型属性';
-- Add comments to the columns 
comment on column SYS_RULE_MODEL_ATTR.MODEL_EN_NAME
  is '实体类英文名';
comment on column SYS_RULE_MODEL_ATTR.CH_NAME
  is '中文名';
comment on column SYS_RULE_MODEL_ATTR.EN_NAME
  is '英文名';
comment on column SYS_RULE_MODEL_ATTR.DATA_TYPE
  is '数据类型';
comment on column SYS_RULE_MODEL_ATTR.DATA_LONG
  is '长度';
comment on column SYS_RULE_MODEL_ATTR.DICT_CODE
  is '引用字典编码';
comment on column SYS_RULE_MODEL_ATTR.REMARK
  is '备注';
comment on column SYS_RULE_MODEL_ATTR.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_RULE_MODEL_ATTR.SEQUENCE
  is '序号';
comment on column SYS_RULE_MODEL_ATTR.RELATION_MODEL
  is '关联模型';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_RULE_MODEL_ATTR
  add constraint PK_SYS_RULE_MODEL_ATTR primary key (ID);
  
  
  
  
create table SYS_RULE_MODEL_ATTR_CONFIG
(
  ID                 VARCHAR2(50) not null,
  SYS_ID             VARCHAR2(50),
  SEQUENCE           NUMBER,
  PRE_BRACKETS       VARCHAR2(20),
  CONDITION_ATTR_EN  VARCHAR2(200),
  CONDITION_ATTR_CH  VARCHAR2(50),
  OPERATOR           VARCHAR2(20),
  AFTER_BRACKETS     VARCHAR2(20),
  MANUAL_CONFIG      VARCHAR2(200),
  COMPILATION_RESULT VARCHAR2(200),
  VALIDATE_STATE     VARCHAR2(2)
);
-- Add comments to the table 
comment on table SYS_RULE_MODEL_ATTR_CONFIG
  is '业务模型属性配置';
-- Add comments to the columns 
comment on column SYS_RULE_MODEL_ATTR_CONFIG.ID
  is '主键';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.SYS_ID
  is '实体类属性_主键';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.SEQUENCE
  is '序号';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.PRE_BRACKETS
  is '前括号';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.CONDITION_ATTR_EN
  is '条件属性';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.CONDITION_ATTR_CH
  is '条件属性中文';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.OPERATOR
  is '运算符：加 减 乘 除 无';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.AFTER_BRACKETS
  is '后括号';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.MANUAL_CONFIG
  is '手工设置';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.COMPILATION_RESULT
  is '编译结果';
comment on column SYS_RULE_MODEL_ATTR_CONFIG.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_RULE_MODEL_ATTR_CONFIG
  add constraint PK_SYS_RULE_MODEL_ATRR_CONFIG primary key (ID);
  
  
  
create table SYS_RULE_LIST
(
  ID                    VARCHAR2(50) not null,
  RULE_TYPE             VARCHAR2(20),
  CH_NAME               VARCHAR2(50),
  EN_NAME               VARCHAR2(50),
  EFTECT_TIME           DATE,
  VALIDATE_STATE        VARCHAR2(2),
  RULE_CODE             VARCHAR2(50),
  REMARK                VARCHAR2(200),
  CREATE_USER           VARCHAR2(50),
  CREATE_DATE           DATE,
  UPDATE_USER           VARCHAR2(50),
  UPDATE_DATE           DATE,
  NEW_VERSION_IS_UPDATE NUMBER default 0,
  VERSION_NUM           VARCHAR2(20),
  HELP                  VARCHAR2(2000)
);
-- Add comments to the table 
comment on table SYS_RULE_LIST
  is '规则集';
-- Add comments to the columns 
comment on column SYS_RULE_LIST.RULE_TYPE
  is '类型：1 规则库  2规则集 3规则包 4规则';
comment on column SYS_RULE_LIST.CH_NAME
  is '中文名';
comment on column SYS_RULE_LIST.EN_NAME
  is '英文名：如果是规则库或规则包不需要设置英文名，规则集对应drl文件名
';
comment on column SYS_RULE_LIST.EFTECT_TIME
  is '生效时间，只有规则集才需要设置此值';
comment on column SYS_RULE_LIST.VALIDATE_STATE
  is '有效状态';
comment on column SYS_RULE_LIST.RULE_CODE
  is '编码';
comment on column SYS_RULE_LIST.REMARK
  is '备注';
comment on column SYS_RULE_LIST.CREATE_USER
  is '创建人';
comment on column SYS_RULE_LIST.CREATE_DATE
  is '创建时间';
comment on column SYS_RULE_LIST.UPDATE_USER
  is '修改人';
comment on column SYS_RULE_LIST.UPDATE_DATE
  is '修改时间';
comment on column SYS_RULE_LIST.NEW_VERSION_IS_UPDATE
  is '此版本是否修改：1 是 0否 ，默认值为0';
comment on column SYS_RULE_LIST.VERSION_NUM
  is '版本号';
comment on column SYS_RULE_LIST.HELP
  is '帮助信息：保存一条规则时，自动生成帮助信息';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_RULE_LIST
  add constraint PK_SYS_RULE_LIST primary key (ID);
  
  
  
create table SYS_RULE_WHEN_CONFIG
(
  ID                    VARCHAR2(50) not null,
  RULE_CODE             VARCHAR2(50),
  RULE_NAME_CH          VARCHAR2(50),
  RULE_NAME_EN          VARCHAR2(50),
  SEQUENCE              NUMBER(10),
  PRE_BRACKETS          VARCHAR2(20),
  CONDITION_ATTR_EN     VARCHAR2(300),
  CONDITION_ATTR_CH     VARCHAR2(50),
  OPERATOR              VARCHAR2(20),
  COMPARE_VALUE_EN      VARCHAR2(300),
  COMPARE_VALUE_CH      VARCHAR2(50),
  AFTER_BRACKETS        VARCHAR2(20),
  LOGICAL_OPERATOR      VARCHAR2(20),
  MANUAL_RULE           VARCHAR2(200),
  COMPILATION_RESULT    VARCHAR2(500),
  CREATE_USER           VARCHAR2(50),
  CREATE_DATE           DATE,
  UPDATE_USER           VARCHAR2(50),
  UPDATE_DATE           DATE,
  VERSION_NUM           VARCHAR2(20),
  NEW_VERSION_IS_UPDATE NUMBER default 0,
  VALIDATE_STATE        VARCHAR2(2) default 1,
  PARENT_CODE           VARCHAR2(50)
);
-- Add comments to the table 
comment on table SYS_RULE_WHEN_CONFIG
  is '规则条件设置';
-- Add comments to the columns 
comment on column SYS_RULE_WHEN_CONFIG.RULE_CODE
  is '编码';
comment on column SYS_RULE_WHEN_CONFIG.RULE_NAME_CH
  is '中文规则名';
comment on column SYS_RULE_WHEN_CONFIG.RULE_NAME_EN
  is '英文规则名';
comment on column SYS_RULE_WHEN_CONFIG.SEQUENCE
  is '序号';
comment on column SYS_RULE_WHEN_CONFIG.PRE_BRACKETS
  is '前括号';
comment on column SYS_RULE_WHEN_CONFIG.CONDITION_ATTR_EN
  is '条件属性';
comment on column SYS_RULE_WHEN_CONFIG.CONDITION_ATTR_CH
  is '条件属性中文';
comment on column SYS_RULE_WHEN_CONFIG.OPERATOR
  is '运算符：加 减 乘 除 包含 不包含';
comment on column SYS_RULE_WHEN_CONFIG.COMPARE_VALUE_EN
  is '比较值';
comment on column SYS_RULE_WHEN_CONFIG.COMPARE_VALUE_CH
  is '比较值中文';
comment on column SYS_RULE_WHEN_CONFIG.AFTER_BRACKETS
  is '后括号';
comment on column SYS_RULE_WHEN_CONFIG.LOGICAL_OPERATOR
  is '逻辑操作符';
comment on column SYS_RULE_WHEN_CONFIG.MANUAL_RULE
  is '手输规则';
comment on column SYS_RULE_WHEN_CONFIG.COMPILATION_RESULT
  is '编译结果';
comment on column SYS_RULE_WHEN_CONFIG.CREATE_USER
  is '创建人';
comment on column SYS_RULE_WHEN_CONFIG.CREATE_DATE
  is '创建时间';
comment on column SYS_RULE_WHEN_CONFIG.UPDATE_USER
  is '修改人';
comment on column SYS_RULE_WHEN_CONFIG.UPDATE_DATE
  is '修改时间';
comment on column SYS_RULE_WHEN_CONFIG.VERSION_NUM
  is '版本号';
comment on column SYS_RULE_WHEN_CONFIG.NEW_VERSION_IS_UPDATE
  is '此版本是否修改：1 是 0否 ，默认值为0';
comment on column SYS_RULE_WHEN_CONFIG.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_RULE_WHEN_CONFIG.PARENT_CODE
  is '规则表的规则编码';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYS_RULE_WHEN_CONFIG
  add constraint PK_SYS_RULE_WHEN_CONFIG primary key (ID);
  
  
  
  
  
  
-- Create table
create table SYS_RULE_THEN_CONFIG
(
  ID                    VARCHAR2(50),
  EXCUTION_CODE         VARCHAR2(2000),
  RULE_CODE             VARCHAR2(50),
  REMARK                VARCHAR2(1000),
  CREATE_USER           VARCHAR2(50),
  CREATE_DATE           DATE,
  UPDATE_USER           VARCHAR2(50),
  UPDATE_DATE           DATE,
  VERSION_NUM           VARCHAR2(20),
  NEW_VERSION_IS_UPDATE NUMBER default 0,
  HELP                  VARCHAR2(2000),
  VALIDATE_STATE        VARCHAR2(2),
  PARENT_CODE           VARCHAR2(50)
);
-- Add comments to the table 
comment on table SYS_RULE_THEN_CONFIG
  is '规则执行表';
-- Add comments to the columns 
comment on column SYS_RULE_THEN_CONFIG.ID
  is '主键';
comment on column SYS_RULE_THEN_CONFIG.EXCUTION_CODE
  is '执行代码';
comment on column SYS_RULE_THEN_CONFIG.RULE_CODE
  is '编码';
comment on column SYS_RULE_THEN_CONFIG.REMARK
  is '备注';
comment on column SYS_RULE_THEN_CONFIG.CREATE_USER
  is '创建人';
comment on column SYS_RULE_THEN_CONFIG.CREATE_DATE
  is '创建时间';
comment on column SYS_RULE_THEN_CONFIG.UPDATE_USER
  is '修改人';
comment on column SYS_RULE_THEN_CONFIG.UPDATE_DATE
  is '修改时间';
comment on column SYS_RULE_THEN_CONFIG.VERSION_NUM
  is '版本号';
comment on column SYS_RULE_THEN_CONFIG.NEW_VERSION_IS_UPDATE
  is '此版本是否修改：1 是 0否 ，默认值为0';
comment on column SYS_RULE_THEN_CONFIG.HELP
  is '帮助信息，保存一条规则时，自动生成帮助信息';
comment on column SYS_RULE_THEN_CONFIG.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column SYS_RULE_THEN_CONFIG.PARENT_CODE
  is '规则表的规则编码';
  
  
alter table SYS_RULE_LIST  add  EXPIRES_TIME  DATE  
  