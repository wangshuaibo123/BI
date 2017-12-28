<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="app" uri="/app" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="syscode" uri="/syscode" %>
<%@taglib prefix="ruleMapping" uri="/ruleMapping" %>
<%@ taglib uri="/sysuser" prefix="sysuser"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String jspPath = request.getSession().getServletContext().getRealPath("")+request.getRequestURI().replace(path, "");
%>
<c:set var="basePath" value="${pageContext.request.scheme}${'://'}${pageContext.request.serverName}${':'}${pageContext.request.serverPort}${pageContext.request.contextPath}/"></c:set>
<% String myDate="20150617"; %>
<%-- <script src="<%=basePath %>js/plat/require.js?d=<%=myDate%>"></script> --%>
<%--获取系统当前登录人ID --%>
<%-- <sysuser:search var="curUser" scope="request" /> --%>

<script type="text/javascript">
	//Global use for Javascript code scope. 
	var contextRootPath = "<%=path%>";
	var CUR_USER_ID_JS="${curUser.userId}";//定义JS变量获取系统当前登录任务
	if(CUR_USER_ID_JS && CUR_USER_ID_JS.length < 7){
		CUR_USER_ID_JS = CUR_USER_ID_JS+"00";
	}
	//控制干扰
	var DISTURB_SECRET_JS ="Y";
	//干扰的extname
	var JQUERY_TABLE_OBJS_JS =[];
	JQUERY_TABLE_OBJS_JS.push("cardId");
<%-- 	require.config({
	    paths : {
	        "jquery" : ["<%=basePath %>js/threeJs/jquery/jquery.js", "js/jquery"]
	    }
	});
	
	require(["jquery"],function($){
	    $(function(){
	        alert("load finished");  
	    })
	}); --%>
</script>

<script src="<%=basePath %>js/threeJs/jquery/jquery.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/threeJs/jquery/jquery-ui.min.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/threeJs/jquery/datepicker-zh-TW.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/threeJs/jquery/jquery.mousewheel.min.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/threeJs/zTree_v3/js/jquery.ztree.all-3.5.min.js?d=<%=myDate%>"></script>

<script src="<%=basePath %>js/plat/base/base.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/table/table.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/searchform/searchform.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/toolbar.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/searchIframe.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/dialog/dialog.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/menu/menu.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/tree/tree.js?d=<%=myDate%>"></script>
 

<script src="<%=basePath %>js/plat/treeMenu.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/tab/tab.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/check.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/imageTool/imageTool.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/message.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/tip/tooltip.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/JBPM.common.js"></script>
<script src="<%=basePath %>js/plat/JBPM.biz.js"></script>
<script src="<%=basePath %>js/threeJs/ajTools.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/plat/autocomplete/autocomplete.js?d=<%=myDate%>"></script>

<script src="<%=basePath %>js/platform/sysarea/sysArea.js?d=<%=myDate%>"></script>
<script src="<%=basePath %>js/threeJs/My97DatePicker/WdatePicker.js?d=<%=myDate%>"></script>

<script type="text/javascript" src="<%=basePath %>js/threeJs/multiSelect/jquery.multiselect.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/threeJs/multiSelect/jquery.multiselect.css" />
<script type="text/javascript"  src="<%=basePath%>/js/threeJs/highcharts/highcharts.js"></script>
<script type="text/javascript"  src="<%=basePath%>/js/threeJs/highcharts/highcharts-3d.js"></script>

<link rel="stylesheet" href="<%=basePath %>js/threeJs/zTree_v3/css/zTreeStyle/zTreeStyle.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/threeJs/jquery/jquery-ui.min.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/table/table.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/menu/menu.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/tab/tab.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/dialog/dialog.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/base/base.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/tip/tooltip.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/searchform/searchform.css?d=<%=myDate%>">
<link rel="stylesheet" href="<%=basePath %>js/plat/imageTool/imageTool.css?d=<%=myDate%>">
<script type="text/javascript">
var COPY_LOG_JS ="N";//控制是否监听ctrl+c 事件打印日志

window.onload=function(){
   if(COPY_LOG_JS=="Y"){
	   try{
		document.body.oncopy = function(){ 
			var copyText = window.getSelection?window.getSelection():document.selection.createRange().text; 
			if (copyText.toString().length > 0) {
				copyLog(copyText);
			}  
		};
	   }catch(e){}
	}
}
//监听ctrl+c 事件打印日志
function copyLog(txt){
	try{
		$.ajax({
			url: contextRootPath+'/front/ctrl/c?copyText='+txt,
			type: 'POST', 
			success: function(data){}
		});
	}catch(e){}
}

</script>
