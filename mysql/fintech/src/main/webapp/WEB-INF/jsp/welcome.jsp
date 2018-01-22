<%@ page language="java" import="java.util.*,com.fintech.modules.common.util.ObtainPropertiesInfo" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/app" prefix="app"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=ObtainPropertiesInfo.getValByKey("app.name") %></title>
<jsp:include page="/common/StaticJavascript.jsp" />
<script src="<%=basePath%>component/jbpm/persontask/js/personaltask.js"></script>
<script type="text/javascript" src="<%=basePath %>js/threeJs/echarts/echarts.min.js"></script>
<script type="text/javascript">
//查看通知通告明细
function viewGrobalMsg(vId){
	var dialogStruct={
			'display':contextRootPath+'/sysMessage/prepareExecute/toView?id='+vId,
			'width':800,
			'height':500,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};
		
	var dialogView = jyDialog(dialogStruct).open();
}


//查看最新消息明细
function viewMyMsg(vId){
	var dialogStruct={
			'display':contextRootPath+'/sysMessagecenter/prepareExecute/toView?id='+vId,
			'width':800,
			'height':500,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};
		
	var dialogView = jyDialog(dialogStruct).open();
}

//查看系统版本明细
function viewSysVer(vId){
	var dialogStruct={
			'display':contextRootPath+'/sysVersion/prepareExecute/toView?id='+vId,
			'width':800,
			'height':500,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};
		
	var dialogView = jyDialog(dialogStruct).open();
}
</script>
	<style type="text/css">
		.winSwarp{
		    border: 1px solid #c6eded;
		    -moz-border-radius: 5px;
		    -webkit-border-radius: 5px;
		    border-radius: 5px;
		    float: left;
		    margin: 10px 10px;
		}
		.oneOfThree{
			width:32%;
		}
		.oneOfTwo{
			width:48%;
		}
		.oneOfOne{
			width:98%;
		}
		.winSwarp .titleSwarp{
			
		}
		.titleContent a{
			float:right;
			display:none;
			font-size:12px;
			font-weight:normal;
			color:#416484;
			dispaly:block;
			text-decoration:none;
		}
		.titleContent a:hover{
			font-weight:bold;
		}
		.winSwarp .titleContent{
		    /* background: #5bc7c4; */
		    background:url("<%=basePath%>/css/login/images/bg_banner.png") no-repeat right #5bc7c4;
		    padding: 6px 10px;
		    color: #333333;
		    font-size: 14px;
		    border-bottom: 1px solid #c6eded;
		    font-weight: bold;
		}
		.winSwarp .winContent{
			padding:5px;
			overflow:auto;
			height:140px;
		}
		.winSwarp .winContent li{
			list-style-type: square;
			color:#336699;
		}
		.winSwarp .winContent a{
			text-decoration:none;
			font-size:12px;
			color:#336699;
			margin-left: -3px;
		}
		.winSwarp .winContent A:active,.winSwarp .winContent A:visited{
			color:#333333;
			text-decoration:underline;
		}
		.welcomTable{
			width: 100%;
			background: #D9D8DF;
			border: none;
		}
		
		.welcomTableTd{
			text-align:left;
			padding:6px 5px;
			vertical-align: middle;
			font-size:12px;
			min-width:80px;
		}
	</style>
