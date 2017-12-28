create index IDX_HIST_PRO_000 on JBPM4_HIST_PROCINST (ID_) ;
create index IDX_HIST_PRO_001 on JBPM4_HIST_PROCINST (END_) ;
create index IDX_HIST_PRO_002 on JBPM4_HIST_PROCINST (START_) ;

create index IDX_TASK_000 on JBPM4_TASK (EXECUTION_ID_) ;
create index IDX_TASK_001 on JBPM4_TASK (CREATE_) ;
create index IDX_TASK_002 on JBPM4_TASK (STATE_) ;
create index IDX_TASK_SUPERTASK on JBPM4_TASK (SUPERTASK_) ;

-- Add comments to the table 

comment on table jbpm4_task is '待办任务表';

comment on table jbpm4_deployment is '流程定义表';

comment on table jbpm4_deployprop is '流程定义属性表';

comment on table jbpm4_execution is '流程实例表';

comment on table jbpm4_property  is '流程引擎表';

comment on table jbpm4_variable is '上下文表';

comment on table jbpm4_job is '定时表';

comment on table jbpm4_lob is '存储表';

comment on table jbpm4_swimlane is '泳道表';

comment on table jbpm4_participation is '参与者表';

comment on table jbpm4_hist_actinst is '流程活动（节点）实例表';

comment on table jbpm4_hist_detail is '流程历史详情表';


comment on table jbpm4_hist_procinst is '流程实例历史表';

comment on table jbpm4_hist_task is '流程任务实例历史表';

comment on table jbpm4_hist_var is '流程上下文历史表';

comment on table jbpm4_id_group is '组表';

comment on table jbpm4_id_user is '用户表';

comment on table jbpm4_id_membership is '用户角色表';
comment on table jbpm4_task is '待办任务表';

-- Add comments to the columns 
comment on column jbpm4_task.dbid_  is '主键';
comment on column jbpm4_task.execution_ IS '流程实例id';
comment on column jbpm4_task.assignee_ IS '待办任务参与者id';
comment on column jbpm4_task.create_ IS '待办任务创建时间';

comment on column jbpm4_execution.dbid_ IS '流程实例id';

comment on column jbpm4_execution.id_ IS '流程实例id';
comment on column jbpm4_execution.superexec_ IS '主流流程实例id';
comment on column jbpm4_execution.parent_ IS 'parent id 并发子流程的主流程实例 id 可以关联 主流程实例id';


comment on column jbpm4_hist_task.dbid_ IS '流程实例id';

comment on column jbpm4_hist_task.execution_ IS '流程实例id';

comment on column jbpm4_hist_task.state_ IS '待办任务状态';

comment on column jbpm4_hist_task.outcome_ IS '流程路径';

comment on column jbpm4_hist_task.assignee_ IS '参与者';

comment on column jbpm4_hist_task.create_ IS '待办任务创建时间';
comment on column jbpm4_hist_task.end_ IS '待办任务完成时间';


--添加 主流程实例id 字段
alter table jbpm4_hist_procinst add main_id_ varchar2(255);

--添加 任务状态是否锁定标志
alter table jbpm4_task add lock_state varchar2(2) default('0');

comment on column jbpm4_task.lock_state is '该任务是否锁定（1：锁定，0：解锁）';


create table JBPM4_BIZ_TAB 
(
   ID                   number(18)                     not null primary key,
   biz_tab_name         varchar2(200)                  null,
   biz_type             varchar2(50)                   null,
   biz_inf_id           varchar2(50)                   null,
   biz_inf_name         varchar2(500)                  null,
   biz_task_type        VARCHAR2(2)                    null,
   pro_instance_id      varchar2(200)                  not null,
   START_PRO_USERID     varchar2(20)                   null,
   pro_instance_state   VARCHAR2(2)                    null,
   task_state           VARCHAR2(2)                    null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   OWNER_ID             VARCHAR2(20)                   null,
   ORG_ID               VARCHAR2(20)                   null,
   CREATE_TIME          TIMESTAMP(6) 									 null,
   MODIFY_TIME          TIMESTAMP(6)                   null,
   CREATE_BY            VARCHAR2(20)                   null,
   MODIFY_BY            VARCHAR2(20)                   null,
   remark               varchar2(500)                  null
);

