-- Create table
create table leave_demo_info
(
  ID                 NUMBER(18) not null primary key ,
  app_name 					VARCHAR2(50),
  app_reason    VARCHAR2(200),
  app_day            VARCHAR2(20),
  VALIDATE_STATE     VARCHAR2(2),
  app_state           VARCHAR2(20),
  CREATE_TIME        TIMESTAMP(6),
  MODIFY_TIME        TIMESTAMP(6),
  CREATE_BY          VARCHAR2(20),
  MODIFY_BY          VARCHAR2(20),
  REMARK             VARCHAR2(500)
);
-- Add comments to the table 
comment on table leave_demo_info
  is '申请请假表';
-- Add comments to the columns 
comment on column leave_demo_info.ID
  is '主键';
comment on column leave_demo_info.app_name
  is '申请人姓名';
comment on column leave_demo_info.app_reason
  is '请假原因';
comment on column leave_demo_info.app_day
  is '申请请假天数';
comment on column leave_demo_info.VALIDATE_STATE
  is '数据有效性（1：有效、0：无效）';
comment on column leave_demo_info.app_state
  is '是否批准';
comment on column leave_demo_info.CREATE_TIME
  is '创建时间
';
comment on column leave_demo_info.MODIFY_TIME
  is '修改时间
';
comment on column leave_demo_info.CREATE_BY
  is '创建人
';
comment on column leave_demo_info.MODIFY_BY
  is '修改人
';
comment on column leave_demo_info.REMARK
  is '备注'; 

create sequence seq_leave_demo_info start with 1 increment by 1;