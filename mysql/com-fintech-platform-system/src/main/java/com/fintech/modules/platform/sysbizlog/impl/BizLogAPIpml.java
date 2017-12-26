package com.fintech.modules.platform.sysbizlog.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fintech.modules.platform.sysbizlog.dto.SysBizLogDTO;
import com.fintech.modules.platform.sysbizlog.service.SysBizLogService;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.syslog.BizLogAPI;

/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:
 *
 * Module ID  : com-pt-platform-system 
 *
 * 
 * @since 0.1
 * @version: 0.1
 * @author 
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年10月23日 | 0.1     | cyy| CREATE THE JAVA FILE: BizLogAPIpml.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
@Component
public class BizLogAPIpml implements BizLogAPI {
	
    private static final Logger logger =  LoggerFactory.getLogger(BizLogAPI.class);
	
	@Autowired
	SysBizLogService sysBizLogService;
	
	@Autowired
	SysUserService sysUserService;
	
	
	@Override
	public boolean log(String clientIp, String userId, String logModule,
			String logContent, LogType logType, OperateType logOperate) {
		boolean result  = false;
		//新增的日志对象
		SysBizLogDTO dto = new SysBizLogDTO();
        try {
        	if(userId!= null ){
        		SysUserDTO sysUserDTO = sysUserService.querySysUserByPrimaryKey(userId);
        		dto.setUserName(sysUserDTO.getUserName());
        		dto.setUserId(sysUserDTO.getId());
        	}
            dto.setOpUserId(Long.parseLong(userId));
            dto.setClientIp(clientIp);
            dto.setLogModule(logModule);
            dto.setLogContent(logContent);
            dto.setLogType(logType.toString());
            dto.setLogOperate(logOperate.toString());
            dto.setIsArchive("0");//默认未归档
            sysBizLogService.insertSysBizLog(dto);//插入日志
            result = true;
        }catch (Exception e) {
            result = false;
        	logger.error("执行方法insertSysResource异常：", e);
        }
		return result;
	}
}