comment on table JBPM4_BIZ_TAB is 
'工作流与业务表关联表';

comment on column JBPM4_BIZ_TAB.ID is 
'主键ID';

comment on column JBPM4_BIZ_TAB.biz_tab_name is 
'业务表名称';

comment on column JBPM4_BIZ_TAB.biz_type is 
'业务类型';

comment on column JBPM4_BIZ_TAB.biz_inf_id is 
'业务表主键ID';

comment on column JBPM4_BIZ_TAB.biz_inf_name is 
'业务任务名称';

comment on column JBPM4_BIZ_TAB.biz_task_type is 
'业务任务状态';

comment on column JBPM4_BIZ_TAB.pro_instance_id is 
'主流程实例ID';

comment on column JBPM4_BIZ_TAB.START_PRO_USERID is 
'流程发起者';

comment on column JBPM4_BIZ_TAB.pro_instance_state is 
'流程实例状态（-1：异常终止，0：暂停，1：正常）';

comment on column JBPM4_BIZ_TAB.task_state is 
'当前流程实例下的任务状态（0：解锁，1：加锁）';

comment on column JBPM4_BIZ_TAB.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column JBPM4_BIZ_TAB.OWNER_ID is 
'业务归属人';

comment on column JBPM4_BIZ_TAB.ORG_ID is 
'业务所属机构
';

comment on column JBPM4_BIZ_TAB.CREATE_TIME is 
'创建时间
';

comment on column JBPM4_BIZ_TAB.MODIFY_TIME is 
'修改时间
';

comment on column JBPM4_BIZ_TAB.CREATE_BY is 
'创建人
';

comment on column JBPM4_BIZ_TAB.MODIFY_BY is 
'修改人
';

comment on column JBPM4_BIZ_TAB.remark is 
'备注';

alter table jbpm4_biz_tab add is_hidden varchar2(2);
comment on column JBPM4_BIZ_TAB.is_hidden is '是否隐藏待办';


create table jbpm4_biz_option_info 
(
   ID                   number(18)                     not null primary key,
   fk_jbpm_biz_tab_id   varchar2(20)                   null,
   pro_instance_id      varchar2(200)                  null,
   task_id              varchar2(20)                   null,
   active_name          varchar2(200)                  null,
   system_active_info   varchar2(200)                  null,
   option_remark        varchar2(4000)                 null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   OWNER_ID             varchar2(20)                   null,
   ORG_ID               varchar2(20)                   null,
   CREATE_TIME          TIMESTAMP(6)									 null,
   MODIFY_TIME          TIMESTAMP(6)                   null,
   CREATE_BY            varchar2(20)                   null,
   MODIFY_BY            varchar2(20)                   null,
   remark               varchar2(500)                  null
);

comment on table jbpm4_biz_option_info is 
'业务流程节点意见表';

comment on column jbpm4_biz_option_info.ID is 
'主键';

comment on column jbpm4_biz_option_info.fk_jbpm_biz_tab_id is 
'关联jbpm4_biz_tab.id';

comment on column jbpm4_biz_option_info.pro_instance_id is 
'主流程实例ID';

comment on column jbpm4_biz_option_info.task_id is 
'任务ID';

comment on column jbpm4_biz_option_info.active_name is 
'环节(节点名称)';

comment on column jbpm4_biz_option_info.system_active_info is 
'系统在该环节的描述';

comment on column jbpm4_biz_option_info.option_remark is 
'意见信息（备注）';

comment on column jbpm4_biz_option_info.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column jbpm4_biz_option_info.OWNER_ID is 
'业务归属人';

comment on column jbpm4_biz_option_info.ORG_ID is 
'业务所属机构
';

comment on column jbpm4_biz_option_info.CREATE_TIME is 
'创建时间
';

comment on column jbpm4_biz_option_info.MODIFY_TIME is 
'修改时间
';

comment on column jbpm4_biz_option_info.CREATE_BY is 
'创建人
';

comment on column jbpm4_biz_option_info.MODIFY_BY is 
'修改人
';

