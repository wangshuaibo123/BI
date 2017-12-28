//utf-8
//点击新增按钮 事件跳转至 新增页面
var dialogAddObj;
var dialogUpdateObj;
function toAddData(){
	var dialogStruct={
		'display':contextRootPath+'/sysFlumeConfigZk/prepareExecute/toAdd',
		'width':850,
		'height':600,
		'title':'新增',
		'buttons':[
         {'text':'保存','action':doAddFrom,'isClose':false},
         {'text':'提交','action':doAddCommitFrom,'isClose':false},
		 {'text':'关闭','isClose':true}
		]
	};
	
	dialogAddObj=jyDialog(dialogStruct);
	dialogAddObj.open();
}

//新增页面的保存操作
function doAddFrom(){
	var obj = dialogAddObj.getIframe();
	var appFlag = obj.$("#appFlag").val();
	var appIp = obj.$("#appIp").val();
	var config = obj.$("#config").val();
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请填写系统标示");
		return false;
	}
	if(appIp==null || $.trim(appIp)==""){
		jyDialog({"type":"info"}).alert("请填写机器IP");
		return false;
	}
	if(config==null || $.trim(config)==""){
		jyDialog({"type":"info"}).alert("请填写配置信息");
		return false;
	}
	
	var params=obj.$("#addNewsFormData").serialize();
	var url=contextRootPath+'/sysFlumeConfigZk/insertSysFlumeConfigZk';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//新增成功后，
			$("").newMsg({}).show(msg.msg);
			var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogAddObj.close();
        		queryData();
        	}
  	});
}

function doAddCommitFrom(){
	var obj = dialogAddObj.getIframe();
	var appFlag = obj.$("#appFlag").val();
	var appIp = obj.$("#appIp").val();
	var config = obj.$("#config").val();
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请填写系统标示");
		return false;
	}
	if(appIp==null || $.trim(appIp)==""){
		jyDialog({"type":"info"}).alert("请填写机器IP");
		return false;
	}
	if(config==null || $.trim(config)==""){
		jyDialog({"type":"info"}).alert("请填写配置信息");
		return false;
	}
	
	var params=obj.$("#addNewsFormData").serialize();
	var url=contextRootPath+'/sysFlumeConfigZk/insertAndCommitSysFlumeConfigZk';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//新增成功后，
			$("").newMsg({}).show(msg.msg);
			var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogAddObj.close();
        		queryData();
        	}
  	});
}

//修改 事件
function toUpdateData(){
	var v_ids = iframe.iframeObj["table"].getSelectedIds().join(",");
	//如果没有选中 数据则
	if(v_ids == ""){
		jyDialog({"type":"info"}).alert("请选择待修改的数据！");
		return;
	}
	//如果选择多个 择提示
	if(v_ids.indexOf(",") > -1){
		jyDialog({"type":"info"}).alert("请选择一条数据！");
		return;
	}

	var dialogStruct={
			'display':contextRootPath+'/sysFlumeConfigZk/prepareExecute/toUpdate?id='+v_ids,
			'width':850,
			'height':600,
			'title':'修改',
			'buttons':[
             {'text':'保存','action':doUpdateFrom,'isClose':false},
             {'text':'提交','action':doUpdateCommitFrom,'isClose':false},
			 {'text':'关闭','isClose':true}
			]
		};
		
	dialogUpdateObj = jyDialog(dialogStruct);
	dialogUpdateObj.open();
}

//修改页面 的 保存操作
function doUpdateFrom(){
	var obj = dialogUpdateObj.getIframe();
	var appFlag = obj.$("#appFlag").val();
	var appIp = obj.$("#appIp").val();
	var config = obj.$("#config").val();
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请填写系统标示");
		return false;
	}
	if(appIp==null || $.trim(appIp)==""){
		jyDialog({"type":"info"}).alert("请填写机器IP");
		return false;
	}
	if(config==null || $.trim(config)==""){
		jyDialog({"type":"info"}).alert("请填写配置信息");
		return false;
	}
	
	var params=obj.$("#updateNewsFormData").serialize();
	var url=contextRootPath+'/sysFlumeConfigZk/updateSysFlumeConfigZk';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//保存成功后，执行查询页面查询方法
			$("").newMsg({}).show(msg.msg);
        	var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogUpdateObj.close();
        		queryData();
        	}
  	});
}

