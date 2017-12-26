package com.fintech.modules.platform.sysorg.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysorg.dto.SysOrgChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserSynDTO;
import com.fintech.modules.platform.sysorg.service.SynClientService;
import com.fintech.modules.platform.sysorg.service.SysOrgSynService;
import com.fintech.modules.platform.sysorg.service.SysOrgUserSynService;
import com.fintech.modules.platform.sysorg.service.SysPositionSynService;
import com.fintech.modules.platform.sysorg.service.SysUserSynService;
import com.fintech.modules.platform.sysorg.service.UserSynService;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
/**
 * 定时同步主数据 用户组织机构 任务处理类
 * @author
 */
@Service
public class SynMainDataHandle implements Serializable ,Job{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(SynMainDataHandle.class);
	
	private SysConfigAPI sysConfigAPI;
	
	private String appFlag = "";
	
	/***用户同步服务***/
	private UserSynService userSynService;
	
	private SysOrgSynService sysOrgSynService;
    
	private SysPositionSynService sysPositionSynService;
    
	private SysOrgUserSynService sysOrgUserSynService;
    
	private SysUserSynService sysUserSynService;
	
	private SynClientService synClientService;
	
	private static boolean isNext = true;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("--------进入方法---SynMainDataHandle- execute----------");
			return;
		}
		
		isNext = false;
		
		SchedulerContext cont;
		String msg = "orguser客户端自动更新同步数据出现错误！";
		try {
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			sysConfigAPI = (SysConfigAPI) appCtx.getBean(SysConfigAPI.class);
			userSynService = (UserSynService)appCtx.getBean(UserSynService.class);
			sysOrgSynService = (SysOrgSynService)appCtx.getBean(SysOrgSynService.class);
			sysPositionSynService = (SysPositionSynService)appCtx.getBean(SysPositionSynService.class);
			sysOrgUserSynService = (SysOrgUserSynService)appCtx.getBean(SysOrgUserSynService.class);
			sysUserSynService = (SysUserSynService)appCtx.getBean(SysUserSynService.class);
			synClientService = (SynClientService)appCtx.getBean(SynClientService.class);
			execute();
			msg = "orguser客户端自动更新同步数据成功！";
		} catch (SchedulerException e) {
			logger.error("--------同步用户组织机构 error:",e);
			e.printStackTrace();
		}finally{
			isNext = true;
			logger.info(msg);
		}
	}
	
	public void execute(){
		List<Long> versions = new ArrayList<Long>();
		
		logger.debug("定时同步主数据 begin");
		versions.addAll( synOrg() );
		versions.addAll( synUser() );
		versions.addAll( synOrgPositionRelation() );
		versions.addAll( synPosition() );
		logger.debug("定时同步主数据 end");
		
		//同步可以直接插入的数据
		insert(versions);
	}
	
	/**
	 * 查询出用户同步数据
	 */
	private List<Long> synUser(){
		//最后一次同步时间
		String lastSynTime = userSynService.obtainLastSynTime(new HashMap<String, Object>(), UserSynService.SYNDATATYPE.USER);
		logger.info("SynMainDataHandle find SysUserSynDTO lastSynTime:"+lastSynTime);
		String url = getAppFlagUrl() + "/api/platform/sysUserSyn/search/v1";
		QueryReqBean sparams = new QueryReqBean();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("dto", new SysUserSynDTO());
		hashMap.put("beginSynDateTime", lastSynTime);
		sparams.setSearchParams(hashMap);
		ResponseMsg<QueryRespBean<SysUserSynDTO>> doPost = RestClient.doPost(appFlag, url,sparams, new TypeReference<ResponseMsg<QueryRespBean<SysUserSynDTO>>>(){});
		List<SysUserSynDTO> result = doPost.getResponseBody().getResult();
		logger.info("SynMainDataHandle find SysUserSynDTO size:"+result.size());
		List<Long> versions = new ArrayList<Long>();
		if(result.size() > 0){
			for(SysUserSynDTO sysUserSynDTO: result){
				versions.add(sysUserSynDTO.getVersion());
			}
			//新增用户
			userSynService.insertSysUserSyns(result);
			logger.info("SynMainDataHandle insert SysUserSynDTO end");
		}
		return versions;
	}
	
	/**
	 * 查询出机构同步数据
	 */
	private List<Long> synOrg(){
		//最后一次同步时间
		String lastSynTime = userSynService.obtainLastSynTime(new HashMap<String, Object>(), UserSynService.SYNDATATYPE.ORG);
		logger.info("SynMainDataHandle find SysOrgSynDTO lastSynTime:"+lastSynTime);
		String url = getAppFlagUrl() + "/api/platform/sysOrgSyn/search/v1";
		QueryReqBean sparams = new QueryReqBean();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("dto", new SysOrgSynDTO());
		hashMap.put("beginSynDateTime", lastSynTime);
		sparams.setSearchParams(hashMap);
		ResponseMsg<QueryRespBean<SysOrgSynDTO>> doPost = RestClient.doPost(appFlag, url,sparams, new TypeReference<ResponseMsg<QueryRespBean<SysOrgSynDTO>>>(){});
		List<SysOrgSynDTO> result = doPost.getResponseBody().getResult();
		logger.info("SynMainDataHandle find SysOrgSynDTO size:"+result.size());
		List<Long> versions = new ArrayList<Long>();
		if(result.size() > 0){
			for(SysOrgSynDTO sysOrgSynDTO: result){
				versions.add(sysOrgSynDTO.getVersion());
			}
			//新增机构
			userSynService.insertSysOrgSyns(result);
			logger.info("SynMainDataHandle insert SysOrgSynDTO end");
		}
		return versions;
	}
	/**
	 * 查询出岗位同步数据
	 */
	private List<Long>  synPosition(){
		//最后一次同步时间
		String lastSynTime = userSynService.obtainLastSynTime(new HashMap<String, Object>(), UserSynService.SYNDATATYPE.POSITION);
		logger.info("SynMainDataHandle find SysOrgSynDTO lastSynTime:"+lastSynTime);
		String url = getAppFlagUrl() + "/api/platform/sysPositionSyn/search/v1";
		QueryReqBean sparams = new QueryReqBean();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("dto", new SysOrgSynDTO());
		hashMap.put("beginSynDateTime", lastSynTime);
		sparams.setSearchParams(hashMap);
		ResponseMsg<QueryRespBean<SysPositionSynDTO>> doPost = RestClient.doPost(appFlag, url,sparams, new TypeReference<ResponseMsg<QueryRespBean<SysPositionSynDTO>>>(){});
		List<SysPositionSynDTO> result = doPost.getResponseBody().getResult();
		logger.info("SynMainDataHandle find SysPositionSynDTO size:"+result.size());
		List<Long> versions = new ArrayList<Long>();
		if(result.size() > 0){
			for(SysPositionSynDTO sysPositionSynDTO: result){
				versions.add(sysPositionSynDTO.getVersion());
			}
			//新增岗位
			userSynService.insertSysPositionSyns(result);
			logger.info("SynMainDataHandle insert SysPositionSynDTO end");
		}
		return versions;

	}
	/**
	 * 查询出用户机构岗位关系同步数据
	 */
	private List<Long>  synOrgPositionRelation(){
		//最后一次同步时间
		String lastSynTime = userSynService.obtainLastSynTime(new HashMap<String, Object>(), UserSynService.SYNDATATYPE.ORG_POSITION_RELATION);
		logger.info("SynMainDataHandle find SysOrgUserSynDTO lastSynTime:"+lastSynTime);
		String url = getAppFlagUrl() + "/api/platform/sysOrgUserSyn/search/v1";
		QueryReqBean sparams = new QueryReqBean();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("dto", new SysOrgSynDTO());
		hashMap.put("beginSynDateTime", lastSynTime);
		sparams.setSearchParams(hashMap);
		ResponseMsg<QueryRespBean<SysOrgUserSynDTO>> doPost = RestClient.doPost(appFlag, url,sparams, new TypeReference<ResponseMsg<QueryRespBean<SysOrgUserSynDTO>>>(){});
		List<SysOrgUserSynDTO> result = doPost.getResponseBody().getResult();
		logger.info("SynMainDataHandle find SysOrgUserSynDTO size:"+result.size());
		List<Long> versions = new ArrayList<Long>();
		if(result.size() > 0){
			for(SysOrgUserSynDTO sysOrgUserSynDTO: result){
				versions.add(sysOrgUserSynDTO.getVersion());
			}
			//新增用户机构岗位关系
			userSynService.insertSysOrgUserSyns(result);
			logger.info("SynMainDataHandle insert SysOrgUserSynDTO end");
		}
		return versions;
	}
	
	/**Description: 更新
	 * Create Date: 2015年2月13日下午1:46:45<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 */
	public void insert(List<Long> versions){
		try {
			for (Long version : versions){
				
				//用户
				List<SysUserChangeDTO> sysUserChangeDTOs = sysUserSynService.getChangeData(version.toString());
				//机构
				SysOrgChangeDTO sysOrgChangeDTO = sysOrgSynService.getChangeData(version.toString());
				//岗位
				SysPositionChangeDTO sysPositionChangeDTO = sysPositionSynService.getChangeData(version.toString());
				//归属机构以及任职
				List<SysOrgUserChangeDTO> sysOrgUserChangeDTOs = sysOrgUserSynService.getChangeData(version.toString());
				
				//理论上只有三种情况  
				//1.更改/新增用户
				boolean isInsert = true;
				if(sysUserChangeDTOs!=null && sysUserChangeDTOs.size() > 0  && sysOrgUserChangeDTOs !=null && sysOrgUserChangeDTOs.size() >0 ){
					for (SysUserChangeDTO sysUserChangeDTO : sysUserChangeDTOs) {
						if( ! "insert".equals( sysUserChangeDTO.getChangeType() )){
							isInsert = false;
						}else{
							for( SysOrgUserChangeDTO sysOrgUserChangeDTO : sysOrgUserChangeDTOs ){
								if( !"nochange".equals( sysOrgUserChangeDTO.getChangeType() ) ){
									isInsert = false;
								}
							}
						}
					}
				}
				//2.更改/新增机构
				else if (sysOrgChangeDTO != null){
					if( ! "insert".equals(sysOrgChangeDTO.getChangeType() )){
						isInsert = false;
					}
				}
				//3.更改/新增岗位
				else if( sysPositionChangeDTO !=null){
					if( !"insert".equals(sysPositionChangeDTO.getChangeType() )){
						isInsert = false;
					}
				}
				if(isInsert){
					//更新
					synClientService.updateSysDataSyn(version.toString());
				}
			}
		} catch (Exception e) {
			logger.error("orguser客户端自动更新同步数据出现错误！");
			e.printStackTrace();
		}
	}
	
	public String getAppFlagUrl() {
		logger.info("appFlagUrl is ::"+RestClientConfig.getServiceUrl(getAppFlag()));
		return RestClientConfig.getServiceUrl(getAppFlag());
	}
	public String getAppFlag() {
		appFlag = sysConfigAPI.getValue("orgClientId");
		logger.info("appFlag is ::"+appFlag);
		return appFlag;
	}
	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}
	
}
