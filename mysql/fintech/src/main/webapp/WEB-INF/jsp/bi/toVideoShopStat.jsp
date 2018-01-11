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
</script>

</head>
<body style="background-color:#FFFFFF">
	<h3 class="" style="height: 30px;line-height: 30px;padding-left:2px;background-color: #e8f6f6;border: 1px solid #c6eded;border-bottom: 0px;">
		按版权方统计视频个数
	</h3>
	<%-- <div>
		<div class="field">
			<label class="fieldName" for="speed"> 开始时间 :</label>
			<input type="text" style=" width:200px; " value="${startTime }" name="startTime" id="startTime" onClick="WdatePicker()">
		</div>
		<div class="field">
			<label class="fieldName" for="speed"> 结束时间 :</label>
			<input type="text" style=" width:200px; " value="${endTime }" name="endTime" id="endTime" onClick="WdatePicker()">
		</div>
		<div class="searchBtn">
			<button class="ui-button ui-corner-all ui-widget" onclick="queryData()">
				<span class="ui-button-icon ui-icon ui-icon-search"></span>
				<span class="ui-button-icon-space"> </span>查询
			</button>
		</div>
	</div> --%>
	<div id="tableToolbar" class="tableToolbar" >
		 <a href="javascript:void(0)" onclick="returnFun()" index="0">返回</a>
	 </div>
	 <!-- 页面初始化 需要的 div -->
	<div id="content" style="position: relative;"></div>
	<div id="main" style="width: 1000px;height:400px;position: relative;clear: both;margin-top: 10px;">
	</div>
	
</body>
<!-- 相关js方法 -->	
<script>
	var iframe;
	//初始化 查询页面元素
	function initFn(){
		var myChart = echarts.init(document.getElementById('main'));
		// 指定图表的配置项和数据
	    var option = {
	        title: {
	            text: '按版权方统计视频数'
	        }
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    myChart.showLoading();
	    
	    var startTime = $("#startTime").val();
	    var endTime = $("#endTime").val();
	    $.ajax({
            type:"POST",
            dataType:"JSON",
            url:contextRootPath+"/bi/getVideoShopStat",
            data:{"startTime":startTime,"endTime":endTime},
            success:function(msg){
				console.log(msg);
            	myChart.hideLoading();
            	var data = msg.data;
            	if(!data){
            		return;
            	}
            	//定义 table 列表信息	
        		var tableStructure = {
        			//定义table 列表的表头信息
        			columns : [
        			 {display : '版权方', code : 'showName', width : 150, align : 'left', type:'text', isOrder : false},
        			 {display : '视频数', code : 'count', width : 150, align : 'left', type:'text', isOrder : false},
        		   ],
        			//url : "${basePath}bi/testbi1",
        			datas:data,
        			//pageSize : 10,
        			//toolbar:"tableToolbar",
        			selectType : 'checkbox',
        			isCheck : true,
        			rownumbers : true,
        			isPage:false,
        			hiddenFooter:true,
        			//pages : [ 10, 20, 30 ],
        			trHeight : 30,
        			primaryKey:"id"
        		};
        		//组装 searchIframe 的相关参数		
        		var searchIframe={"toolbar":toolbar,"table":tableStructure};	
        		//初始化 form 表单 table 列表 及工具条 
        		//iframe=$("#content").newSearchIframe(searchIframe);
        		iframe=$("#content").newTable(tableStructure);
        		iframe.show(true);
        		
        		var legendArr = [];
        		var seriesDataArr = [];
        		for(var i = 0 ; i < data.length ; i ++){
        			var obj = data[i];
        			legendArr.push(obj.showName);
        			var seriesData = {};
        			seriesData.name=obj.showName;
        			seriesData.value=obj.count;
        			seriesDataArr.push(seriesData);
        		}
        		
        		var newOption = {
        			    title : {
        			        text: '按版权方统计视频数',
        			        x:'center'
        			    },
        			    tooltip : {
        			        trigger: 'item',
        			        formatter: "{a} <br/>{b} : {c} ({d}%)"
        			    },
        			    legend: {
        			        orient : 'vertical',
        			        x : 'left',
        			        data:legendArr
        			    },
        			    calculable : true,
        			    series : [
        			        {
        			            name:'视频数',
        			            type:'pie',
        			            radius : '55%',
        			            center: ['50%', '60%'],
        			            itemStyle: {  
        			                normal: {label:{  
        			                  show:true,  
        			                  formatter:'{b} : {c} ({d}%)'  
        			              },  
        			              labelLine:{show:true}},  
        			                emphasis: {  
        			                    label: {  
        			                        show: true,  
        			                        formatter: "{b}\n{c} ({d}%)",  
        			                        position: 'center',  
        			                        textStyle: {  
        			                            fontSize: '20',  
        			                            fontWeight: 'bold'  
        			                        }  
        			                    }  
        			                }  
        			            },
        			            data:seriesDataArr
        			        }
        			    ]
        			};
            	myChart.setOption(newOption);
            },
            error(xht,err,e){
            	console.log(e);
            }
        });
	}
</script> 
</html>