comment on column jbpm4_biz_option_info.remark is 
'备注';
create table jbpm4_consign_person 
(
   ID                   number(18)                     not null primary key,
   from_user_id         varchar2(50)                   null,
   to_user_id           varchar2(50)                   null,
   pro_def_key          varchar2(200)                  null,
   type                 VARCHAR2(2)                    null,
   reason               varchar2(4000)                 null,
   start_time           date                           null,
   end_time             date                           null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   CREATED_BY           varchar2(20)                   null,
   CREATED              date                           null,
   LAST_UPD_BY          varchar2(20)                   null,
   LAST_UPD             date                           null,
   REMARK               varchar2(500)                  null
);

comment on table jbpm4_consign_person is 
'设置任务委托表';

comment on column jbpm4_consign_person.ID is 
'主键ID';

comment on column jbpm4_consign_person.from_user_id is 
'委托人的ID(须检测与to_user_id在交叉时间段内不能重复)';

comment on column jbpm4_consign_person.to_user_id is 
'接受委托的人ID';

comment on column jbpm4_consign_person.pro_def_key is 
'流程定义key';

comment on column jbpm4_consign_person.type is 
'委托类型（1：请假委托，2：离职委托，3：代理委托）';

comment on column jbpm4_consign_person.reason is 
'事由';

comment on column jbpm4_consign_person.start_time is 
'委托开始时间';

comment on column jbpm4_consign_person.end_time is 
'委托结束时间';

comment on column jbpm4_consign_person.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column jbpm4_consign_person.CREATED_BY is 
'新增人';

comment on column jbpm4_consign_person.CREATED is 
'新增时间';

comment on column jbpm4_consign_person.LAST_UPD_BY is 
'修改人';

comment on column jbpm4_consign_person.LAST_UPD is 
'修改时间';

comment on column jbpm4_consign_person.REMARK is 
'备注';

/*==============================================================*/
/* Table: jbpm4_consigned_task                                  */
/*==============================================================*/
create table jbpm4_consigned_task 
(
   ID                   number(18)                     not null primary key,
   task_id              varchar2(50)                   null,
   task_type            VARCHAR2(2)                    null,
   from_user_id         varchar2(20)                   null,
   to_user_id           varchar2(20)                   null,
   ORG_ID               varchar2(20)                   null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   CREATED_BY           varchar2(20)                   null,
   CREATED              date                           null,
   LAST_UPD_BY          varchar2(20)                   null,
   LAST_UPD             date                           null,
   REMARK               varchar2(500)                  null
);

comment on table jbpm4_consigned_task is 
'已经委托的任务表(同时记录任务部分转移轨迹)';

comment on column jbpm4_consigned_task.ID is 
'主键ID';

comment on column jbpm4_consigned_task.task_id is 
'任务ID';

comment on column jbpm4_consigned_task.task_type is 
'任务类型';

comment on column jbpm4_consigned_task.from_user_id is 
'委托人ID';

comment on column jbpm4_consigned_task.to_user_id is 
'接受委托的人ID';

comment on column jbpm4_consigned_task.ORG_ID is 
'业务所属机构';

comment on column jbpm4_consigned_task.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column jbpm4_consigned_task.CREATED_BY is 
'新增人';

comment on column jbpm4_consigned_task.CREATED is 
'新增时间';

comment on column jbpm4_consigned_task.LAST_UPD_BY is 
'修改人';

comment on column jbpm4_consigned_task.LAST_UPD is 
'修改时间';

comment on column jbpm4_consigned_task.REMARK is 
'备注';

/*==============================================================*/
/* Table: jbpm4_form_info                                       */
/*==============================================================*/
create table jbpm4_form_info
(
  ID                  NUMBER(18) not null primary key,
  pro_def_key          VARCHAR2(500),
  pro_activity_name   VARCHAR2(500),
  pro_activity_form   VARCHAR2(500),
  participator_type   VARCHAR2(100),
  branch_type         VARCHAR2(20),
  other_params        VARCHAR2(4000),
  rule_info           VARCHAR2(4000),
  create_time          date,
  remark              VARCHAR2(500),
  EXT1                VARCHAR2(500),
  EXT2                VARCHAR2(500),
  EXT3                VARCHAR2(500),
  pro_level			  varchar2(20),
  validate_state varchar2(2)
);

