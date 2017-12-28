package com.jy.modules.platform.dataprv.sysprvusershare.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.jy.modules.platform.dataprv.sysprvusershare.dao.SysPrvUserShareDao;
import com.jy.modules.platform.dataprv.sysprvusershare.dto.SysPrvUserShareDTO;
import com.jy.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.jy.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvUserShareService
 * @description: 定义 数据共享表 实现类
 * @author: wangxz
 */
@Service("com.jy.modules.platform.dataprv.sysprvusershare.service.SysPrvUserShareService")
public class SysPrvUserShareService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvUserShareDao dao;
	@Autowired
	private SysPrvAuthResultService rService;
	
	@Autowired
	private SysPrvAuthPoolService pService;

	/**
	 * @author wangxz
	 * @description: 分页查询 数据共享表列表
	 * @date 2014-10-18 16:07:49
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvUserShareDTO> searchSysPrvUserShareByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvUserShareDTO> dataList = dao
				.searchSysPrvUserShareByPaging(searchParams);
		return dataList;
	}
	
	/**
	 * @author wangxz
	 * @description: 按条件查询数据共享表列表
	 * @date 2014-10-18 16:07:49
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvUserShareDTO> searchSysPrvUserShare(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvUserShareDTO> dataList = dao
				.searchSysPrvUserShare(searchParams);
		return dataList;
	}
	
	/**
	 * 
	 * @param searchParams
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int searchSysPrvUserShareTotalCount(Map<String, Object> searchParams)throws Exception{
	    return dao.searchSysPrvUserShareCount(searchParams);
	}
	
	    
	/**
	 * @author wangxz
	 * @description: 查询数据共享表对象
	 * @date 2014-10-18 16:07:49
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvUserShareDTO querySysPrvUserShareByPrimaryKey(String id)
			throws Exception {

		SysPrvUserShareDTO dto = dao.findSysPrvUserShareByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvUserShareDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvUserShare
	 * @author wangxz
	 * @description: 新增 数据共享表对象    取消共享表  共享的权限直接插入数据权限池  pengliuxiang
	 * @date 2014-10-18 16:07:49
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void insertSysPrvUserShare(String fromUserIds,String toUserId) throws Exception {
		
		SysPrvUserShareDTO dto = new SysPrvUserShareDTO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dto.setToUserId(Long.parseLong(toUserId));
		paramMap.put("dto", dto);
		if(fromUserIds!=null&&fromUserIds.length()>0){
			if(fromUserIds.indexOf(",")>0){
				String[] froms = fromUserIds.split(",");
				for(String str:froms){
					dto.setFromUserId(Long.parseLong(str));
					dao.insertSysPrvUserShare(paramMap);
					insertAuthPool(str,toUserId);
					
				}
			}else{
				dto.setFromUserId(Long.parseLong(fromUserIds));
				dao.insertSysPrvUserShare(paramMap);
				insertAuthPool(fromUserIds,toUserId);
			}
		}
	}
	/**
	 * 
	 * 添加到数据权限池
	 * @param roleId
	 */
	private void insertAuthPool(String fromUserId,String toUserId) throws Exception{
		SysPrvAuthPoolDTO dto = new SysPrvAuthPoolDTO();
		dto.setUserid(Long.parseLong(toUserId));
		dto.setOwnerid(Long.parseLong(fromUserId));
		dto.setDatesource("用户共享");
		dto.setDatesourceid(fromUserId);
		dto.setStatus("1");   //状态1 代表共享可用  其他状态共享不可用
		pService.insertSysPrvAuthPool(dto);
	}
	
	/**
	 * @title: updateSysPrvUserShare
	 * @author wangxz
	 * @description: 修改 数据共享表对象
	 * @date 2014-10-18 16:07:49
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvUserShare(SysPrvUserShareDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		SysPrvUserShareDTO oldDto = dao.findSysPrvUserShareByPrimaryKey(dto
				.getId().toString());
		rService.updateOrDeleteUserAuth(oldDto.getToUserId(),
				oldDto.getFromUserId(), "S");
		dao.updateSysPrvUserShare(paramMap);
	}

	/**
	 * @title: deleteSysPrvUserShareByID
	 * @author wangxz
	 * @description: 删除 数据共享表,按主键
	 * @date 2014-10-18 16:07:49
	 * @param paramMap
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void deleteSysPrvUserShareByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
//		List<SysPrvUserShareDTO> oldDtos = dao.searchSysPrvUserShareByIds(paramMap);
//		for(SysPrvUserShareDTO dto:oldDtos){
//		rService.updateOrDeleteUserAuth(dto.getToUserId(),
//				dto.getFromUserId(), "S");
//		}
		List<SysPrvUserShareDTO> dataList=dao.searchysPrvUserShareByID(paramMap);
		for(SysPrvUserShareDTO s:dataList)
		{
			SysPrvAuthPoolDTO pooldto= new SysPrvAuthPoolDTO();
			pooldto.setDatesource("用户共享");
			pooldto.setDatesourceid(s.getFromUserId().toString());
			pooldto.setUserid(s.getToUserId());
			pService.deleteSysPrvAuthPoolByMap(pooldto);
		}
		dao.deleteSysPrvUserShareByID(paramMap);
	}

	public String queryInfoNumByUser(Map<String,Object> paramMap){
		return dao.queryInfoNumByUser(paramMap);
	}
}
