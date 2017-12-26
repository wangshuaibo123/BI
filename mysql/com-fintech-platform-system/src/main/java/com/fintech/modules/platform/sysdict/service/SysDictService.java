package com.fintech.modules.platform.sysdict.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysdict.dao.SysDictDao;
import com.fintech.modules.platform.sysdict.dto.SysDictDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysDictService
 * @description: 定义 数据字典 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysdict.service.SysDictService")
public class SysDictService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysDictDao dao;

	@Autowired
	@Qualifier("com.fintech.modules.platform.sysdict.service.SysDictDetailService")
	private SysDictDetailService detailService;

	/**
	 * @author
	 * @description: 分页查询 数据字典列表
	 * @date 2014-10-15 10:28:27
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysDictDTO> searchSysDictByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysDictDTO> dataList = dao.searchSysDictByPaging(searchParams);
		return dataList;
	}
	
	/**
	 * @author
	 * @description: 分页查询 数据字典列表(不记录缓存,不带关键字query，search)
	 * @date 2014-10-15 10:28:27
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysDictDTO> sysDictByPagingNoCache(
			Map<String, Object> searchParams) throws Exception {
		List<SysDictDTO> dataList = dao.searchSysDictByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询数据字典列表
	 * @date 2014-10-15 10:28:27
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysDictDTO> searchSysDict(Map<String, Object> searchParams)
			throws Exception {
		List<SysDictDTO> dataList = dao.searchSysDict(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询数据字典对象
	 * @date 2014-10-15 10:28:27
	 * @param id
	 * @return
	 * @throws
	 */
	public SysDictDTO querySysDictByPrimaryKey(String id) throws Exception {

		SysDictDTO dto = dao.findSysDictByPrimaryKey(id);

		if (dto == null)
			dto = new SysDictDTO();

		return dto;

	}

	/**
	 * @title: insertSysDict
	 * @author
	 * @description: 新增 数据字典对象
	 * @date 2014-10-15 10:28:27
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysDict(SysDictDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysDict(paramMap);

		SysDictDTO resultDto = (SysDictDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysDict
	 * @author
	 * @description: 修改 数据字典对象
	 * @date 2014-10-15 10:28:27
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysDict(SysDictDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateSysDict(paramMap);
	}

	/**
	 * @title: deleteSysDictByPrimaryKey
	 * @author
	 * @description: 删除 数据字典,按主键
	 * @date 2014-10-15 10:28:27
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysDictByPrimaryKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysDictByPrimaryKey(paramMap);
		detailService.deleteSysDictDetailByDictKey(baseDto, ids);
	}

	/**
	 * @author
	 * @description: 验证唯一性
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public String queryDictCodeIsOk(String code) throws Exception {
		return dao.queryDictCodeIsOk(code);
	}

	public Map<String,String> queryDictInfoByCode(String code){
		return dao.queryDictInfoByCode(code);
	}
}
