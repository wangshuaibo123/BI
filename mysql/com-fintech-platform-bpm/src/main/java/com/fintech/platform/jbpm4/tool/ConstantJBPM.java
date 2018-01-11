package com.fintech.platform.jbpm4.tool;

import java.util.HashMap;
import java.util.Map;


/**
 * 定义工作流 常量信息
 * @author
 *1、@controller 控制器（注入服务）
 *2、@service 服务（注入dao）
 *3、@repository dao（实现dao访问）
 *4、@component （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 */
public class ConstantJBPM {
	//选择参与者的 方法 类型
	public static final String PARTNER_TYPE_1 ="系统选择参与者";
	public static final String PARTNER_TYPE_2 ="按规则选择参与者";
	public static final String PARTNER_TYPE_3 ="人工选择参与者";
	public static final String PARTNER_TYPE_4 ="自定义选择参与者";
	public static final String PARTNER_TYPE_5 ="按规则自动选择参与者";
	public static final String PARTNER_TYPE_5_BEAN_ID = "com.fintech.platform.jbpm4.jbpmPartner.JYSelectPartnerServiceImpl";
	//参与者 具体类型
	public static final String PARTNER_0 ="角色";//角色
	public static final String PARTNER_1 ="机构";//机构
	public static final String PARTNER_2 ="人员";//人员
	//定义存放 request 对象的参数 json 数据 
	public static final String MSG = "REQUEST_MSG";
	//定义存放 request 主表主键id
	public static final String MAIN_ID = "MAIN_ID";
	
	public static final String FAILD = "操作失败！！！";
	
	public static final String SUCCESS = "操作成功";
	
	public static final String DO_FAILD = "执行失败！！！";
	
	public static final String DO_SUCCESS = "执行成功";

	
	public static final String OPERATE_SUCCESS = "操作成功";
	public static final String OPERATE_FAILED = "操作失败！！！";
	
	public static final String ADD_SUCESS = "新增成功";
	
	public static final String ADD_FAILED="新增失败！！！";
	
	public static final String UPDATE_SUCESS = "修改成功";
	
	public static final String UPDATE_FAILED="修改失败！！！";
	
	public static final String DELETE_SUCCESS="删除成功";
	
	public static final String DELETE_FAILED="删除失败！！！";
	
	
	public static final String STRING_1 = "1";
	
	public static final String BRING_BACK_FAILD = "执行失败,您不能取回该任务！";
		
	//查询 流程图的id
	public static final String QUERY_PROCESS_PHOTO_ID = "QUERY_PROCESS_PHOTO_ID";
	
	//隐藏待办任务
	public static final String HIDDEN_ASSIGEN_OF_TASK = "HIDDEN_ASSIGEN_OF_TASK";
	
	//显示待办任务
	public static final String BACKTO_ASSIGEN_OF_TASK = "BACKTO_ASSIGEN_OF_TASK";
	
	//删除 流程实例
	public static final String EXCEPTION_STOP_PROCESS_INSTANCE = "EXCEPTION_STOP_PROCESS_INSTANCE";
	
	//指定 代办者
	public static final String UPDATE_ASSIGEN_OF_TASK = "UPDATE_ASSIGEN_OF_TASK";
	
	//设置委托 代办者
	public static final String UPDATE_ENTRUST_OF_TASK = "UPDATE_ENTRUST_OF_TASK";
	//回退 待办任务
	public static final String BACK_WAY_PROCESS ="BACK_WAY_PROCESS";	
	//取回 任务操作
	public static final String BRING_BACK = "BRING_BACK";
	//选择参与者 类型 部门内人员
	public static final String SELECT_PAR_TYPE_OF_PERSON = "部门内人员";
	
	//选择参与者 类型 其他部门 综合岗
	public static final String SELECT_PAR_TYPE_OF_OTHER_DEPT = "其他部门综合岗";
	
	//选择其他部门处长或部门总
	public static final String SELECT_PAR_TYPE_OF_CHIEF = "选择其他部门处长或部门总";
	
	public static final String SELECT_PAR_TYPE = "系统缩小参与者范围";
	
	//与数据库中的值相同...
	public static final String SELECT_PAR_OPERATE_1 = "主办部门综合岗选送其他部门的综合岗";
	
	public static final String SELECT_PAR_OPERATE_2 = "综合岗送回主办部门综合岗";
	
	
	//查询 某个人同部门内的 所有普通员工/工作流处长 信息
	public static final String QUERY_PAR_INFO_OF_EMP = "QUERY_PAR_INFO_OF_EMP";
	
