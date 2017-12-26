<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/StaticJavascript2.jsp" %>
<div id="formPageSwap">
  <br/>
<form id="updateNewsFormData" name="updateNewsFormData" isCheck="true" action="${jspDTO.actionPackageName}.${jspDTO.actionClassName}">
<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${'$'}{dto.id}" />
 <table id="updateNewsTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1" >
<#list dataInfoList as dataInfo>
<#assign d_inde = dataInfo_index />
<#if (d_inde %2 ==0 ) >
<tr>
</#if>
  <th><#if (dataInfo.COLUMN_COMMENTS??)> <#if (dataInfo.COLUMN_COMMENTS?length>6)>${dataInfo.COLUMN_COMMENTS[0..6]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if> <#else>${dataInfo.COLUMN_NAME}</#if>：</th>
  <td <#if ( ((dataInfoList ? size) -1) == d_inde ) >colspan="<#if (  (d_inde+1) %2 == 1) >5</#if><#if (  (d_inde+1) %3 == 2) >2</#if>"</#if>>${'$'}{${dataInfo.DTO_COLUMN_NAME}}</td>
<#if ((d_inde+1) %2 ==0 || ((dataInfoList ? size) -1) == d_inde ) >
</tr>
</#if>
</#list>
  </table>

<!-- 关闭 按钮 在 查询页面进行控制 -->  
</form>

</div>