<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改行政区域</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.modules.platform.sysarea.controller.SysAreaController">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 区域编码 ：</th>
  <td > 
  <input type="text" class="text" id="dtoareaCode" name="areaCode" notNull="false" maxLength="25" value="${dto.areaCode}" />
  </td>
  <th> 区域名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtoareaName" name="areaName" notNull="false" maxLength="50" value="${dto.areaName}" />
  </td>
  <th> 父节点ID ：</th>
  <td colspan=""> 
  <input type="text" class="text" id="dtoparentId" name="parentId" notNull="false" maxLength="11" value="${dto.parentId}" />
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