<script type="text/javascript">
	$(function(){
		//PVUV图表
		initPVUVDataFun();
		//用户活跃图
		initActiveDataFun();
		//剪视频剪直播活跃用户数
		initClipLiveVideoActiveDataFun();
		//上传素材量趋势图
		initUploadMaterialsDataFun();
		//上传素材活跃用户
		initUploadMaterialsActiveUserStatDataFun();
	});
	function initPVUVDataFun(){
		var myChart = echarts.init(document.getElementById('pvuvDIV'));
		// 指定图表的配置项和数据
	    var option = {
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
		$.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getPVUVData",
            data:{},
            success:function(msg){
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	var dateList = data.dateList;
            	var pvCountMap = data.pvCountMap;
            	var uvCountMap = data.uvCountMap;
            	var pvData = new Array;
            	var uvData = new Array;
            	for(var i = 0 ; i < dateList.length ; i ++){
            		var dateStr = dateList[i];
            		
            		var pvCount = pvCountMap[dateStr];
            		var uvCount = uvCountMap[dateStr];
            		pvData.push(pvCount);
            		uvData.push(uvCount);
            	}
            	var option = {
           		    title : {
           		        text: '近一周PV/UV趋势图'
           		    },
           		    tooltip : {
           		        trigger: 'axis'
           		    },
           		    legend: {
           		        data:['PV','UV']
           		    },
           		    calculable : true,
           		    xAxis : [
           		        {
           		            type : 'category',
           		            boundaryGap : false,
           		            data : dateList
           		        }
           		    ],
           		    yAxis : [
           		        {
           		            type : 'value',
           		            axisLabel : {
           		                formatter: '{value}'
           		            }
           		        }
           		    ],
           		    series : [
           		        {
           		            name:'PV',
           		            type:'line',
           		         smooth:true,
           		     /*  itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:pvData
           		        },
           		        {
           		            name:'UV',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:uvData
           		        }
           		    ]
           		};
            	
            	myChart.setOption(option);
            },
            error(xht,err,e){
            	myChart.hideLoading();
            	console.log(e);
            }
        });
	}

	
	function initActiveDataFun(){
		var myChart = echarts.init(document.getElementById('activeUserDIV'));
		// 指定图表的配置项和数据
	    var option = {
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
		$.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getActiveUserData",
            data:{},
            success:function(msg){
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	var dateList = data.dateList;
            	var orgCountMap = data.orgCountMap;
            	var commCountMap = data.commCountMap;
            	var otherCountMap = data.otherCountMap;
            	var orgData = new Array;
            	var commData = new Array;
            	var otherData = new Array;
            	for(var i = 0 ; i < dateList.length ; i ++){
            		var dateStr = dateList[i];
            		
            		var orgCount = orgCountMap[dateStr];
            		var commCount = commCountMap[dateStr];
            		var otherCount = otherCountMap[dateStr];
            		orgData.push(orgCount);
            		commData.push(commCount);
            		otherData.push(otherCount);
            	}
            	var option = {
           		    title : {
           		        text: '一周内活跃用户趋势图'
           		    },
           		    tooltip : {
           		        trigger: 'axis'
           		    },
           		    legend: {
           		        data:['机构用户','个人用户',"其他"]
           		    },
           		    /* color:["blue","red","yellow"], */
           		    calculable : true,
           		    xAxis : [
           		        {
           		            type : 'category',
           		            boundaryGap : false,
           		            data : dateList
           		        }
           		    ],
           		    yAxis : [
           		        {
           		            type : 'value',
           		            axisLabel : {
           		                formatter: '{value}'
           		            }
           		        }
           		    ],
           		    series : [
           		        {
           		            name:'机构用户',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:orgData
           		        },
           		        {
           		            name:'个人用户',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:commData
           		        },
           		        {
           		            name:'其他',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:otherData
           		        }
           		    ]
           		};
            	
            	myChart.setOption(option);
            },
            error(xht,err,e){
            	myChart.hideLoading();
            	console.log(e);
            }
        });
	}
	
	function initClipLiveVideoActiveDataFun(){
		var myChart = echarts.init(document.getElementById('jzbJspActiveUserDIV'));
		// 指定图表的配置项和数据
	    var option = {
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
		$.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getClipLiveVideoActiveUserData",
            data:{},
            success:function(msg){
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	var dateList = data.dateList;
            	var jspCountMap = data.jspCountMap;
            	var jzbCountMap = data.jzbCountMap;
            	var jspData = new Array;
            	var jzbData = new Array;
            	for(var i = 0 ; i < dateList.length ; i ++){
            		var dateStr = dateList[i];
            		var jspCount = jspCountMap[dateStr];
            		var jzbCount = jzbCountMap[dateStr];
            		jspData.push(jspCount);
            		jzbData.push(jzbCount);
            	}
            	var option = {
           		    title : {
           		        text: '一周内剪视频/剪直播活跃用户趋势图'
           		    },
           		    tooltip : {
           		        trigger: 'axis'
           		    },
           		    legend: {
           		        data:['剪视频','剪直播']
           		    },
           		    /* color:["blue","red"], */
           		    calculable : true,
           		    xAxis : [
           		        {
           		            type : 'category',
           		            boundaryGap : false,
           		            data : dateList
           		        }
           		    ],
           		    yAxis : [
           		        {
           		            type : 'value',
           		            axisLabel : {
           		                formatter: '{value}'
           		            }
           		        }
           		    ],
           		    series : [
           		        {
           		            name:'剪视频',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:jspData
           		        },
           		        {
           		            name:'剪直播',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:jzbData
           		        }
           		    ]
           		};
            	
            	myChart.setOption(option);
            },
            error(xht,err,e){
            	myChart.hideLoading();
            	console.log(e);
            }
        });
	}
	
	function initUploadMaterialsDataFun(){
		var myChart = echarts.init(document.getElementById('uploadMaterialsDIV'));
		// 指定图表的配置项和数据
	    var option = {
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
		$.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getUploadMaterialsStatCount",
            data:{},
            success:function(msg){
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	var dateList = data.dateList;
            	var uploadMaterialsCountMap = data.uploadMaterialsCountMap;
            	var buyMaterialsCountMap = data.buyMaterialsCountMap;
            	var uploadMaterialsData = new Array;
            	var buyMaterialsData = new Array;
            	for(var i = 0 ; i < dateList.length ; i ++){
            		var dateStr = dateList[i];
            		var uploadMaterialsCount = uploadMaterialsCountMap[dateStr];
            		var buyMaterialsCount = buyMaterialsCountMap[dateStr];
            		uploadMaterialsData.push(uploadMaterialsCount);
            		buyMaterialsData.push(buyMaterialsCount);
            	}
            	var option = {
           		    title : {
           		        text: '一周内上传/购买素材量趋势图'
           		    },
           		    tooltip : {
           		        trigger: 'axis',
	           	        axisPointer: {
	           	            type: 'shadow',
	           	            label: {
	           	                show: true,
	           	                backgroundColor: '#333'
	           	            }
	           	        }
           		    },
           		    legend: {
           		        data:['上传素材量',"购买素材量"]
           		    },
           		    /* color:["blue","red"], */
           		    calculable : true,
           		    xAxis : [
           		        {
           		            type : 'category',
           		            boundaryGap : true,
           		            data : dateList
           		        }
           		    ],
           		    yAxis : [
           		        {
           		            type : 'value',
           		            axisLabel : {
           		                formatter: '{value}'
           		            }
           		        }
           		    ],
           		    series : [
           		        {
           		            name:'上传素材量',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:uploadMaterialsData
           		        },
           		     	{
           		            name:'购买素材量',
           		            type:'line',
           		         	smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:buyMaterialsData
           		        }
           		    ]
           		};
            	
            	myChart.setOption(option);
            },
            error(xht,err,e){
            	myChart.hideLoading();
            	console.log(e);
            }
        });
	}
	
	function initUploadMaterialsActiveUserStatDataFun(){
		var myChart = echarts.init(document.getElementById('uploadMaterialsUserDIV'));
		// 指定图表的配置项和数据
	    var option = {
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
		$.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getUploadMaterialsActiveUserStatCount",
            data:{},
            success:function(msg){
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	var dateList = data.dateList;
            	var uploadActiveUserCountMap = data.uploadActiveUserCountMap;
            	var buyActiveUserCountMap = data.buyActiveUserCountMap;
            	var materialsData = new Array;
            	var buyMaterialsData = new Array;
            	for(var i = 0 ; i < dateList.length ; i ++){
            		var dateStr = dateList[i];
            		var materialsCount = uploadActiveUserCountMap[dateStr];
            		var buyMaterialsCount = buyActiveUserCountMap[dateStr];
            		materialsData.push(materialsCount);
            		buyMaterialsData.push(buyMaterialsCount);
            	}
            	var option = {
           		    title : {
           		        text: '一周内上传/购买素材活跃用户量趋势图'
           		    },
           		    tooltip : {
           		        trigger: 'axis'
           		    },
           		    legend: {
           		        data:['上传素材活跃用户',"购买素材活跃用户"]
           		    },
           		    /* color:["blue","red"], */
           		    calculable : true,
           		    xAxis : [
           		        {
           		            type : 'category',
           		            boundaryGap : false,
           		            data : dateList
           		        }
           		    ],
           		    yAxis : [
           		        {
           		            type : 'value',
           		            axisLabel : {
           		                formatter: '{value}'
           		            }
           		        }
           		    ],
           		    series : [
           		        {
           		            name:'上传素材活跃用户',
           		            type:'line',
           		         smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:materialsData
           		        },
           		     {
           		            name:'购买素材活跃用户',
           		            type:'line',
           		         	smooth:true,
           		      /* itemStyle: {normal: {areaStyle: {type: 'default'}}}, */
           		            data:buyMaterialsData
           		        }
           		    ]
           		};
            	
            	myChart.setOption(option);
            },
            error(xht,err,e){
            	myChart.hideLoading();
            	console.log(e);
            }
        });
	}
	
