-- Create table 规则定义
create table RULE_DEFINITION
(
  ID             NUMBER not null,
  RULE_CODE      VARCHAR2(200) not null,
  RULE_NAME      VARCHAR2(200),
  RULE_RESOURCE  CLOB,
  RESOURCE_TYPE  VARCHAR2(10) not null,
  DEF_LEVEL      NUMBER not null,
  VALIDATE_STATE VARCHAR2(1) not null,
  VERSION        NUMBER(16) not null
);

-- Add comments to the columns 
comment on column RULE_DEFINITION.ID
  is '主键';
comment on column RULE_DEFINITION.RULE_CODE
  is '规则编码';
comment on column RULE_DEFINITION.RULE_NAME
  is '规则名称';
comment on column RULE_DEFINITION.RULE_RESOURCE
  is '规则定义';
comment on column RULE_DEFINITION.RESOURCE_TYPE
  is '定义类型';
comment on column RULE_DEFINITION.DEF_LEVEL
  is '层级';
comment on column RULE_DEFINITION.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column RULE_DEFINITION.VERSION
  is '乐观锁';

-- Create/Recreate primary, unique and foreign key constraints 
alter table RULE_DEFINITION
  add constraint PK_RULE_DEF primary key (ID);
alter table RULE_DEFINITION
  add constraint IDX_RULE_DEF_CODE unique (RULE_CODE);


-- Create table 规则树
create table RULE_TREE
(
  ID             NUMBER not null,
  PARENT_ID      NUMBER,
  NODE_CODE      VARCHAR2(200),
  NODE_NAME      VARCHAR2(200) not null,
  VALIDATE_STATE VARCHAR2(1) not null,
  VERSION        NUMBER not null,
  CREATE_TIME    TIMESTAMP(6),
  CREATE_BY      NUMBER,
  UPDATE_TIME    TIMESTAMP(6),
  UPDATE_BY      NUMBER
);

-- Add comments to the table 
comment on table RULE_TREE
  is '规则树';
-- Add comments to the columns 
comment on column RULE_TREE.ID
  is '主键';
comment on column RULE_TREE.PARENT_ID
  is '父节点';
comment on column RULE_TREE.NODE_CODE
  is '节点编码';
comment on column RULE_TREE.NODE_NAME
  is '节点名称';
comment on column RULE_TREE.VALIDATE_STATE
  is '有效性状态，1有效，0无效，默认1';
comment on column RULE_TREE.VERSION
  is '乐观锁';
comment on column RULE_TREE.CREATE_TIME
  is '创建时间';
comment on column RULE_TREE.CREATE_BY
  is '创建人';
comment on column RULE_TREE.UPDATE_TIME
  is '最后更新时间';
comment on column RULE_TREE.UPDATE_BY
  is '更新人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table RULE_TREE
  add constraint PK_RULE_TREE primary key (ID);
-- Create/Recreate indexes 
create unique index IDX_RULE_TREE_NODE_CODE on RULE_TREE (NODE_CODE);
create index IDX_RULE_TREE_PARENT_ID on RULE_TREE (PARENT_ID);


-- Create sequence 
create sequence SEQ_RULE_DEFINITION
minvalue 1
maxvalue 9999999999999999999999999999
start with 10001
increment by 1
cache 20;