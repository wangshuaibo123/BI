/*==============================================================*/
/* Table: quartz_task_group_def                                 */
/*==============================================================*/
create table quartz_task_group_def 
(
   ID                   number(18)                     not null primary key,
   group_id             VARCHAR2(50)                   null,
   group_Name           VARCHAR2(40)                   null,
   group_state          VARCHAR2(40)                   null,
   Task_Id              varchar2(20)                   not null,
   Task_Name            VARCHAR2(100)                  null,
   bean_id              varchar2(100)                  null,
   Deal_Step            number                         null,
   Pre_Step             varchar2(20)                   null,
   pre_step_state       VARCHAR2(2)                    null,
   Auto_Exec            VARCHAR2(2)                    null,
   Deal_Chance          VARCHAR(10)                    null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   CREATED_BY           varchar2(20)                   null,
   CREATED              date                           null,
   LAST_UPD_BY          varchar2(20)                   null,
   LAST_UPD             date                           null,
   REMARK               varchar2(500)                  null 
);

comment on table quartz_task_group_def is 
'任务分组定义表';

comment on column quartz_task_group_def.ID is 
'主键ID';

comment on column quartz_task_group_def.group_id is 
'分组处理编号';

comment on column quartz_task_group_def.group_Name is 
'分组处理名称';

comment on column quartz_task_group_def.group_state is 
'分组任务是否发布（1：发布，0：待发布）';

comment on column quartz_task_group_def.Task_Id is 
'任务编号';

comment on column quartz_task_group_def.Task_Name is 
'任务描述';

comment on column quartz_task_group_def.bean_id is 
'任务类名（实体beanID）';

comment on column quartz_task_group_def.Deal_Step is 
'执行步骤(顺序序号)';

comment on column quartz_task_group_def.Pre_Step is 
'前提步骤（任务编号）';

comment on column quartz_task_group_def.pre_step_state is 
'前置步骤返回状态（1：成功，0：是失败）';

comment on column quartz_task_group_def.Auto_Exec is 
'是否自动执行（1：自动，0：手动）';

comment on column quartz_task_group_def.Deal_Chance is 
'执行时机（day:每天，year：每年12月31日，norun:不执行）';

comment on column quartz_task_group_def.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column quartz_task_group_def.CREATED_BY is 
'新增人';

comment on column quartz_task_group_def.CREATED is 
'新增时间';

comment on column quartz_task_group_def.LAST_UPD_BY is 
'修改人';

comment on column quartz_task_group_def.LAST_UPD is 
'修改时间';

comment on column quartz_task_group_def.REMARK is 
'备注';

/*==============================================================*/
/* Table: quartz_task_group_instance                            */
/*==============================================================*/
create table quartz_task_group_instance 
(
   ID                   number(18)                     not null primary key,
   group_id             VARCHAR2(50)                   null,
   group_Name           VARCHAR2(40)                   null,
   batch_No             VARCHAR2(40)                   null,
   Task_Id              varchar2(20)                   not null,
   Task_Name            VARCHAR2(100)                  null,
   bean_id              varchar2(100)                  null,
   Deal_Step            number                         null,
   Pre_Step             varchar2(20)                   null,
   pre_step_state       VARCHAR2(2)                    null,
   Auto_Exec            VARCHAR2(2)                    null,
   Deal_Chance          VARCHAR(10)                    null,
   task_ins_state       VARCHAR2(2)                    null,
   bug_continue         VARCHAR2(2)                    null,
   is_end               VARCHAR2(2)                    null,
   VALIDATE_STATE       VARCHAR2(2)                    null,
   CREATED              date                           null,
   LAST_UPD             date                           null,
   REMARK               varchar2(500)                  null 
);

comment on table quartz_task_group_instance is 
'任务分组实例表';

comment on column quartz_task_group_instance.ID is 
'主键ID';

comment on column quartz_task_group_instance.group_id is 
'分组处理编号';

comment on column quartz_task_group_instance.group_Name is 
'分组处理名称';

comment on column quartz_task_group_instance.batch_No is 
'批次号码';

comment on column quartz_task_group_instance.Task_Id is 
'任务编号';

comment on column quartz_task_group_instance.Task_Name is 
'任务描述';

comment on column quartz_task_group_instance.bean_id is 
'任务类名（实体beanID）';

