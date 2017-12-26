package com.fintech.modules.platform.sysconfig.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysconfig.dao.SysConfigDao;
import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysConfigService
 * @description: 定义 系统配置表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysconfig.service.SysConfigService")
public class SysConfigService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysConfigDao dao;

	/**
	 * @author
	 * @description: 分页查询 系统配置表列表
	 * @date 2014-10-15 10:27:46
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysConfigDTO> searchSysConfigByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysConfigDTO> dataList = dao.searchSysConfigByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询系统配置表列表
	 * @date 2014-10-15 10:27:46
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysConfigDTO> searchSysConfig(Map<String, Object> searchParams)
			throws Exception {
		List<SysConfigDTO> dataList = dao.searchSysConfig(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询系统配置表对象
	 * @date 2014-10-15 10:27:46
	 * @param id
	 * @return
	 * @throws
	 */
	public SysConfigDTO querySysConfigByPrimaryKey(String id) throws Exception {

		SysConfigDTO dto = dao.findSysConfigByPrimaryKey(id);

		if (dto == null)
			dto = new SysConfigDTO();

		return dto;

	}

	/**
	 * @title: insertSysConfig
	 * @author
	 * @description: 新增 系统配置表对象
	 * @date 2014-10-15 10:27:46
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysConfig(SysConfigDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysConfig(paramMap);

		SysConfigDTO resultDto = (SysConfigDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysConfig
	 * @author
	 * @description: 修改 系统配置表对象
	 * @date 2014-10-15 10:27:46
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysConfig(SysConfigDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateSysConfig(paramMap);
	}

	/**
	 * @title: deleteSysConfigByPrimaryKey
	 * @author
	 * @description: 删除 系统配置表,按主键
	 * @date 2014-10-15 10:27:46
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysConfigByPrimaryKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysConfigByPrimaryKey(paramMap);
	}

	/**
	 * 唯一性验证
	 * 
	 * @param code
	 * @return
	 */
	public String queryConfigCodeIsOk(String code) {
		return dao.queryConfigCodeIsOk(code);
	}

	public String queryValueByCode(String code) {
		Map<String, Object> map = dao.queryValueByCode(code);
		if (map != null && map.size() > 0) {
			return map.get("VALUE").toString();
		}
		return null;
	}
	/**
	 * 实时缓取参数内容,无缓存
	 * @param code
	 * @return
	 */
	public String realGainValueByCode(String code) {
		Map<String, Object> map = dao.realGainValueByCode(code);
		if (map != null && map.size() > 0) {
			return map.get("VALUE").toString();
		}
		return null;
	}
	
	/**
	 * @title: updateSysConfigForAftloan
	 * @author 
	 * @description: (贷后操作日终的开/关机、修改业务区时间)修改 系统配置表对象（根据业务编码修改对应数据的状态）
	 * @date 2014-12-05 10:27:46
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysConfigForAftloan(Map<String,Object> paramMap) throws Exception {
		dao.updateSysConfigForAftloan(paramMap);
	}
	
	
	public List<SysConfigDTO> sysConfigByPagingNoCache(
			Map<String, Object> searchParams) throws Exception {
		List<SysConfigDTO> dataList = dao.searchSysConfigByPaging(searchParams);
		return dataList;
	}
	
}