	//查询 部门处长 
	public static final String QUERY_PAR_INFO_OF_CHU_ZHANG = "QUERY_PAR_INFO_OF_CHU_ZHANG";
	
	//处长 指定其 部门总
	public static final String QUERY_PAR_INFO_OF_DEPT_CHIEF = "QUERY_PAR_INFO_OF_DEPT_CHIEF";
	
	//部门总 指定其他部门总
	public static final String QUERY_PAR_INFO_OF_OTHER_DEPT_CHIEF = "QUERY_PAR_INFO_OF_OTHER_DEPT_CHIEF";
	
	//查询 同一个机构下的综合岗
	public static final String QUERY_PAR_INFO_OF_ZONG_HE = "QUERY_PAR_INFO_OF_ZONG_HE";
	
	//综合岗 指定所在部门 leval > 1 的部门总，部门处长
	public static final String QUERY_PAR_INFO_OF_ZONG_AND_CHU_ZHANG = "QUERY_PAR_INFO_OF_ZONG_AND_CHU_ZHANG";
	//添加 条款意见 信息
	public static final String ADD_PROVISION_OPTION = "ADD_PROVISION_OPTION";
	//修改 条款意见信息
	public static final String MODIFY_PROVISION_OPTION = "MODIFY_PROVISION_OPTION";
	//delete 条款意见信息
	public static final String DELETE_PROVISION_OPTION = "DELETE_PROVISION_OPTION";
	//通过 业务主表中的 已选会签部门 选择其 综合岗
	public static final String QUERY_ZONG_HE_INFO_BY_SELECTED_DEPT = "QUERY_ZONG_HE_INFO_BY_SELECTED_DEPT";

	public static final String QUERY_PAR_INFO_OF_HOUST_ZONG_HE = "QUERY_PAR_INFO_OF_HOUST_ZONG_HE";
	// 查询 监查审计 指派的人
	public static final String BUSINESS_JUSTICE_BY_SQL = "BUSINESS_JUSTICE_BY_SQL";
	//修改 流程节点 的意见描述
	public static final String MODIFY_OPTION_OF_PRO = "MODIFY_OPTION_OF_PRO";
	//删除 流程节点的意见描述
	public static final String DELETE_OPTION_OF_PRO = "DELETE_OPTION_OF_PRO";
	
	//查询 总公司一级审核人员
	public static final String QUERY_PAR_OF_0_ONE_LEVEL = "QUERY_PAR_OF_0_ONE_LEVEL";
	//查询 总公司二级审核人员
	public static final String QUERY_PAR_OF_0_TWO_LEVEL = "QUERY_PAR_OF_0_TWO_LEVEL";
	//查询 总公司三级审核人员
	public static final String QUERY_PAR_OF_0_THREE_LEVEL = "QUERY_PAR_OF_0_THREE_LEVEL";
	//查询 总公司四级审核人员
	public static final String QUERY_PAR_OF_0_FOUR_LEVEL = "QUERY_PAR_OF_0_FOUR_LEVEL";
	//查询 总公司任务分配员
	public static final String QUERY_PAR_OF_0_ASSIGNER = "QUERY_PAR_OF_0_ASSIGNER";

	//同一 处室
	public static final String SAME_CHU_SHI = "same_chu_shi";
	//同一 二级部门及其以下
	public static final String SAME_BU_MEN = "same_bu_men";
	
	//同一 机构(公司)
	public static final String SAME_BRANCH = "same_branch";
	//人保名下所有公司
	public static final String SAME_COMPANY = "same_company";
	//工作流角色类型
	public static final String JBPM_ROLE_TYPE ="2";
	/**
	 * 定义 工作流选人的相关 sql
	 * 后续添加需要 添加operatePartnerMap
	 * 添加责任类 继承 PartnerHandler 
	 */
	public static final Map<String,String> operatePartnerMap = new HashMap<String,String>();
	static{
		operatePartnerMap.put("相同处室", SAME_CHU_SHI);
		operatePartnerMap.put("相同二级部门", SAME_BU_MEN);
		operatePartnerMap.put("相同公司", SAME_BRANCH);
		operatePartnerMap.put("人保所有公司", SAME_COMPANY);
		
	}
	//是否是会签 1：是 0：否
	public static final String IS_COUNTERSIGN= "isCountersign";
	
}
