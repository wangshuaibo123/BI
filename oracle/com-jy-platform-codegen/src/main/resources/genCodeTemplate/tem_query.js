//utf-8
//点击新增按钮 事件跳转至 新增页面
var dialogAddObj;
function toAddData() {
    var dialogStruct = {
        'display': contextRootPath + '/view/platform/${transTableName?lower_case}/add${formated_tab_name}',
        'width': 800,
        'height': 500,
        'title': '新增',
        'buttons': [{
            'text': '保存',
            'action': doAddFrom,
            'isClose': true
        }, {
            'text': '关闭',
            'isClose': true
        }]
    };

    dialogAddObj = jyDialog(dialogStruct);
    dialogAddObj.open();
}

//新增页面的保存操作
function doAddFrom() {
    var obj = dialogAddObj.getIframe();
    //序列化 新增页面的form表单数据
    var params = obj.$("#addForm").serialize();
    var url = contextRootPath + '/${transTableName}/insert${formated_tab_name}';
    
    //通过ajax保存
    jyAjax(url, params, function(result){
        //新增成功后，
        alert(result.msg);
        var resultStatus = result.status;
        if (resultStatus.indexOf('ok') > -1) {
            //新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的 数据
            queryData();
        }
    });
}

var dialogUpdateObj;
//修改 事件
function toUpdateData() {
    var ids = mainTable.getSelectedIds().join(",");
    
    //如果没有选中 数据则
    if (ids == "") {
        alert("请选择待修改的数据！");
        return;
    }
    
    //如果选择多个 择提示
    if (ids.indexOf(",") > -1) {
        alert("请选择一条数据！");
        return;
    }

    var dialogStruct = {
        'display': contextRootPath + '/view/platform/${transTableName?lower_case}/update${formated_tab_name}?id=' + ids,
        'width': 800,
        'height': 500,
        'title': '修改',
        'buttons': [{
            'text': '保存',
            'action': doUpdateFrom,
            'isClose': true
        }, {
            'text': '关闭',
            'isClose': true
        }]
    };

    dialogUpdateObj = jyDialog(dialogStruct);
    dialogUpdateObj.open();
}

//修改页面 的 保存操作
function doUpdateFrom() {
    var obj = dialogUpdateObj.getIframe();
    //序列化 新增页面的form表单数据
    var params = obj.$("#updateForm").serialize();
    var url = contextRootPath + '/${transTableName}/update${formated_tab_name}';
    
    //通过ajax保存
    jyAjax(url, params, function(result) {
        //保存成功后，执行查询页面查询方法
        alert(result.msg);
        var resultStatus = result.status;
        if (resultStatus.indexOf('ok') > -1) {
            //新增成功后 刷新页面 或 只查询 id为msg.data['id'] 的 数据
            queryData();
        }
    });
}

//删除 事件
function deleteData() {
    var ids = mainTable.getSelectedIds().join(",");
    
    //如果没有选中 数据则
    if (ids == "") {
        alert("请选择待删除的数据！");
        return;
    }
    
    var url = contextRootPath + "/${transTableName}/delete${formated_tab_name}?ids=" + ids;
    if (confirm("您确认要删除选择的数据吗？")) {
        jyAjax(url, "", function(result) {
            //保存成功后，执行查询页面查询方法
            alert(result.msg);
            var resultStatus = result.status;
            if (resultStatus.indexOf('ok') > -1) {
                mainTable.removeSelectedRow();
            }
        });
    }
}

//查看明细
function viewData(vId) {
    var dialogStruct = {
        'display': contextRootPath + '/view/platform/${transTableName?lower_case}/view${formated_tab_name}?id=' + vId,
        'width': 800,
        'height': 500,
        'title': '查看明细',
        'buttons': [{
            'text': '关闭',
            'isClose': true
        }]
    };

    jyDialog(dialogStruct).open();
}