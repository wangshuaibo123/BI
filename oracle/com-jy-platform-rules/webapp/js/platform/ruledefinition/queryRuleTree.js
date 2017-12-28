//utf-8
//点击新增按钮 事件跳转至 新增页面
var dialogAddTreeObj;
var dialogUpdateTreeObj;

function toAddTreeData(id, obj){
	var dialogStruct={
		'display':contextRootPath+'/ruleTree/prepareExecute/toAdd?parentId='+obj.ID,
		'width':500,
		'height':350,
		'title':'新增',
		'buttons':[
         {'text':'保存','action':doAddTreeFrom,'isClose':false},
		 {'text':'关闭','isClose':true}
		]
	};
	
	dialogAddTreeObj=jyDialog(dialogStruct);
	dialogAddTreeObj.open();
}

//新增页面的保存操作
function doAddTreeFrom(){
	var obj = dialogAddTreeObj.getIframe();
	//序列化 新增页面的form表单数据
	if(!checkIsNull(obj.$("#addNewsFormData"))) 
		return;
	var params=obj.$("#addNewsFormData").serialize();
	var url=contextRootPath+'/ruleTree/insertRuleTree';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//新增成功后，
			$("").newMsg({}).show(msg.msg);
			var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		//新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的  数据
        		//queryData();
        		dialogAddTreeObj.close();
        		loadRuleTree();
        	}
  	});
}

//修改 事件
function toUpdateTreeData(id, obj){
//	var v_ids = iframe.iframeObj["table"].getSelectedIds().join(",");
	//如果没有选中 数据则
//	if(v_ids == ""){
//		jyDialog({"type":"info"}).alert("请选择待修改的数据！");
//		return;
//	}
	//如果选择多个 择提示
//	if(v_ids.indexOf(",") > -1){
//		jyDialog({"type":"info"}).alert("请选择一条数据！");
//		return;
//	}

	var dialogStruct={
			'display':contextRootPath+'/ruleTree/prepareExecute/toUpdate?id='+obj.ID,
			'width':500,
			'height':350,
			'title':'修改',
			'buttons':[
             {'text':'保存','action':doUpdateTreeFrom,'isClose':false},
			 {'text':'关闭','isClose':true}
			]
		};
		
	dialogUpdateTreeObj = jyDialog(dialogStruct);
	dialogUpdateTreeObj.open();
}

//修改页面 的 保存操作
function doUpdateTreeFrom(){
	var obj = dialogUpdateTreeObj.getIframe();
	//序列化 新增页面的form表单数据
	if(!checkIsNull(obj.$("#updateNewsFormData"))) return;
	var params=obj.$("#updateNewsFormData").serialize();
	var url=contextRootPath+'/ruleTree/updateRuleTree';
	//通过ajax保存
	jyAjax(
		url,
		params,
		function(msg){
			//保存成功后，执行查询页面查询方法
			$("").newMsg({}).show(msg.msg);
        	var v_status = msg.status;
        	if(v_status.indexOf('ok') >-1){
        		//新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的  数据
        		//queryData();
        		dialogUpdateTreeObj.close();
        		loadRuleTree();
        	}
  	});
}

//删除 事件
function deleteTreeData(id, obj){
	//var v_ids = iframe.iframeObj["table"].getSelectedIds().join(",");
	//如果没有选中 数据则
	if(!obj || !obj.ID){
		jyDialog({"type":"info"}).alert("请选择待删除的数据！");
		return;
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url:contextRootPath+"/ruleTree/deleteRuleTree?id="+obj.ID,
		success:function(msg){
			$("").newMsg({}).show(msg.msg);
			var v_status = msg.status;
			//删除成功后
			if(v_status.indexOf('ok') >-1){
				//iframe.iframeObj["table"].removeSelectedRow();
				//loadRuleTree();
				tree.remove(obj);
			}
		}
	});
}
