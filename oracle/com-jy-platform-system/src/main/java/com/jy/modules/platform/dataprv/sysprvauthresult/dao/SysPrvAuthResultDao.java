package com.jy.modules.platform.dataprv.sysprvauthresult.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.dataprv.sysprvauthresult.dto.SysPrvAuthResultDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: SysPrvAuthResultDao
 * @description: 定义 数据权限授权结果表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口,
 *               动态在Spring Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author: wangxz
 */
@MyBatisRepository
public interface SysPrvAuthResultDao {

	/**
	 * @author wangxz
	 * @description: 分页查询数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param searchParams
	 * @return
	 */
	public List<SysPrvAuthResultDTO> searchSysPrvAuthResultByPaging(
			Map<String, Object> searchParams);

	/**
	 * @author wangxz
	 * @description:查询对象数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param searchParams
	 * @return
	 */
	public List<SysPrvAuthResultDTO> searchSysPrvAuthResult(
			Map<String, Object> searchParams);

	/**
	 * @author wangxz
	 * @description:查询对象数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param id
	 * @return
	 */
	public SysPrvAuthResultDTO findSysPrvAuthResultByPrimaryKey(String id);
	/**
	 * @author wangxz
	 * @description:查询对象数据权限授权结果表根据userFrom和userTo
	 * @date 2014-10-18 16:06:51
	 * @param id
	 * @return
	 */
	public List<SysPrvAuthResultDTO> findSysPrvAuthResultByUser(List<SysPrvAuthResultDTO> list);
	
	/**
	 * 整体更新时删除所有数据
	 */
	public void deleteAllSysPrvAuthResult();
	
	/**
	 * 跟据userFrom和userTo修改insertFrom的值
	 * @param dto
	 */
	public void updateSysPrvAuthResultType(SysPrvAuthResultDTO dto);

	/**
	 * @author wangxz
	 * @description: 新增对象数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param paramMap
	 * @return
	 */
	public int insertSysPrvAuthResult(Map<String, Object> paramMap);
	
	public int insertSysPrvAuthResultMy(SysPrvAuthResultDTO dto);

	/**
	 * @author wangxz
	 * @description: 更新对象数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param paramMap
	 */
	public void updateSysPrvAuthResult(Map<String, Object> paramMap);

	/**
	 * @author wangxz
	 * @description: 按主键删除数据权限授权结果表
	 * @date 2014-10-18 16:06:51
	 * @param ids
	 * @return
	 */
	public void deleteSysPrvAuthResultByPrimaryKey(Map<String, Object> paramMap);

	/**
	 * 根据fromUser,toUser删除记录
	 * 
	 * @param paramMap
	 */
	public void deleteSysPrvAuthResultByUser(Map<String, Object> paramMap);
	
	public void deleteSysPrvAuthResultByUserBatch(List<SysPrvAuthResultDTO> deleteList);

	/**
	 * 查找增量数据权限用户-->用户
	 */

	public List<Map> searchUserPrvMappingByAction(Map<String, Object> paramMap);

	/**
	 * 查找增量数据权限用户-->组织机构
	 */

	public List<Map> searchOrgPrvMappingByAction(Map<String, Object> paramMap);

	/**
	 * 批量插入
	 */
	public void insertSysPrvAuthResultBatch(List<SysPrvAuthResultDTO> dataList);
}
