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
	
	function returnFun(){
		window.location.href="${basePath}bi/prepareExecute/biList";
	}
	
	function queryData(){
		initFn();
	}
	//初始化 查询页面元素
	function initFn(){
	    var startTime = $("#startTime").val();
	    var endTime = $("#endTime").val();
	    var url = contextRootPath+"/bi/getCloudClipWeeklyStatData";
	    var params = {"startTime":startTime,"endTime":endTime};
	    //JSON.stringify(params)
	    jyAjax(url,params,function(msg){
	    	console.log(msg);
        	var data = msg.data;
        	var copyrightDataMap = data.copyrightDataMap;
        	var thisClipDataMap = data.thisClipDataMap;
        	var thisClipShareDataMap = data.thisClipShareDataMap;
        	var lastClipDataMap = data.lastClipDataMap;
        	var lastClipShareDataMap = data.lastClipShareDataMap;
        	
        	var thisCumulativeClipDataMap = data.thisCumulativeClipDataMap;
        	var thisCumulativeClipShareDataMap = data.thisCumulativeClipShareDataMap;
        	var lastCumulativeClipDataMap = data.lastCumulativeClipDataMap;
        	var lastCumulativeClipShareDataMap = data.lastCumulativeClipShareDataMap;
        	//账号情况
        	$("#allUserCountSpan").html(data["allUserCount"]);
        	$("#issImsUserCountSpan").html(data["issImsUserCount"]);
        	$("#zwMediaUserCountSpan").html(data["zwMediaUserCount"]);
        	$("#newAddUserCountSpan").html(data["newAddUserCount"]);
        	//版权情况
        	$("#abroadVideoCountSpan").html(copyrightDataMap["1"]["count"]);
        	$("#abroadVideoHourSpan").html(copyrightDataMap["1"]["hour"]);
        	$("#homeVideoCountSpan").html(copyrightDataMap["0"]["count"]);
        	$("#homeVideoHourSpan").html(copyrightDataMap["0"]["hour"]);
        	$("#totalVideoCountSpan").html(copyrightDataMap["1"]["count"] + copyrightDataMap["0"]["count"]);
        	$("#totalVideoHourSpan").html(copyrightDataMap["1"]["hour"] + copyrightDataMap["0"]["hour"]);
        	//本周数据
        	var zbCount = thisClipDataMap["1_3"];
        	var lastZbCount = lastClipDataMap["1_3"];
        	var zbjzCount = thisClipDataMap["1_4"];
        	var lastZbjzCount = lastClipDataMap["1_4"];
        	var zbjzShareCount = thisClipShareDataMap["1_4"];
        	var lastZbjzShareCount = lastClipShareDataMap["1_4"];
        	
        	zbCount = zbCount ? zbCount : 0;
        	lastZbCount = lastZbCount ? lastZbCount : 0;
        	zbjzCount = zbjzCount ? zbjzCount : 0;
        	lastZbjzCount = lastZbjzCount ? lastZbjzCount : 0;
        	zbjzShareCount = zbjzShareCount ? zbjzShareCount : 0;
        	lastZbjzShareCount = lastZbjzShareCount ? lastZbjzShareCount : 0;
        	$("#zbCountSpan").html(zbCount);
        	$("#zbPercentSpan").html(getPercent(zbCount,lastZbCount));
        	$("#zbjzCountSpan").html(zbjzCount);
        	$("#zbjzPercentSpan").html(getPercent(lastZbjzCount,lastZbjzCount));
        	$("#zbjzShareCountSpan").html(zbjzShareCount);
        	$("#zbjzSharePercentSpan").html(getPercent(zbjzShareCount,lastZbjzShareCount));
        	//======================================
        	var uploadCount = thisClipDataMap["1_0"];
        	var lastUploadCount = lastClipDataMap["1_0"];
        	var uploadNotIssImsCount = thisClipDataMap["2_0"];
        	var lastUploadNotIssImsCount = lastClipDataMap["2_0"];
        	var uploadShareCount = thisClipShareDataMap["1_0"];
        	var lastUploadShareCount = lastClipShareDataMap["1_0"];
        	var uploadNotIssImsShareCount = thisClipShareDataMap["2_0"];
        	var lastUploadNotIssImsShareCount = lastClipShareDataMap["2_0"];
        	
        	uploadCount = uploadCount ? uploadCount : 0;
        	lastUploadCount = lastUploadCount ? lastUploadCount : 0;
        	uploadNotIssImsCount = uploadNotIssImsCount ? uploadNotIssImsCount : 0;
        	lastUploadNotIssImsCount = lastUploadNotIssImsCount ? lastUploadNotIssImsCount : 0;
        	uploadShareCount = uploadShareCount ? uploadShareCount : 0;
        	lastUploadShareCount = lastUploadShareCount ? lastUploadShareCount : 0;
        	uploadNotIssImsShareCount = uploadNotIssImsShareCount ? uploadNotIssImsShareCount : 0;
        	lastUploadNotIssImsShareCount = lastUploadNotIssImsShareCount ? lastUploadNotIssImsShareCount : 0;
        		
        	$("#uploadCountSpan").html(uploadCount);
        	$("#uploadPercentSpan").html(getPercent(uploadCount,lastUploadCount));
        	$("#uploadNotIssImsCountSpan").html(uploadNotIssImsCount);
        	$("#uploadNotIssImsPercentSpan").html(getPercent(uploadNotIssImsCount,lastUploadNotIssImsCount));
        	$("#uploadShareCountSpan").html(uploadShareCount);
        	$("#uploadSharePercentSpan").html(getPercent(uploadShareCount,lastUploadShareCount));
        	$("#uploadNotIssImsShareCountSpan").html(uploadNotIssImsShareCount);
        	$("#uploadNotIssImsSharePercentSpan").html(getPercent(uploadNotIssImsShareCount,lastUploadNotIssImsShareCount));
        	//======================================
        	var jzCount = thisClipDataMap["1_2"];
        	var lastJzCount = lastClipDataMap["1_2"];
        	var jzNotIssImsCount = thisClipDataMap["2_2"];
        	var lastJzNotIssImsCount = lastClipDataMap["2_2"];
        	var jzShareCount = thisClipShareDataMap["1_2"];
        	var lastJzShareCount = lastClipShareDataMap["1_2"];
        	var jzNotIssImsShareCount = thisClipShareDataMap["2_2"];
        	var lastJzNotIssImsShareCount = lastClipShareDataMap["2_2"];
        	
        	jzCount = jzCount ? jzCount : 0;
        	lastJzCount = lastJzCount ? lastJzCount : 0;
        	jzNotIssImsCount = jzNotIssImsCount ? jzNotIssImsCount : 0;
        	lastJzNotIssImsCount = lastJzNotIssImsCount ? lastJzNotIssImsCount : 0;
        	jzShareCount = jzShareCount ? jzShareCount : 0;
        	lastJzShareCount = lastJzShareCount ? lastJzShareCount : 0;
        	jzNotIssImsShareCount = jzNotIssImsShareCount ? jzNotIssImsShareCount : 0;
        	lastJzNotIssImsShareCount = lastJzNotIssImsShareCount ? lastJzNotIssImsShareCount : 0;
        	$("#jzCountSpan").html(jzCount);
        	$("#jzPercentSpan").html(getPercent(jzCount,lastJzCount));
        	$("#jzNotIssImsCountSpan").html(jzNotIssImsCount);
        	$("#jzNotIssImsPercentSpan").html(getPercent(jzNotIssImsCount,lastJzNotIssImsCount));
        	$("#jzShareCountSpan").html(jzShareCount);
        	$("#jzSharePercentSpan").html(getPercent(jzShareCount,lastJzShareCount));
        	$("#jzNotIssImsShareCountSpan").html(jzNotIssImsShareCount);
        	$("#jzNotIssImsSharePercentSpan").html(getPercent(jzNotIssImsShareCount,lastJzNotIssImsShareCount));
        	
        	
        	$("#activeUserCountSpan").html(data["activeUserCount"]);
        	$("#activeAvgUserCountSpan").html(data["activeAvgUserCount"]);
        	$("#shareCountSpan").html(data["videoShareCount"]);
        	$("#shareAvgCountSpan").html(data["videoAvgShareCount"]);
        	$("#playCountSpan").html(data["videoVVCount"]);
        	$("#playAvgCountSpan").html(data["videoAvgVVCount"]);
        	
        	//累计数据
        	var zbCount = thisCumulativeClipDataMap["1_3"];
        	var lastZbCount = lastCumulativeClipDataMap["1_3"];
        	var zbjzCount = thisCumulativeClipDataMap["1_4"];
        	var lastZbjzCount = lastCumulativeClipDataMap["1_4"];
        	var zbjzShareCount = thisCumulativeClipShareDataMap["1_4"];
        	var lastZbjzShareCount = lastCumulativeClipShareDataMap["1_4"];
        	
        	zbCount = zbCount ? zbCount : 0;
        	lastZbCount = lastZbCount ? lastZbCount : 0;
        	zbjzCount = zbjzCount ? zbjzCount : 0;
        	lastZbjzCount = lastZbjzCount ? lastZbjzCount : 0;
        	zbjzShareCount = zbjzShareCount ? zbjzShareCount : 0;
        	lastZbjzShareCount = lastZbjzShareCount ? lastZbjzShareCount : 0;
        	$("#ljzbCountSpan").html(zbCount);
        	$("#ljzbPercentSpan").html(getPercent(zbCount,lastZbCount));
        	$("#ljzbjzCountSpan").html(zbjzCount);
        	$("#ljzbjzPercentSpan").html(getPercent(lastZbjzCount,lastZbjzCount));
        	$("#ljzbjzShareCountSpan").html(zbjzShareCount);
        	$("#ljzbjzSharePercentSpan").html(getPercent(zbjzShareCount,lastZbjzShareCount));
        	//======================================
        	var uploadCount = thisCumulativeClipDataMap["1_0"];
        	var lastUploadCount = lastCumulativeClipDataMap["1_0"];
        	var uploadNotIssImsCount = thisCumulativeClipDataMap["2_0"];
        	var lastUploadNotIssImsCount = lastCumulativeClipDataMap["2_0"];
        	var uploadShareCount = thisCumulativeClipShareDataMap["1_0"];
        	var lastUploadShareCount = lastCumulativeClipShareDataMap["1_0"];
        	var uploadNotIssImsShareCount = thisCumulativeClipShareDataMap["2_0"];
        	var lastUploadNotIssImsShareCount = lastCumulativeClipShareDataMap["2_0"];
        	
        	uploadCount = uploadCount ? uploadCount : 0;
        	lastUploadCount = lastUploadCount ? lastUploadCount : 0;
        	uploadNotIssImsCount = uploadNotIssImsCount ? uploadNotIssImsCount : 0;
        	lastUploadNotIssImsCount = lastUploadNotIssImsCount ? lastUploadNotIssImsCount : 0;
        	uploadShareCount = uploadShareCount ? uploadShareCount : 0;
        	lastUploadShareCount = lastUploadShareCount ? lastUploadShareCount : 0;
        	uploadNotIssImsShareCount = uploadNotIssImsShareCount ? uploadNotIssImsShareCount : 0;
        	lastUploadNotIssImsShareCount = lastUploadNotIssImsShareCount ? lastUploadNotIssImsShareCount : 0;
        		
        	$("#ljuploadCountSpan").html(uploadCount);
        	$("#ljuploadPercentSpan").html(getPercent(uploadCount,lastUploadCount));
        	$("#ljuploadNotIssImsCountSpan").html(uploadNotIssImsCount);
        	$("#ljuploadNotIssImsPercentSpan").html(getPercent(uploadNotIssImsCount,lastUploadNotIssImsCount));
        	$("#ljuploadShareCountSpan").html(uploadShareCount);
        	$("#ljuploadSharePercentSpan").html(getPercent(uploadShareCount,lastUploadShareCount));
        	$("#ljuploadNotIssImsShareCountSpan").html(uploadNotIssImsShareCount);
        	$("#ljuploadNotIssImsSharePercentSpan").html(getPercent(uploadNotIssImsShareCount,lastUploadNotIssImsShareCount));
        	//======================================
        	var jzCount = thisCumulativeClipDataMap["1_2"];
        	var lastJzCount = lastCumulativeClipDataMap["1_2"];
        	var jzNotIssImsCount = thisCumulativeClipDataMap["2_2"];
        	var lastJzNotIssImsCount = lastCumulativeClipDataMap["2_2"];
        	var jzShareCount = thisCumulativeClipShareDataMap["1_2"];
        	var lastJzShareCount = lastCumulativeClipShareDataMap["1_2"];
        	var jzNotIssImsShareCount = thisCumulativeClipShareDataMap["2_2"];
        	var lastJzNotIssImsShareCount = lastCumulativeClipShareDataMap["2_2"];
        	
        	jzCount = jzCount ? jzCount : 0;
        	lastJzCount = lastJzCount ? lastJzCount : 0;
        	jzNotIssImsCount = jzNotIssImsCount ? jzNotIssImsCount : 0;
        	lastJzNotIssImsCount = lastJzNotIssImsCount ? lastJzNotIssImsCount : 0;
        	jzShareCount = jzShareCount ? jzShareCount : 0;
        	lastJzShareCount = lastJzShareCount ? lastJzShareCount : 0;
        	jzNotIssImsShareCount = jzNotIssImsShareCount ? jzNotIssImsShareCount : 0;
        	lastJzNotIssImsShareCount = lastJzNotIssImsShareCount ? lastJzNotIssImsShareCount : 0;
        	$("#ljjzCountSpan").html(jzCount);
        	$("#ljjzPercentSpan").html(getPercent(jzCount,lastJzCount));
        	$("#ljjzNotIssImsCountSpan").html(jzNotIssImsCount);
        	$("#ljjzNotIssImsPercentSpan").html(getPercent(jzNotIssImsCount,lastJzNotIssImsCount));
        	$("#ljjzShareCountSpan").html(jzShareCount);
        	$("#ljjzSharePercentSpan").html(getPercent(jzShareCount,lastJzShareCount));
        	$("#ljjzNotIssImsShareCountSpan").html(jzNotIssImsShareCount);
        	$("#ljjzNotIssImsSharePercentSpan").html(getPercent(jzNotIssImsShareCount,lastJzNotIssImsShareCount));
        	
        	$("#ljshareCountSpan").html(data["ljvideoShareCount"]);
        	$("#ljplayCountSpan").html(data["ljvideoVVCount"]);
		})
	    
	   /* $.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getCloudClipWeeklyStatData",
            data:{"startTime":startTime,"endTime":endTime},
            success:function(msg){
				
            },
            error(xht,err,e){
            	console.log(e);
            }
        });*/
	}
	
	function getPercent(thisValue,lastValue){
		var returnValue = (lastValue == 0 ? ((thisValue-lastValue)*100) : ((thisValue-lastValue)*100/lastValue)).toFixed(2) + "%"
		return returnValue;
	}
	
	function exportExcelFun(){
		var startTime = $("#startTime").val();
	    var endTime = $("#endTime").val();
	    var url = contextRootPath+"/bi/exportCloudClipWeeklyStatData";
	    var params = {"startTime":startTime,"endTime":endTime};
	    var mask=$("").newMask();
		mask.show("正在导出...");
	    document.frm.action = url;
	    document.frm.submit();
	    var ttt = window.setInterval(function(){
	    	$.ajax({
	            type:"POST",
	            dataType:"JSON",
	            url:contextRootPath+"/bi/checkExportCloudClipWeeklyStatDataSuccess",
	            data:{},
	            success:function(msg){
	            	if(msg == 1){
	            		window.clearInterval(ttt);
	            		mask.close();
	            	}
	            },
	            error(xht,err,e){
	            	console.log(e);
	            }
	        });
	    },500);
	    
	    //mask.close();
	    //JSON.stringify(params)
	    //jyAjax(url,params,function(msg){
	    	
	    //});
	    
	    
	}
	
