package com.fintech.modules.platform.sysdict.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysdict.dto.SysDictDetailDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: SysDictDetailDao
 * @description: 定义 数据字典明细表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口,
 *               动态在Spring Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysDictDetailDao {

	/**
	 * @author
	 * @description: 分页查询数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 * @return
	 */
	public List<SysDictDetailDTO> searchSysDictDetailByPaging(
			Map<String, Object> searchParams);

	/**
	 * @author
	 * @description:查询对象数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param searchParams
	 * @return
	 */
	public List<SysDictDetailDTO> searchSysDictDetail(
			Map<String, Object> searchParams);

	/**
	 * @author
	 * @description:查询对象数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param id
	 * @return
	 */
	public SysDictDetailDTO findSysDictDetailByPrimaryKey(String id);

	/**
	 * @author
	 * @description: 新增对象数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param paramMap
	 * @return
	 */
	public int insertSysDictDetail(Map<String, Object> paramMap);

	/**
	 * @author
	 * @description: 更新对象数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param paramMap
	 */
	public void updateSysDictDetail(Map<String, Object> paramMap);

	/**
	 * @author
	 * @description: 按主键删除数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param ids
	 * @return
	 */
	public void deleteSysDictDetailByPrimaryKey(Map<String, Object> paramMap);

	/**
	 * @author
	 * @description: 按字典表主键删除数据字典明细表
	 * @date 2014-10-15 10:28:43
	 * @param ids
	 * @return
	 */
	public void deleteSysDictDetailByDictKey(Map<String, Object> paramMap);

	/**
	 * 获取最大的排序值
	 * @param dictId
	 * @return
	 */
	public String findSysDictMaxOrderBy(String dictId);
	
	/**
	 * 验证code是否存在
	 * @param code
	 * @return
	 */
	public String queryDetailCodeIsOk(Map<String,Object> searchParams);

	
	public List<Map> queryDetailByDictCode(Map<String,String> searchParams);
	
	
	/**
	 * 通过字典编码和字典明细获得明细数据
	 * @param searchParams
	 * @return
	 */
	public SysDictDetailDTO queryDetailByDictCodeAndDeatailValue(Map<String,Object> searchParams);
}
