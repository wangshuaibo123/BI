ALTER TABLE SYS_USER ADD(ANNUAL_LEAVE NUMBER(2));
comment on column SYS_USER.ANNUAL_LEAVE is '标准年假';
 
ALTER TABLE SYS_USER_HIS ADD(ANNUAL_LEAVE NUMBER(2));
comment on column SYS_USER_HIS.ANNUAL_LEAVE is '标准年假';
 
ALTER TABLE SYS_USER_SYN ADD(ANNUAL_LEAVE NUMBER(2));
comment on column SYS_USER_SYN.ANNUAL_LEAVE is '标准年假';