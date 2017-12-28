<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 新增规则定义 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>新增</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="addNewsFormData" name="addNewsFormData" isCheck="true" action="com.jy.modules.platform.rule.ruledefinition.controller.RuleDefinitionController">
 <table id="addNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th width="50"> 规则集编码 ：</th>
  <td > 
  <input type="hidden" class="text" id="dtoparentId" name="parentId" notNull="true" value="${treeDto.id}" />
  <input type="text" class="text" id="dtoruleCode" notNull="true" readonly="readonly" value="${treeDto.nodeCode}" />
  </td>
  <th> 规则名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtoruleName" name="ruleName" notNull="true" maxLength="200" value="" />
  </td>
</tr>
<tr height="420">
  <th width="50"> 规则定义 ：</th>
  <td colspan="3"> 
  <textarea rows="25" cols="100" class="text" id="dtoruleResource" name="ruleResource" maxLength="2000" value="" ></textarea>
  </td>
</tr>
<tr>
  <th> 定义类型 ：</th>
  <td>
  <!-- 
  <input type="text" class="text" id="dtoresourceType" name="resourceType" notNull="true" maxLength="5" value="" />
   -->
    <select name="resourceType">
        <option value="DRL">规则脚本</option>
        <!-- 
        <option value="DRF">规则流程</option>
         -->
    </select>
  </td>
  <th> 是否启用 ：</th>
  <td>
  <!-- 
  <input type="text" class="text" id="dtoresourceType" name="resourceType" notNull="true" maxLength="5" value="" />
   -->
    <select name="validateState">
        <option value="1">启用</option>
        <option value="0">停用</option>
    </select>
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
