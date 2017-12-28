insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='SYSTEMFLAG'),'捷越联合核心系统接口','S007', '7', '1', null);
commit;

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='SYSTEMFLAG'),'捷越联合日志监控系统','S008', '8', '1', null);
commit;

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='RELATIONTYPE'),'本人','0', '0', '1', null);
commit;

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='LOANFUNDS'),'58同城','58TC', '10', '1', null);
commit;

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='LOANFUNDS'),'华澳信托','HAXT', '11', '1', null);
commit;

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='SYSTEMFLAG'),'捷越联合押品系统','S009', '9', '1', null);
commit;


insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='UNITNATURE'),'上市企业','6', '6', '1', null);

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='UNITNATURE'),'外资企业','7', '7', '1', null);

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='UNITNATURE'),'合资企业','8', '8', '1', null);

insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='UNITNATURE'),'个体工商户','9', '9', '1', null);

commit;


insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values 
(seq_sys_dict_detail.nextval, (select max(id) id from sys_dict where dict_code='SYSTEMFLAG'),'捷越联合快速录单系统','S010', '10', '1', null);
commit;