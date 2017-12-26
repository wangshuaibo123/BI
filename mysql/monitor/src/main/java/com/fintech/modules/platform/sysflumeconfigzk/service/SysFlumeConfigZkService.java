package com.fintech.modules.platform.sysflumeconfigzk.service;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysflumeconfigzk.dao.SysFlumeConfigZkDao;
import com.fintech.modules.platform.sysflumeconfigzk.dto.SysFlumeConfigZkDTO;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.zookeeper.ZookeeperClient;

/**
 * @classname: SysFlumeConfigZkService
 * @description: 定义  Flume配置表 实现类
 */
@Service("com.fintech.modules.platform.sysflumeconfigzk.service.SysFlumeConfigZkService")
public class SysFlumeConfigZkService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysFlumeConfigZkDao dao;
	
	@Autowired
	private ZookeeperClient zookeeperClient;

	/**
     * @description: 分页查询 Flume配置表列表
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysFlumeConfigZkDTO> searchSysFlumeConfigZkByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysFlumeConfigZkDTO> dataList =  dao.searchSysFlumeConfigZkByPaging(searchParams);
		return dataList;
	}
	/**
     * @description: 按条件查询Flume配置表列表
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysFlumeConfigZkDTO> searchSysFlumeConfigZk(Map<String,Object> searchParams) throws Exception {
	    List<SysFlumeConfigZkDTO> dataList = dao.searchSysFlumeConfigZk(searchParams);
        return dataList;
    }
	/**
     * @description: 查询Flume配置表对象
     * @param id
     * @return
     * @throws
     */ 
	public SysFlumeConfigZkDTO querySysFlumeConfigZkByPrimaryKey(String id) throws Exception {
		SysFlumeConfigZkDTO dto = dao.findSysFlumeConfigZkByPrimaryKey(id);
		
		if(dto == null) dto = new SysFlumeConfigZkDTO();
		
		return dto;
	}
	
	/**
	 * 替换占位符
	 * @return
	 */
	private String replaceConfPlaceholder(SysFlumeConfigZkDTO dto){
		String conf = dto.getConfig();
		
		//替换IP
		conf = conf.replaceAll("\\$\\{app_ip\\}", dto.getAppIp());
		conf = conf.replaceAll("\\$\\{app_ip_underline\\}", dto.getAppIp().replaceAll("\\.", "_"));
		//替换系统标示
		conf = conf.replaceAll("\\$\\{app_flag\\}", dto.getAppFlag());
		
		return conf;
	}

	/**
     * @title: insertSysFlumeConfigZk
     * @description: 新增 Flume配置表对象
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysFlumeConfigZk(SysFlumeConfigZkDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysFlumeConfigZk(paramMap);
		
		SysFlumeConfigZkDTO resultDto = (SysFlumeConfigZkDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	
	
	/**
	 * 新增并提交
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertAndCommitSysFlumeConfigZk(SysFlumeConfigZkDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = dao.insertSysFlumeConfigZk(paramMap);
		SysFlumeConfigZkDTO resultDto = (SysFlumeConfigZkDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		
		//提交
		String path = "/flume/app_" + dto.getAppIp().replaceAll("\\.", "_");
		String data = replaceConfPlaceholder(dto);
		zookeeperClient.create(path, false, data);
		
		return keyId;
	}
	
	
	/**
     * @title: updateSysFlumeConfigZk
     * @description: 修改 Flume配置表对象
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysFlumeConfigZk(SysFlumeConfigZkDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysFlumeConfigZk(paramMap);
	}
	
	/**
	 * 更新并提交
	 * @param dto
	 * @throws Exception
	 */
	public void updateAndCommitSysFlumeConfigZk(SysFlumeConfigZkDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateSysFlumeConfigZk(paramMap);
		
		//提交
		String path = "/flume/app_" + dto.getAppIp().replaceAll("\\.", "_");
		String data = replaceConfPlaceholder(dto);
		//节点已经存在
		if(zookeeperClient.exists(path)){
			zookeeperClient.setData(path, data);
		}
		else{
			zookeeperClient.create(path, false, data);
		}
	}
	
	/**
     * @title: deleteSysFlumeConfigZkByPrimaryKey
     * @description: 删除 Flume配置表,按主键
     * @param paramMap
     * @throws
     */ 
	public void deleteSysFlumeConfigZkByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysFlumeConfigZkByPrimaryKey(paramMap);
	}

	/**
	 * 物理删除
	 * @param baseDto
	 * @param ids
	 * @throws Exception
	 */
	public void deleteSysFlumeConfigZkByID(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		String[] idArr = ids.split(",");
		List<SysFlumeConfigZkDTO> flumeConfigList = new ArrayList<SysFlumeConfigZkDTO>();
		if(idArr!=null && idArr.length>0){
			for(int i=0;i<idArr.length;i++){
				flumeConfigList.add(querySysFlumeConfigZkByPrimaryKey(idArr[i]));
			}
		}
		
		//删除
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysFlumeConfigZkByID(paramMap);
		
		//zk删除
		String path = "";
		SysFlumeConfigZkDTO dto = null;
		for(int i=0;i<flumeConfigList.size();i++){
			dto = flumeConfigList.get(i);
			path = "/flume/app_" + dto.getAppIp().replaceAll("\\.", "_");
			if(zookeeperClient.exists(path)){
				zookeeperClient.delete(path);
			}
		}
	}
	
	
	/**
	 * 批量提交
	 * @param baseDto
	 * @param ids
	 * @throws Exception
	 */
	public void batchCommitToZk(SysFlumeConfigZkDTO sysFlumeConfigZkDTO, String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("提交失败！传入的参数主键为null");
		
		String[] idArr = ids.split(",");
		if(idArr!=null && idArr.length>0){
			SysFlumeConfigZkDTO dto = null;
			for(int i=0;i<idArr.length;i++){
				dto = querySysFlumeConfigZkByPrimaryKey(idArr[i]);
				dto.setStatus("commited");
	        	dto.setUpdateBy(sysFlumeConfigZkDTO.getUpdateBy());
	        	dto.setUpdateByName(sysFlumeConfigZkDTO.getUpdateByName());
	        	dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				//更新状态
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("dto", dto);
				dao.updateSysFlumeConfigZk(paramMap);
				
				//提交
				String path = "/flume/app_" + dto.getAppIp().replaceAll("\\.", "_");
				String data = replaceConfPlaceholder(dto);
				//节点已经存在
				if(zookeeperClient.exists(path)){
					zookeeperClient.setData(path, data);
				}
				else{
					zookeeperClient.create(path, false, data);
				}
				
				Thread.sleep(500);
			}
		}
	}
}

