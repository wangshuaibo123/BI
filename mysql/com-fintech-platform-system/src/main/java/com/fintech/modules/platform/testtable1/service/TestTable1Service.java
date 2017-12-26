package com.fintech.modules.platform.testtable1.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.testtable1.dao.TestTable1Dao;
import com.fintech.modules.platform.testtable1.dto.SysDataPrvDTO;
import com.fintech.modules.platform.testtable1.dto.TestTable1DTO;
import com.fintech.platform.api.dataprv.DataPrvAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: TestTable1Service
 * @description: 定义 测试表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.testtable1.service.TestTable1Service")
public class TestTable1Service implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TestTable1Dao dao;

	// 数据权限API注入
	@Autowired
	private DataPrvAPI dApi;

	/**
	 * @author
	 * @description: 分页查询 测试表列表
	 * @date 2014-10-25 15:06:44
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<TestTable1DTO> searchTestTable1ByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<TestTable1DTO> dataList = dao
				.searchTestTable1ByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询测试表列表
	 * @date 2014-10-25 15:06:44
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<TestTable1DTO> searchTestTable1(Map<String, Object> searchParams)
			throws Exception {
		List<TestTable1DTO> dataList = dao.searchTestTable1(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询测试表对象
	 * @date 2014-10-25 15:06:44
	 * @param id
	 * @return
	 * @throws
	 */
	public TestTable1DTO queryTestTable1ByPrimaryKey(String id)
			throws Exception {

		TestTable1DTO dto = dao.findTestTable1ByPrimaryKey(id);

		if (dto == null)
			dto = new TestTable1DTO();

		return dto;

	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:查询用户权限集合
	 * 
	 */
	public List<SysDataPrvDTO> searchUserPrvs(String id) throws Exception{
	        
	    List<SysDataPrvDTO> dataPrvDTOs = dao.searchUserPrvs(id);
	        
     return dataPrvDTOs;
	}
	

	/**
	 * @title: insertTestTable1
	 * @author
	 * @description: 新增 测试表对象
	 * @date 2014-10-25 15:06:44
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertTestTable1(TestTable1DTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertTestTable1(paramMap);

		TestTable1DTO resultDto = (TestTable1DTO) paramMap.get("dto");
		Long keyId = resultDto.getId();

		// 数据权限表插入权限数据,插入用户业务数据资源表，需要传入的内容是
		Map<String, Object> insertMap = new HashMap<String, Object>();

		// 1、业务数据所属用户ID userId
		insertMap.put("userId", dto.getOpUserId());
		// 2、用户所属组织机构ID orgId ???是否可以在实现里面通过OrgApi取？？
		insertMap.put("orgId", dto.getUserOrgId());
		// 3、业务数据的资源表名称 tableName
		insertMap.put("tableName", "TEST_TABLE1");
		// 4、业务数据的资源ID bizId
		insertMap.put("bizId", keyId);
		// 调用接口插入数据权限表
		dApi.insertDataPrv(insertMap);
		return keyId;
	}

	/**
	 * @title: updateTestTable1
	 * @author
	 * @description: 修改 测试表对象
	 * @date 2014-10-25 15:06:44
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateTestTable1(TestTable1DTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateTestTable1(paramMap);
		paramMap.put("bizIds", dto.getId());
		paramMap.put("toUserId", "44");
		paramMap.put("tableName", "TEST_TABLE1");
		dApi.updateDataUser(paramMap);
	}

	/**
	 * @title: deleteTestTable1ByPrimaryKey
	 * @author
	 * @description: 删除 测试表,按主键
	 * @date 2014-10-25 15:06:44
	 * @param paramMap
	 * @throws
	 */
	public void deleteTestTable1ByPrimaryKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteTestTable1ByPrimaryKey(paramMap);
		// 删除用户业务数据资源表，要传入的内容是
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		// 1、业务数据资源表的名称 tableName
		deleteMap.put("tableName", "TEST_TABLE1");
		// 2、要删除的业务IDS bizIds 多条的格式为 id1,id2,id3
		deleteMap.put("bizIds", ids);
		dApi.deleteDataPrv(deleteMap);
	}

}
