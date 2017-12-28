//utf-8
//点击新增按钮 事件跳转至 新增页面

//查看明细
function viewData(vId){
	var dialogStruct={
			'display':contextRootPath+'/qrtzExtLog/prepareExecute/toView?id='+vId,
			'width':800,
			'height':500,
			'title':'查看明细',
			'buttons':[
			 {'text':'关闭','isClose':true}
			]
	};

	var dialogView = jyDialog(dialogStruct).open();
}