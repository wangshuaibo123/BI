<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改角色用户表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.modules.platform.sysauth.sysroleuser.controller.SysRoleUserController">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 角色ID ：</th>
  <td >${dto.roleId}</td>
  <th> 用户ID或者机 ：</th>
  <td >${dto.targetId}</td>
 <%--  <th> 系统ID，备用 ：</th>
  <td >${dto.appId}</td> --%>
</tr>
<%-- <tr>
  <th> user用户类 ：</th>
  <td >${dto.targetType}</td>
  <th> 乐观锁 ：</th>
  <td colspan="3">${dto.version}</td>
</tr> --%>
  </table>

<!-- 关闭 按钮 在 查询页面进行控制 -->  
</form>

</div>

</body>

<script type="text/javascript">
   $(document).ready(function(){
   		
	});
</script>
  
</html>
