<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 查看规则定义 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>查看</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.modules.platform.rule.ruledefinition.controller.RuleDefinitionController">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th> 规则集编码 ：</th>
  <td >${dto.ruleCode}</td>
  <th> 规则名称 ：</th>
  <td >${dto.ruleName}</td>
</tr>
<tr>
  <th> 定义类型 ：</th>
  <td>
	  <c:if test='${dto.resourceType=="DRL"}'>规则脚本</c:if>
	  <c:if test='${dto.resourceType=="DRF"}'>规则流程</c:if>
  </td>
  <th> 是否启用 ：</th>
  <td>
    <c:if test='${dto.validateState=="1"}'>启用</c:if>
    <c:if test='${dto.validateState=="0"}'>停用</c:if>
  </td>

</tr>
<tr>
  <th> 规则定义 ：</th>
  <td colspan="3">
  <textarea rows="28" cols="100" readonly="readonly">${dto.ruleResource}</textarea></td>
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
