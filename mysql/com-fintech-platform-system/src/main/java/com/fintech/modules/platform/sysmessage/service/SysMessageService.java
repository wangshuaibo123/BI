package com.fintech.modules.platform.sysmessage.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysmessage.dao.SysMessageDao;
import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.UserSysMessageDTO;
import com.fintech.modules.platform.sysmessage.impl.MsgApiAddImpl;
import com.fintech.modules.platform.sysmessage.impl.MsgApiDelImpl;
import com.fintech.modules.platform.sysmessage.impl.MsgApiUpdateImpl;
import com.fintech.modules.platform.sysmessage.util.MsgUtil;
import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.api.msg.MsgAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysMessageService
 * @description: 定义  SYS_MESSAGE 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysmessage.service.SysMessageService")
public class SysMessageService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysMessageDao dao;
	

	/**
     * @author
     * @description: 分页查询 SYS_MESSAGE列表
     * @date 2014-11-14 11:07:13
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysMessageDTO> searchSysMessageByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysMessageDTO> dataList =  dao.searchSysMessageByPaging(searchParams);
		return dataList;
	}
	
	/**
     * @author
     * @description: 分页查询 SYS_MESSAGE列表 我的消息
     * @date 2014-11-14 11:07:13
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<UserSysMessageDTO> searchMySysMessageByPaging(Map<String,Object> searchParams) throws Exception {
		List<UserSysMessageDTO> dataList =  dao.searchMySysMessageByPaging(searchParams);
		return dataList;
	}
	
	/**
     * @author
     * @description: 按条件查询SYS_MESSAGE列表
     * @date 2014-11-14 11:07:13
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysMessageDTO> searchSysMessage(Map<String,Object> searchParams) throws Exception {
	    List<SysMessageDTO> dataList = dao.searchSysMessage(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询两天内
     * @date 2015-12-11 11:07:13
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysMessageDTO> searchUnRead2DSysMessage(Map<String,Object> searchParams) throws Exception {
	    List<SysMessageDTO> dataList = dao.searchUnRead2DSysMessage(searchParams);
        return dataList;
    }
	/**
	 * @author
	 * @description: 查询SYS_MESSAGE对象
	 * @date 2014-11-14 11:07:13
	 * @param id
	 * @return
	 * @throws
	 */ 
	public SysMessageDTO querySysMessageByPrimaryKey(String id) throws Exception {
		
		SysMessageDTO dto = dao.findSysMessageAndRelationByID(id);
		
		if(dto == null) dto = new SysMessageDTO();
		
		return dto;
		
	}
	/**
     * @author
     * @description: 查询SYS_MESSAGE对象 并且查询出信息用户关联表
     * @date 2014-11-14 11:07:13
     * @param id
     * @return
     * @throws
     */ 
	public SysMessageDTO querySysMessageAndRelationByApi(String id,String currentUserId) throws Exception {
		
		SysMessageDTO dto = dao.findSysMessageAndRelationByID(id);
		MsgAPI api = new MsgApiUpdateImpl();
		api.updateMsgHasRead(id, dto.getSysFlag(), currentUserId);
		if(dto == null) dto = new SysMessageDTO();
		return dto;
		
	}

	/**
	 * @title: insertSysMessage
	 * @author
	 * @description: 新增 SYS_MESSAGE对象
	 * @date 2014-11-14 11:07:13
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysMessage(SysMessageDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = dao.insertSysMessage(paramMap);
		SysMessageDTO resultDto = (SysMessageDTO) paramMap.get("dto");
		Long keyId = resultDto.getMsgId();
		return keyId;
	}
	/**
     * @title: insertSysMessageByApi
     * @author
     * @description: 新增 SYS_MESSAGE对象 通过调用消息API
     * @date 2014-11-14 11:07:13
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public boolean insertSysMessageByApi(SysMessageDTO dto) throws Exception {
		//调用消息接口,发布消息
		MsgAPI api = new MsgApiAddImpl();
		Msg msg = MsgUtil.beanChange(dto);
		String receiverId = dto.getUserId();
		msg.setUserIds(receiverId);
		return api.addMsg(msg);
	}
	/**
     * @title: updateSysMessage
     * @author
     * @description: 修改 SYS_MESSAGE对象
     * @date 2014-11-14 11:07:13
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysMessage(SysMessageDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysMessage(paramMap);
	}
	/**
     * @title: deleteSysMessageByApi
     * @author
     * @description: 删除 SYS_MESSAGE,按主键
     * @date 2014-11-14 11:07:13
     * @param paramMap
     * @throws
     */ 
	public void deleteSysMessageByApi(BaseDTO baseDto,String ids,String currentUserId) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		//调用消息接口,更新消息为已删除,并且同步缓存
		MsgAPI api = new MsgApiDelImpl();
		String[] idArray = ids.split(",");
		//查询出消息所属的系统
		for (String id : idArray) {
			SysMessageDTO dto = querySysMessageByPrimaryKey(id);
			boolean deleteMsg = api.deleteMsg(id, dto.getSysFlag(), currentUserId);
		}
	}
	
	/**
     * @title: deleteSysMessageByApi
     * @author
     * @description: 删除 SYS_MESSAGE, 收件人删除消息
     * @date 2014-11-14 11:07:13
     * @param paramMap
     * @throws
     */ 
	public void deleteMySysMessageByApi(BaseDTO baseDto,String ids,String currentUserId) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		//调用消息接口,更新消息为已删除,并且同步缓存
		MsgAPI api = new MsgApiDelImpl();
		String[] idArray = ids.split(",");
		//查询出消息所属的系统
		for (String id : idArray) {
			SysMessageDTO dto = querySysMessageByPrimaryKey(id);
			api.readerDeleteMsg(id, dto.getSysFlag(), currentUserId);
		}
	}
	
	/**
     * @author
     * @description: 按条件查询SYS_MESSAGE列表
     * @date 2014-11-14 11:07:13
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysMessageDTO> complexSearch(Map<String,Object> searchParams) throws Exception {
	    List<SysMessageDTO> dataList = dao.complexSearch(searchParams);
        return dataList;
    }
	/**
     * @title: deleteSysMessageByPrimaryKey
     * @author
     * @description: 删除 SYS_MESSAGE,按主键
     * @date 2014-11-14 11:07:13
     * @param paramMap
     * @throws
     */ 
	public void deleteSysMessageByID(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysMessageByID(paramMap);
	}
	
	/**
     * @title: countSysMessageByUserId
     * @author
     * @description: 按用户ID统计消息数量
     * @date 2014-11-14 11:07:13
     * @param paramMap
     * @throws
     */ 
	public int countSysMessageByUserId(BaseDTO baseDto,String id) throws Exception {
		if(StringUtils.isEmpty(id)) throw new Exception("查询失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("userId", id);
		int count = dao.countSysMessageByUserID(paramMap);
		return count;
	}

}

