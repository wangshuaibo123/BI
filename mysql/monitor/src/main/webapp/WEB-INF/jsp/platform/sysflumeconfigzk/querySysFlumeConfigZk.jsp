<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
  <title>查询Flume配置表</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="background-color:#FFFFFF">
<!-- 列表按钮操作 start -->
	<div id="tableToolbar" class="tableToolbar" style="display:none;">
	  	<a href="javascript:void(0)" onclick="toAddData()" index="0">新增</a>
	  	<a href="javascript:void(0)" onclick="toUpdateData()" index="1">修改</a>
	  	<a href="javascript:void(0)" onclick="doCommitData()" index="2">提交</a>
	  	<a href="javascript:void(0)" onclick="deleteData()" index="3">删除</a>
	</div>
	
<!-- 列表按钮操作 end -->
	<!-- 页面初始化 需要的 div -->
	<div id="content"></div>

</body>
<!-- 相关js方法 -->
<script type="text/javascript" src="${basePath}/js/platform/sysflumeconfigzk/querySysFlumeConfigZk.js?d=<%=myDate%>"></script>
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
			 {display : ' 系统标示 ', code : 'appFlag', width : 200,  type:'text'}
	        ,{display : ' 机器IP ', code : 'appIp', width : 200,  type:'text'}
	        ,{display : ' 状态 ', code : 'status', width : 200,  type:'select', value:[{"value":"","text":"请选择类型"},{"value":"saved","text":"已保存"},{"value":"commited","text":"已提交"}]}
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
			 {display : ' 系统标示 ', code : 'appFlag', width : 100, align : 'center', type:'text', isOrder : false}
			,{display : ' 机器IP ', code : 'appIp', width : 100, align : 'center', type:'fun', isOrder : false,
			  value:function (obj){
					return "<a href='javascript:void(0)' onclick='viewData("+obj.id+")'>"+obj.appIp+"</a>";
			    }	 
			}
			,{display : ' 状态 ', code : 'status', width : 100, align : 'center', type:'select',value:[{"value":"","text":"请选择类型"},{"value":"saved","text":"已保存"},{"value":"commited","text":"已提交"}], isOrder : false}
			,{display : ' 创建姓名 ', code : 'createByName', width : 100, align : 'center', type:'text', isOrder : false}
			,{display : ' 创建时间 ', code : 'createTime', width : 120, align : 'center', type:'date', format:'yyyy-MM-dd HH:mm:ss', isOrder : false}
			,{display : ' 最后更新姓名 ', code : 'updateByName', width : 100, align : 'center', type:'text', isOrder : false}
			,{display : ' 最后更新时间 ', code : 'updateTime', width : 120, align : 'center', type:'date', format:'yyyy-MM-dd HH:mm:ss', isOrder : false}
		   ],
			url : "${basePath}sysFlumeConfigZk/queryListSysFlumeConfigZk",
			toolbar:"tableToolbar",
			pageSize : 10,
			selectType : 'checkbox',
			isCheck : true,
			rownumbers : true,
			pages : [ 10, 20, 30 ],
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
