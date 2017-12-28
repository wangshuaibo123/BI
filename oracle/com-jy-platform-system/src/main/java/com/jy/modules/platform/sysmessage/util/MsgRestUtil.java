package com.jy.modules.platform.sysmessage.util;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.core.message.ResponseStatus;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;

/**
 * @Description: 消息rest服务工具类
 * @author zhanglin
 * @date 2014年11月17日 下午1:35:38
 */
public final class MsgRestUtil {

	public static final String jyptAppId = "jypt"; // rest服务appId
	
	public static final String jyptURL = RestService.getServiceUrl(jyptAppId);// rest服务地址
	
	public static final String ADD_ONE_MSG_URL   = jyptURL + "/api/platform/sysMessage/add/v1"; 
	
	public static final String GET_ONE_MSG_URL  = jyptURL + "/api/platform/sysMessage/get/v1/";
	
	public static final String GET_MORE_MSG_URL = jyptURL + "/api/platform/sysMessage/search/v1";
	
	public static final String GET_MSGS_URL = jyptURL + "/api/platform/sysMessage/search/v1";
	
	public static final String UPDATE_MSG_URL = jyptURL + "/api/platform/sysMessage/update/v1";
	
	/**更新并返回**/
	public static final String UPDATE_MSG_RTN_URL = jyptURL + "/api/platform/sysMessage/updateRtn/v1";
	
	public static final String UPDATE_USER_MSG_URL = jyptURL + "/api/platform/sysUserMsgRelation/update/v1";
	
	public static final String UPDATE_USER_MSG_RTN_URL = jyptURL + "/api/platform/sysUserMsgRelation/updateRtn/v1";
	
	public static final String ADD_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/add/v1";
	
	public static final String GET_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/search/v1";
	
	public static final String SEARCH_ALL_USEFUL_SYSUSER_URL = jyptURL + "/api/platform/sysUserMsgRelation/queryAllUsefulSysUser/v1";
	
	
	
	/**
	 * 保存消息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<SysMessageDTO> addMsg(SysMessageDTO dto) throws Exception{
		ResponseMsg<SysMessageDTO> responseMsg  = RestClient.doPost(jyptAppId, ADD_ONE_MSG_URL, dto, new TypeReference<ResponseMsg<SysMessageDTO>>(){});
		return responseMsg;
	}
	
	/**
	 * 保存消息关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<SysUserMsgRelationDTO> addUserMsgRelation(SysUserMsgRelationDTO relationDTO) throws Exception{
		ResponseMsg<SysUserMsgRelationDTO> responseMsg =  RestClient.doPost(jyptAppId, ADD_USER_MSG_REL_URL, relationDTO, new TypeReference<ResponseMsg<SysUserMsgRelationDTO>>(){});
		return responseMsg; 
	}
	
	/**
	 * 根据条件查询消息数据
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<QueryRespBean<SysMessageDTO>> searchMsgByCondition(QueryReqBean params) throws Exception{
		ResponseMsg<QueryRespBean<SysMessageDTO>> relData = RestClient.doPost(jyptAppId,GET_MORE_MSG_URL, params,
			new TypeReference<ResponseMsg<QueryRespBean<SysMessageDTO>>>() {
			});
		return relData;
	}
	
	/**
	 * 根据条件查询消息数据
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<QueryRespBean<SysMessageDTO>> searchMsgByComplexCondition(QueryReqBean params) throws Exception{
		ResponseMsg<QueryRespBean<SysMessageDTO>> relData = RestClient.doPost(jyptAppId,GET_MORE_MSG_URL, params,
			new TypeReference<ResponseMsg<QueryRespBean<SysMessageDTO>>>() {
			});
		return relData;
	}
	
	/**
	 * 更新消息状态
	 */
	public static ResponseMsg<Void> updateMsgState(SysMessageDTO dto) throws Exception{
		ResponseMsg<Void> responseMsg = RestClient.doPost(jyptAppId,UPDATE_MSG_URL, dto,
				new TypeReference<ResponseMsg<Void>>() {
				});
		return responseMsg;
	}
	
	/**
	 * 更新消息状态
	 */
	public static ResponseMsg<SysMessageDTO> updateMsg(SysMessageDTO dto) throws Exception{
		ResponseMsg<SysMessageDTO> responseMsg = RestClient.doPost(jyptAppId,UPDATE_MSG_RTN_URL, dto,
				new TypeReference<ResponseMsg<SysMessageDTO>>() {
				});
		return responseMsg;
	}
	
	
	/**
	 * 根据ID查询消息
	 * @param msgId
	 * @return
	 */
	public static ResponseMsg<SysMessageDTO> getSysMessage(long msgId) {
		ResponseMsg<SysMessageDTO> responseMsg  = RestClient.doGet(jyptAppId, GET_ONE_MSG_URL+ msgId, new TypeReference<ResponseMsg<SysMessageDTO>>(){});
		if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
			return responseMsg;
		}
		return null;
	}
	
	/**
	 * 更新消息关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<SysUserMsgRelationDTO> updateUserMsgRelation(SysUserMsgRelationDTO relationDTO) throws Exception{
		ResponseMsg<SysUserMsgRelationDTO> responseMsg =  RestClient.doPost(jyptAppId, UPDATE_USER_MSG_RTN_URL, relationDTO, new TypeReference<ResponseMsg<SysUserMsgRelationDTO>>(){});
		return responseMsg; 
	}
	
	
	/**
	 * 根据条件查询消息关系数据
	 * @return
	 * @throws Exception
	 */
	public static ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> searchMsgRelationData(QueryReqBean params) throws Exception{
		ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> relData = RestClient.doPost(jyptAppId,GET_USER_MSG_REL_URL, params,
			new TypeReference<ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>>>() {
			});
		return relData;
	}
	
	public static <T> boolean isRequestOK(ResponseMsg<T> responseMsg){
		return (responseMsg!=null && ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()));
	}
	
	/**
     * @description: 获取所有可用的（且已分配角色）用户编号列表 
     * @date 2015-12-03 09:37:58
     * @return
     */
	public static ResponseMsg<List<SysUserDTO>> searchAllUsefulSysUser() throws Exception{
		ResponseMsg<List<SysUserDTO>> relData = RestClient.doPost(jyptAppId,SEARCH_ALL_USEFUL_SYSUSER_URL, null,new TypeReference<ResponseMsg<List<SysUserDTO>>>() {});
		return relData;
	}
}