comment on table jbpm4_form_info is '工作流表单配置表';
COMMENT ON COLUMN jbpm4_form_info.ID IS 'ID';
COMMENT ON COLUMN jbpm4_form_info.pro_def_key IS '流程定义key';
COMMENT ON COLUMN jbpm4_form_info.pro_activity_name IS '节点名称';
COMMENT ON COLUMN jbpm4_form_info.pro_activity_form IS '节点所挂表单';
COMMENT ON COLUMN jbpm4_form_info.participator_type IS '参与者类型';
COMMENT ON COLUMN jbpm4_form_info.other_params IS '当前节点其他参数信息';
COMMENT ON COLUMN jbpm4_form_info.remark IS '备注';
COMMENT ON COLUMN jbpm4_form_info.rule_info IS '规则定义';
comment on column JBPM4_FORM_INFO.VALIDATE_STATE is '数据有效性（1：有效、0：无效）';
comment on column JBPM4_FORM_INFO.pro_level is '流程的版本信息';

/*==============================================================*/
/* Table: jbpm4_rule_info                                       */
/*==============================================================*/
create table jbpm4_rule_info 
(
   ID                   number(18)                     not null primary key,
   fk_rule_id           varchar2(50)                   null,
   rule_state           VARCHAR2(2)                    null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   CREATED_BY           varchar2(20)                   null,
   CREATED              date                           null,
   LAST_UPD_BY          varchar2(20)                   null,
   LAST_UPD             date                           null,
   REMARK               varchar2(500)                  null 
);

comment on table jbpm4_rule_info is 
'工作流规则配置表';

comment on column jbpm4_rule_info.ID is 
'主键ID';

comment on column jbpm4_rule_info.fk_rule_id is 
'业务规则表主键ID';

comment on column jbpm4_rule_info.rule_state is 
'业务规则状态（-1：失效，0：非必填，1：必填）';

comment on column jbpm4_rule_info.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column jbpm4_rule_info.CREATED_BY is 
'新增人';

comment on column jbpm4_rule_info.CREATED is 
'新增时间';

comment on column jbpm4_rule_info.LAST_UPD_BY is 
'修改人';

comment on column jbpm4_rule_info.LAST_UPD is 
'修改时间';

comment on column jbpm4_rule_info.REMARK is 
'备注';


create table temporary_jbpm4_info 
(
    id                   number(18),
    xml_content          clob,
    validate_state       varchar2(2),
    last_upd_by          varchar2(20),
    last_upd             date,
    created_by           varchar2(20),
    created              date,
    remark               varchar2(500),
    ext1                 varchar2(200),
    pro_png blob
);

comment on table temporary_jbpm4_info is 
'工作流暂存表';

comment on column temporary_jbpm4_info.id is 
'主键';

comment on column temporary_jbpm4_info.xml_content is 
'流程信息';

comment on column temporary_jbpm4_info.validate_state is 
'数据有效性（1：有效、0：无效）';

comment on column temporary_jbpm4_info.last_upd_by is 
'修改人';

comment on column temporary_jbpm4_info.last_upd is 
'修改时间';

comment on column temporary_jbpm4_info.created_by is 
'新增人';

comment on column temporary_jbpm4_info.created is 
'新增时间';

comment on column temporary_jbpm4_info.remark is 
'备注';

comment on column temporary_jbpm4_info.ext1 is 
'扩展字段';


alter table temporary_jbpm4_info add biz_file  varchar2(4000);

alter table temporary_jbpm4_info add proc_name  varchar2(4000);

alter table temporary_jbpm4_info add proc_code  varchar2(4000);

alter table temporary_jbpm4_info add proc_version  varchar2(4000);


comment on column  temporary_jbpm4_info.biz_file is '业务目录';

comment on column  temporary_jbpm4_info.proc_name is '流程名称';

comment on column  temporary_jbpm4_info.proc_code is '流程编码';
comment on column  temporary_jbpm4_info.proc_version is '流程版本号';


comment on column temporary_jbpm4_info.pro_png is '流程图片（只有成功发布的流程才会生成图片）';

