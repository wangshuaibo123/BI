package com.fintech.modules.platform.bizauth.vmrulemapping.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.modules.platform.bizauth.vmrulemapping.dto.VmruleMappingDTO;
import com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.fintech.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
/**
 * @description:新增门店 定时刷新数据权限定时任务入口
 * 建议定时任务每天执行1或2次。
 * @author
 * @date: 2016年7月21日 下午4:01:12
 */
@Component("com.fintech.modules.platform.bizauth.vmrulemapping.job.AutoFlushVmruleMappingTask")
public class AutoFlushVmruleMappingTask implements Serializable, Job{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AutoFlushVmruleMappingTask.class);
	
    //控制  不允许 多线程 执行 public void execute(JobExecutionContext context) throws JobExecutionException  方法
    private static boolean isNext = true;
   
    private VmtreeInfoService vmtreeInfoService;
    private VmruleMappingService vmruleMappingService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("------------AutoFlushVmruleMappingTask-------isNext:"+isNext);
			return ;
		}
		isNext = false;
		SchedulerContext cont;
		try {
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			vmtreeInfoService = (VmtreeInfoService)appCtx.getBean(VmtreeInfoService.class);
			vmruleMappingService = (VmruleMappingService)appCtx.getBean(VmruleMappingService.class);
			
			//查询虚拟树一段时间内新增的数据
			Map<String,Object> searchParams = new HashMap<String,Object>();
			searchParams.put("createTime_interval", 60 * 24);//查询一天以内修改的数据
			VmtreeInfoDTO vmtreeInfoParam = new VmtreeInfoDTO();
			searchParams.put("dto", vmtreeInfoParam);
			List vmtreeInfoList = vmtreeInfoService.searchVmtreeInfo(searchParams);
			
			//没有数据
			if(vmtreeInfoList==null || vmtreeInfoList.size()<=0){
				return;
			}
			
			VmtreeInfoDTO vmtreeInfoDTO = null;
			VmtreeInfoDTO parentVmtreeInfoDTO = null;
			Long parentOrgId = null;
			//遍历所有最近新增的机构
			for(int i=0;i<vmtreeInfoList.size();i++){
				vmtreeInfoDTO = (VmtreeInfoDTO)vmtreeInfoList.get(i);
				
				parentOrgId = vmtreeInfoDTO.getParentId();
				while(parentOrgId!=null && parentOrgId.longValue()>0L){
					//如果父机构不存在，就不继续刷新数据权限了
					parentVmtreeInfoDTO = vmtreeInfoService.queryVmtreeInfoByPrimaryKey(parentOrgId.toString(), vmtreeInfoDTO.getOrgType());
					if(parentVmtreeInfoDTO.getOrgName()==null || "".equals(parentVmtreeInfoDTO.getOrgName())){
						break;
					}
					
					//查找是否有“人对parentOrgId”的映射
					Map<String,Object> searchVmruleMappingParams = new HashMap<String,Object>();
					VmruleMappingDTO vmruleMappingParam = new VmruleMappingDTO();
					vmruleMappingParam.setMapType("2");//人对机构
					vmruleMappingParam.setMapValue(parentOrgId.toString());
					searchVmruleMappingParams.put("dto", vmruleMappingParam);
					searchVmruleMappingParams.put("vmTableName",parentVmtreeInfoDTO.getOrgType() + "_" + "VMRULE_MAPPING");
					List vmruleMappingList = vmruleMappingService.searchVmruleMapping(searchVmruleMappingParams);
					
					//如果有“人对parentOrgId”的映射，刷新此映射
					if(vmruleMappingList!=null && vmruleMappingList.size()>0){
						VmruleMappingDTO vmruleMappingDTO = null;
						for(int j=0;j<vmruleMappingList.size();j++){
							vmruleMappingDTO = (VmruleMappingDTO)vmruleMappingList.get(j);
							
							//刷新数据权限
							vmruleMappingService.fulshVmruleMapping(vmruleMappingDTO.getId()+"", vmruleMappingDTO.getOrgType());
						}
					}
					
					//继续刷新父机构的数据权限
					parentOrgId = parentVmtreeInfoDTO.getParentId();
				}
			}
		} 
		catch(Exception ex){
			logger.error("---------------AutoFlushVmruleMappingTask-------error----------"+ex.getMessage());
		}
		finally{
			logger.info("----------------AutoFlushVmruleMappingTask-------end-------------");
			isNext= true;
		}
	}
	
	
	
	
}
