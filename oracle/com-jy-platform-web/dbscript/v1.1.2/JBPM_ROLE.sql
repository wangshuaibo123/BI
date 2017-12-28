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

create sequence SEQ_jbpm4_role_mapping minvalue 1 maxvalue 99999999999 start with 1 increment by 1 ;