<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
   <%@ include file="/common/StaticJavascript.jsp" %>
  <title>统计分析列表</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script type="text/javascript" src="${basePath}js/threeJs/echarts/echarts.min.js"></script>
<!-- 相关js方法 -->
<script type="text/javascript">
	//页面加载完后 
	$(document).ready(function(){
		initFn();
	});
	<%-- $(document).ready(function(){
		
		
	   	var  orgurl='<%=basePath%>/sysOrg/queryTreeSysOrgByCurrentUserOrg?&trace=down';
	   	$("[name='configValue']").treeMenu({
	   		"treeUrl":orgurl
	   		,"treeType":"radio"
	   		,"treeIdObj":$("[name='configCode']")
	   		,"width":"200"
	   		,"height":"300"});
	   	
	}); --%>
	
	
	function viewData(id){
		var url = "";
		switch (id) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				url = "${basePath}bi/prepareExecute/toVideoShopStat?id=" + id;
				break;
			case 4:
				url = "${basePath}bi/prepareExecute/toVideoCopyrightStat?id=" + id;
				break;
			case 5:
				url = "${basePath}bi/prepareExecute/toVideosSourceTypeStatCount?id=" + id;
				break;
			case 6:
				url = "${basePath}bi/prepareExecute/toVideosSourceTypeShareStatCount?id=" + id;
				break;
		}
		if(url == ""){
			alert("找不到页面！");
			return ;
		}
		window.location.href = url;
	}
	
</script>

</head>
<body style="background-color:#FFFFFF">
	<!-- 页面初始化 需要的 div -->
	<div id="content"></div>
	
	<div id="tableToolbar" class="tableToolbar" style="display:none;">
		 <!--  <a href="javascript:void(0)" onclick="toAddData()" index="0">新增</a> -->
	 </div>

</body>
<!-- 相关js方法 -->	
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
		/* var formStructure={};
		var formStructure={
			// 定义form表单 字段信息
			columns : [
			 {display : ' 配置名称 ', code : 'configName', width : 200,  type:'text'}
	        ,{display : ' 配置编码 ', code : 'configCode', width : 200,  type:'text'}
	        ,{display : ' 配置值 ', code : 'configValue', width : 200,  type:'text'}
	        ,{display : '类型', code : 'configType', width : 200,  type:'select',value:[{"value":"","text":"请选择类型"},{"value":"0","text":"系统级"},{"value":"1","text":"项目级"}]}
	        ,{display : '多选类型', code : 'configType2', width : 200,  type:'multiautocomplete',value:[{"value":"","text":"请选择类型"},{"value":"0","text":"系统级"},{"value":"1","text":"项目级"}]}
	        //,{display : ' 乐观锁 ', code : 'version', width : 200,  type:'text'}
			],
			//定义form 表单 按钮信息
			buttons:[
			 {"text":"查询","fun":queryData,icon:"ui-icon-search"}
			,{"text":"重置","fun":resetData,icon:"ui-icon-extlink"}
			]
		} */
		//定义工具条	
		var toolbar={
			title:"统计分析列表"
		}
		//定义 table 列表信息	
		var tableStructure = {
			//定义table 列表的表头信息
			columns : [
			 {display : ' 名称 ', code : 'name', width : 200, align : 'left', type:'fun', isOrder : false,
			  value:function (obj){
						return "<a href='javascript:void(0)' onclick='viewData("+obj.id+")'>"+obj.name+"</a>";
				    }	 
			 }
			,{display : ' 描述', code : 'des', width : 350, align : 'left', type:'text', isOrder : false}
		   ],
			url : "${basePath}bi/getBiList",
			//pageSize : 10,
			toolbar:"tableToolbar",
			selectType : 'checkbox',
			isCheck : true,
			rownumbers : true,
			isPage:false,
			//pages : [ 10, 20, 30 ],
			trHeight : 30,
			primaryKey:"id"
		};
		//组装 searchIframe 的相关参数		
		var searchIframe={"toolbar":toolbar,"table":tableStructure};	
		//初始化 form 表单 table 列表 及工具条 
		iframe=$("#content").newSearchIframe(searchIframe);
		iframe.show();
	}
</script> 
</html>
