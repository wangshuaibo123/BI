//utf-8
/**
*	对应jsp 页面中的js 只存放 $(document).ready(function(){ ... });
*
**/
function closeWindow(){
var api = frameElement.api, W = api.opener;
	 //获取父页面的值
	 api.close();
}


function saveData(){
var api = frameElement.api, W = api.opener;
	//所有的验证通过后才进行表单的提交
	var v_boolean = $("#newsForm").validationEngine("validate");
	if(v_boolean){
		$("#saveBtn").attr("disabled",true);
		//document.newsForm.submit();
		dealData();
		//调用ajax开始生成代码
		var timestamp = (new Date()).valueOf();
		$.ajax({
		type:"POST",
		async:false, 
		url:JBPM4_PATH+"addJbpm4FormInfo.do?timestamp="+timestamp,
		//data:{operateOfAction:$("#dtotableName").val()},
		data:$("#newsForm").serialize(),//提交的表单信息
		success: function(data){
				alert(data);
				closeWindow();
			}
		});	
	}
}


function changeHideOrShow(){
var v_hml = $("#partnerRolesIdLeg").html();
 	if(v_hml.indexOf("显示") != -1){
 		$("#partnerRolesIdLeg").html("∧隐藏选择参与者设置")
 		$("#partnerRolesId").show();
 		
 	}else{
 		$("#partnerRolesIdLeg").html("∨显示选择参与者设置")
 		$("#partnerRolesId").hide();
 	}
}