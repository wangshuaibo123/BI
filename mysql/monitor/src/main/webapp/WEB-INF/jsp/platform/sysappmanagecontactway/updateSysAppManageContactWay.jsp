<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript2.jsp" %>
   <title>修改系统管理者联系方式</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.fintech.modules.boot.logmonitor.sysappmanagecontactway.controller.SysAppManageContactWayController">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 系统标识 ：</th>
  <td > 
  <syscode:dictionary id="dtoappFlag" name="appFlag" codeType="SYSTEMFLAG" type="select" defaultValue="${dto.appFlag}"/>
  </td>
  <th> email地址 ：</th>
  <td > 
  <input type="text" class="text" id="dtoemail" name="email" notNull="false" maxLength="50" value="${dto.email}" />
  </td>
</tr>
<tr>
  <th> 管理者名称 ：</th>
  <td> 
  <input type="text" class="text" id="dtomanageName" name="manageName" notNull="false" maxLength="50" value="${dto.manageName}" />
  </td>
  <th> 手机号 ：</th>
  <td > 
  <input type="text" class="text" id="dtotel" name="tel" notNull="false" maxLength="11" value="${dto.tel}" />
  </td>
</tr>
<tr>
  <th> 接收错误级别 ：</th>
  <td colspan="3"> 
  <syscode:dictionary id="dtologLevel" name="logLevel" codeType="LOG_LEVEL" type="select" defaultValue="${dto.logLevel}"/>
  </td>
</tr>
<tr>
  <th> 接收关键字：</th>
  <td colspan="3"> 
  <input type="text" class="text" id="dtokeyWord" name="keyWord" style="width:350px"  maxLength="200" value="${dto.keyWord}" />
  </td>
</tr>
  </table>

<!-- 保存 关闭 按钮 在 查询页面进行控制 -->  
</form>

</div>

</body>

<script type="text/javascript">
   $(document).ready(function(){
   	checkedInit();
	});
</script>
  
</html>