function doUpdateCommitFrom(){
	var obj = dialogUpdateObj.getIframe();
	var appFlag = obj.$("#appFlag").val();
	var appIp = obj.$("#appIp").val();
	var config = obj.$("#config").val();
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请填写系统标示");
		return false;
	}
	if(appIp==null || $.trim(appIp)==""){
		jyDialog({"type":"info"}).alert("请填写机器IP");
		return false;
	}
	if(config==null || $.trim(config)==""){
		jyDialog({"type":"info"}).alert("请填写配置信息");
		return false;
	}
	
	var params=obj.$("#updateNewsFormData").serialize();
	var url=contextRootPath+'/sysFlumeConfigZk/updateAndCommitSysFlumeConfigZk';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//保存成功后，执行查询页面查询方法
			$("").newMsg({}).show(msg.msg);
        	var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogUpdateObj.close();
        		queryData();
        	}
  	});
}

//批量提交
function doCommitData(){
	var v_ids = iframe.iframeObj["table"].getSelectedIds().join(",");
	//如果没有选中 数据则
	if(v_ids == ""){
		jyDialog({"type":"info"}).alert("请选择待提交的数据！");
		return;
	}
	
	/*
	var that=this;
	var isDisplay=' display:block; ';
	var tsV="0.12";
	var text="正在提交...";
	var mName = "mName";
	*/
	jyDialog({"type":"question"}).confirm("您确认要提交选择的数据吗？",function(){
		/*
		$(that.container).append(
				'<div style="position: absolute;width:100%;top:0px;bottom:0px;z-index:100000;background:rgba(73, 69, 69, '+tsV+')" id="'
						+ mName
						+ '">'
						+ '<div style="position: absolute; width:105px;left:50%;'+isDisplay
						+ 'border: 2px solid #69AAE6;background:#FFFFFF; margin-left:-55px;top:50%;margin-top:-15px;margin:auto;">'
						+ '<div class="loading"></div><div style="float: left;margin: 6px;">'+text+'</div></div></div>');
		*/
		
		var mask=$("").newMask();
    	mask.show("正在提交...");
		
		$.ajax({
            type:"POST",
            dataType:"json",
            url:contextRootPath+"/sysFlumeConfigZk/batchCommitToZk?ids="+v_ids,
            success:function(msg){
            	$("").newMsg({}).show(msg.msg);
            	var v_status = msg.status;
            	//删除成功后
            	if(v_status.indexOf('ok') >-1){
            		/*
            		$("#"+mName).remove();
            		*/
            		mask.close();
            		queryData();
            	}
            }
        });
	   },"确认提示");
}

//删除 事件
function deleteData(){
	var v_ids = iframe.iframeObj["table"].getSelectedIds().join(",");
	//如果没有选中 数据则
	if(v_ids == ""){
		jyDialog({"type":"info"}).alert("请选择待删除的数据！");
		return;
	}
	jyDialog({"type":"question"}).confirm("您确认要删除选择的数据吗？",function(){
		$.ajax({
            type:"POST",
            dataType:"json",
            url:contextRootPath+"/sysFlumeConfigZk/deleteSysFlumeConfigZk?ids="+v_ids,
            success:function(msg){
            	$("").newMsg({}).show(msg.msg);
            	var v_status = msg.status;
            	//删除成功后
            	if(v_status.indexOf('ok') >-1){
            		iframe.iframeObj["table"].removeSelectedRow();
            	}
            	
            	
            }
        });
	   },"确认提示");
}

//查看明细
function viewData(vId){
	var dialogStruct={
			'display':contextRootPath+'/sysFlumeConfigZk/prepareExecute/toView?id='+vId,
			'width':850,
			'height':600,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};
		
	var dialogView = jyDialog(dialogStruct).open();
}