comment on column quartz_task_group_instance.Deal_Step is 
'执行步骤(顺序序号)';

comment on column quartz_task_group_instance.Pre_Step is 
'前提步骤（任务编号）';

comment on column quartz_task_group_instance.pre_step_state is 
'前置步骤返回状态（1：成功，0：是失败）';

comment on column quartz_task_group_instance.Auto_Exec is 
'是否自动执行（1：自动，0：手动）';

comment on column quartz_task_group_instance.Deal_Chance is 
'执行时机（day:每天，year：每年12月31日，norun:不执行）';

comment on column quartz_task_group_instance.task_ins_state is 
'任务实例执行状态（1：成功：0：失败）';

comment on column quartz_task_group_instance.bug_continue is 
'失败后，断点执行（1：是，0：否）';

comment on column quartz_task_group_instance.is_end is 
'任务是否执行（0：执行，1：未执行）';

comment on column quartz_task_group_instance.VALIDATE_STATE is 
'数据有效性（1：有效、0：无效）';

comment on column quartz_task_group_instance.CREATED is 
'新增时间';

comment on column quartz_task_group_instance.LAST_UPD is 
'修改时间';

comment on column quartz_task_group_instance.REMARK is 
'备注';

/*==============================================================*/
/* Table: quartz_task_his                                       */
/*==============================================================*/
create table quartz_task_his 
(
   ID                   number(18)                     not null primary key,
   batch_No             varchar2(50)                   null,
   group_id             varchar2(50)                   null,
   thread_id            varchar2(50)                   null,
   Task_Id              varchar2(20)                   not null,
   bean_id              varchar2(100)                  null,
   task_state           varchar2(2)                    null,
   task_start_time      timestamp                      null,
   task_end_time        timestamp                      null,
   task_info            varchar2(4000)                 null,
   error_info           varchar2(500)                  null 
);

comment on table quartz_task_his is 
'定时任务执行轨迹表';

comment on column quartz_task_his.ID is 
'主键ID';

comment on column quartz_task_his.batch_No is 
'批次号码';

comment on column quartz_task_his.group_id is 
'分组编码';

comment on column quartz_task_his.thread_id is 
'执行任务的主线程ID';

comment on column quartz_task_his.Task_Id is 
'任务编号';

comment on column quartz_task_his.bean_id is 
'任务类名（实体beanID）';

comment on column quartz_task_his.task_state is 
'任务执行返回结果（1：成功，0：是失败）';

comment on column quartz_task_his.task_start_time is 
'任务执行开始时间';

comment on column quartz_task_his.task_end_time is 
'任务执行结束时间';

comment on column quartz_task_his.task_info is 
'任务执行中信息描述';

comment on column quartz_task_his.error_info is 
'任务执行失败，错误描述';

alter table quartz_task_group_instance add person_set_start_time varchar2(50);
comment on column  quartz_task_group_instance.person_set_start_time is '人工设置的手动执行时间（如：2014-10-10 10:10:00）';

alter table quartz_task_group_instance add TASK_START_TIME varchar2(20);
comment on column  quartz_task_group_instance.TASK_START_TIME is '任务开始时间';

alter table quartz_task_group_instance add TASK_END_TIME varchar2(20);
comment on column  quartz_task_group_instance.TASK_END_TIME is '任务结束时间';

alter table QUARTZ_TASK_GROUP_INSTANCE add fail_times int default 0;
comment on column  quartz_task_group_instance.fail_times is '失败次数';

alter table QUARTZ_TASK_GROUP_DEF add RUN_TIME  varchar2(10);
comment on column  QUARTZ_TASK_GROUP_DEF.RUN_TIME is '执行时间';

alter table QUARTZ_TASK_GROUP_DEF add IS_END varchar2(1) default 0;
comment on column  QUARTZ_TASK_GROUP_DEF.IS_END is '是否执行';

alter table QUARTZ_TASK_GROUP_DEF modify PRE_STEP varchar2(100);

alter table QUARTZ_TASK_GROUP_INSTANCE  modify PRE_STEP varchar2(100);

create sequence SEQ_quartz_task_group_def
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

create sequence SEQ_quartz_task_group_instance
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;


create sequence SEQ_quartz_task_his
minvalue 1
maxvalue 99999999
start with 1
increment by 1
cache 20;

alter table quartz_task_his modify error_info varchar2(4000);
