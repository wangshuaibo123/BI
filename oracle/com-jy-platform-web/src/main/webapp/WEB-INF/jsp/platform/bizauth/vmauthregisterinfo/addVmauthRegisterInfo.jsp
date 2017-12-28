<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>新增权限注册表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="addNewsFormData" name="addNewsFormData" isCheck="true" action="com.jy.modules.platform.bizauth.vmauthregisterinfo.controller.VmauthRegisterInfoController">
 <table id="addNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 虚拟树代码 ：</th>
  <td > 
  <input type="text" class="text" id="dtovmtreeCode" name="vmtreeCode" notNull="false" maxLength="25" value="" onblur="changeTableName()"/>
  </td>
  <th> 虚拟树名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtovmtreeName" name="vmtreeName" notNull="false" maxLength="50" value="" />
  </td>
</tr>
<tr>
  <th> 映射表名 ：</th>
  <td > 
  <input type="text" class="text" id="dtomapTabName" name="mapTabName" notNull="false" maxLength="50" value="" readonly="readonly" />
  </td>
  <th> 数据权限表名 ：</th>
  <td > 
  <input type="text" class="text" id="dtodataTabName" name="dataTabName" notNull="false" maxLength="50" value="" readonly="readonly"  />
  </td>
</tr>
<tr>
  <th> 创建映射表SQL：</th>
  <td colspan="3"> 
  <textarea id="dtomapInitSql" name="mapInitSql"  rows="5" cols="70" readonly="readonly"></textarea>
  </td>
</tr>
<tr>
  <th> 创建数据权限表SQL：</th>
  <td colspan="3"> 
  <textarea id="dtodataInitSql" name="dataInitSql" rows="5" cols="70" readonly="readonly"></textarea>
  </td>
</tr>

</table>

  <div id="dtodataInitSqlDIV" style="display: none">
  create table BIZ2_VMDATA_PRIV
(
  ID          NUMBER(18) not null primary key,
  USER_ID     NUMBER(18),
  OWNER_ID    NUMBER(18),
  ORG_ID      NUMBER(18),
  BIZ_ID      NUMBER(18),
  ORG_TYPE    VARCHAR2(50) default 'BIZ2',
  CREATE_TIME TIMESTAMP(6),
  CREATE_BY   NUMBER(18),
  VMRULE_MAPPING_ID NUMBER(18) 
);
comment on table BIZ2_VMDATA_PRIV is '数据权限表';
comment on column BIZ2_VMDATA_PRIV.ID is '主键ID';
comment on column BIZ2_VMDATA_PRIV.USER_ID is '用户ID';
comment on column BIZ2_VMDATA_PRIV.OWNER_ID is '数据归属人ID';
comment on column BIZ2_VMDATA_PRIV.ORG_ID   is '数据归属机构ID';
comment on column BIZ2_VMDATA_PRIV.BIZ_ID is '业务表主键ID';
comment on column BIZ2_VMDATA_PRIV.CREATE_TIME is '创建时间';
comment on column BIZ2_VMDATA_PRIV.CREATE_BY  is '创建人';
comment on column BIZ2_VMDATA_PRIV.VMRULE_MAPPING_ID  is '映射表主键ID';
create sequence seq_BIZ2_VMDATA_PRIV start with 1 increment by 1;
</div>

<div id="dtomapInitSqlDIV" style="display: none">
create table BIZ2_VMRULE_MAPPING
(
  ID          NUMBER(18) not null primary key,
  MAP_TYPE    VARCHAR2(2) not null,
  MAP_KEY     VARCHAR2(20) not null,
  MAP_VALUE   VARCHAR2(20) not null,
  ORG_TYPE    VARCHAR2(10) default 'BIZ2',
  CREATE_TIME TIMESTAMP(6),
  CREATE_BY   NUMBER(18)
);
comment on table BIZ2_VMRULE_MAPPING is '映射表';
comment on column BIZ2_VMRULE_MAPPING.ID  is '主键ID';
comment on column BIZ2_VMRULE_MAPPING.MAP_TYPE is '类型（1：人对人，2：人对机构，3：机构对机构）';
comment on column BIZ2_VMRULE_MAPPING.MAP_KEY is '关联KEY信息';
comment on column BIZ2_VMRULE_MAPPING.MAP_VALUE is '关联VALUE信息';
comment on column BIZ2_VMRULE_MAPPING.ORG_TYPE is '虚拟树代码';
comment on column BIZ2_VMRULE_MAPPING.CREATE_TIME is '创建时间';
comment on column BIZ2_VMRULE_MAPPING.CREATE_BY is '创建人';
create sequence seq_BIZ2_VMRULE_MAPPING start with 1 increment by 1;
</div>
<!-- 保存 关闭 按钮 在 查询页面进行控制 -->  
</form>

</div>

</body>

<script type="text/javascript">
$(document).ready(function(){
  	checkedInit();
});
function changeTableName(){
	var _value = $("#dtovmtreeCode").val();
	var _mapTab = _value+"_VMRULE_MAPPING";
	var _dataTab =_value +"_VMDATA_PRIV";
	$("#dtomapTabName").val(_mapTab);
	$("#dtodataTabName").val(_dataTab);
	
	var _mapSql = $("#dtomapInitSqlDIV").html();
	_mapSql = _mapSql.replace(/BIZ2/g, _value);
	var _dataSql = $("#dtodataInitSqlDIV").html();
	_dataSql = _dataSql.replace(/BIZ2/g, _value);
	$("#dtomapInitSql").val(_mapSql);
	$("#dtodataInitSql").val(_dataSql);
}
</script>
  
</html>
