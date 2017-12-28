<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
  <%@ include file="/common/StaticJavascript.jsp" %>
  <title>查询${jspDTO.tableComments}</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script type="text/javascript" src="${'$'}{basePath}/js/${jspDTO.jspPrefix}${transTableName?lower_case}/query${formated_tab_name}.js?d=<%=myDate%>"></script>

<!-- 相关js方法 -->
<script type="text/javascript">
    //页面加载完后 
    $(document).ready(function(){
        initFn();
    });
</script>
</head>

<body style="background-color:#FFFFFF">

    <div id="formData" loadUrl="">
        <div class="formCon">
            <#list dataInfoList as dataInfo>
	        <#assign d_inde = dataInfo_index />
	        <div class="field">
			    <label class="fieldName"><#if (dataInfo.COLUMN_COMMENTS??)><#if (dataInfo.COLUMN_COMMENTS?length>3)>${dataInfo.COLUMN_COMMENTS[0..3]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if><#else>${dataInfo.COLUMN_NAME}</#if>:</label>
				<input name="${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}" type="text" value="" />
			</div>
	        </#list>
	        
	        <div class="searchBtn">
				<div class="ui-button ui-button-text-icon-primary" onclick="queryData()">
					<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
					<span class="ui-button-text">查询</span>
				</div>
				<div class="ui-button ui-button-text-icon-primary" onclick="resetData()">
					<span class="ui-button-icon-primary ui-icon ui-icon-extlink"></span>
					<span class="ui-button-text">重置</span>
				</div>
			</div>
        </div>
    </div>
    
    <div id="tableData"></div>
    
    <!-- 列表按钮操作 start -->
	<div id="tableToolbar" class="tableToolbar" style="display:none;">
        <shiro:hasPermission name="${jspDTO.jspPrefix}${transTableName?lower_case}/query${formated_tab_name}:add">
		    <a href="javascript:void(0)" onclick="toAddData()" index="0">新增</a>
		</shiro:hasPermission>
	  	<shiro:hasPermission name="${jspDTO.jspPrefix}${transTableName?lower_case}/query${formated_tab_name}:modify">
		    <a href="javascript:void(0)" onclick="toUpdateData()" index="1">修改</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${jspDTO.jspPrefix}${transTableName?lower_case}/query${formated_tab_name}:delete">
		    <a href="javascript:void(0)" onclick="deleteData()" index="2">删除</a>
		</shiro:hasPermission>
	</div>
    <!-- 列表按钮操作 end -->

	<div id="jy-table" pages="[10,20,30]" toolbar="tableToolbar" pageSize="10" isCheck="true" rownumbers="true" selectType="checkbox" 
	     url="${'$'}{basePath}${transTableName}/queryList${formated_tab_name}" primaryKey="id" style="display:none;" >
	    <#list dataInfoList as dataInfo>
	    <#assign d_inde = dataInfo_index />
	    <#if (d_inde =0 ) >
	    <div class="jy-table-con" display="<#if (dataInfo.COLUMN_COMMENTS??)><#if (dataInfo.COLUMN_COMMENTS?length>6)>${dataInfo.COLUMN_COMMENTS[0..6]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if><#else>${dataInfo.COLUMN_NAME}</#if>" code="${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}" width="100", align="left" type="fun" isOrder="false" value="viewDataFun"></div>
	    </#if>
	    <#if (d_inde >0 ) >
	    <div class="jy-table-con" display="<#if (dataInfo.COLUMN_COMMENTS??)><#if (dataInfo.COLUMN_COMMENTS?length>6)>${dataInfo.COLUMN_COMMENTS[0..6]?default("")}<#else>${dataInfo.COLUMN_COMMENTS?default("")}</#if><#else>${dataInfo.COLUMN_NAME}</#if> " code="${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}" width="100", align="left" type="text" isOrder="false"></div>
	    </#if>
	    </#list>
	</div> 
</body>

<!-- 相关js方法 -->	
<script>
	var mainForm, mainTable;
	
	//定义form表单 查询 方法
	function queryData(){
	    var params = mainForm.serialize();
	    mainTable.query(params);
	}
	
	//定义 form表单 重置方法
	function resetData(){
	    mainForm.reset();
	}
	
	function viewDataFun(obj){
	    <#list dataInfoList as dataInfo>
		<#assign d_inde = dataInfo_index />
		<#if (d_inde = 0 ) >
		return "<a href='javascript:void(0)' onclick='viewData("+obj.id+")'>"+obj.${dataInfo.DTO_COLUMN_NAME?replace("dto.","")}+"</a>";
		</#if>
		</#list>
	}
	
	//初始化 查询页面元素
	function initFn(){
	    mainForm = $("#formData").newSearchForm();
		mainForm.show();
		
		mainTable = $("#tableData").newTable();
		mainTable.show();
	}
	
</script> 
</html>