alter table  jbpm4_biz_tab modify pro_instance_state varchar2(2) default('1');
comment on column jbpm4_biz_tab.pro_instance_state is '流程实例状态（1：正常，0：暂停）';

alter table jbpm4_biz_tab add  biz_inf_no varchar2(100);
comment on column jbpm4_biz_tab.biz_inf_no is '业务编号';
alter table jbpm4_biz_tab add  over_time TIMESTAMP(6);
comment on column jbpm4_biz_tab.over_time is '超时提醒时间';
alter table jbpm4_biz_tab add  remind_time TIMESTAMP(6);
comment on column jbpm4_biz_tab.remind_time is '提醒时间';

alter table JBPM4_FORM_INFO rename column BRANCH_TYPE to part_TYPE;
alter table JBPM4_FORM_INFO modify part_TYPE VARCHAR2(50);
comment on column JBPM4_FORM_INFO.part_TYPE is '人工选择参与者时，配置的规则（1：机构，2：人员，0：角色）';

create table LB_T_LEAVE_INFO
(
  id             NUMBER(18) not null,
  leave_user_id  VARCHAR2(20),
  user_level     VARCHAR2(200),
  user_name      VARCHAR2(50),
  org_name       VARCHAR2(200),
  reason         VARCHAR2(500),
  email          VARCHAR2(100),
  status         VARCHAR2(4),
  start_time     DATE,
  end_time       DATE,
  leave_type     VARCHAR2(2),
  validate_state VARCHAR2(2),
  owner_id       NUMBER(16),
  create_time    TIMESTAMP(6),
  modify_time    TIMESTAMP(6),
  create_by      NUMBER(16),
  remark         VARCHAR2(500),
  modify_by      NUMBER(16),
  org_id         NUMBER(18)
)
;
comment on table LB_T_LEAVE_INFO
  is '员工请假表';
comment on column LB_T_LEAVE_INFO.id
  is '主键ID';
comment on column LB_T_LEAVE_INFO.leave_user_id
  is '请假人ID';
comment on column LB_T_LEAVE_INFO.user_level
  is '员工职级';
comment on column LB_T_LEAVE_INFO.user_name
  is '员工姓名';
comment on column LB_T_LEAVE_INFO.org_name
  is '业务所属机构
';
comment on column LB_T_LEAVE_INFO.reason
  is '事由';
comment on column LB_T_LEAVE_INFO.email
  is '电子邮箱';
comment on column LB_T_LEAVE_INFO.status
  is '状态（1：正常   2：请假中）';
comment on column LB_T_LEAVE_INFO.start_time
  is '请假开始时间';
comment on column LB_T_LEAVE_INFO.end_time
  is '请假结束时间';
comment on column LB_T_LEAVE_INFO.leave_type
  is '请假类型（1：代理请假，2：本人请假）';
comment on column LB_T_LEAVE_INFO.validate_state
  is '数据有效性（1：有效、0：无效）';
comment on column LB_T_LEAVE_INFO.owner_id
  is '业务归属人';
comment on column LB_T_LEAVE_INFO.create_time
  is '创建时间
';
comment on column LB_T_LEAVE_INFO.modify_time
  is '修改时间
';
comment on column LB_T_LEAVE_INFO.create_by
  is '创建人
';
comment on column LB_T_LEAVE_INFO.remark
  is '备注';
comment on column LB_T_LEAVE_INFO.modify_by
  is '修改人
';
comment on column LB_T_LEAVE_INFO.org_id
  is '业务所属机构
';
alter table LB_T_LEAVE_INFO  add constraint PK_LB_T_LEAVE_INFO primary key (ID);

create table BIZ_CALENDAR
(
  id             NUMBER(18) not null,
  holidays       VARCHAR2(20),
  validate_state VARCHAR2(2),
  created_by     VARCHAR2(20),
  created        DATE,
  last_upd_by    VARCHAR2(20),
  last_upd       DATE,
  remark         VARCHAR2(500)
)
;
comment on table BIZ_CALENDAR
  is '假日表';
comment on column BIZ_CALENDAR.id
  is '主键ID';
comment on column BIZ_CALENDAR.holidays
  is '假期（如：2014-09-10）';
