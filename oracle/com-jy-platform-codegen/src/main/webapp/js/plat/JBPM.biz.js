/* UTF-8
 * JBPM.biz.js
 * 使用jquery1.4
 * 封装了介于jquery和jbpm 业务逻辑之间的公共功能部分，主要是公共js效果
 * author: chengang
 * 调用示例(详细说明见各个对象及方法)：
 * JBPM.biz.getViewBizUrl('1000');
 */
(function(){
	var _biz = {
		/*
		 * 工作流预览 业务数据 对应的 历史 页面
		 * 获取对应的业务历史信息阅览url
		 * v_bizType WORKFLOW_BIZ_TYPE 对应的业务代码
		 * acitityName 当前流程实例ID中活动的节点名称
		 */
		getViewBizUrl : function(v_bizType){
			var v_url= contextRootPath;
			var v_viewURI = '';
			if("1001" == v_bizType){//客户复议审批
				v_viewURI = '/dojbpm/jbpmReconsideration/prepareExecute/toViewAppInfo';
				v_url += encodeURI(v_viewURI);
			}else if("1000" == v_bizType){//信审审批
				v_viewURI = '/dojbpm/jbpmcreditaudit/prepareExecute/toViewAppInfo';
				v_url += encodeURI(v_viewURI);
			}else if("1002" == v_bizType){//线上稽核审批
				v_viewURI = '/dojbpm/jbpmonlineaudit/prepareExecute/toViewAppInfo';
				v_url += encodeURI(v_viewURI);
			}else if("1003" == v_bizType){//减免审批
				v_viewURI = '/dojbpm/jbpmReductionApp/prepareExecute/toSubRiskAppInfo2';
				v_url += encodeURI(v_viewURI);
			}else if("1004" == v_bizType){//提前结清审批
				v_viewURI = '/dojbpm/jbpmAdvfinished/prepareExecute/toTQJQSPONEV2';
				v_url += encodeURI(v_viewURI);
			}else if("1005" == v_bizType){//息费计停审批
				v_viewURI = '/aftloanjbpm/jbpmStopbilled/toStopRiskV2';
				v_url += encodeURI(v_viewURI);
			}else if("1006" == v_bizType){//取消息费计停审批
				v_viewURI = '/aftloanjbpm/jbpmStopbilled/toCancelRiskV2';
				v_url += encodeURI(v_viewURI);
			}else if("1008" == v_bizType){//贷款信息维护审批
				v_viewURI = '/dojbpm/jbpmDloanmain/prepareExecute/toDloanmainSubRiskAppInfo2';
				v_url += encodeURI(v_viewURI);
			}else if("1009" == v_bizType){//提前还款审批
				v_viewURI = '/dojbpm/jbpmDxretuahea/prepareExecute/toCPSP1';
				v_url += encodeURI(v_viewURI);
			}else if("1010" == v_bizType){//强制结清审批
				v_viewURI = '/aftloanjbpm/jbpmTakebreak/toTakebreakRiskV2';
				v_url += encodeURI(v_viewURI);
			}else if("1011" == v_bizType){//已结TAB减免详情
				v_viewURI = '/dojbpm/jbpmReductionApp/prepareExecute/toSubReductionAppInfoThree';
				v_url += encodeURI(v_viewURI);
			}else if("1012" == v_bizType){//车贷
				v_viewURI = '/dojbpm/jbpmcarloanaudit/prepareExecute/toViewAppInfo';
				v_url += encodeURI(v_viewURI);
			}else if("1015" == v_bizType){//房贷
				v_viewURI = '/dojbpm/jbpmHouseLoanAudit/prepareExecute/toViewAppInfo';
				v_url += encodeURI(v_viewURI);
			}else if("1013" == v_bizType){//取消强制结清审批
				v_viewURI = '/aftloanjbpm/jbpmTakebreak/toCancelTakebreakRiskV2';
				v_url += encodeURI(v_viewURI);
			}else if("1018" == v_bizType){//退费审批审批流
				v_viewURI = '/refundjbpm/jbpmRefund/prepareExecute/toViewJbpmRefund';
				v_url += encodeURI(v_viewURI);
			}else if("1019" == v_bizType){//罚制扣款审批审批流
				v_viewURI = '/penaltyjbpm/jbpmPenalty/prepareExecute/toViewJbpmPenalty';
				v_url += encodeURI(v_viewURI);
			}else if("1020" == v_bizType){//保证金退还审批审批流
				v_viewURI = '/bailRefundjbpm/jbpmBailRefund/prepareExecute/toViewJbpmBailRefund';
				v_url += encodeURI(v_viewURI);
			}else if("1021" == v_bizType){//车辆处置申请审批审批流
				v_viewURI = '/carHandlingjbpm/jbpmCarHandling/prepareExecute/toViewJbpmCarHandling';
				v_url += encodeURI(v_viewURI);
			}else if("2014" == v_bizType){//车贷展期审批流
				v_viewURI = '/aftloanjbpm/jbpmRollLoan/prepareExecute/viewRollLoanAudit';
				v_url += encodeURI(v_viewURI);
			}else if("1016" == v_bizType){//房贷展期审批流
				v_viewURI = '/aftloanjbpm/jbpmHouseRollLoan/prepareExecute/viewHouseRollLoanAudit';
				v_url += encodeURI(v_viewURI);
			}
			
			return v_url;
		}
	};
	
	if(typeof JBPM == "undefined") window.JBPM={};
	JBPM.biz = _biz;
})();