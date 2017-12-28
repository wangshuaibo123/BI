<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改QRTZ_EXT_LOG</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.modules.quartzjob.qrtzextlog.controller.QrtzExtLogController">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 作业名称 ：</th>
  <td >${dto.jobName}</td>
  <th> 触发时间 ：</th>
  <td >${dto.fireTime}</td>
  <th> 执行线程 ：</th>
  <td >${dto.threadId}</td>
</tr>
<tr>
  <th> 开始时间 ：</th>
  <td >${dto.startTime}</td>
  <th> 结束时间 ：</th>
  <td >${dto.endTime}</td>
  <th> 状态 ：</th>
  <td >${dto.state}</td>
</tr>
<tr>
  <th> 结果 ：</th>
  <td colspan="5">${dto.result}</td>
</tr>
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
