<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>修改${jspDTO.tableComments}</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<br/>

<form id="updateForm" name="updateForm" isCheck="true" action="${jspDTO.actionPackageName}.${jspDTO.actionClassName}"
      loadUrl="${'$'}{basePath}${transTableName}/get${formated_tab_name}?id=${'$'}{id}" >
    <input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="" />
    <table id="updateTable" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
        <#list dataInfoList as dataInfo>
        <#assign d_inde = dataInfo_index />
        <#if (d_inde %3 ==0 ) >
        <tr>
        </#if>
            <th><#if (dataInfo.COLUMN_COMMENTS??)> <#if (dataInfo.COLUMN_COMMENTS?length>6)>${dataInfo.COLUMN_COMMENTS[0..6]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if> <#else>${dataInfo.COLUMN_NAME}</#if>：</th>
            <td <#if ( ((dataInfoList ? size) -1) == d_inde ) >colspan="<#if (  (d_inde+1) %3 == 1) >5</#if><#if (  (d_inde+1) %3 == 2) >3</#if>"</#if>> 
                <input type="text" class="text" id="${dataInfo.DTO_COLUMN_NAME?replace(".","")}" name="${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}" notNull="false" maxLength="${(dataInfo.DATA_LENGTH / 2 )?string('#')}" value="" />
            </td>
        <#if ((d_inde+1) %3 ==0 || ((dataInfoList ? size) -1) == d_inde ) >
        </tr>
        </#if>
        </#list>
    </table>

<!-- 保存 关闭 按钮 在 查询页面进行控制 -->  
</form>
</body>

<script type="text/javascript">
    $(document).ready(function(){
        var mainForm = $("#updateForm").newSearchForm();
    	mainForm.show();
    	mainForm.loadData();
	});
</script>
  
</html>