</script>
</head>

<body>
	<div style="position: absolute;top:0px; bottom:0px; left:0px;right:0px;overflow:auto;padding:10px;">
	
	
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">一周内PV/UV趋势图</div>
			<div class="winContent" id="pvuvDIV" style="height: 300px;">
			
			
			</div>
		</div>
		
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">一周内活跃用户趋势图</div>
			<div class="winContent" id="activeUserDIV" style="height: 300px;">
			
			
			</div>
		</div>
		
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">一周内剪直播剪视频活跃用户趋势图</div>
			<div class="winContent" id="jzbJspActiveUserDIV" style="height: 300px;">
			
			
			</div>
		</div>
		
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">一周内上传/购买素材量趋势图</div>
			<div class="winContent" id="uploadMaterialsDIV" style="height: 300px;">
			
			
			</div>
		</div>
		
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">一周内上传/购买素材活跃用户趋势图</div>
			<div class="winContent" id="uploadMaterialsUserDIV" style="height: 300px;">
			
			
			</div>
		</div>
	
		<!-- 
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">我的任务<a href="javascript:void(0)" class="moreTitle" moreUrl="<%=basePath %>component/jbpm/persontask/queryMyTaskInfo.jsp" moreTitle="我的任务 " id="myTask_more">更多></a></div>
			<div class="winContent" id="myTask">
			</div>
		</div>
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">最新消息</div>
			<div class="winContent">
				<table style="width: 100%; border: none; font-size:13px;">
					<c:forEach var="temp" items="${myMsgList}" varStatus="myMsg">
						<tr >
						<td align="left" class="welcomTableTd"><a href='javascript:void(0)' onclick='viewMyMsg(<c:out value="${temp.msgId}"/>);'><c:out value="${temp.title}"/></a></td>
						<td align="right" class="welcomTableTd" style="width: 100px;"><fmt:formatDate value="${temp.createDate}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">通知通告</div>
			<div class="winContent">
				<table style="width: 100%; border: none; font-size:13px;">
					<c:forEach var="temp" items="${grobalMsgList}" varStatus="grobal">
						<tr >
						<td align="left" class="welcomTableTd"><a href='javascript:void(0)' onclick='viewGrobalMsg(<c:out value="${temp.msgId}"/>);'><c:out value="${temp.title}"/></a></td>
						<td align="right" class="welcomTableTd" style="width: 100px;"><fmt:formatDate value="${temp.createDate}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="winSwarp oneOfTwo">
			<div class="titleContent">关于系统</div>
			<div class="winContent">
				<table style="width: 100%; border: none; font-size:13px;">
					<c:forEach var="temp" items="${sysVersionList}" varStatus="sysVer">
						<tr >
						<td align="left" class="welcomTableTd"><a href='javascript:void(0)' onclick='viewSysVer(<c:out value="${temp.id}"/>);'><c:out value="${temp.versionNum}"/></a>&nbsp;&nbsp;<c:out value="${temp.versionName}"/></td>
						<td align="right" class="welcomTableTd" style="width: 110px;">${temp.versionTime}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		 -->
		
	</div>
	 
