package com.fintech.modules.platform.testtable1.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.testtable1.dto.SysDataPrvDTO;
import com.fintech.modules.platform.testtable1.dto.TestTable1DTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: TestTable1Dao
 * @description: 定义  测试表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface TestTable1Dao {
    
    /**
     * @author
     * @description: 分页查询测试表
     * @date 2014-10-25 15:06:45
     * @param searchParams
     * @return
     */
    public List<TestTable1DTO> searchTestTable1ByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象测试表
     * @date 2014-10-25 15:06:45
     * @param searchParams
     * @return
     */
    public List<TestTable1DTO> searchTestTable1(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象测试表
     * @date 2014-10-25 15:06:45
     * @param id
     * @return
     */
    public TestTable1DTO findTestTable1ByPrimaryKey(String id);
    
    /**
     * 
     * @param id
     * @return
     * @Description:查询用户权限集合
     */
    public List<SysDataPrvDTO> searchUserPrvs(String id); 
    
    /**
     * @author
     * @description: 新增对象测试表
     * @date 2014-10-25 15:06:45
     * @param paramMap
     * @return
     */
    public int insertTestTable1(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象测试表
     * @date 2014-10-25 15:06:45
     * @param paramMap
     */
    public void updateTestTable1(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除测试表
     * @date 2014-10-25 15:06:45
     * @param ids
     * @return
     */ 
    public void deleteTestTable1ByPrimaryKey(Map<String, Object> paramMap);
    
    
}
