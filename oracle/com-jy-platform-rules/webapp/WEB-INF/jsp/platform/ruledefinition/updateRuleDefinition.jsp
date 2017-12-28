<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 修改规则定义 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改</title>
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
  <td > 
  <input type="text" class="text" id="dtoruleCode" name="ruleCode" readonly="readonly" maxLength="100" value="${dto.ruleCode}" />
  </td>
  <th> 规则名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtoruleName" name="ruleName" notNull="false" maxLength="100" value="${dto.ruleName}" />
  </td>
</tr>
<tr>
  <th> 规则定义 ：</th>
  <td colspan="3"> 
  <textarea rows="25" cols="100" class="text" id="dtoruleResource" name="ruleResource">${dto.ruleResource}</textarea>
  </td>
</tr>
<tr>
  <th> 定义类型 ：</th>
  <td>
    <select id="dtoresourceType" name="resourceType">
      <c:if test='${dto.resourceType=="DRL"}'>
        <option value="DRL" selected="selected">规则脚本</option>
        <!-- 
        <option value="DRF">规则流程</option>
         -->
      </c:if>
      <c:if test='${dto.resourceType=="DRF"}'>
        <option value="DRL">规则脚本</option>
        <option value="DRF" selected="selected">规则流程</option>
      </c:if>
    </select>
  </td>
  <th> 是否启用 ：</th>
  <td > 
    <select id="dtovalidateState" name="validateState">
      <c:if test='${dto.validateState=="1"}'>
        <option value="1" selected="selected">启用</option>
        <option value="0">停用</option>
      </c:if>
      <c:if test='${dto.validateState=="0"}'>
        <option value="1">启用</option>
        <option value="0" selected="selected">停用</option>
      </c:if>
    </select>
  </td>
  <input type="hidden" class="text" id="dtoversion" name="version" notNull="false" maxLength="11" value="${dto.version}" />

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
