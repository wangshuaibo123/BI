declare
	dictid number:=0;
begin

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_POSITION_SEQUENCE', '岗位序列', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '管理序列', '01', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '信息技术序列', '02', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '销售序列', '03', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '业务运营序列', '04', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '职能支撑序列', '05', '2', '1', null);

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_POST_CODE', '职级', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '总裁', '100', '0', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '副总裁', '110', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '总监', '120', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '副总监', '130', '4', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '高级经理', '149', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '经理', '150', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '副经理', '160', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '主管', '180', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '助理', '190', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '专员', '198', '5', '1', null);
	
	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_POLITICAL_STATUS', '政治面貌', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '群众', '00', '0', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '中共党员', '01', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '预备党员', '02', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '共青团员', '03', '4', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '民主党派', '12', '5', '1', null);

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_USER_RELATION', '员工关系', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '正式员工', '001', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '试用员工', '002', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '停薪留职', '003', '3', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '长期病假', '007', '7', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '离休', '012', '12', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '返聘', '013', '13', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '失踪', '030', '30', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '待分配', '035', '35', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '长学', 'S001', '101', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '实习', 'S002', '102', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '实习终止', 'S003', '103', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '劳务派遣', 'S004', '104', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '派遣终止', 'S005', '105', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '临时工', 'S006', '106', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '临时工终止', 'S007', '107', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '返聘终止', 'S008', '108', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '离职', 'S009', '109', '1', null);

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_EDUCATION', '员工学历', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '初中以下', '01', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '高中', '02', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '中专', '03', '3', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '大学专科', '04', '4', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '大学本科', '05', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '硕士', '06', '6', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '博士', '07', '7', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '博士后', '08', '8', '1', null);

	dictid:=0;
	select seq_sys_dict.nextval into dictid from dual;
	insert into sys_dict (ID, DICT_CODE, DICT_NAME, DICT_TYPE, VALIDATE_STATE, VERSION) values (dictid, 'MD_ORG_LEVEL', '机构层级', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '集团', '0', '1', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '分支机构', '1', '2', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '区域', '2', '3', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '分公司/分部', '3', '4', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '营业部', '4', '5', '1', null);
	insert into sys_dict_detail (ID, DICT_ID, DICT_DETAIL_NAME, DICT_DETAIL_VALUE, ORDER_BY, VALIDATE_STATE, VERSION)	values (seq_sys_dict_detail.nextval, dictid, '部门', '5', '6', '1', null);
	
	commit; 
end; 
/
