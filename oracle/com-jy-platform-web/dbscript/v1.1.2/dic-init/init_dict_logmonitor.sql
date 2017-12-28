--初始化系统数据字典的脚本
declare
	dictid number:=0;     
begin

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE,VALIDATE_STATE, VERSION) values (dictid,'LOG_LEVEL','日志级别', '0', '1', null) ;
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'一般','1', '1', '1', null); 
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'资讯','2', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'一般提醒','3', '3', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'提醒','4', '4', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'一般警告','5', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'警告','6', '6', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'一般严重','7', '7', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'严重','8', '8', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'灾难','9', '9', '1', null);
	
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE,VALIDATE_STATE, VERSION) values (dictid,'LOG_RATE_UNIT','时间单位', '0', '1', null) ;
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'分钟','1', '1', '1', null); 
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION) values (seq_sys_dict_detail.nextval, dictid,'小时','2', '2', '1', null); 
	commit; 
end;
/