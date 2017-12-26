package com.fintech.modules.platform.sysconfig.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: SysConfigDao
 * @description: 定义 系统配置表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring
 *               Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysConfigDao {

	/**
	 * @author
	 * @description: 分页查询系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param searchParams
	 * @return
	 */
	public List<SysConfigDTO> searchSysConfigByPaging(
			Map<String, Object> searchParams);

	/**
	 * @author
	 * @description:查询对象系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param searchParams
	 * @return
	 */
	public List<SysConfigDTO> searchSysConfig(Map<String, Object> searchParams);

	/**
	 * @author
	 * @description:查询对象系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param id
	 * @return
	 */
	public SysConfigDTO findSysConfigByPrimaryKey(String id);

	/**
	 * @author
	 * @description: 新增对象系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param paramMap
	 * @return
	 */
	public int insertSysConfig(Map<String, Object> paramMap);

	/**
	 * @author
	 * @description: 更新对象系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param paramMap
	 */
	public void updateSysConfig(Map<String, Object> paramMap);

	/**
	 * @author
	 * @description: 按主键删除系统配置表
	 * @date 2014-10-15 10:27:46
	 * @param ids
	 * @return
	 */
	public void deleteSysConfigByPrimaryKey(Map<String, Object> paramMap);

	/**
	 * 唯一性验证
	 * 
	 * @param code
	 * @return
	 */
	public String queryConfigCodeIsOk(String code);

	public Map<String, Object> queryValueByCode(String code);
	/**
	 * 同一session无缓存,即时查询
	 * @param code
	 * @return
	 */
	public Map<String, Object> realGainValueByCode(String code);
	
	/**
	 * @author
	 * @description: (贷后操作日终的开/关机、修改业务区时间)修改 系统配置表对象（根据业务编码修改对应数据的状态）
	 * @date 2014-12-02 20:27:46
	 * @param paramMap
	 */
	public void updateSysConfigForAftloan(Map<String, Object> paramMap);
}
