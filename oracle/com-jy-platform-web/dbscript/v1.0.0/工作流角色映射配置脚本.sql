--���̼������Ȩ�޿���
create table JBPM4_ROLE_MAPPING 
(
   ID                   number(18)                     not null primary key,
   ROLE_CODE            varchar2(50)                   null,
   MAPPING_ROLE_CODE    varchar2(50)                   null,  
   VALIDATE_STATE       VARCHAR2(2)                    null  
);

comment on table JBPM4_ROLE_MAPPING is 
'���̼������Ȩ��ӳ���';

comment on column JBPM4_ROLE_MAPPING.ID is 
'����ID';

comment on column JBPM4_ROLE_MAPPING.ROLE_CODE is 
'��ɫ����';

comment on column JBPM4_ROLE_MAPPING.MAPPING_ROLE_CODE is 
'ӳ���ɫ����';

comment on column JBPM4_ROLE_MAPPING.VALIDATE_STATE is 
'������Ч�ԣ�1����Ч��0����Ч��';

create sequence SEQ_jbpm4_role_mapping minvalue 1 maxvalue 99999999999 start with 1 increment by 1 ;