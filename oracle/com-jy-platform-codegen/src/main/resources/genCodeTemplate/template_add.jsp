<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
   <title>新增${jspDTO.tableComments}</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
</head>
  
<body style="background-color:#FFFFFF">
<div id="formPageSwap">
<br/>

<form id="addForm" name="addForm" isCheck="true" action="${jspDTO.actionPackageName}.${jspDTO.actionClassName}">
    <table id="addTable" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
        <#list dataInfoList as dataInfo>
        <#assign d_inde = dataInfo_index />
        <#if (d_inde %3 ==0 ) >
        <tr>
        </#if>
            <th><#if (dataInfo.COLUMN_COMMENTS??)> <#if (dataInfo.COLUMN_COMMENTS?length>6)>${dataInfo.COLUMN_COMMENTS[0..6]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if> <#else>${dataInfo.COLUMN_NAME}</#if>：</th>
            <td <#if ( ((dataInfoList ? size) -1) == d_inde ) >colspan="${ (3- ((d_inde+1) %3))*2 +1}"</#if>> 
                <input type="text" class="text" id="${dataInfo.DTO_COLUMN_NAME?replace(".","")}" name="${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}" notNull="false" maxLength="${(dataInfo.DATA_LENGTH / 2 )?string('#')}" value="" />
            </td>
        <#if ((d_inde+1) %3 ==0 || ((dataInfoList ? size) -1) == d_inde ) >
        </tr>
        </#if>
        </#list>
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
