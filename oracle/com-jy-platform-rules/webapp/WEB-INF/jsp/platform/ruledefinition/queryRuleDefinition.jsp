<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 查询规则定义-废弃 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
  <%@ include file="/common/StaticJavascript.jsp" %>
  <%--@ include file="/common/StaticJspTaglib.jsp" --%>
  <title>查询</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="background-color:#FFFFFF">
<!-- 列表按钮操作 start -->
	<div id="tableToolbar" class="tableToolbar" style="display:none;">
		  <shiro:hasPermission name="platform/ruledefinition/queryRuleDefinition:add">
		  </shiro:hasPermission>
		  	<a href="javascript:void(0)" onclick="toAddData()" index="0">新增</a>
	  	 <shiro:hasPermission name="platform/ruledefinition/queryRuleDefinition:modify">
		  </shiro:hasPermission>
		  	<a href="javascript:void(0)" onclick="toUpdateData()" index="1">修改</a>
		  <shiro:hasPermission name="platform/ruledefinition/queryRuleDefinition:delete">
		  </shiro:hasPermission>
		  	<a href="javascript:void(0)" onclick="deleteData()" index="2">删除</a>

		  	<a href="javascript:void(0)" onclick="doTest()" index="2">测试</a>
	  </div>
<!-- 列表按钮操作 end -->
	<!-- 页面初始化 需要的 div -->
	<div id="content"></div>

</body>

<!-- 测试用begin -->
<script type="text/javascript">
function doTest(){
    var rules = document.getElementsByName("ruleCode")[0].value;
    //如果没有选中 数据则
    if(rules == ""){
        jyDialog({"type":"info"}).alert("请输入要执行的规则！");
        return;
    }
    jyDialog({"type":"question"}).confirm("您确认要执行"+rules+"规则吗？",function(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:contextRootPath+"/testRule/dotest?rules="+rules,
            success:function(msg){
                $("").newMsg({}).show(msg.msg);
            }
        });
       },"确认提示");
}
</script>
<!-- 测试用end -->

<!-- 相关js方法 -->
<script type="text/javascript" src="${basePath}/js/platform/ruledefinition/queryRuleDefinition.js?d=<%="myDate"%>"></script>
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
		if (!iframe.iframeObj["table"].url) {
			iframe.iframeObj["table"].url="${basePath}ruleDefinition/queryListRuleDefinition";
		}
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
			 {display : ' 规则编码 ', code : 'ruleCode', width : 150,  type:'text'}
	        ,{display : ' 规则名称 ', code : 'ruleName', width : 150,  type:'text'}
	        ,{display : ' 是否启用 ', code : 'validateState', width : 100,  type:'select',
	        	value:[{"value": "", "text": ""},{"value": "1", "text": "启用"},{"value": "0", "text": "停用"}]}
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
			 {display : ' 规则编码 ', code : 'ruleCode', width : 200, align : 'left', type:'fun', isOrder : false,
			  value:function (obj){
						return "<a href='javascript:void(0)' onclick='viewData("+obj.id+")'>"+obj.ruleCode+"</a>";
				    }	 
			 }
			,{display : ' 规则名称 ', code : 'ruleName', width : 200, align : 'left', type:'text', isOrder : false}
			,{display : ' 定义类型 ', code : 'resourceType', width : 100, align : 'left', type:'select', 
				value:[{"value": "", "text": ""},{"value": "DRL", "text": "规则脚本"},{"value": "DRF", "text": "规则流程"}]}
			,{display : ' 是否启用 ', code : 'validateState', width : 100, align : 'left', type:'select', 
				value:[{"value": "", "text": ""},{"value": "1", "text": "启用"},{"value": "0", "text": "停用"}]}
		   ],
			//url : "${basePath}ruleDefinition/queryListRuleDefinition",
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
