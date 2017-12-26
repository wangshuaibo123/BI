package com.fintech.modules.platform.sysdict.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysdict.dao.SysDictDetailDao;
import com.fintech.modules.platform.sysdict.dto.SysDictDetailDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysDictDetailService
 * @description: 定义 数据字典明细表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysdict.service.SysDictDetailService")
public class SysDictDetailService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysDictDetailDao dao;

	/**
	 * @author
	 * @description: 分页查询 数据字典明细表列表
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysDictDetailDTO> searchSysDictDetailByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysDictDetailDTO> dataList = dao
				.searchSysDictDetailByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 分页查询 数据字典明细表列表
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public String findSysDictMaxOrderBy(String dictId)
			throws Exception {
		return dao.findSysDictMaxOrderBy(dictId);
	}
	/**
	 * @author
	 * @description:  验证唯一性
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public String queryDetailCodeIsOk(Map<String,Object> searchParams)
			throws Exception {
		return dao.queryDetailCodeIsOk(searchParams);
	}

	/**
	 * @author
	 * @description: 按条件查询数据字典明细表列表
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysDictDetailDTO> searchSysDictDetail(
			Map<String, Object> searchParams) throws Exception {
		List<SysDictDetailDTO> dataList = dao.searchSysDictDetail(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询数据字典明细表对象
	 * @date 2014-10-15 10:28:43
	 * @param id
	 * @return
	 * @throws
	 */
	public SysDictDetailDTO querySysDictDetailByPrimaryKey(String id)
			throws Exception {

		SysDictDetailDTO dto = dao.findSysDictDetailByPrimaryKey(id);

		if (dto == null)
			dto = new SysDictDetailDTO();

		return dto;

	}

	/**
	 * @title: insertSysDictDetail
	 * @author
	 * @description: 新增 数据字典明细表对象
	 * @date 2014-10-15 10:28:43
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysDictDetail(SysDictDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysDictDetail(paramMap);

		SysDictDetailDTO resultDto = (SysDictDetailDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysDictDetail
	 * @author
	 * @description: 修改 数据字典明细表对象
	 * @date 2014-10-15 10:28:43
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysDictDetail(SysDictDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateSysDictDetail(paramMap);
	}

	/**
	 * @title: deleteSysDictDetailByPrimaryKey
	 * @author
	 * @description: 删除 数据字典明细表,按主键
	 * @date 2014-10-15 10:28:43
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysDictDetailByPrimaryKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysDictDetailByPrimaryKey(paramMap);
	}

	/**
	 * @title: deleteSysDictDetailByDictId
	 * @author
	 * @description: 删除 数据字典明细表,按数据字典主表主键
	 * @date 2014-10-15 10:28:43
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysDictDetailByDictKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysDictDetailByDictKey(paramMap);
	}

	public List<Map> queryDetailByDictCode(String code) {
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("code", code);
		return dao.queryDetailByDictCode(temp);
	}
	
	/**
	 * 通过字典编码和字典明细获得明细数据
	 * @param searchParams
	 * @return
	 */
	public SysDictDetailDTO queryDetailByDictCodeAndDeatailValue(String dict_code,String detail_value){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dict_code", dict_code);
		paramMap.put("detail_value", detail_value);
		return dao.queryDetailByDictCodeAndDeatailValue(paramMap);
	}
}
