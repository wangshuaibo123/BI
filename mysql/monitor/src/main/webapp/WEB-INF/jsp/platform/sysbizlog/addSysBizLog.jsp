<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>新增业务日志表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="addNewsFormData" name="addNewsFormData" isCheck="true" action="com.fintech.modules.boot.platform.sysbizlog.controller.SysBizLogController">
 <table id="addNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 客户端IP ：</th>
  <td > 
  <input type="text" class="text" id="dtoclientIp" name="clientIp" notNull="false" maxLength="25" value="" />
  </td>
  <th> 操作人ID ：</th>
  <td > 
  <input type="text" class="text" id="dtouserId" name="userId" notNull="false" maxLength="11" value="" />
  </td>
  <th> 操作人姓名 ：</th>
  <td > 
  <input type="text" class="text" id="dtouserName" name="userName" notNull="false" maxLength="25" value="" />
  </td>
</tr>
<tr>
  <th> 日志内容 ：</th>
  <td > 
  <input type="text" class="text" id="dtologContent" name="logContent" notNull="false" maxLength="2000" value="" />
  </td>
  
  <th> 日志类型 ：</th>
  <td colson="3"> 
  <input type="text" class="text" id="dtologType" name="logType" notNull="false" maxLength="25" value="" />
  </td>
</tr>
<tr>
  <th> 所属模块 ：</th>
  <td > 
  <input type="text" class="text" id="dtologModule" name="logModule" notNull="false" maxLength="25" value="" />
  </td>
  <th> 操作类型 ：</th>
  <td > 
  <input type="text" class="text" id="dtologOperate" name="logOperate" notNull="false" maxLength="25" value="" />
  </td>
  <th> 是否归档，1已 ：</th>
  <td colspan="7"> 
  <input type="text" class="text" id="dtoisArchive" name="isArchive" notNull="false" maxLength="1" value="" />
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
