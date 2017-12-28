<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/app" prefix="app"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>代码生成</title>
<jsp:include page="/common/StaticJavascript.jsp" />
<script>
  window.onload=function(){
	function query(){
		iframe.iframeObj["table"].query();
	}
	function reset(){
		iframe.iframeObj["form"].reset();
	}
	
	var URL =contextRootPath+"/sysDict/queryTreeJSON?codeType=SYS_DAI_MA_CODE";
	
	
	var formStructure={
		columns : [
			 {display : '数据库url', code : 'dburl', width : 350,  type:'text', value:"jdbc:oracle:thin:@172.18.100.86:1521:testdb"}
			,{display : '数据库username', code : 'dbusername', width : 200,  type:'text'}
			,{display : '数据库password', code : 'dbpassword', width : 200,  type:'text'}
			,{display : '表名称', code : 'tableName', width : 200,  type:'text',title:'不区分大小写'}
			,{display : 'java代码包前缀', code : 'packageNamePrefix', width : 345, value:"com.jy.platform"}
			,{display : 'jsp目录前缀', code : 'jspPrefix', width : 215, value:"platform/"}
			,{display : '生成类别', code : 'acceptStore', width : 200, type:'tree',value:URL,treeWidth:'200',treeHeight:'200',treeType:'checkbox',disabledLink: "true"}
		],																	
		buttons:[
		 		/* {"text":"查询","fun":query,icon:"ui-icon-search"},
				{"text":"重置","fun":reset,icon:"ui-icon-extlink"}, */
				{"text":"生成代码","fun":function(){productCode()},icon:"ui-icon-extlink"},
		]
	}
	var toolbar={
		title:"代码生成列表"
	}
  	
	var tableStructure = {
			columns : [
				{display : '表名称', code : 'TABLE_NAME', width : 200, align : 'left', type:'text', isOrder : false},
				{display : '字段英文名称', code : 'COLUMN_NAME', width : 200, align : 'left', isOrder: false},
				{display : '字段中文名称（简称）', code : 'COLUMN_COMMENTS', width : 200, align : 'left', type:'text', isOrder : false},
				{display : '字段类型', code : 'DATA_TYPE', width : 200, align : 'center',type:'text',isOrder : false },
				{display : '类型长度', code : 'DATA_LENGTH', width : 200, align : 'left', type:'text', isOrder : false},
				{display : '不必填', code : 'NULLABLE', width : 200, align : 'center',type:'text',isOrder : false },
				{display : '表中文名称', code : 'TABLE_COMMENTS', width : 200, align : 'left', type:'text', isOrder : false},
		   ],
				url :  contextRootPath+"/generate/queryColumnsOfTable",
				pageSize : 10,
				selectType : 'radio',
				checkbox : false,
				rownumbers : true,
				pages : [ 10, 20, 30 ],
				primaryKey:"ID",
				trHeight : 30,
				form:"form"
		};
	
	var searchIframe={"toolbar":toolbar,"form":formStructure,"table":tableStructure};	
	iframe=$("#content").newSearchIframe(searchIframe);
	iframe.show();	
}
  
  //复制数据到 div
function copyDataToDiv(i,columName){
	var v_commentsId = "comments_"+columName;
	var v_typeId = "type_"+columName;
	var v_LengthId = "length_"+columName;
	var oldDivId = "div_param";
	if(i >0) oldDivId = oldDivId +"_"+(i-1);
	$("#"+oldDivId).find("[name=columnName]").attr("value",columName);
	$("#"+oldDivId).find("[name=columnComments]").attr("value",$("#"+v_commentsId).val());
	$("#"+oldDivId).find("[name=dataType]").attr("value",$("#"+v_typeId).val());
	$("#"+oldDivId).find("[name=dataLength]").attr("value",$("#"+v_LengthId).val());
}
  
//生成代码
function productCode(){
	//获取 所有 设置的字段属性信息 通过ajax 提交到后台
	var v_max = $("[id^=display_]").length;
	$("[id^=display_]").each(function(i){
		//先复制div 在给div 中的input 赋值
		if(v_max > i +1){
			addDiv(i);
		}
		var v_columName = $(this).attr("id").substring("display_".length,$(this).attr("id").length);
		copyDataToDiv(i,v_columName);
	});
  	
  	var url=contextRootPath+"/generate/generateCode";
  	
  	//校验
  	var dburl = $("input[name='dburl']").val();
  	var dbusername = $("input[name='dbusername']").val();
  	var dbpassword = $("input[name='dbpassword']").val();
  	var tableName = $("input[name='tableName']").val();
  	var packageNamePrefix = $("input[name='packageNamePrefix']").val();
  	var jspPrefix = $("input[name='jspPrefix']").val();
  	
  	if(dburl==null || $.trim(dburl)==""){
		jyDialog({"type":"info"}).alert("数据库url为必填");
		return false;
	}
  	if(dbusername==null || $.trim(dbusername)==""){
		jyDialog({"type":"info"}).alert("数据库username为必填");
		return false;
	}
  	if(dbpassword==null || $.trim(dbpassword)==""){
		jyDialog({"type":"info"}).alert("数据库password为必填");
		return false;
	}
  	if(tableName==null || $.trim(tableName)==""){
		jyDialog({"type":"info"}).alert("表名称为必填");
		return false;
	}
  	if(packageNamePrefix==null || $.trim(packageNamePrefix)==""){
		jyDialog({"type":"info"}).alert("java代码包前缀为必填");
		return false;
	}
  	if(jspPrefix==null || $.trim(jspPrefix)==""){
		jyDialog({"type":"info"}).alert("jsp目录前缀为必填");
		return false;
	}
  	
  	//var mask=$("").newMask();
	//mask.show("正在生产代码...");
  	
  	var params=iframe.iframeObj["form"].serialize();
  	//alert(params);
  	
  	$('#exportForm').attr('action',url+'?'+params);
	$('#exportForm')[0].submit();
	
	//mask.close();
	
  	/*
  	$.ajax({
  		url: url,
  		type: 'POST',
  		data:params,
  		dataType: 'json',
  		error: function(){
  		    mask.close();
  		    jyDialog({"type":"error"}).alert('error');
  		},
  		success: function(result){
  		    mask.close();
  			if(result.result&&result.result=="success"){
  				 $("").newMsg({}).show(result.message);
  			}else{
  				 $("").newMsg({}).show(result.message);
  			}
  		}  
  	});
  	*/
  }
</script>
</head>
<body>

	<div id="content"></div>
	
	<!-- 用来存储 页面设置的字段属性信息 -->
<div id="div_param" style=" display: none">
<input name="columnName" id="columnNameId" value="" />
<input name="columnComments" id="columnCommentsId" value="" />
<input name="dataType" id="dataTypeId" value="" />
<input name="dataLength" id="dataLengthId" value="" />
<form action="" target='_blank' id='exportForm' method='post'>
</form>
</div>
</body>
</html>