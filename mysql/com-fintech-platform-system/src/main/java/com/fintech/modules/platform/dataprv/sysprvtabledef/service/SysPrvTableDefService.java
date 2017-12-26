package com.fintech.modules.platform.dataprv.sysprvtabledef.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.dataprv.sysprvtabledef.dao.SysPrvTableDefDao;
import com.fintech.modules.platform.dataprv.sysprvtabledef.dto.SysPrvTableDefDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvTableDefService
 * @description: 定义 数据权限表定义 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvtabledef.service.SysPrvTableDefService")
public class SysPrvTableDefService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvTableDefDao dao;

	/**
	 * @author
	 * @description: 分页查询 数据权限表定义列表
	 * @date 2014-10-18 16:07:40
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvTableDefDTO> searchSysPrvTableDefByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvTableDefDTO> dataList = dao
				.searchSysPrvTableDefByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询数据权限表定义列表
	 * @date 2014-10-18 16:07:40
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvTableDefDTO> searchSysPrvTableDef(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvTableDefDTO> dataList = dao
				.searchSysPrvTableDef(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询数据权限表定义对象
	 * @date 2014-10-18 16:07:40
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvTableDefDTO querySysPrvTableDefByPrimaryKey(String id)
			throws Exception {

		SysPrvTableDefDTO dto = dao.findSysPrvTableDefByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvTableDefDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvTableDef
	 * @author
	 * @description: 新增 数据权限表定义对象
	 * @date 2014-10-18 16:07:40
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysPrvTableDef(SysPrvTableDefDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysPrvTableDef(paramMap);

		SysPrvTableDefDTO resultDto = (SysPrvTableDefDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysPrvTableDef
	 * @author
	 * @description: 修改 数据权限表定义对象
	 * @date 2014-10-18 16:07:40
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvTableDef(SysPrvTableDefDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateSysPrvTableDef(paramMap);
	}

	/**
	 * @title: deleteSysPrvTableDefByID
	 * @author
	 * @description: 删除 数据权限表定义,按主键
	 * @date 2014-10-18 16:07:40
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysPrvTableDefByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysPrvTableDefByID(paramMap);
	}

	/**
	 * 获取所有的业务表
	 * @return
	 */
	public List<String> queryTableList() {
		return dao.queryTableList();
	}
	
	
	/**
	 * 获取需要数据权限的业务表
	 * @return
	 */
	public List<String> queryDataPrvTableList() {
		return dao.queryDataPrvTableList();
	}

}