</body>
</html>
<script type="text/javascript">
	//执行待办任务
	/**
	 * v_taskId 任务ID
	 * v_proInsId 流程实例ID
	 * v_actNa 当前节点名称
	 * v_infId 业务表主键ID
	 * v_tabName 业务表名
	 */
	<%----%> function doTask(v_taskId,v_url,v_proInsId,v_actNa,v_infId,v_tabName,v_bizTabId,_bizType){
	/* 	$.dialog({
		id:	'executeTaskId',
	    lock: true,
	    width:850,
	    height:300,
	    title:'执行任务',
	    content: 'url:<%=basePath%>'+encodeURI(v_url)
		}).max(); */
		//todo 验证 待流程实例是否是挂起 状态
		var v_lock = JBPM.common.getProInsState(v_bizTabId);
		if(!v_lock){
			jyDialog({"type":"warn"}).alert("该待办任务的流程实例已经挂起，暂时不能处理该任务！");
			return ;
		}
		//todo 验证待办任务是否可以执行
		var v_msg = JBPM.common.getOperateTaskStateInfo(v_taskId,'<shiro:principal property="id"/>');
		if(v_msg != null && "" != v_msg){
			jyDialog({"type":"warn"}).alert(v_msg);
			return;
		}
		v_url = '<%=basePath%>'+encodeURI(v_url);
		v_url = v_url +"?taskId="+encodeURI(v_taskId)+"&processInsId="+encodeURI(v_proInsId)+"&acitityName="+encodeURI(v_actNa);
		v_url = v_url +"&bizInfId="+encodeURI(v_infId)+"&bizTabName="+encodeURI(v_tabName)+"&bizTabId="+encodeURI(v_bizTabId);
		v_url = v_url +"&bizType="+encodeURI(_bizType);
		//通过ajax 来锁定数据，页面局部刷新 2014-10-31 17:33:47 chj
		dataLockImp(v_taskId,"1","N");
		
		window.open(v_url,'newwindow'+v_infId);
	}

	function callTaskFun(obj,linkStr){
		 var v_aInfo = "<li><a href='javascript:void(0)' onclick='doTask(\""+obj.TASKID+"\",\""+obj.PRO_ACTIVITY_FORM
				+"\",\""+obj.CUR_EXE_ID+"\",\""+obj.CUR_ACT_NAME+"\",\""+obj.BIZ_INF_ID+"\",\""+obj.BIZ_TAB_NAME+"\",\""+obj.BIZ_TAB_ID+"\",\""+obj.BIZ_TYPE+"\")'>";
		if(obj.BIZ_INF_NAME)	v_aInfo = v_aInfo + obj.BIZ_INF_NAME ;
		
		v_aInfo = v_aInfo+linkStr+"</a> </li>"; 
		return v_aInfo;
		
	}	

	
	function createTable(objs,totalRows,k,v,contentId,opFun){
		if(totalRows == 0) return ;
		var MIN_COUNT=5;
		var tableStr=['<table style="width:100%;font-size:14px;">'];
		debugger;
		var moreBtn=$("#"+contentId+"_more");
		var rowCount=MIN_COUNT;
		if(totalRows > MIN_COUNT){
			moreBtn.show();
			moreBtn.bind("click",function(){
				var url=$(this).attr("moreUrl");
				var title=$(this).attr("moreTitle");
				parent.showObj({"url":url,"text":title});
			});
			
		}else{
			moreBtn.hide();
		}
		if(totalRows<MIN_COUNT){
			rowCount=totalRows;
		}
		
		for(var i=0;i<rowCount;i++){
			tableStr.push('<tr style="height:25px;font-size:12px;">');
			tableStr.push('<td style="text-align:left;white-space: nowrap; word-wrap: normal; overflow: hidden;"><div style="width:200px;white-space: nowrap;word-wrap: normal;">');
			tableStr.push(opFun(objs[i],"&nbsp;&nbsp;"));
			tableStr.push('</div></td>');
			tableStr.push('<td style="width:120px; text-align:right;"><div>');
			//tableStr.push(objs[i][v]);
			tableStr.push('</div></td>');
			tableStr.push('</tr>');
		}
		tableStr.push('</table>');
		$("#"+contentId).html(tableStr.join(""));
	}
	(function loadTask(){
		var url='<%=basePath%>workFlowProvider/findTaskInfo.do?processState=PROCESS_TASK&paramUserId=<shiro:principal property="id"/>&pageSize=5';
		if(false){
			jyAjax(url,"",function(msg){
				var str=createTable(msg.data,msg.totalRows,"BIZ_INF_NAME","START_PRO_TIME","myTask",callTaskFun);
			})
		}
	})(); 

</script>
