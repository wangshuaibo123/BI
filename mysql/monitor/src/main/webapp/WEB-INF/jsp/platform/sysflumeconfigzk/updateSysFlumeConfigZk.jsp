<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改Flume配置表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
  
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.modules.platform.sysflumeconfigzk.controller.SysFlumeConfigZkController">
	<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 	<table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
		<tr>
		  <th style="min-width:30px;"> 系统标示 ：</th>
		  <td > 
		  	<input type="text" class="text" id="appFlag" name="appFlag" notNull="false" maxLength="25" value="${dto.appFlag}" />
		  </td>
		  <th > 机器IP ：</th>
		  <td > 
		  	<input type="text" class="text" id="appIp" name="appIp" notNull="false" maxLength="25" value="${dto.appIp}" />
		  </td>
		</tr>
		<tr>
		  <th style="min-width:30px;"> 配置信息 ：</th>
		  <td colspan="3"> 
		  	<textarea id="config" name="config" rows="19" cols="80" notNull="false">${dto.config}</textarea>
		  </td>
		</tr> 
  </table>
</form>

</div>

</body>

<script type="text/javascript">
   $(document).ready(function(){
   	checkedInit();
	});
</script>
  
</html>