comment on column BIZ_CALENDAR.validate_state
  is '数据有效性（1：有效、0：无效）';
comment on column BIZ_CALENDAR.created_by
  is '新增人';
comment on column BIZ_CALENDAR.created
  is '新增时间';
comment on column BIZ_CALENDAR.last_upd_by
  is '修改人';
comment on column BIZ_CALENDAR.last_upd
  is '修改时间';
comment on column BIZ_CALENDAR.remark
  is '备注';
create unique index SYS_BIZ_CAL on BIZ_CALENDAR (HOLIDAYS);
alter table BIZ_CALENDAR add primary key (ID);
comment on column jbpm4_biz_tab.task_state is '流程实例紧急度（1:提醒，2：超时）';

--流程监控数据权限控制
create table JBPM4_ROLE_MAPPING 
(
   ID                   number(18)                     not null primary key,
   ROLE_CODE            varchar2(50)                   null,
   MAPPING_ROLE_CODE    varchar2(50)                   null,  
   VALIDATE_STATE       VARCHAR2(2)                    null  
);

comment on table JBPM4_ROLE_MAPPING is 
'流程监控数据权限映射表';

comment on column JBPM4_ROLE_MAPPING.ID is 
'主键ID';

comment on column JBPM4_ROLE_MAPPING.ROLE_CODE is 
'角色编码';

comment on column JBPM4_ROLE_MAPPING.MAPPING_ROLE_CODE is 
'映射角色编码';

comment on column JBPM4_ROLE_MAPPING.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

create sequence SEQ_jbpm4_role_mapping start with 1 increment by 1 ;

create sequence SEQ_BIZ_CALENDAR
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_LB_T_LEAVE_INFO
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_temporary_jbpm4_info
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;


create sequence SEQ_JBPM4_BIZ_TAB
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_jbpm4_biz_option_info
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_jbpm4_form_info
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_jbpm4_rule_info
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_jbpm4_consign_person
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;


create sequence SEQ_jbpm4_consigned_task
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

-----创建索引优化SQL
create index  idx_JBPM4_BIZ_TAB_inf_name on  JBPM4_BIZ_TAB(biz_inf_name);
create index  idx_jbpm4_hist_actinst_exec on  jbpm4_hist_actinst(execution_);

create index IDX_HIST_PRO_000 on JBPM4_HIST_PROCINST (ID_) ;
create index IDX_HIST_PRO_001 on JBPM4_HIST_PROCINST (END_) ;
create index IDX_HIST_PRO_002 on JBPM4_HIST_PROCINST (START_);

create index IDX_HIST_TASK_000 on JBPM4_HIST_TASK (END_) ;
create index IDX_HIST_TASK_002 on JBPM4_HIST_TASK (STATE_) ;
create index IDX_HIST_TASK_003 on JBPM4_HIST_TASK (EXECUTION_) ;
create index IDX_HIST_TASK_004 on JBPM4_HIST_TASK (ASSIGNEE_) ;
create index IDX_HSUPERT_SUB on JBPM4_HIST_TASK (SUPERTASK_);

-------------------------------创建触发器 start
create or replace trigger tri_jbpm4_hist_procinst
/*
* 触发 保存 主流程实例id （main_id_）  信息
* 实现 结束的历史 主子流程信息查询
* 先查询 处其主流程实例id，有则保存，
*　否则　不保存
*　
*
*
*/

before  insert
on jbpm4_hist_procinst
for each row
declare

v_main_id       jbpm4_hist_procinst.main_id_%type;
v_main_superexec_           jbpm4_execution.superexec_%type;

v_temp   jbpm4_execution%rowtype;
cursor my_cur is

select t2.*
       from
  jbpm4_execution t1,
  jbpm4_execution t2
   where
   t1.superexec_ = t2.dbid_
   and t1.id_ =:new.id_;
begin
--- 查询 该流程是否有 主流程

for v_temp in my_cur loop
    v_main_id := v_temp.id_;
    :new.main_id_ := v_main_id;

end loop;

exception when others then
dbms_output.put_line('-----tri_jbpm4_hist_procinst------');

end;
/
-------------------------------创建触发器 end