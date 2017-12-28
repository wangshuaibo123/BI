//utf-8
//点击新增按钮 事件跳转至 新增页面
var dialogAdd;
function toAddData(){
	var dialogStruct={
		'display':contextRootPath+'/sysAppLevelSetup/prepareExecute/toAdd',
		'width':800,
		'height':500,
		'title':'新增',
		'buttons':[
         {'text':'保存','action':doAddFrom,'isClose':false},
		 {'text':'关闭','isClose':true}
		]
	};
	
	dialogAdd = jyDialog(dialogStruct);
	dialogAdd.open();
}
//新增页面的保存操作
function doAddFrom(){
	//序列化 新增页面的form表单数据
	//if(!checkIsNull($("#addNewsFormData"))) return;
	var obj = dialogAdd.getIframe();
	var appFlag = obj.$("#dtoappFlag").val();
	var logLevel = obj.$("#dtologLevel").val();
	var emailFlag = obj.$("#dtoemailFlag").val();
	var showDetailFlag = obj.$("#dtoshowDetailFlag").val();
	var smsFlag = obj.$("#dtosmsFlag").val();
	var rate = obj.$("#dtorate").val();
	var rateUnit = obj.$("#dtorateUnit").val();
	var keyWord = obj.$("#dtokeyWord").val();
	
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请选择业务系统");
		return false;
	}
	if(logLevel==null || $.trim(logLevel)==""){
		jyDialog({"type":"info"}).alert("请选择级别类型");
		return false;
	}
	if(emailFlag==null || $.trim(emailFlag)==""){
		jyDialog({"type":"info"}).alert("请选择邮件提醒标示");
		return false;
	}
	if(showDetailFlag==null || $.trim(showDetailFlag)==""){
		jyDialog({"type":"info"}).alert("请选择展示详细信息标示");
		return false;
	}
	if(smsFlag==null || $.trim(smsFlag)==""){
		jyDialog({"type":"info"}).alert("请选择短信提醒标示");
		return false;
	}
	if(rate==null || $.trim(rate)=="" || $.trim(rate)=="0"){
		jyDialog({"type":"info"}).alert("请填写提醒频率，不能为零");
		return false;
	}
	if(rateUnit==null || $.trim(rateUnit)==""){
		jyDialog({"type":"info"}).alert("请选择提醒频率");
		return false;
	}
	if(keyWord==null || $.trim(keyWord)==""){
		jyDialog({"type":"info"}).alert("请填写关键字");
		return false;
	}
	
	var params = obj.$("#addNewsFormData").serialize();
	var url = contextRootPath+'/sysAppLevelSetup/insertSysAppLevelSetup';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//新增成功后，
			$("").newMsg({}).show(msg.msg);;
			var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogAdd.close();
        		//新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的  数据
        		queryData();
        	}
  	});
}
//修改 事件
var dialogUpdate;
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
			'display':contextRootPath+'/sysAppLevelSetup/prepareExecute/toUpdate?id='+v_ids,
			'width':800,
			'height':500,
			'title':'修改',
			'buttons':[
             {'text':'保存','action':doUpdateFrom,'isClose':false},
			 {'text':'关闭','isClose':true}
			]
		};
		
	dialogUpdate = jyDialog(dialogStruct);
	dialogUpdate.open();
}

//修改页面 的 保存操作
function doUpdateFrom(){
	//序列化 新增页面的form表单数据
	//if(!checkIsNull($("#updateNewsFormData"))) return;
	var obj = dialogUpdate.getIframe();
	var appFlag = obj.$("#dtoappFlag").val();
	var logLevel = obj.$("#dtologLevel").val();
	var emailFlag = obj.$("#dtoemailFlag").val();
	var showDetailFlag = obj.$("#dtoshowDetailFlag").val();
	var smsFlag = obj.$("#dtosmsFlag").val();
	var rate = obj.$("#dtorate").val();
	var rateUnit = obj.$("#dtorateUnit").val();
	var keyWord = obj.$("#dtokeyWord").val();
	
	
	if(appFlag==null || $.trim(appFlag)==""){
		jyDialog({"type":"info"}).alert("请选择业务系统");
		return false;
	}
	if(logLevel==null || $.trim(logLevel)==""){
		jyDialog({"type":"info"}).alert("请选择级别类型");
		return false;
	}
	if(emailFlag==null || $.trim(emailFlag)==""){
		jyDialog({"type":"info"}).alert("请选择邮件提醒标示");
		return false;
	}
	if(showDetailFlag==null || $.trim(showDetailFlag)==""){
		jyDialog({"type":"info"}).alert("请选择展示详细信息标示");
		return false;
	}
	if(smsFlag==null || $.trim(smsFlag)==""){
		jyDialog({"type":"info"}).alert("请选择短信提醒标示");
		return false;
	}
	if(rate==null || $.trim(rate)=="" || $.trim(rate)=="0"){
		jyDialog({"type":"info"}).alert("请填写提醒频率，不能为零");
		return false;
	}
	if(rateUnit==null || $.trim(rateUnit)==""){
		jyDialog({"type":"info"}).alert("请选择提醒频率");
		return false;
	}
	if(keyWord==null || $.trim(keyWord)==""){
		jyDialog({"type":"info"}).alert("请填写关键字");
		return false;
	}
	
	
	var params = obj.$("#updateNewsFormData").serialize();
	var url = contextRootPath+'/sysAppLevelSetup/updateSysAppLevelSetup';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//保存成功后，执行查询页面查询方法
			$("").newMsg({}).show(msg.msg);;
        	var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		dialogUpdate.close();
        		//新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的  数据
        		queryData();
        	}
  	});
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
	            url:contextRootPath+"/sysAppLevelSetup/deleteSysAppLevelSetup?ids="+v_ids,
	            success:function(msg){
	            	$("").newMsg({}).show(msg.msg);;
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
			'display':contextRootPath+'/sysAppLevelSetup/prepareExecute/toView?id='+vId,
			'width':800,
			'height':500,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};
		
	var dialogView = jyDialog(dialogStruct).open();
}