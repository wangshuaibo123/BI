<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
  <title>查询QRTZ_EXT_LOG</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="background-color:#FFFFFF">
	<!-- 页面初始化 需要的 div -->
	<div id="content"></div>

</body>
<!-- 相关js方法 -->
<script type="text/javascript" src="${basePath}/js/platform/qrtzextlog/queryQrtzExtLog.js?d=<%=myDate%>"></script>
<script type="text/javascript">
	//页面加载完后 
	$(document).ready(function(){
		initFn();
	});
</script>	
<script>
	var iframe;
	
	//定义form表单 查询 方法
	function queryData(){
		iframe.iframeObj["table"].query();
	}
	//定义 form表单 重置方法
	function resetData(){
		iframe.iframeObj["form"].reset();
	}
	//初始化 查询页面元素
	function initFn(){
		//定义 form表单查询 信息
		 var formStructure={
			// 定义form表单 字段信息
			columns : [
			 {display : ' 作业名称 ', code : 'jobName', width : 200,  type:'text', value: '${jobName}'}
			],
			//定义form 表单 按钮信息
			buttons:[
			 {"text":"查询","fun":queryData,icon:"ui-icon-search"}
			,{"text":"重置","fun":resetData,icon:"ui-icon-extlink"}
			]
		}
		//定义工具条	
		var toolbar={
			title:"查询列表"
		}
		//定义 table 列表信息	
		var tableStructure = {
			//定义table 列表的表头信息
			columns : [
			 {display : ' 数据库ID ', code : 'id', width : 60, align : 'left', type:'text', isOrder : false}
			,{display : ' 作业名称 ', code : 'jobName', width : 250, align : 'left', type:'text', isOrder : false}
			,{display : ' 触发时间 ', code : 'fireTime', width : 130, align : 'center', type:'date', format:'yyyy-MM-dd HH:mm:ss', isOrder : false}
			,{display : ' 执行线程 ', code : 'threadId', width : 150, align : 'left', type:'text', isOrder : false}
			,{display : ' 开始时间 ', code : 'startTime', width : 130, align : 'center', type:'date', format:'yyyy-MM-dd HH:mm:ss', isOrder : false}
			,{display : ' 结束时间 ', code : 'endTime', width : 130, align : 'center', type:'date', format:'yyyy-MM-dd HH:mm:ss', isOrder : false}
			,{display : ' 状态 ', code : 'state', width : 80, align : 'left', type:'text', isOrder : false}
			,{display : ' 结果 ', code : 'result', width : 200, align : 'left', type:'text', isOrder : false}
		   ],
			url : "${basePath}qrtzExtLog/queryListQrtzExtLog",
			toolbar:"tableToolbar",
			pageSize : 10,
			selectType : 'radio',
			isCheck : true,
			rownumbers : true,
			pages : [ 10, 20, 30, 50, 100 ],
			trHeight : 30,
			primaryKey:"id"
		};
		//组装 searchIframe 的相关参数		
		var searchIframe={"toolbar":toolbar,"form":formStructure,"table":tableStructure};	
		//初始化 form 表单 table 列表 及工具条 
		iframe=$("#content").newSearchIframe(searchIframe);
		iframe.show();
	}
</script> 
</html>
