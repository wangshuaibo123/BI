<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 修改规则树节点 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改规则树</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="com.jy.platform.rule.ruletree.controller.RuleTreeController">
  <input type="hidden" class="text" id="dtoid" name="id" notNull="false" value="${dto.id}" />
  <input type="hidden" class="text" id="dtoparentId" name="parentId"  value="${dto.parentId}" />
  <input type="hidden" class="text" id="dtoversion" name="version"  value="${dto.version}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <th width="30%"> 父节点编码 ：</th>
  <td > 
  <input type="text" class="text" id="dtoparentCode" readonly="readonly" value="${dto.parentCode}" />
  </td>
</tr>
<tr>
  <th> 父节点名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtoparentName" readonly="readonly" value="${dto.parentName}" />
  </td>
</tr>
<tr>
  <th> 节点编码 ：</th>
  <td > 
  <input type="text" class="text" id="dtonodeCode" name="nodeCode" notNull="false" maxLength="100" value="${dto.nodeCode}" />
  *不能重复
  </td>
</tr>
<tr>
  <th> 节点名称 ：</th>
  <td > 
  <input type="text" class="text" id="dtonodeName" name="nodeName" notNull="false" maxLength="100" value="${dto.nodeName}" />
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