</script>
<style type="text/css">

	.tableContent table tr td{
		text-align: center;
		vertical-align: middle;
		height: 23px;
		width: 150px;
	}
	
	.tableContent table tr td.tdHeader{
		background-color: #F5F5F5;
	}
</style>
</head>
<body style="background-color:#FFFFFF">
	<h3 class="" style="height: 30px;line-height: 30px;padding-left:2px;background-color: #e8f6f6;border: 1px solid #c6eded;border-bottom: 0px;">
		云剪业务周报
	</h3>
	
	<div>
		<form name="frm" method="post" action="">
			<div class="field">
				<label class="fieldName" for="speed"> 开始时间 :</label>
				<input type="text" style=" width:200px; " value="${startTime }" name="startTime" id="startTime" onClick="WdatePicker()">
			</div>
			<div class="field">
				<label class="fieldName" for="speed"> 结束时间 :</label>
				<input type="text" style=" width:200px; " value="${endTime }" name="endTime" id="endTime" onClick="WdatePicker()">
			</div>
		</form>
		<div class="searchBtn">
			<button class="ui-button ui-corner-all ui-widget" onclick="queryData()">
				<span class="ui-button-icon ui-icon ui-icon-search"></span>
				<span class="ui-button-icon-space"> </span>查询
			</button>
		</div>
	</div>
	
	<div id="tableToolbar" class="tableToolbar" >
		 <a href="javascript:void(0)" onclick="returnFun()" index="0">返回</a>
		 <a href="javascript:void(0)" onclick="exportExcelFun()" index="0">导出到Excel</a>
	 </div>
	 <!-- 页面初始化 需要的 div -->
	<div id="content" class="tableContent" style="position: relative;min-width: 1000px;">
		<table cellpadding="0" cellspacing="0" width="" border="0">
			<tr>
				<td rowspan="2" style="font-weight: bold;">用户情况</td>
				<td class="tdHeader">ISS/IMS账号数</td>
				<td class="tdHeader">政务媒体账号数</td>
				<td class="tdHeader">账号总数</td>
				<td class="tdHeader">本周新增账号总数</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>	
			<tr>
				<td><span id="issImsUserCountSpan"></span></td>
				<td><span id="zwMediaUserCountSpan"></span></td>
				<td><span id="allUserCountSpan"></span></td>
				<td><span id="newAddUserCountSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td rowspan="2" style="font-weight: bold;">版权情况</td>
				<td class="tdHeader">海外片段数量</td>
				<td class="tdHeader">海外总时长</td>
				<td class="tdHeader">国内片段数量</td>
				<td class="tdHeader">国内总时长</td>
				<td class="tdHeader">总计片段数量</td>
				<td class="tdHeader">总计时长</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>	
			<tr>
				<td><span id="abroadVideoCountSpan"></span></td>
				<td><span id="abroadVideoHourSpan"></span></td>
				<td><span id="homeVideoCountSpan"></span></td>
				<td><span id="homeVideoHourSpan"></span></td>
				<td><span id="totalVideoCountSpan"></span></td>
				<td><span id="totalVideoHourSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td rowspan="8" style="font-weight: bold;">本周云剪后台数据</td>
				<td class="tdHeader">接入直播数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">直播内容剪辑数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">直播内容剪辑分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>	
			<tr>
				<td><span id="zbCountSpan"></span></td>
				<td><span id="zbPercentSpan"></span></td>
				<td><span id="zbjzCountSpan"></span></td>
				<td><span id="zbjzPercentSpan"></span></td>
				<td><span id="zbjzShareCountSpan"></span></td>
				<td><span id="zbjzSharePercentSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="tdHeader">上传数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传数量（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传后分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传后分享（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
			</tr>	
			<tr>
				<td><span id="uploadCountSpan"></span></td>
				<td><span id="uploadPercentSpan"></span></td>
				<td><span id="uploadNotIssImsCountSpan"></span></td>
				<td><span id="uploadNotIssImsPercentSpan"></span></td>
				<td><span id="uploadShareCountSpan"></span></td>
				<td><span id="uploadSharePercentSpan"></span></td>
				<td><span id="uploadNotIssImsShareCountSpan"></span></td>
				<td><span id="uploadNotIssImsSharePercentSpan"></span></td>
			</tr>
			<tr>
				<td class="tdHeader">剪辑数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑数量（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑后分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑后分享 （去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
			</tr>	
			<tr>
				<td><span id="jzCountSpan"></span></td>
				<td><span id="jzPercentSpan"></span></td>
				<td><span id="jzNotIssImsCountSpan"></span></td>
				<td><span id="jzNotIssImsPercentSpan"></span></td>
				<td><span id="jzShareCountSpan"></span></td>
				<td><span id="jzSharePercentSpan"></span></td>
				<td><span id="jzNotIssImsShareCountSpan"></span></td>
				<td><span id="jzNotIssImsSharePercentSpan"></span></td>
			</tr>
			<tr>
				<td class="tdHeader">活跃用户数</td>
				<td class="tdHeader">日均活跃用户数</td>
				<td class="tdHeader">视频分享数</td>
				<td class="tdHeader">日均视频分享数</td>
				<td class="tdHeader">视频播放量</td>
				<td class="tdHeader">日均视频播放量</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>
			<tr>
				<td><span id="activeUserCountSpan"></span></td>
				<td><span id="activeAvgUserCountSpan"></span></td>
				<td><span id="shareCountSpan"></span></td>
				<td><span id="shareAvgCountSpan"></span></td>
				<td><span id="playCountSpan"></span></td>
				<td><span id="playAvgCountSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td rowspan="8" style="font-weight: bold;">累计云剪后台数据</td>
				<td class="tdHeader">接入直播数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">直播内容剪辑数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">直播内容剪辑分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>	
			<tr>
				<td><span id="ljzbCountSpan"></span></td>
				<td><span id="ljzbPercentSpan"></span></td>
				<td><span id="ljzbjzCountSpan"></span></td>
				<td><span id="ljzbjzPercentSpan"></span></td>
				<td><span id="ljzbjzShareCountSpan"></span></td>
				<td><span id="ljzbjzSharePercentSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="tdHeader">上传数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传数量（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传后分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">上传后分享（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
			</tr>	
			<tr>
				<td><span id="ljuploadCountSpan"></span></td>
				<td><span id="ljuploadPercentSpan"></span></td>
				<td><span id="ljuploadNotIssImsCountSpan"></span></td>
				<td><span id="ljuploadNotIssImsPercentSpan"></span></td>
				<td><span id="ljuploadShareCountSpan"></span></td>
				<td><span id="ljuploadSharePercentSpan"></span></td>
				<td><span id="ljuploadNotIssImsShareCountSpan"></span></td>
				<td><span id="ljuploadNotIssImsSharePercentSpan"></span></td>
			</tr>
			<tr>
				<td class="tdHeader">剪辑数量</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑数量（去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑后分享</td>
				<td class="tdHeader">环比增长</td>
				<td class="tdHeader">剪辑后分享 （去除ISS/IMS）</td>
				<td class="tdHeader">环比增长</td>
			</tr>	
			<tr>
				<td><span id="ljjzCountSpan"></span></td>
				<td><span id="ljjzPercentSpan"></span></td>
				<td><span id="ljjzNotIssImsCountSpan"></span></td>
				<td><span id="ljjzNotIssImsPercentSpan"></span></td>
				<td><span id="ljjzShareCountSpan"></span></td>
				<td><span id="ljjzSharePercentSpan"></span></td>
				<td><span id="ljjzNotIssImsShareCountSpan"></span></td>
				<td><span id="ljjzNotIssImsSharePercentSpan"></span></td>
			</tr>
			<tr>
				<td class="tdHeader">视频分享数</td>
				<td class="tdHeader">视频播放量</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
				<td class="tdHeader">&nbsp;</td>
			</tr>
			<tr>
				<td><span id="ljshareCountSpan"></span></td>
				<td><span id="ljplayCountSpan"></span></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>
