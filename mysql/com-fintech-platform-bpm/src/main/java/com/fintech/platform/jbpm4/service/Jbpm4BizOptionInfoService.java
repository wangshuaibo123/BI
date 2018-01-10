package com.fintech.platform.jbpm4.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.OrgInfo;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysRoleAPI;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
import com.fintech.platform.jbpm4.repository.Jbpm4BizOptionInfoDao;
import com.fintech.platform.jbpm4.tool.ThreadPool;

/**
 * @classname: Jbpm4BizOptionInfoService
 * @description: 定义  业务流程节点意见表 实现类
 * @author
 */
@Service("com.fintech.platform.jbpm4.service.Jbpm4BizOptionInfoService")
public class Jbpm4BizOptionInfoService implements Serializable {
	private static ThreadPoolExecutor executor = ThreadPool.getThreadPool(15,30, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000));
    private static final long serialVersionUID = 1L;
    
	@Autowired
	private Jbpm4BizOptionInfoDao dao;

	/**
     * @author
     * @description: 分页查询 业务流程节点意见表列表
     * @date 2014-10-29 14:37:17
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<Jbpm4BizOptionInfoDTO> dataList =  dao.searchJbpm4BizOptionInfoByPaging(searchParams);
		List<Jbpm4BizOptionInfoDTO> resultList= new ArrayList<Jbpm4BizOptionInfoDTO>();
		List<BaseDTO> list = null;
	    if(dataList != null && dataList.size() >0){
	    	list = new ArrayList<BaseDTO>();
	    	for(Jbpm4BizOptionInfoDTO option:dataList){
	    		BaseDTO base = (BaseDTO)option;
	    		base.setBaseExt2(option.getCreateBy());////人员
	    		base.setBaseExt4(option.getOrgId());//机构
	    		list.add(base);
	    	}
	    	list = this.makeupUserOrgBaseDTO(list);
	    	for(BaseDTO base:list){
	    		Jbpm4BizOptionInfoDTO option =(Jbpm4BizOptionInfoDTO)base;
	    		resultList.add(option);
	    	}
	    }
		return resultList;
	}
	
	public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfoByPage(Map<String,Object> searchParams) throws Exception {
		
		PageParameter page = (PageParameter)searchParams.get("page");
		String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());
        searchParams.put("beginrow", beginrow);
        searchParams.put("endrow", endrow);
        
		List<Jbpm4BizOptionInfoDTO> dataList =  dao.searchJbpm4BizOptionInfoByPage(searchParams);
		List<Jbpm4BizOptionInfoDTO> resultList= new ArrayList<Jbpm4BizOptionInfoDTO>();
		List<BaseDTO> list = null;
	    if(dataList != null && dataList.size() >0){
	    	list = new ArrayList<BaseDTO>();
	    	for(Jbpm4BizOptionInfoDTO option:dataList){
	    		BaseDTO base = (BaseDTO)option;
	    		base.setBaseExt2(option.getCreateBy());////人员
	    		base.setBaseExt4(option.getOrgId());//机构
	    		list.add(base);
	    	}
	    	list = this.makeupUserOrgBaseDTO(list);
	    	for(BaseDTO base:list){
	    		Jbpm4BizOptionInfoDTO option =(Jbpm4BizOptionInfoDTO)base;
	    		resultList.add(option);
	    	}
	    }
		return resultList;
	}
	/**
     * @author
     * @description: 按条件查询业务流程节点意见表列表
     * @date 2014-10-29 14:37:17
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfo(Map<String,Object> searchParams) throws Exception {
		//List<Jbpm4BizOptionInfoDTO> resultList= new ArrayList<Jbpm4BizOptionInfoDTO>();
		List<Jbpm4BizOptionInfoDTO> dataList = dao.searchJbpm4BizOptionInfo(searchParams);
	    
	   
	    /*List<BaseDTO> list = null;
	    if(dataList != null && dataList.size() >0){
	    	list = new ArrayList<BaseDTO>();
	    	for(Jbpm4BizOptionInfoDTO option:dataList){
	    		BaseDTO base = (BaseDTO)option;
	    		base.setBaseExt2(option.getCreateBy());
	    		list.add(base);
	    	}
	    	list = this.makeupUserOrgBaseDTO(list);
	    	for(BaseDTO base:list){
	    		Jbpm4BizOptionInfoDTO option =(Jbpm4BizOptionInfoDTO)base;
	    		resultList.add(option);
	    	}
	    }*/
	    
        return dataList;
    }
	/**
     * @author
     * @description: 查询业务流程节点意见表对象
     * @date 2014-10-29 14:37:17
     * @param id
     * @return
     * @throws
     */ 
	public Jbpm4BizOptionInfoDTO queryJbpm4BizOptionInfoByPrimaryKey(String id) throws Exception {
		
		Jbpm4BizOptionInfoDTO dto = dao.findJbpm4BizOptionInfoByPrimaryKey(id);
		
		if(dto == null) dto = new Jbpm4BizOptionInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertJbpm4BizOptionInfo
     * @author
     * @description: 新增 业务流程节点意见表对象
     * @date 2014-10-29 14:37:17
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertJbpm4BizOptionInfo(Jbpm4BizOptionInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertJbpm4BizOptionInfo(paramMap);
		
		Jbpm4BizOptionInfoDTO resultDto = (Jbpm4BizOptionInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateJbpm4BizOptionInfo
     * @author
     * @description: 修改 业务流程节点意见表对象
     * @date 2014-10-29 14:37:17
     * @param paramMap
     * @return
     * @throws
     */
	public void updateJbpm4BizOptionInfo(Jbpm4BizOptionInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateJbpm4BizOptionInfo(paramMap);
	}
	/**
     * @title: deleteJbpm4BizOptionInfoByPrimaryKey
     * @author
     * @description: 删除 业务流程节点意见表,按主键
     * @date 2014-10-29 14:37:17
     * @param paramMap
     * @throws
     */ 
	public void deleteJbpm4BizOptionInfoByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteJbpm4BizOptionInfoByPrimaryKey(paramMap);
	}
	public void deleteOldSomeBizOptionInf(Map<String, String> param) {
		dao.deleteOldSomeBizOptionInf(param);
	}
	/**
	 * 将 dataList 中 DTO 用户，机构的id 转译成 对应的名称
	 * BaseDTO中属性 baseExt2 ，baseExt3 存放 userID ，转译之后将在baseExt2 ，baseExt3存放 名称
	 * BaseDTO中属性 baseExt4 ，baseExt5 存放 orgID ，转译之后将在baseExt4 ，baseExt5存放 名称
	 * BaseDTO中属性 baseExt6 ，baseExt7 存放 roleCode ，转译之后将在baseExt6 ，baseExt7存放 名称
	 * 将 dataList 中 BaseDTO 用户，机构的id,角色编码, 转译成 对应的名称
	 * @param dataList
	 * @return
	 */
	private List<BaseDTO> makeupUserOrgBaseDTO(List<BaseDTO> dataList) {
		if(dataList != null && dataList.size() >0){
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			
			final OrgAPI orgAPI = (OrgAPI) context.getBean("orgAPI");
			final SysRoleAPI sysRoleAPI =(SysRoleAPI) context.getBean("sysRoleAPI");
			try {
				//线程池初始化
				//ThreadPoolExecutor executor = ThreadPool.getThreadPool(10,20, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000));
				//得到线程池中线程队列
				//LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();
				
				int count = dataList.size() ;
				final CountDownLatch countDownLatch = new CountDownLatch(count);
				
				for(int i = 0;i < count;i ++){
					final BaseDTO baseDTO = dataList.get(i);
					
					final BaseDTO paramDTO = new BaseDTO();
					paramDTO.setBaseExt2(baseDTO.getBaseExt2());//存放 userId 的
					paramDTO.setBaseExt3(baseDTO.getBaseExt3());//存放 userId 的
					
					paramDTO.setBaseExt4(baseDTO.getBaseExt4());//存放 orgId 的
					paramDTO.setBaseExt5(baseDTO.getBaseExt5());//存放 orgId 的
					
					paramDTO.setBaseExt6(baseDTO.getBaseExt6());//存放 orleCode 的
					paramDTO.setBaseExt7(baseDTO.getBaseExt7());//存放 orleCode 的
					
					executor.execute(new Runnable(){
						public void run() {
							try {
								String userId2 = paramDTO.getBaseExt2();
								String userId3 = paramDTO.getBaseExt3();
								
								String orgId4 = paramDTO.getBaseExt4();
								String orgId5 = paramDTO.getBaseExt5();
								
								String roleCode6 = paramDTO.getBaseExt6();
								String roleCode7 = paramDTO.getBaseExt7();
								
								if(StringUtils.isNotEmpty(userId2)){//通过 用户ID接口 获取 用户的其他信息
									UserInfo user = orgAPI.getUserInfoDetail(userId2);
									if(user != null ) baseDTO.setBaseExt2(user.getUserName());
								}
								if(StringUtils.isNotEmpty(userId3)){
									UserInfo user = orgAPI.getUserInfoDetail(userId3);
									if(user != null ) baseDTO.setBaseExt3(user.getUserName());
								}
								if(StringUtils.isNotEmpty(orgId4)){
									OrgInfo org = orgAPI.getOrgInfo(orgId4);
									if(org != null ) baseDTO.setBaseExt4(org.getOrgName());
								}
								if(StringUtils.isNotEmpty(orgId5)){
									OrgInfo org = orgAPI.getOrgInfo(orgId5);
									if(org != null ) baseDTO.setBaseExt5(org.getOrgName());
								}
								if(StringUtils.isNotEmpty(roleCode6)){
									Map<String, Object> param = new HashMap<String, Object>();
									param.put("roleCode", roleCode6);
									//param.put("roleGroupCode", roleCode6);
									List<Map<String, Object>> roleList = sysRoleAPI.queryRoleList(0, 1, param);//sysRoleAPI.queryRoleGroupList(0, 1, param);
									if(roleList != null && roleList.size() > 0){
										Map<String,Object> roleInfo = roleList.get(0);
										baseDTO.setBaseExt6((String)roleInfo.get("roleName"));//roleGroupName
									}
								}
								if(StringUtils.isNotEmpty(roleCode7)){
									Map<String, Object> param = new HashMap<String, Object>();
									param.put("roleCode", roleCode7);
									List<Map<String, Object>> roleList = sysRoleAPI.queryRoleList(0, 1, param);
									if(roleList != null && roleList.size() > 0){
										Map<String,Object> roleInfo = roleList.get(0);
										baseDTO.setBaseExt7((String)roleInfo.get("roleName"));//roleGroupName
									}
								}
							} catch (Exception e) {
								JYLoggerUtil.error(this.getClass(), "=============makeupUserOrgBaseDTO===========",e);
							}
							
							countDownLatch.countDown();
						}
					});
				}
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return dataList;
	}

}

